/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.util.Locale;

/**
 * This is the interface for exceptions and runtime exceptions with real
 * <em>native language support</em> (NLS).<br>
 * In extension to a regular {@link Throwable throwable} it brings enhanced
 * native language support (NLS). In a server environment there can be multiple
 * users active at a time with different locales. <br>
 * This approach aims to simplify the NLS when creating throwables with error
 * messages. It has the following principles:
 * <ul>
 * <li>The throwable itself contains the message in English language so this is
 * always available even if nationalization fails.</li>
 * <li>The language independent parts of the message are separated in an
 * argument list that is inserted into the message after nationalization.</li>
 * <li>The throwable itself does not know the component that does the actual
 * localization which is done by the callback interface
 * {@link net.sf.mmm.util.nls.api.NlsTemplateResolver}.</li>
 * </ul>
 * 
 * @see NlsMessage
 * @see net.sf.mmm.util.nls.NlsException
 * @see net.sf.mmm.util.nls.NlsRuntimeException
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface NlsThrowable extends NlsObject {

  /**
   * @return the {@link Throwable#getCause() cause}.
   */
  Throwable getCause();

  /**
   * @return the {@link Throwable#getMessage() message}.
   */
  String getMessage();

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
   * @param resolver is used to resolve the template in order to translate the
   *        original i18n message.
   * @return the localized message.
   */
  String getLocalizedMessage(Locale locale, NlsTemplateResolver resolver);

  /**
   * This method writes the localized message to the given string buffer. <br>
   * 
   * @see net.sf.mmm.util.nls.api.NlsMessage#getLocalizedMessage(Locale,
   *      NlsTemplateResolver, Appendable)
   * 
   * @param locale is the locale to translate to.
   * @param resolver is used to resolve the template required to translate the
   *        {@link #getNlsMessage() internationalized message}.
   * @param buffer is the buffer where to write the message to.
   * @throws IllegalStateException if the given <code>buffer</code> produced
   *         an {@link java.io.IOException}.
   */
  void getLocalizedMessage(Locale locale, NlsTemplateResolver resolver, Appendable buffer)
      throws IllegalStateException;

  /**
   * This method prints the stack trace with localized exception message(s).
   * 
   * @param locale is the locale to translate to.
   * @param resolver translates the original message.
   * @param buffer is where to write the stack trace to.
   * @throws IllegalStateException if the given <code>buffer</code> produced
   *         an {@link java.io.IOException}.
   */
  void printStackTrace(Locale locale, NlsTemplateResolver resolver, Appendable buffer)
      throws IllegalStateException;

  /**
   * @return the {@link Throwable#getStackTrace() stack-trace}.
   */
  StackTraceElement[] getStackTrace();

}
