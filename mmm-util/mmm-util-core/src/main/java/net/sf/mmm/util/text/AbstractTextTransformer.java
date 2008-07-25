/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text;

import java.util.List;

import net.sf.mmm.util.text.api.Singularizer;
import net.sf.mmm.util.transformer.Transformer;

/**
 * This is the abstract base implementation of the {@link Singularizer}
 * interface. It aims for simplicity rather than linguistic perfection.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractTextTransformer implements Transformer<String> {

  /**
   * The constructor.
   */
  public AbstractTextTransformer() {

    super();
  }

  /**
   * This method gets the list of rules. These rules are processed in the order
   * of occurrence. If a rule matches (does NOT
   * {@link TransformerRule#transform(String, String) return} <code>null</code>),
   * that result is taken as singular form and no further rule is invoked.
   * 
   * @return the rules.
   */
  protected abstract List<TransformerRule> getRules();

  /**
   * {@inheritDoc}
   * 
   * This implementation processes the {@link #getRules() rules} in the order of
   * occurrence. If a rule matches (does NOT
   * {@link TransformerRule#transform(String, String) return} <code>null</code>),
   * that result is taken as result and no further rule is invoked.
   */
  public String transform(String string) {

    if ((string == null) || (string.length() == 0)) {
      return string;
    }
    String pluralLower = string.toLowerCase();
    for (TransformerRule rule : getRules()) {
      String singular = rule.transform(string, pluralLower);
      if (singular != null) {
        return singular;
      }
    }
    return string;
  }

}
