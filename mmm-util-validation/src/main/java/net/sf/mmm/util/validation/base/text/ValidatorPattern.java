/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.text;

import java.util.Objects;
import java.util.regex.Pattern;

import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.validation.NlsBundleUtilValidationRoot;
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

  /**
   * The constructor.
   *
   * @param pattern the regular expression {@link Pattern#compile(String) pattern}.
   */
  public ValidatorPattern(String pattern) {
    this(Pattern.compile(pattern));
  }

  @Override
  protected String getCode() {

    return CODE;
  }

  @Override
  protected NlsMessage validateNotNull(CharSequence value) {

    boolean matches = this.pattern.matcher(value).matches();
    if (!matches) {
      return createBundle(NlsBundleUtilValidationRoot.class).errorValueFormat(null, this.pattern);
    }
    return null;
  }

  @Override
  public int hashCode() {

    return Objects.hashCode(this.pattern);
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ValidatorPattern other = (ValidatorPattern) obj;
    if (!Objects.equals(this.pattern, other.pattern)) {
      return false;
    }
    return true;
  }

}
