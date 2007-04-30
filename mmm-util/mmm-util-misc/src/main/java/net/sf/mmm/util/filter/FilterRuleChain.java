/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter;

/**
 * This class is a {@link Filter}&lt;String&gt; implementation that is based on
 * a chain of {@link FilterRule}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FilterRuleChain implements Filter<String> {

  /** the rules */
  private final FilterRule[] rules;

  /**
   * The constructor.
   * 
   * @param rules
   *        is the chain of rules.
   */
  public FilterRuleChain(FilterRule[] rules) {

    super();
    this.rules = rules;
  }

  /**
   * {@inheritDoc}
   * 
   * This method checks all rules in the chain and returns the result of the
   * first matching rule. If no rule matches, <code>false</code> is returned.
   * To prevent this, add a rule that always returns <code>true</code> to the
   * end of the chain.
   */
  public boolean accept(String string) {

    for (FilterRule rule : this.rules) {
      Boolean result = rule.accept(string);
      if (result != null) {
        return result.booleanValue();
      }
    }
    return false;
  }

}
