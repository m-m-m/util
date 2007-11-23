/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text;

import java.util.List;

/**
 * This is the abstract base implementation of the {@link Singularizer}
 * interface. It aims for simplicity rather than linguistic perfection.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractSingularizer implements Singularizer {

  /**
   * The constructor.
   */
  public AbstractSingularizer() {

    super();
  }

  /**
   * This method gets the list of rules. These rules are processed in the order
   * of occurrence. If a rule matches (does NOT
   * {@link SingularizerRule#toSingular(String, String) return}
   * <code>null</code>), that result is taken as singular form and no further
   * rule is invoked.
   * 
   * @return the rules.
   */
  protected abstract List<SingularizerRule> getRules();

  /**
   * {@inheritDoc}
   */
  public String toSingular(String plural) {

    if ((plural == null) || (plural.length() == 0)) {
      return plural;
    }
    String pluralLower = plural.toLowerCase();
    for (SingularizerRule rule : getRules()) {
      String singular = rule.toSingular(plural, pluralLower);
      if (singular != null) {
        return singular;
      }
    }
    return plural;
  }

}
