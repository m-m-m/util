/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.text;

import net.sf.mmm.util.StringUtil;

/**
 * This is a simple implementation of the {@link SingularizerRule}. If the
 * plural form ends with a given suffix, that suffix is replaced by a suffix for
 * the singular form.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SimpleSingularizerRule extends SingularizerRule {

  /** @see SimpleSingularizerRule#SimpleSingularizerRule(String, String) */
  private final String pluralSuffix;

  /** @see SimpleSingularizerRule#SimpleSingularizerRule(String, String) */
  private final String singularSuffix;

  /**
   * The constructor.
   * 
   * @param pluralSuffix is the suffix of the plural form. If a plural term ends
   *        with that suffix, this rule will apply and replace it with the given
   *        <code>singularSuffix</code>.
   * @param singularSuffix is the suffix of the singular form. It is the
   *        replacement for <code>pluralSuffix</code>. Use the empty string
   *        if you just want to remove the <code>pluralSuffix</code>.
   */
  public SimpleSingularizerRule(String pluralSuffix, String singularSuffix) {

    super();
    this.pluralSuffix = pluralSuffix;
    this.singularSuffix = singularSuffix;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toSingular(String plural, String pluralLowerCase) {

    if (pluralLowerCase.endsWith(this.pluralSuffix)) {
      return StringUtil.INSTANCE.replaceSuffixWithCase(plural, this.pluralSuffix.length(),
          this.singularSuffix);
    }
    return null;
  }
}
