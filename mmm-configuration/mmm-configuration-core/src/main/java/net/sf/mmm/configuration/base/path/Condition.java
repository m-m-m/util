/* $Id$ */
package net.sf.mmm.configuration.base.path;

import java.util.regex.Pattern;

import net.sf.mmm.configuration.base.AbstractConfiguration;
import net.sf.mmm.value.api.GenericValue;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.configuration.api.Configuration} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class Condition implements ConditionIF {

  /**
   * the pattern the name must match or <code>null</code> if name does not
   * matter.
   */
  private final Pattern namePattern;

  /** the name of the child to check or <code>null</code> no child to check */
  private final String childName;

  /** the value of the child */
  private final String childValue;

  /**
   * the pattern the name must match or <code>null</code> if name does not
   * matter.
   */
  private final Pattern valuePattern;

  /**
   * the pattern the name must match or <code>null</code> if name does not
   * matter.
   */
  private final ComparatorIF comparator;

  /**
   * The constructor.
   * 
   * @param nameGlobPattern
   *        is the
   *        {@link net.sf.mmm.util.StringUtil#compileGlobPattern(String) glob-pattern}
   *        the
   *        {@link net.sf.mmm.configuration.api.Configuration#getName() name}
   *        must match or <code>null</code> if any name is acceptable.
   */
  public Condition(Pattern nameGlobPattern) {

    this(nameGlobPattern, null, null, null, null);
  }

  /**
   * The constructor.
   * 
   * @param nameGlobPattern
   *        is the
   *        {@link net.sf.mmm.util.StringUtil#compileGlobPattern(String) glob-pattern}
   *        the
   *        {@link net.sf.mmm.configuration.api.Configuration#getName() name}
   *        must match or <code>null</code> if any name is acceptable.
   * @param childsName
   * @param childsValue
   * @param childsValuePattern
   * @param cmp
   */
  public Condition(Pattern nameGlobPattern, String childsName, String childsValue,
      Pattern childsValuePattern, ComparatorIF cmp) {

    super();
    this.namePattern = nameGlobPattern;
    this.childName = childsName;
    this.childValue = childsValue;
    this.comparator = cmp;
    this.valuePattern = childsValuePattern;
  }

  /**
   * @see net.sf.mmm.configuration.base.path.ConditionIF#accept(net.sf.mmm.configuration.base.AbstractConfiguration)
   */
  public boolean accept(AbstractConfiguration configuration) {

    if (this.namePattern != null) {
      if (!this.namePattern.matcher(configuration.getName()).matches()) {
        return false;
      }
    }
    if (this.childName == null) {
      return true;
    } else {
      AbstractConfiguration child = configuration.getChild(this.childName);
      if (child == null) {
        return false;
      }
      GenericValue value = child.getValue();
      if (value.isEmpty()) {
        return false;
      }
      return this.comparator.accept(value, this.childValue, this.valuePattern);
    }
  }

  /**
   * This method gets the
   * {@link net.sf.mmm.configuration.api.Configuration#getName() name} of the
   * {@link AbstractConfiguration#getChild(String) child} to check.
   * 
   * @return the child name.
   */
  public String getChildName() {

    return this.childName;
  }

  /**
   * This method gets the value to compare with the the
   * {@link AbstractConfiguration#getChild(String) childs}
   * {@link net.sf.mmm.configuration.api.Configuration#getValue() value}.
   * 
   * @return the childs value.
   */
  public String getChildValue() {

    return this.childValue;
  }

}
