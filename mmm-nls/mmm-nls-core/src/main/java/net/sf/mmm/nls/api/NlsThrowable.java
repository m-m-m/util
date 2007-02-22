/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.nls.api;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * This is the interface that all exceptions and runtime exceptions of this
 * project should implement. <br>
 * In extension to usual throwables it brings enhanced native language support
 * (NLS). In a server environment there can be multiple users active at a time
 * with different locales. <br>
 * This approach aims to simplify the NLS when creating throwables with error
 * messages. It has the following principles:
 * <ul>
 * <li>The throwable itself contains the message in english language so this is
 * always available even if nationalization fails.</li>
 * <li>The language independed parts of the message are separated in an
 * argument list that is inserted into the message after nationalization.</li>
 * <li>The trowable itself does not know the component that does the actual
 * nationalization and uses a simplest callback interface possible (
 * {@link net.sf.mmm.nls.api.StringTranslator})</li>
 * </ul>
 * 
 * @see NlsMessage
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface NlsThrowable {

  /**
   * @return the {@link Throwable#getCause() cause}.
   */
  Throwable getCause();

  /**
   * @return the {@link Throwable#getMessage() message}.
   */
  String getMessage();

  /**
   * This method gets the localized message as string.
   * 
   * @see NlsThrowable#getLocalizedMessage(StringTranslator, StringBuffer)
   * 
   * @param nationalizer
   *        is used to translate the original i18n message.
   * @return the localized message.
   */
  String getLocalizedMessage(StringTranslator nationalizer);

  /**
   * This method writes the nationalized (or localized) message to the given
   * string buffer. <br>
   * The actual nationalization is done by the given translator who will get the
   * value of the contained internationalized message without arguments
   * {@link net.sf.mmm.nls.api.NlsMessage#getInternationalizedMessage()}. If
   * this translator fails (returns <code>null</code>), the original message
   * (in english language) will be used. After translation is done, the language
   * independed arguments will be filled in the translated message string.
   * 
   * @see net.sf.mmm.nls.api.NlsMessage#getLocalizedMessage(StringTranslator,
   *      StringBuffer)
   * 
   * @param nationalizer
   *        is used to translate the original i18n message.
   * @param message
   *        is the buffer where to write the message to.
   */
  void getLocalizedMessage(StringTranslator nationalizer, StringBuffer message);

  /**
   * This method prints the strack trace with localized exception message(s).
   * 
   * @param stream
   *        is where to write the strack trace.
   * @param nationalizer
   *        translates the original message. May be <code>null</code>.
   */
  void printStackTrace(PrintStream stream, StringTranslator nationalizer);

  /**
   * This method prints the strack trace with localized exception message(s).
   * 
   * @param writer
   *        is where to write the strack trace.
   * @param nationalizer
   *        translates the original message. Mey be <code>null</code>.
   */
  void printStackTrace(PrintWriter writer, StringTranslator nationalizer);

}
