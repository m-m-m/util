/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.api;

import java.util.Collections;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.exception.api.ExceptionTruncation;
import net.sf.mmm.util.nls.api.NlsRuntimeException;

/**
 * This exception is thrown if a validation failed unexpectedly. You may just use
 * {@link javax.validation.ValidationException}. However, this exception implements
 * {@link net.sf.mmm.util.nls.api.NlsThrowable} will all features and allows
 * {@link net.sf.mmm.util.exception.api.ExceptionTruncation}.<br/>
 * <b>ATTENTION:</b><br/>
 * Regular validation should not use exceptions. E.g. in a rich client validation happens for usability to
 * give early feedback to the end-user in case his data is incomplete or wrong and to prevent sending requests
 * to the server that can only fail. Fur such purpose bean validation (JSR303) does a great job and can be
 * used without exceptions. However on the server-side validation has to be performed again for
 * {@link net.sf.mmm.util.lang.api.concern.Security} reasons. In case a validation fails for a remote request,
 * the caller has to be informed by an exception. This is an abnormal situation where an exception is
 * suitable.
 *
 * @see javax.validation.ValidationException
 * @see javax.validation.ConstraintViolationException
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public class ValidationErrorUserException extends NlsRuntimeException {

  /** @see #getCode() */
  public static final String CODE = "ValidationError";

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /** @see #getConstraintViolations() */
  private Set<ConstraintViolation<?>> constraintViolations;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected ValidationErrorUserException() {

    super();
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception. Typically a
   *        {@link javax.validation.ValidationException}.
   */
  public ValidationErrorUserException(Throwable nested) {

    super(nested, createBundle(NlsBundleUtilCoreRoot.class).errorValidation());
    if (nested instanceof ConstraintViolationException) {
      this.constraintViolations = ((ConstraintViolationException) nested).getConstraintViolations();
    } else {
      this.constraintViolations = Collections.emptySet();
    }
  }

  /**
   * The copy constructor.
   *
   * @see net.sf.mmm.util.nls.api.AbstractNlsRuntimeException#AbstractNlsRuntimeException(net.sf.mmm.util.nls.api.AbstractNlsRuntimeException,
   *      ExceptionTruncation)
   *
   * @param copySource is the exception to copy.
   * @param truncation is the {@link ExceptionTruncation} to configure potential truncations.
   */
  protected ValidationErrorUserException(ValidationErrorUserException copySource, ExceptionTruncation truncation) {

    super(copySource, truncation);
    this.constraintViolations = copySource.constraintViolations;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ValidationErrorUserException createCopy(ExceptionTruncation truncation) {

    return new ValidationErrorUserException(this, truncation);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return CODE;
  }

  /**
   * @see ConstraintViolationException#getConstraintViolations()
   *
   * @return the {@link ConstraintViolation}s. Will be {@link Set#isEmpty() empty} if not created from
   *         {@link ConstraintViolationException}.
   */
  public Set<ConstraintViolation<?>> getConstraintViolations() {

    return this.constraintViolations;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isForUser() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTechnical() {

    return false;
  }

}
