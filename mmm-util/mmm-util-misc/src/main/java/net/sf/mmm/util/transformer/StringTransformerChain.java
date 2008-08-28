/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transformer;

import net.sf.mmm.util.transformer.api.Transformer;

/**
 * This class represents a {@link Transformer} for {@link String}s that is
 * build out of a list of {@link StringTransformerRule rules}. It performs its
 * {@link #transform(String) transformation} by passing the given value to the
 * first rule and its result to the next rule and so on. If a rule matched, it
 * can stop further proceeding via the
 * {@link StringTransformerRule#isStopOnMatch() stop-on-match} flag and cause
 * its result to be returned immediately. Otherwise the result of the last rule
 * in the chain is returned (like a left associative operator).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class StringTransformerChain implements Transformer<String> {

  /** The rules of this chain. */
  private StringTransformerRule[] rules;

  /**
   * The constructor.
   * 
   * @param rules are the rules of this chain.
   */
  public StringTransformerChain(StringTransformerRule[] rules) {

    super();
    this.rules = rules;
  }

  /**
   * {@inheritDoc}
   */
  public String transform(String original) {

    String value = original;
    for (StringTransformerRule rule : this.rules) {
      String transformed = rule.transform(value);
      if ((transformed != value) && (rule.isStopOnMatch())) {
        return transformed;
      }
      value = transformed;
    }
    return value;
  }

  /**
   * This method extends this chain with <code>additionalRules</code>.
   * 
   * @param additionalRules are the rules to add.
   * @return the chain that also checks the <code>additionalRules</code> if
   *         none of this rules match.
   */
  public StringTransformerChain extend(StringTransformerRule... additionalRules) {

    StringTransformerRule[] newRules = new StringTransformerRule[this.rules.length
        + additionalRules.length];
    System.arraycopy(this.rules, 0, newRules, 0, this.rules.length);
    System.arraycopy(additionalRules, 0, newRules, this.rules.length, additionalRules.length);
    return new StringTransformerChain(newRules);

  }

}
