/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.api;

import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is a generic exception to transport an arbitrary {@link #isForUser() user} error
 * {@link #getNlsMessage() message}. It can be used for application barriers to avoid serializing
 * implementation specific exceptions that may not be available on the client and could not be de-serialized.
 *
 * @see TechnicalErrorUserException
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public class BusinessErrorUserException extends NlsRuntimeException {

  /** @see #getCode() */
  public static final String CODE = "BusinessError";

  private static final long serialVersionUID = 7301700837160217721L;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected BusinessErrorUserException() {

    super();
  }

  /**
   * The constructor.
   *
   * @param message is the {@link #getNlsMessage()}.
   */
  public BusinessErrorUserException(NlsMessage message) {

    super(message);
  }

  /**
   * The constructor.
   *
   * @param message is the {@link #getNlsMessage()}.
   * @param cause is the {@link #getCause() cause}.
   */
  public BusinessErrorUserException(NlsMessage message, Throwable cause) {

    super(cause, message);
  }

  /**
   * The constructor.
   *
   * @param cause is the {@link #getCause() cause}.
   * @param message is the {@link #getMessage() message}.
   */
  public BusinessErrorUserException(Throwable cause, String message) {

    super(cause, message);
  }

  @Override
  public String getCode() {

    return CODE;
  }

  @Override
  public boolean isTechnical() {

    return false;
  }

}
