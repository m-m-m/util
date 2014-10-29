/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter.base;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.filter.api.FilterRule;

/**
 * This class implements a {@link Filter} that is based on a chain of {@link FilterRule}s.
 * 
 * @param <V> is the generic type of the value to check.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FilterRuleChain<V> implements Filter<V> {

  /** @see #getId() */
  @XmlID
  @XmlAttribute(name = "id")
  private String id;

  /** The parent that is extended by this chain or <code>null</code>. */
  @XmlIDREF
  @XmlAttribute(name = "parent")
  private FilterRuleChain<V> parent;

  /** the rules */
  @XmlElement(name = "rule", type = PatternFilterRule.class)
  private FilterRule<V>[] rules;

  /** @see #getDefaultResult() */
  @XmlAttribute(name = "default-result", required = false)
  private boolean defaultResult;

  /**
   * The non-arg constructor. <br>
   * <b>NOTE:</b><br>
   * This constructor should not be called directly! It is only intended for reflective access (e.g. for
   * JAXB).
   */
  public FilterRuleChain() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param defaultResult is the {@link #accept(Object) result} if none of the <code>rules</code> match.
   * @param rules is the chain of rules.
   */
  @SafeVarargs
  public FilterRuleChain(boolean defaultResult, FilterRule<V>... rules) {

    super();
    this.rules = rules;
    this.defaultResult = defaultResult;
  }

  /**
   * The constructor.
   * 
   * @param id is the {@link #getId() ID}.
   * @param parent is the parent-{@link FilterRuleChain chain} to extend or <code>null</code> for a
   *        root-chain.
   * @param rules is the chain of rules.
   * @param defaultResult is the {@link #accept(Object) result} if none of the <code>rules</code> match.
   */
  @SafeVarargs
  public FilterRuleChain(String id, FilterRuleChain<V> parent, boolean defaultResult, FilterRule<V>... rules) {

    super();
    this.id = id;
    this.parent = parent;
    this.rules = rules;
    this.defaultResult = defaultResult;
  }

  /**
   * This method gets the default {@link #accept(Object) result} used if none of the rules matched.
   * 
   * @return the default result.
   */
  public boolean getDefaultResult() {

    return this.defaultResult;
  }

  /**
   * {@inheritDoc}
   * 
   * This method checks all rules in the chain and returns the result of the first matching rule. If no rule
   * matches, <code>{@link #getDefaultResult()}</code> is returned.
   */
  @Override
  public boolean accept(V value) {

    Boolean result = acceptRecursive(value);
    if (result == null) {
      return this.defaultResult;
    } else {
      return result.booleanValue();
    }
  }

  /**
   * This method implements {@link #accept(Object)} recursively.
   * 
   * @param value is the value to filter.
   * @return <code>true</code> if the value is accepted, <code>false</code> if the value is NOT accepted, or
   *         <code>null</code> if no decision is made.
   */
  private Boolean acceptRecursive(V value) {

    Boolean result = null;
    if (this.parent != null) {
      result = this.parent.acceptRecursive(value);
      if (result != null) {
        return result;
      }
    }
    for (FilterRule<V> rule : this.rules) {
      result = rule.accept(value);
      if (result != null) {
        return result;
      }
    }
    return result;
  }

  /**
   * This method extends this chain with <code>additionalRules</code>.
   * 
   * @param newDefaultResult is the result of the new extended chain if none of the rules match.
   * @param additionalRules are the rules to add.
   * @return the chain that also checks the <code>additionalRules</code> if none of this rules match.
   */
  @SuppressWarnings("unchecked")
  public FilterRuleChain<V> extend(boolean newDefaultResult, FilterRule<V>... additionalRules) {

    FilterRule<V>[] newRules = new FilterRule[this.rules.length + additionalRules.length];
    System.arraycopy(this.rules, 0, newRules, 0, this.rules.length);
    System.arraycopy(additionalRules, 0, newRules, this.rules.length, additionalRules.length);
    return new FilterRuleChain<V>(newDefaultResult, newRules);
  }

  /**
   * This method gets the ID used to identify this chain.
   * 
   * @return the ID or <code>null</code> if undefined.
   */
  public String getId() {

    return this.id;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    int len = 0;
    if (this.rules != null) {
      len = this.rules.length;
    }
    return FilterRuleChain.class.getSimpleName() + "[" + this.id + "][" + len + "]";
  }

}
