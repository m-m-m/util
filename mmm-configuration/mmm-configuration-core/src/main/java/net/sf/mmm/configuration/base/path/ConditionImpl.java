/* $Id$ */
package net.sf.mmm.configuration.base.path;

import java.util.Iterator;
import java.util.regex.Pattern;

import net.sf.mmm.configuration.base.AbstractConfiguration;
import net.sf.mmm.util.StringUtil;
import net.sf.mmm.value.api.GenericValue;

/**
 * This is the implementation of the {@link Condition} interface.<br>
 * An example for a condition is <code>[foo/bar/@attr=value]</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ConditionImpl implements Condition {

  /** the segments */
  private final SimplePathSegment[] segments;

  /** @see #getValue() */
  private final String value;

  /** the value as pattern or <code>null</code> if name does not matter. */
  private final Pattern valuePattern;

  /**
   * the pattern the name must match or <code>null</code> if name does not
   * matter.
   */
  private final Comparator comparator;

  /**
   * The constructor.
   * 
   * @param pathSegments
   *        are the segments specifying the path to the descendant matched by
   *        this condition.
   */
  public ConditionImpl(SimplePathSegment[] pathSegments) {

    super();
    this.segments = pathSegments;
    this.value = null;
    this.comparator = null;
    this.valuePattern = null;
  }

  /**
   * The constructor.
   * 
   * @param pathSegments
   *        are the segments specifying the path to the descendant matched by
   *        this condition.
   * @param descendantValue
   *        is the {@link #getValue() value} to compare the descendant with.
   * @param cmp
   *        is the comparator defining how to compare the descendant with the
   *        value.
   */
  public ConditionImpl(SimplePathSegment[] pathSegments, String descendantValue, Comparator cmp) {

    super();
    this.segments = pathSegments;
    this.value = descendantValue;
    this.comparator = cmp;
    this.valuePattern = StringUtil.compileGlobPattern(descendantValue, true);
  }

  /**
   * This method recursively implements the accept method.
   * 
   * @param configuration
   *        is the current configuration node.
   * @param segmentIndex
   *        is the current index in the segment-list.
   * @return <code>true</code> if there is at least one path that matches.
   */
  private boolean accept(AbstractConfiguration configuration, int segmentIndex) {

    SimplePathSegment segment = this.segments[segmentIndex];
    Iterator<AbstractConfiguration> childIterator;
    if (segment.isPattern()) {
      childIterator = configuration.getChildren(segment.getPattern());
    } else {
      childIterator = configuration.getChildren(segment.getString());
    }
    int nextIndex = segmentIndex + 1;
    while (childIterator.hasNext()) {
      AbstractConfiguration child = childIterator.next();
      if (nextIndex < this.segments.length) {
        boolean acceptChild = accept(child, nextIndex);
        if (acceptChild) {
          return true;
        }
      } else {
        GenericValue childValue = child.getValue();
        if (childValue.isEmpty()) {
          return false;
        }
        if (this.comparator == null) {
          return true;
        } else {
          return this.comparator.accept(child.getValue(), this.value, this.valuePattern);
        }
      }
    }
    return false;
  }

  /**
   * @see net.sf.mmm.configuration.base.path.Condition#accept(net.sf.mmm.configuration.base.AbstractConfiguration)
   */
  public boolean accept(AbstractConfiguration configuration) {

    return accept(configuration, 0);
  }

  /**
   * This method gets the value to compare with the the
   * {@link AbstractConfiguration#getChild(String) childrens}
   * {@link net.sf.mmm.configuration.api.Configuration#getValue() value}.
   * 
   * @return the childs value or <code>null</code> if only the existence of a
   *         specific child is checked.
   */
  public String getValue() {

    return this.value;
  }

  /**
   * The comparator used to compare the matching children in relation to
   * {@link #getValue() child-value}.
   * 
   * @return the comparator.
   */
  public Comparator getComparator() {

    return this.comparator;
  }

}
