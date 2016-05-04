/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.validation.api.ValidationFailure;

/**
 * This is the abstract base implementation of {@link net.sf.mmm.util.validation.api.ValueValidator}.
 *
 * @param <V> is the generic type of the value to {@link #validate(Object) validate}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public abstract class AbstractValueValidator<V> extends AbstractValidator<V> {

  /**
   * The constructor.
   */
  public AbstractValueValidator() {

    super();
  }

  @Override
  public ValidationFailure validate(V value, Object valueSource) {

    NlsMessage failureMessage;
    if (value == null) {
      failureMessage = validateNull();
    } else {
      failureMessage = validateNotNull(value);
    }
    ValidationFailure result = null;
    if (failureMessage != null) {
      result = new ValidationFailureImpl(getCode(), valueSource, failureMessage);
    }
    return result;
  }

  /**
   * This method performs the validation in case {@code null} was provided as value. By default {@code null} should be
   * considered as a legal value. Only for validators such as {@link ValidatorMandatory} this method should be
   * overridden.
   *
   * @return the {@link ValidationFailure#getMessage() failure message} or {@code null} if the {@code null} -value is
   *         valid.
   */
  protected NlsMessage validateNull() {

    return null;
  }

  /**
   * This method performs the validation in case {@code value} is NOT {@code null}. This method contains the actual
   * custom logic for the validation. It is therefore designed in a way that makes it most simple to implement custom
   * validators. <br>
   * <b>ATTENTION:</b><br>
   * For internationalization you should not directly return string literals but use
   * {@link net.sf.mmm.util.nls.api.NlsMessage} instead.
   *
   * @param value is the value to validate.
   * @return the {@link ValidationFailure#getMessage() failure message} or {@code null} if the the given {@code value}
   *         is valid.
   */
  protected abstract NlsMessage validateNotNull(V value);

}
