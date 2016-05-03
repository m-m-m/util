/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.io.Serializable;
import java.util.Locale;

/**
 * This is the interface for an internationalized message. It stores an {@link #getInternationalizedMessage()
 * internationalized-message} separated from language independent {@link #getArgument(String) arguments}. This
 * approach ensures that the message is always available in the internationalized language (should be English)
 * while it still allows to {@link #getLocalizedMessage(Locale, NlsTemplateResolver) translate} the message to
 * a native language. For an introduction first read {@link net.sf.mmm.util.nls.api here}<br>
 * The format of the {@link #getInternationalizedMessage() internationalized-message} is compatible to
 * {@link java.text.MessageFormat}. This allows to migrate existing code from {@link java.text.MessageFormat}
 * to {@link NlsMessage} easily. However there are some advanced features available. While using numbers to
 * identify the {@link #getArgument(int) argument} is a maintenance-hell for large messages, it is also
 * possible to use {@link #getArgument(String) named arguments}. Further there is also support for additional
 * styles as well as {@link net.sf.mmm.util.text.api.Justification}. The format specification for
 * parameter-syntax is as following:
 *
 * <pre>'{' ArgumentKey [ ',' FormatType [ ',' FormatStyle ] ] [ '{' Justification '}' ] '}'</pre>
 *
 * The literals are explained in this table.
 * <table border="1">
 * <tr>
 * <th>literal</th>
 * <th>explanation</th>
 * </tr>
 * <tr>
 * <td>ArgumentKey</td>
 * <td>is the key of the parameter (may be numeric for legacy support)</td>
 * </tr>
 * <tr>
 * <td>FormatType</td>
 * <td>a type specifying how to format the value (see below). Should fit to the {@link Object#getClass() type}
 * of the value.
 * </tr>
 * <tr>
 * <td>FormatStyle</td>
 * <td>a style according to {@code FormatType} (see below).</td>
 * </tr>
 * <tr>
 * <td>Justification</td>
 * <td>a {@link net.sf.mmm.util.text.api.JustificationBuilder justification}</td>
 * </tr>
 * </table>
 *
 * <br>
 * The following table shows the supported variants of {@code FormatType} and {@code FormatStyle}:
 * <table border="1">
 * <tr>
 * <th>FormatType</th>
 * <th>FormatStyle</th>
 * <th>Example</th>
 * <th>Comment</th>
 * </tr>
 * <tr>
 * <td>-</td>
 * <td>-</td>
 * <td>value as string</td>
 * <td>default string representation of the value</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_NUMBER number}</td>
 * <td>-</td>
 * <td>123,456.78</td>
 * <td>Numeric representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_NUMBER number}</td>
 * <td>{@link NlsFormatterManager#STYLE_PERCENT percent}</td>
 * <td>12,345,678%</td>
 * <td>Number in percent</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_NUMBER number}</td>
 * <td>{@link NlsFormatterManager#STYLE_CURRENCY currency}</td>
 * <td>123,456.78 �</td>
 * <td>Number as currency</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_NUMBER number}</td>
 * <td>'#'##.##</td>
 * <td>#123456.78</td>
 * <td>Any other style is treated as pattern for {@link java.text.DecimalFormat}</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATE date}</td>
 * <td>-</td>
 * <td>31-Dec-1999</td>
 * <td>Date default representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATE date}</td>
 * <td>{@link NlsFormatterManager#STYLE_SHORT short}</td>
 * <td>31/12/99</td>
 * <td>Date short representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATE date}</td>
 * <td>{@link NlsFormatterManager#STYLE_MEDIUM medium}</td>
 * <td>31-Dec-1999</td>
 * <td>Date medium representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATE date}</td>
 * <td>{@link NlsFormatterManager#STYLE_LONG long}</td>
 * <td>31 December 1999</td>
 * <td>Date long representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATE date}</td>
 * <td>{@link NlsFormatterManager#STYLE_FULL full}</td>
 * <td>Friday, 31 December 1999</td>
 * <td>Date full representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATE date}</td>
 * <td>{@link NlsFormatterManager#STYLE_ISO_8601 iso8601}</td>
 * <td>1999-12-31</td>
 * <td>Date ISO-8601 representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATE date}</td>
 * <td>yyyy.MM.dd</td>
 * <td>2010.01.01</td>
 * <td>Any other style is treated as pattern for {@link java.text.SimpleDateFormat}</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_TIME time}</td>
 * <td>-</td>
 * <td>23:59:59</td>
 * <td>Time default representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_TIME time}</td>
 * <td>{@link NlsFormatterManager#STYLE_SHORT short}</td>
 * <td>23:59</td>
 * <td>Time short representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_TIME time}</td>
 * <td>{@link NlsFormatterManager#STYLE_MEDIUM medium}</td>
 * <td>23:59:59</td>
 * <td>Time medium representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_TIME time}</td>
 * <td>{@link NlsFormatterManager#STYLE_LONG long}</td>
 * <td>23:59:59 GMT+01:00</td>
 * <td>Time long representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_TIME time}</td>
 * <td>{@link NlsFormatterManager#STYLE_FULL full}</td>
 * <td>23:59:59 o'clock GMT+01:00</td>
 * <td>Time full representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_TIME time}</td>
 * <td>{@link NlsFormatterManager#STYLE_ISO_8601 iso8601}</td>
 * <td>23:59:59+01:00</td>
 * <td>Time ISO-8601 representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_TIME time}</td>
 * <td>HH:mm:ss</td>
 * <td>23:59:59</td>
 * <td>Any other style is treated as pattern for {@link java.text.SimpleDateFormat}</td>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATETIME datetime}</td>
 * <td>-</td>
 * <td>31-Dec-1999 23:59:59</td>
 * <td>Date+time default representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATETIME datetime}</td>
 * <td>{@link NlsFormatterManager#STYLE_SHORT short}</td>
 * <td>31/12/99 23:59</td>
 * <td>Date+time short representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATETIME datetime}</td>
 * <td>{@link NlsFormatterManager#STYLE_MEDIUM medium}</td>
 * <td>31-Dec-1999 23:59:59</td>
 * <td>Date+time medium representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATETIME datetime}</td>
 * <td>{@link NlsFormatterManager#STYLE_LONG long}</td>
 * <td>31 December 1999 23:59:59 GMT+01:00</td>
 * <td>Date+time long representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATETIME datetime}</td>
 * <td>{@link NlsFormatterManager#STYLE_FULL full}</td>
 * <td>Friday, 31 December 1999 23:59:59 o'clock GMT+01:00</td>
 * <td>Date+time full representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATETIME datetime}</td>
 * <td>{@link NlsFormatterManager#STYLE_ISO_8601 iso8601}</td>
 * <td>1999-12-31T23:59:59+01:00</td>
 * <td>Date+time ISO-8601 representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATE date}</td>
 * <td>yyyy.MM.dd-HH:mm:ss</td>
 * <td>2010.01.01-23:59:59</td>
 * <td>Any other style is treated as pattern for {@link java.text.SimpleDateFormat}</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_TYPE type}</td>
 * <td>-</td>
 * <td>java.util.List</td>
 * <td>{@link java.lang.reflect.Type} medium representation (omit {@code java.lang})</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_TYPE type}</td>
 * <td>{@link NlsFormatterManager#STYLE_SHORT short}</td>
 * <td>List</td>
 * <td>{@link java.lang.reflect.Type} short representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_TYPE type}</td>
 * <td>{@link NlsFormatterManager#STYLE_MEDIUM medium}</td>
 * <td>java.util.List</td>
 * <td>{@link java.lang.reflect.Type} medium representation (omit {@code java.lang})</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_TYPE type}</td>
 * <td>{@link NlsFormatterManager#STYLE_LONG long}</td>
 * <td>java.util.List&lt;String&gt;</td>
 * <td>{@link java.lang.reflect.Type} long representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_TYPE type}</td>
 * <td>{@link NlsFormatterManager#STYLE_FULL full}</td>
 * <td>java.util.List&lt;java.lang.String&gt;</td>
 * <td>{@link java.lang.reflect.Type} full representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_CHOICE choice}</td>
 * <td>(?==1)'error'(else)'errors'</td>
 * <td>error</td>
 * <td>A choice to distinguish singular/plural forms.</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_CHOICE choice}</td>
 * <td>(?>=5){key,choice,(?==true)'a'(else){key2}}(else)'c'</td>
 * <td>a</td>
 * <td>A choice that results to 'c' if the value is NOT greater or equal to 5. Otherwise it results to 'a' if
 * the value of the argument "key" is {@link Boolean#TRUE}. Otherwise it results to the value of the argument
 * "key2". - This is a wired example. It shows the power of the choice format as well as how things turn out
 * unmaintainable if the power is missused.</td>
 * </tr>
 * </table>
 *
 * For the term <em>internationalization</em> usually the shortcut <em>i18n</em> is used.
 *
 * @see NlsMessageFactory
 * @see net.sf.mmm.util.nls.api
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface NlsMessage extends NlsObject, Serializable {

  /**
   * The prefix appended to the {@link #getInternationalizedMessage() message} if the localization
   * (translation) failed.
   */
  String LOCALIZATION_FAILURE_PREFIX = "#";

  /**
   * This method gets the internationalized message that can be
   * {@link NlsTemplateResolver#resolveTemplate(String) translated} to a native language. The language
   * independent arguments are filled into the message after the translation process. <br>
   * E.g. the i18n message may be <code>"Welcome {name}!"</code> and there is one argument that is the string
   * {@code "Joelle"}. The final result will then be {@code "Welcome Joelle!"}. If the message is
   * translated to German as <code>"Willkommen {name}!"</code> the final result will be
   * {@code "Willkommen Joelle!"}.
   *
   * @see NlsMessage
   * @see #getArgument(String)
   * @see java.text.MessageFormat
   *
   * @return the message for internationalization.
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
   * This method gets the language independent argument for the given {@code key}.
   *
   * @param key is the name of the requested argument.
   * @return the argument for the given key or {@code null} if NOT defined.
   */
  Object getArgument(String key);

  /**
   * This method gets the language independent argument at the given {@code index}.
   *
   * @param index is the index of the requested argument.
   * @return the argument for the given key.
   * @deprecated use {@link #getArgument(String)} instead (and use named keys instead of numbers where
   *             possible).
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
   * This method tries to get the localized message as string using the {@link Locale#getDefault() default}
   * {@link Locale}. <br>
   * <b>ATTENTION:</b><br>
   * If possible try to avoid using this method and use {@link #getLocalizedMessage(Locale)} instead.
   *
   * @return the localized message.
   */
  String getLocalizedMessage();

  /**
   * This method gets the message localized to the given {@link Locale} as string. If this fails for whatever
   * reason, the {@link #getMessage() untranslated} message will be returned as fallback.
   *
   * @see net.sf.mmm.util.nls.api
   *
   * @param locale is the locale to translate to.
   * @return the localized message.
   */
  String getLocalizedMessage(Locale locale);

  /**
   * This method writes the localized message to the given {@code buffer}. <br>
   * The actual localization is done by a {@link NlsTemplate template} that is provided via the given
   * {@code resolver}. If this fails, the {@link #getInternationalizedMessage() original message} will be
   * used. After translation is done, the language independent arguments will be filled in the translated
   * message string according to the given {@code locale}.
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
   * The actual localization is done by a {@link NlsTemplate template} that is provided via the given
   * {@code resolver}. If this fails, the {@link #getInternationalizedMessage() original message} will be
   * used. After translation is done, the language independent arguments will be filled in the translated
   * message string according to the given {@code locale}.
   *
   * @param locale is the locale to translate to.
   * @param resolver is used to resolve the template required to translate the
   *        {@link #getInternationalizedMessage() internationalized message}.
   * @param buffer is the buffer where to write the message to.
   * @deprecated providing external {@link NlsTemplateResolver} has been discouraged.
   */
  @Deprecated
  void getLocalizedMessage(Locale locale, NlsTemplateResolver resolver, Appendable buffer);

}
