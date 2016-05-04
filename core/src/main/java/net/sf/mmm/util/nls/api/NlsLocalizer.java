/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.util.Locale;
import java.util.Map;

/**
 * This is the interface for a localizer that simplifies the localization of messages via {@link NlsMessage}s.
 *
 * @see net.sf.mmm.util.nls.base.NlsCachingLocalizer
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.1
 */
public interface NlsLocalizer {

  /**
   * This method localizes the given {@code internationalizedMessage} with the given {@code arguments} filled in to the
   * given target {@code locale}.
   *
   * @param locale is the {@link Locale} to translate to.
   * @param internationalizedMessage is the {@link NlsMessage#getInternationalizedMessage() internationalized message}
   *        to translate.
   * @param arguments are the {@link NlsMessage#getArgument(String) arguments} to fill in.
   * @return the localized message.
   */
  String localize(Locale locale, String internationalizedMessage, Map<String, Object> arguments);

  /**
   * This method localizes the given {@code internationalizedMessage} to the given target {@code locale}.
   *
   * @param locale is the {@link Locale} to translate to.
   * @param internationalizedMessage is the {@link NlsMessage#getInternationalizedMessage() internationalized message}
   *        to translate.
   * @return the localized message.
   */
  String localize(Locale locale, String internationalizedMessage);

}
