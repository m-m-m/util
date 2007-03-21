/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base.path.condition;

import java.util.regex.Pattern;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.base.AbstractConfiguration;
import net.sf.mmm.configuration.base.path.SimplePathSegment;
import net.sf.mmm.configuration.base.path.comparator.Comparator;
import net.sf.mmm.configuration.base.path.comparator.EqualsComparator;
import net.sf.mmm.util.StringUtil;
import net.sf.mmm.value.api.GenericValue;

/**
 * This is an implementation of the {@link Condition} interface that compares a
 * descendent specified by some path with a given value using a specific
 * comparator.<br>
 * An example for such condition is <code>[foo/bar/@attr='value']</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class CompareCondition extends PathCondition {

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
   * The constructor
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
  public CompareCondition(SimplePathSegment[] pathSegments, String descendantValue, Comparator cmp) {

    super(pathSegments);
    this.value = descendantValue;
    this.valuePattern = StringUtil.compileGlobPattern(this.value, true);
    this.comparator = cmp;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean acceptDescendant(AbstractConfiguration descendant) {

    if (!super.acceptDescendant(descendant)) {
      return false;
    }
    GenericValue childValue = descendant.getValue();
    if (childValue.isEmpty()) {
      return false;
    }
    return this.comparator.accept(childValue, this.value, this.valuePattern);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canBeEstablished(AbstractConfiguration configuration, String namespaceUri) {
  
    AbstractConfiguration node = configuration;
    for (SimplePathSegment segment : getSegments()) {
      node = node.getChild(segment.getString(), namespaceUri);
      if (node == null) {
        return true;
      }
    }
    GenericValue genericValue = node.getValue();
    if (genericValue.isEmpty()) {
      return true;
    }
    return this.comparator.accept(genericValue, this.value, this.valuePattern);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public AbstractConfiguration establish(AbstractConfiguration configuration, String namespaceUri) {

    if (isValuePattern()) {
      // TODO: NLS
      throw new ConfigurationException("Cannot establish condition with wildcards!");
    }
    if (this.comparator.getSymbol() != EqualsComparator.SYMBOL) {
      // TODO: NLS
      throw new ConfigurationException(
          "Can only establish condition for condition comparator equals (=)!");
    }
    return super.establish(configuration, namespaceUri);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doEstablish(AbstractConfiguration node) {

    super.doEstablish(node);
    assert node.isAddDefaults();
    GenericValue genericValue = node.getValue();
    assert genericValue.isEmpty();
    node.getValue().setString(this.value);
  }

  /**
   * This method gets the value to compare with the the
   * {@link AbstractConfiguration#getChild(String) children}
   * {@link net.sf.mmm.configuration.api.Configuration#getValue() value}.
   * 
   * @return the value of the child or <code>null</code> if only the existence
   *         of a specific child is checked.
   */
  public String getValue() {

    return this.value;
  }

  /**
   * This method determines if the {@link #getValue() value} is a
   * {@link net.sf.mmm.util.StringUtil#compileGlobPattern(String) glob-pattern}.
   * 
   * @return <code>true</code> if the {@link #getValue() value} is a pattern
   *         and <code>false</code> if it is a regular value.
   */
  public boolean isValuePattern() {

    return (this.valuePattern != null);
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

  /**
   * {@inheritDoc}
   */
  @Override
  protected void appendCondition(StringBuffer buffer) {

    super.appendCondition(buffer);
    buffer.append(this.comparator.getSymbol());
    buffer.append(this.value.toString());
  }

}
