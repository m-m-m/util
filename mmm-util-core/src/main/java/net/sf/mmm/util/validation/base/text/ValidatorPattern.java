/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.text;

import java.util.regex.Pattern;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.validation.base.AbstractValueValidator;

/**
 * This is a {@link net.sf.mmm.util.validation.api.ValueValidator} that a given {@link CharSequence}
 * {@link Pattern#matcher(CharSequence) matches} a given {@link Pattern}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class ValidatorPattern extends AbstractValueValidator<CharSequence> {

  /** @see #getCode() */
  public static final String CODE = "Pattern";

  private final Pattern pattern;

  /**
   * The constructor.
   *
   * @param pattern the regular expression {@link Pattern}.
   */
  public ValidatorPattern(Pattern pattern) {
    super();
    this.pattern = pattern;
  }

  @Override
  protected String getCode() {

    return CODE;
  }

  @Override
  protected NlsMessage validateNotNull(CharSequence value) {

    boolean matches = this.pattern.matcher(value).matches();
    if (!matches) {
      return createBundle(NlsBundleUtilCoreRoot.class).errorValueFormat(null, this.pattern);
    }
    return null;
  }

}
