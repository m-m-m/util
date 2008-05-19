/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter;

import net.sf.mmm.util.filter.api.Filter;

/**
 * This class is a {@link Filter}&lt;String&gt; implementation that is based on
 * a chain of {@link FilterRule}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FilterRuleChain implements Filter<String> {

  /** the rules */
  private final FilterRule[] rules;

  /** @see #getDefaultResult() */
  private final boolean defaultResult;

  /**
   * The constructor.
   * 
   * @param rules is the chain of rules.
   * @param defaultResult is the {@link #accept(String) result} if none of the
   *        <code>rules</code> match.
   */
  public FilterRuleChain(boolean defaultResult, FilterRule... rules) {

    super();
    this.rules = rules;
    this.defaultResult = defaultResult;
  }

  /**
   * This method gets the default {@link #accept(String) result} used if none of
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
  public boolean accept(String string) {

    for (FilterRule rule : this.rules) {
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
   * @return the chain that also checks the <code>additionalRules</code> if
   *         none of this rules match.
   */
  public FilterRuleChain extend(boolean newDefaultResult, FilterRule... additionalRules) {

    FilterRule[] newRules = new FilterRule[this.rules.length + additionalRules.length];
    System.arraycopy(this.rules, 0, newRules, 0, this.rules.length);
    System.arraycopy(additionalRules, 0, newRules, this.rules.length, additionalRules.length);
    return new FilterRuleChain(newDefaultResult, newRules);
  }
}
