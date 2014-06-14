/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.exception.api.ExceptionTruncation;

/**
 * A {@link TechnicalErrorUserException} is wrapping arbitrary technical errors to a generic exception for
 * end-users or clients.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 * @deprecated use {@link net.sf.mmm.util.exception.api.TechnicalErrorUserException} instead.
 */
@Deprecated
public class TechnicalErrorUserException extends NlsRuntimeException {

  /** @see #getCode() */
  public static final String CODE = "TechnicalError";

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected TechnicalErrorUserException() {

    super();
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause}.
   */
  public TechnicalErrorUserException(Throwable nested) {

    super(nested, createBundle(NlsBundleUtilCoreRoot.class).errorTechnical());
  }

  /**
   * The constructor for the very special case that you want to define a custom message. You are not
   * encouraged to use this constructor. Please also consider that you can still customize the localized texts
   * for the message when using {@link #TechnicalErrorUserException(Throwable)}.
   *
   * @param nested is the {@link #getCause() cause}.
   * @param message is a custom {@link #getNlsMessage() message}.
   */
  public TechnicalErrorUserException(Throwable nested, NlsMessage message) {

    super(nested, message);
  }

  /**
   * The copy constructor.
   *
   * @see net.sf.mmm.util.exception.api.NlsRuntimeException#NlsRuntimeException(net.sf.mmm.util.exception.api.NlsRuntimeException,
   *      ExceptionTruncation)
   *
   * @param copySource is the exception to copy.
   * @param truncation is the {@link ExceptionTruncation} to configure potential truncations.
   */
  protected TechnicalErrorUserException(TechnicalErrorUserException copySource, ExceptionTruncation truncation) {

    super(copySource, truncation);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TechnicalErrorUserException createCopy(ExceptionTruncation truncation) {

    return new TechnicalErrorUserException(this, truncation);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final String getCode() {

    return CODE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isTechnical() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isForUser() {

    return true;
  }

  /**
   * Gets the given {@link Throwable} as {@link NlsThrowable#isForUser() user}
   * {@link AbstractNlsRuntimeException exception} or converts it to such.
   *
   * @param exception is the {@link Throwable} to convert.
   * @return the {@link AbstractNlsRuntimeException} with {@link NlsThrowable#isForUser()} returning
   *         <code>true</code>.
   */
  public static AbstractNlsRuntimeException getOrCreateUserException(Throwable exception) {

    if (exception instanceof NlsThrowable) {
      NlsThrowable nlsThrowable = (NlsThrowable) exception;
      if (nlsThrowable.isForUser()) {
        if (exception instanceof AbstractNlsRuntimeException) {
          return (AbstractNlsRuntimeException) exception;
        } else {
          return new NlsRuntimeWrapperException((AbstractNlsException) exception);
        }
      }
    }
    return new TechnicalErrorUserException(exception);
  }

}
