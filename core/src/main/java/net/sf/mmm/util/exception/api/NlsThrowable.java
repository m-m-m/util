/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.exception.api;

import java.io.Serializable;
import java.util.Locale;

import net.sf.mmm.util.lang.api.attribute.AttributeReadMessage;
import net.sf.mmm.util.lang.api.attribute.AttributeReadMessageCode;
import net.sf.mmm.util.lang.api.attribute.AttributeReadUuid;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsObject;

/**
 * This is the interface for exceptions with the following features:
 * <ul>
 * <li>real <em>native language support</em> via {@link NlsMessage}.</li>
 * <li>a {@link #getUuid() UUID} unique per exception instance automatically generated once per exception
 * {@link #getCause() chain}.</li>
 * <li>an {@link #getCode() error code} that should be unique per exception type.</li>
 * <li>distinguish between {@link #isTechnical() technical} exceptions and exceptions {@link #isForUser()
 * intended for end-users}.</li>
 * <li>support for {@link #createCopy(ExceptionTruncation) copying and truncation} of the exception.</li>
 * </ul>
 * <b>NOTE:</b><br>
 * Exceptions should only occur in unexpected or un-normal situations. Never use exceptions for control flows.
 *
 * @see NlsMessage
 * @see net.sf.mmm.util.exception.api.NlsException
 * @see net.sf.mmm.util.exception.api.NlsRuntimeException
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface NlsThrowable extends NlsObject, AttributeReadUuid, AttributeReadMessage, AttributeReadMessageCode,
    Serializable {

  /**
   * @return the {@link Throwable#getCause() cause}.
   */
  Throwable getCause();

  /**
   * Determines if this is a <em>technical exception</em>.
   * <ul>
   * <li>A technical exception is an unexpected situation that is to be logged on error level and should be
   * analyzed by the operators or software developers of the system. Further in such case the end-user can
   * typically do nothing about the problem (except to retry his operation) and will typically not understand
   * the problem. Therefore a generic message should be {@link #isForUser() displayed to the end-user} in such
   * case. See {@link net.sf.mmm.util.exception.api.TechnicalErrorUserException}.</li>
   * <li>A non technical exception is called <em>user failure</em>. It is an undesired but NOT abnormal
   * situation. It should be logged on a level less than error (typically info). The {@link #getMessage()
   * message} is typically {@link #isForUser() intended for to end-users} and has to be easy to understand.</li>
   * </ul>
   * This separation is intentionally not done via inheritance of technical and business exception base
   * classes to allow reuse. However, if you want to have it this way, create such classes derived from
   * {@link NlsRuntimeException}.
   *
   * @see #isForUser()
   *
   * @return {@code true} if this is a technical exception, {@code false} if this is a user error.
   */
  boolean isTechnical();

  /**
   * @return {@code true} if the {@link #getMessage() message} of this exception is for end-users (or
   *         clients), {@code false} otherwise (for internal {@link #isTechnical() technical} errors).
   */
  boolean isForUser();

  /**
   * This method gets the {@link NlsMessage} describing the problem.
   *
   * @return the {@link NlsMessage}.
   */
  NlsMessage getNlsMessage();

  /**
   * This method gets the localized message as string.
   *
   * @see #getLocalizedMessage(Locale, Appendable)
   *
   * @param locale is the {@link Locale} to translate to.
   * @return the localized message.
   */
  String getLocalizedMessage(Locale locale);

  /**
   * This method writes the localized message to the given string buffer. <br>
   *
   * @see net.sf.mmm.util.nls.api.NlsMessage#getLocalizedMessage(Locale, Appendable)
   *
   * @param locale is the {@link Locale} to translate to.
   * @param appendable is where to {@link Appendable#append(CharSequence) append} the message to.
   */
  void getLocalizedMessage(Locale locale, Appendable appendable);

  /**
   * This method prints the stack trace with localized exception message(s).
   *
   * @param locale is the locale to translate to.
   * @param buffer is where to write the stack trace to.
   * @throws IllegalStateException if the given {@code buffer} produced an {@link java.io.IOException}.
   * @since 2.0.0
   */
  void printStackTrace(Locale locale, Appendable buffer) throws IllegalStateException;

  /**
   * @return the {@link Throwable#getStackTrace() stack-trace}.
   */
  StackTraceElement[] getStackTrace();

  /**
   * Returns a copy of the given exception where the specified details are removed. See {@link ExceptionUtil}
   * for advanced usage and further details. <br>
   * <b>NOTE:</b><br>
   * Implementations/subclasses can override the return type with the subclass. As Java does not support an
   * implicit SELF generic and we do not want to overload all exceptions with a generic type.
   *
   * @param truncation the {@link ExceptionTruncation} to configure what to remove. E.g.
   *        {@link ExceptionTruncation#REMOVE_ALL}.
   * @return a truncated copy of this {@code exception}.
   */
  Throwable createCopy(ExceptionTruncation truncation);

  /**
   * {@inheritDoc}
   *
   * @see #toString(Locale)
   */
  String toString();

  /**
   * Like {@link #toString()} but using the specified {@link Locale}.
   *
   * @param locale is the {@link Locale} used for {@link #getLocalizedMessage(Locale)}.
   * @return the localized string representation of this exception as described in
   *         {@link #toString(Locale, Appendable)}.
   * @since 6.0.0
   */
  String toString(Locale locale);

  /**
   * {@link Appendable#append(CharSequence) appends} the localized string representation of this exception. It
   * is defined as following:
   *
   * <pre>&lt;{@link Class#getName() classname}>: [&lt;custom-{@link #getCode() code}>: ]&lt;{@link #getLocalizedMessage(Locale) message}></pre>
   *
   * @param locale is the {@link Locale} used for {@link #getLocalizedMessage(Locale)}.
   * @param appendable is the buffer to {@link Appendable#append(CharSequence) append} to. Will be created as
   *        {@link StringBuilder} if {@code null} is provided.
   * @return the provided {@link Appendable} or the created one if {@code null} was given.
   * @since 6.0.0
   */
  Appendable toString(Locale locale, Appendable appendable);

}
