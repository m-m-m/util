/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.io.Serializable;
import java.util.Locale;

/**
 * This is the interface for an internationalized message. It stores an {@link #getInternationalizedMessage()
 * internationalized-message} separated from language independent {@link #getArgument(String) arguments}. This approach
 * ensures that the message is always available in the internationalized language (should be English) while it still
 * allows to {@link #getLocalizedMessage(Locale, NlsTemplateResolver) translate} the message to a native language. For
 * an introduction first read {@link net.sf.mmm.util.nls.api here}<br>
 * The format of the {@link #getInternationalizedMessage() internationalized-message} is (almost) compatible to
 * {@link java.text.MessageFormat}. This allows to migrate existing code from {@link java.text.MessageFormat} to
 * {@link NlsMessage} easily. However, there are some advanced features available. While using numbers to identify the
 * {@link #getArgument(int) argument} is a maintenance-hell for large messages, it is also possible to use
 * {@link #getArgument(String) named arguments}. Further there is also support for additional styles as well as
 * {@link net.sf.mmm.util.text.api.Justification}. See {@link NlsArgument} for the specification of the argument
 * syntax.<br>
 * To specify {@link NlsMessage}s you create an {@link NlsBundle} per module.
 *
 * For the term <em>internationalization</em> usually the shortcut <em>i18n</em> is used.
 *
 * @see NlsArgument
 * @see NlsBundle
 * @see NlsMessageFactory
 * @see net.sf.mmm.util.nls.api
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface NlsMessage extends NlsObject, Serializable {

  /**
   * The prefix appended to the {@link #getInternationalizedMessage() message} if the localization (translation) failed.
   */
  String LOCALIZATION_FAILURE_PREFIX = "#";

  /**
   * This method gets the internationalized message what is the actual message template for the {@link Locale#ROOT root
   * locale} without resolving its {@link NlsArgument}s.<br>
   *
   * @see NlsMessage
   * @see #getArgument(String)
   * @see java.text.MessageFormat
   *
   * @return the message for internationalization (e.g. <code>"Welcome {name}!"</code>).
   */
  String getInternationalizedMessage();

  /**
   * This method gets the number of language independent arguments of this exception.
   *
   * @return the argument count.
   * @deprecated this method does NOT make sense anymore.
   */
  @Deprecated
  int getArgumentCount();

  /**
   * This method gets the language independent argument value for the given {@code key}.
   *
   * @see NlsArgument
   *
   * @param key is the name of the requested argument.
   * @return the argument value for the given key or {@code null} if NOT defined.
   */
  Object getArgument(String key);

  /**
   * This method gets the language independent argument at the given {@code index}.
   *
   * @param index is the index of the requested argument.
   * @return the argument for the given key.
   * @deprecated use {@link #getArgument(String)} instead (and use named keys instead of numbers where possible).
   */
  @Deprecated
  Object getArgument(int index);

  /**
   * This method gets the untranslated message (default language should be English) with arguments filled in. <br>
   * <b>ATTENTION:</b><br>
   * In most cases you wand to use {@link #getLocalizedMessage(Locale)} instead of this method.
   *
   * @see #getLocalizedMessage(Locale, NlsTemplateResolver)
   *
   * @return the i18n message with arguments filled in.
   */
  String getMessage();

  /**
   * This method tries to get the {@link #getLocalizedMessage(Locale) localized message} as {@link String} using the
   * default {@link Locale}. In the simplest case {@link Locale#getDefault()} is used but if available
   * {@code LocaleContextHolder} from spring is used.<br>
   * <b>ATTENTION:</b><br>
   * If possible try to avoid using this method and use {@link #getLocalizedMessage(Locale)} instead.
   *
   * @return the localized message.
   */
  String getLocalizedMessage();

  /**
   * This method gets the resolved and localized message.<br>
   * First it will {@link NlsTemplateResolver#resolveTemplate(String) translate} the
   * {@link #getInternationalizedMessage() internationalized message} to the given {@link Locale}. If this fails for
   * whatever reason, the {@link #getInternationalizedMessage() internationalized message} is used as fallback.<br>
   * Then this message gets resolved by replacing the {@link NlsArgument arguments} with their according
   * {@link #getArgument(String) values}.<br>
   * <b>Example:</b><br>
   * We assume the {@link #getInternationalizedMessage() internationalized message} is <code>"Welcome {name}!"</code>
   * and the {@link #getArgument(String) argument} with the key @c{@code "name"} has the value {@code "Joelle"}. After
   * translation to {@link Locale#GERMAN} the message may be <code>"Willkommen {name}!"</code>. This results in the
   * resolved message {@code "Willkommen Joelle!"}. If the German localization was not present or has a syntax error,
   * the {@link Locale#ROOT root locale} is used as fallback result in {@code "Welcome Joelle!"}.
   *
   * @see net.sf.mmm.util.nls.api
   *
   * @param locale is the locale to translate to.
   * @return the localized message.
   */
  String getLocalizedMessage(Locale locale);

  /**
   * This method writes the {@link #getLocalizedMessage(Locale) localized message} to the given {@code buffer}. <br>
   *
   * @see #getLocalizedMessage(Locale)
   *
   * @param locale is the locale to translate to.
   * @param buffer is the buffer where to write the message to.
   * @since 5.0.0
   */
  void getLocalizedMessage(Locale locale, Appendable buffer);

  /**
   * This method gets the localized message as string.
   *
   * @see NlsMessage#getLocalizedMessage(Locale, NlsTemplateResolver, Appendable)
   *
   * @param locale is the locale to translate to.
   * @param resolver is used to translate the message.
   * @return the localized message.
   * @deprecated providing external {@link NlsTemplateResolver} has been discouraged.
   */
  @Deprecated
  String getLocalizedMessage(Locale locale, NlsTemplateResolver resolver);

  /**
   * This method writes the localized message to the given {@code buffer}. <br>
   * The actual localization is done by a {@link NlsTemplate template} that is provided via the given {@code resolver}.
   * If this fails, the {@link #getInternationalizedMessage() original message} will be used. After translation is done,
   * the language independent arguments will be filled in the translated message string according to the given
   * {@code locale}.
   *
   * @param locale is the locale to translate to.
   * @param resolver is used to resolve the template required to translate the {@link #getInternationalizedMessage()
   *        internationalized message}.
   * @param buffer is the buffer where to write the message to.
   * @deprecated providing external {@link NlsTemplateResolver} has been discouraged.
   */
  @Deprecated
  void getLocalizedMessage(Locale locale, NlsTemplateResolver resolver, Appendable buffer);

}
