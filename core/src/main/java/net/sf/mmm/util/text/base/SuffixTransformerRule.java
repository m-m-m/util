/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text.base;

import net.sf.mmm.util.lang.base.StringUtilImpl;

/**
 * This is a simple implementation of the {@link TransformerRule}. If the given string ends with a specific
 * suffix, that suffix is replaced by a suffix for the result form.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SuffixTransformerRule extends TransformerRule {

  /** @see SuffixTransformerRule#SuffixTransformerRule(String, String) */
  private final String sourceSuffix;

  /** @see SuffixTransformerRule#SuffixTransformerRule(String, String) */
  private final String destinationSuffix;

  /**
   * The constructor.
   * 
   * @param sourceSuffix is the suffix of the plural form. If a plural term ends with that suffix, this rule
   *        will apply and replace it with the given {@code singularSuffix}.
   * @param destinationSuffix is the suffix of the singular form. It is the replacement for
   *        {@code pluralSuffix}. Use the empty string if you just want to remove the
   *        {@code pluralSuffix}.
   */
  public SuffixTransformerRule(String sourceSuffix, String destinationSuffix) {

    super();
    this.sourceSuffix = sourceSuffix;
    this.destinationSuffix = destinationSuffix;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String transform(String string, String stringLowerCase) {

    if (stringLowerCase.endsWith(this.sourceSuffix)) {
      return StringUtilImpl.getInstance().replaceSuffixWithCase(string, this.sourceSuffix.length(),
          this.destinationSuffix);
    }
    return null;
  }
}
