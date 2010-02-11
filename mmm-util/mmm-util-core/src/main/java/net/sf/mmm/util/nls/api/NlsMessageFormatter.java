/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * This is the interface for a formatter of a message-text. It is a simplified
 * view on something like {@link java.text.MessageFormat}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface NlsMessageFormatter extends NlsFormatter<Void> {

  /**
   * This method formats the underlying pattern by filling in the given
   * <code>arguments</code> and writing the result into the given
   * <code>buffer</code>.
   * 
   * @param nothing has to be <code>null</code>. Only for generic compatibility.
   * @param locale is the {@link Locale} used for localized formatting. An
   *        implementation may ignore it if it was created locale specific like
   *        {@link java.text.MessageFormat} which is bound to a locale at
   *        construction. In order to allow a thread-safe implementation that
   *        may be used for sub-locales (e.g. for <code>de</code> and
   *        <code>de_DE</code>) it was added in this signature.
   * @param buffer is where to append the formatted message.
   * @param arguments are the argument-objects to be formatted and filled into
   *        the message.
   * @throws IOException if the given {@link Appendable} caused such exception.
   * 
   * @see java.text.MessageFormat#format(Object[], StringBuffer,
   *      java.text.FieldPosition)
   */
  void format(Void nothing, Locale locale, Map<String, Object> arguments, Appendable buffer)
      throws IOException;

}
