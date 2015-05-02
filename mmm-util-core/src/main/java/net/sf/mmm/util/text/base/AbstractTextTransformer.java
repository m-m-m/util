/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import java.util.List;

import net.sf.mmm.util.transformer.api.Transformer;

/**
 * This is the abstract base implementation of a {@link Transformer} for strings.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractTextTransformer implements Transformer<String> {

  /**
   * The constructor.
   */
  public AbstractTextTransformer() {

    super();
  }

  /**
   * This method gets the list of rules. These rules are processed in the order of occurrence. If a rule
   * matches (does NOT {@link TransformerRule#transform(String, String) return} <code>null</code> ), that
   * result is taken as singular form and no further rule is invoked.
   * 
   * @return the rules.
   */
  protected abstract List<TransformerRule> getRules();

  /**
   * {@inheritDoc}
   * 
   * This implementation processes the {@link #getRules() rules} in the order of occurrence. If a rule matches
   * (does NOT {@link TransformerRule#transform(String, String) return} <code>null</code> ), that result is
   * taken as result and no further rule is invoked.
   */
  public String transform(String string) {

    if ((string == null) || (string.length() == 0)) {
      return string;
    }
    String lower = string.toLowerCase();
    for (TransformerRule rule : getRules()) {
      String singular = rule.transform(string, lower);
      if (singular != null) {
        return singular;
      }
    }
    return string;
  }

}
