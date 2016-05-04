/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import net.sf.mmm.util.nls.base.NlsDependencies;

/**
 * This interface represents the template for an internationalized text that can be {@link #translate(Locale)
 * translated} to a given {@link Locale}. <br>
 * There is no direct factory to create an {@link NlsTemplate} as an alternative implementation might take totally
 * different arguments for construction. If you want to use your own implementation of {@link NlsTemplate}, you can
 * either directly supply it when {@link NlsMessageFactory#create(NlsTemplate) creating} an {@link NlsMessage} or
 * otherwise for localization via {@link NlsTemplateResolver}.
 *
 * @see NlsMessage
 * @see NlsTemplateResolver
 * @see net.sf.mmm.util.nls.base.NlsTemplateImpl
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface NlsTemplate extends Serializable {

  /**
   * This method translates the represented string for the given {@code locale}. <br>
   * This typically happens via a lookup in a {@link java.util.ResourceBundle}).
   *
   * @param locale is the locale to translate to.
   * @return the resolved string (closest translation for the given {@code locale}).
   */
  String translate(Locale locale);

  /**
   * This method behaves like {@link #translate(Locale)} but additionally fills the given {@code arguments} into the
   * translated message writing into the given {@code buffer}.
   *
   * @param locale is the locale to translate to.
   * @param arguments are the variable arguments to fill in the message.
   * @param buffer is the buffer where the translation will be appended to.
   * @param resolver is the {@link NlsTemplateResolver}.
   * @param nlsDependencies are the {@link NlsDependencies}.
   * @return {@code true} if the (translated) message has been appended to the given {@code messageBuffer} or
   *         {@code false} if the translation failed.
   * @throws IOException if the given {@link Appendable} caused such exception.
   */
  boolean translate(Locale locale, Map<String, Object> arguments, Appendable buffer, NlsTemplateResolver resolver,
      NlsDependencies nlsDependencies) throws IOException;

}
