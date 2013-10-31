/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;

/**
 * A {@link TechnicalErrorUserException} is wrapping arbitrary technical errors to a generic exception for
 * end-users or clients.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public class TechnicalErrorUserException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -1993835994222744405L;

  /** @see #getCode() */
  public static final String CODE = "TechnicalError";

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause}.
   */
  public TechnicalErrorUserException(Throwable nested) {

    super(nested, createBundle(NlsBundleUtilCoreRoot.class).errorTechnical());
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause}.
   * @param message is a custom {@link #getNlsMessage() message}.
   */
  public TechnicalErrorUserException(Throwable nested, NlsMessage message) {

    super(nested, message);
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
