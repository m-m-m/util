/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.util.Locale;

import net.sf.mmm.util.lang.api.attribute.AttributeReadMessage;
import net.sf.mmm.util.lang.api.attribute.AttributeReadMessageCode;
import net.sf.mmm.util.lang.api.attribute.AttributeReadUuid;

/**
 * This is the interface for exceptions and runtime exceptions with real <em>native language support</em>
 * (NLS).<br>
 * In extension to a regular {@link Throwable throwable} it brings enhanced native language support (NLS). In
 * a server environment there can be multiple users active at a time with different locales. <br>
 * This approach aims to simplify the NLS when creating throwables with error messages. It has the following
 * principles:
 * <ul>
 * <li>The throwable itself contains the message in English language so this is always available even if
 * nationalization fails.</li>
 * <li>The language independent parts of the message are separated in an argument list that is inserted into
 * the message after nationalization.</li>
 * <li>The throwable itself does not know the component that does the actual localization which is done by the
 * callback interface {@link net.sf.mmm.util.nls.api.NlsTemplateResolver}.</li>
 * </ul>
 * 
 * @see NlsMessage
 * @see net.sf.mmm.util.nls.api.NlsException
 * @see net.sf.mmm.util.nls.api.NlsRuntimeException
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface NlsThrowable extends NlsObject, AttributeReadUuid, AttributeReadMessage, AttributeReadMessageCode {

  /**
   * @return the {@link Throwable#getCause() cause}.
   */
  Throwable getCause();

  /**
   * Determines if this is a <em>technical exception</em> or a <em>user failure</em>.
   * <ul>
   * <li>A technical exception is an unexpected situation that is to be logged on error level and should be
   * analyzed by the operators or software developers of the system. Further in such case the end-user can
   * typically do nothing about the problem (except to retry his operation) and will typically not understand
   * the problem. Therefore a generic message should be displayed as {@link net.sf.mmm.util.lang.api.Message}
   * for the end-user in such case.</li>
   * <li>A user failure is an undesired but NOT abnormal situation. It should be logged on a level less than
   * error (typically info). The {@link #getMessage() message} will directly be displayed as
   * {@link net.sf.mmm.util.lang.api.Message} to end-users and has to be easy to understand.</li>
   * </ul>
   * 
   * 
   * @return <code>true</code> if this is a technical exception, <code>false</code> if this is a user error.
   */
  boolean isTechnical();

  /**
   * This method gets the {@link NlsMessage} describing the problem.
   * 
   * @return the {@link NlsMessage}.
   */
  NlsMessage getNlsMessage();

  /**
   * This method gets the localized message as string.
   * 
   * @see #getLocalizedMessage(Locale, NlsTemplateResolver, Appendable)
   * 
   * @param locale is the locale to translate to.
   * @return the localized message.
   */
  String getLocalizedMessage(Locale locale);

  /**
   * This method gets the localized message as string.
   * 
   * @see #getLocalizedMessage(Locale, NlsTemplateResolver, Appendable)
   * 
   * @param locale is the locale to translate to.
   * @param resolver is used to resolve the template in order to translate the original i18n message.
   * @return the localized message.
   */
  String getLocalizedMessage(Locale locale, NlsTemplateResolver resolver);

  /**
   * This method writes the localized message to the given string buffer. <br>
   * 
   * @see net.sf.mmm.util.nls.api.NlsMessage#getLocalizedMessage(Locale, NlsTemplateResolver, Appendable)
   * 
   * @param locale is the locale to translate to.
   * @param resolver is used to resolve the template required to translate the {@link #getNlsMessage()
   *        internationalized message}.
   * @param buffer is the buffer where to write the message to.
   * @throws IllegalStateException if the given <code>buffer</code> produced an {@link java.io.IOException}.
   */
  void getLocalizedMessage(Locale locale, NlsTemplateResolver resolver, Appendable buffer) throws IllegalStateException;

  /**
   * This method prints the stack trace with localized exception message(s).
   * 
   * @param locale is the locale to translate to.
   * @param buffer is where to write the stack trace to.
   * @throws IllegalStateException if the given <code>buffer</code> produced an {@link java.io.IOException}.
   * @since 2.0.0
   */
  void printStackTrace(Locale locale, Appendable buffer) throws IllegalStateException;

  /**
   * This method prints the stack trace with localized exception message(s).
   * 
   * @param locale is the locale to translate to.
   * @param resolver translates the original message.
   * @param buffer is where to write the stack trace to.
   * @throws IllegalStateException if the given <code>buffer</code> produced an {@link java.io.IOException}.
   */
  void printStackTrace(Locale locale, NlsTemplateResolver resolver, Appendable buffer) throws IllegalStateException;

  /**
   * @return the {@link Throwable#getStackTrace() stack-trace}.
   */
  StackTraceElement[] getStackTrace();

  /**
   * This method appends the specified exception (<code>suppressed</code>) to this exception. This is used for
   * exceptions that have been suppressed in order to deliver this exception.<br/>
   * Here an example:
   * 
   * <pre>
   * InputStream inStream = openStream();
   * try {
   *   readData(inStream);
   * } catch (RuntimeIoException e) {
   *   try {
   *     inStream.close();
   *   } catch (Exception suppressed) {
   *     e.addSuppressed(suppressed);
   *   }
   *   throw e;
   * }
   * </pre>
   * 
   * @param suppressed is the {@link Throwable} to add.
   * @since 3.0.0
   * @deprecated should be addSuppressed that is only available in Java 1.7+. As Java 1.7+ marked the method
   *             as final there is no other compatible way to implemented this.
   */
  @Deprecated
  void addSuppressedException(Throwable suppressed);

  /**
   * Returns an array containing all of the exceptions that were {@link #addSuppressedException(Throwable)
   * suppressed}, typically by the {@code try} -with-resources statement, in order to deliver this exception.<br/>
   * 
   * See <code>Throwable.getSuppressed()</code>.
   * 
   * @return an array containing all of the exceptions that have been added via
   *         {@link #addSuppressedException(Throwable)}.
   * @since 3.0.0
   * @deprecated should be getSuppressed that is only available in Java 1.7+. As Java 1.7+ marked the method
   *             as final there is no other compatible way to implemented this.
   */
  @Deprecated
  Throwable[] getSuppressedExceptions();

}
