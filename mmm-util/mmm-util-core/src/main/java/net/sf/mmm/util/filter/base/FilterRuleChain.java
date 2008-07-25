/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter.base;

import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.filter.api.FilterRule;


/**
 * This class implements a {@link Filter} that is based on a chain of
 * {@link FilterRule}s.
 * 
 * @param <V> is the generic type of the value to check.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FilterRuleChain<V> implements Filter<V> {

  /** the rules */
  private final FilterRule<V>[] rules;

  /** @see #getDefaultResult() */
  private final boolean defaultResult;

  /**
   * The constructor.
   * 
   * @param rules is the chain of rules.
   * @param defaultResult is the {@link #accept(Object) result} if none of the
   *        <code>rules</code> match.
   */
  public FilterRuleChain(boolean defaultResult, FilterRule<V>... rules) {

    super();
    this.rules = rules;
    this.defaultResult = defaultResult;
  }

  /**
   * This method gets the default {@link #accept(Object) result} used if none of
   * the rules matched.
   * 
   * @return the default result.
   */
  public boolean getDefaultResult() {

    return this.defaultResult;
  }

  /**
   * {@inheritDoc}
   * 
   * This method checks all rules in the chain and returns the result of the
   * first matching rule. If no rule matches,
   * <code>{@link #getDefaultResult()}</code> is returned.
   */
  public boolean accept(V string) {

    for (FilterRule<V> rule : this.rules) {
      Boolean result = rule.accept(string);
      if (result != null) {
        return result.booleanValue();
      }
    }
    return this.defaultResult;
  }

  /**
   * This method extends this chain with <code>additionalRules</code>.
   * 
   * @param newDefaultResult is the result of the new extended chain if none of
   *        the rules match.
   * @param additionalRules are the rules to add.
   * @return the chain that also checks the <code>additionalRules</code> if none
   *         of this rules match.
   */
  @SuppressWarnings("unchecked")
  public FilterRuleChain<V> extend(boolean newDefaultResult, FilterRule<V>... additionalRules) {

    FilterRule<V>[] newRules = new FilterRule[this.rules.length + additionalRules.length];
    System.arraycopy(this.rules, 0, newRules, 0, this.rules.length);
    System.arraycopy(additionalRules, 0, newRules, this.rules.length, additionalRules.length);
    return new FilterRuleChain<V>(newDefaultResult, newRules);
  }
}
