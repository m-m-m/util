/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.util.Locale;

/**
 * Static helper for {@link Locale} specific stuff.
 *
 * @author hohwille
 * @since 7.6.0
 */
public final class LocaleHelper {

  /** The default separator used for {@link #getLocaleInfixes(Locale) locale-infixes}. */
  public static final char SEPARATOR_DEFAULT = '_';

  /** The separator used for {@link Locale#forLanguageTag(String) language tag}. */
  public static final char SEPARATOR_FOR_LANGUAGE_TAG = '-';

  private LocaleHelper() {

    super();
  }

  /**
   * This method determines the infix-strings for localization lookup ordered from most specific to least
   * specific (empty string representing {@link Locale#ROOT}). Each infix is defined as:
   *
   * <pre>
   * [_&lt;{@link Locale#getLanguage() language}&gt;[_&lt;{@link Locale#getCountry() country}&gt;[_&lt;{@link Locale#getVariant() variant}&gt;]]]
   * </pre>
   *
   * Please note that if a segment is empty but a following segment is present, multiple underscores ('_')
   * will occur. <br>
   * Examples:
   * <table border="1">
   * <tr>
   * <th>locale</th>
   * <th>result</th>
   * </tr>
   * <tr>
   * <td>{@link Locale#GERMANY}</td>
   * <td><code>{"_de_DE", "_de", ""}</code></td>
   * </tr>
   * <tr>
   * <td><code>new {@link Locale#Locale(String, String) Locale}("", "CM")</code></td>
   * <td><code>{"__CM", ""}</code></td>
   * </tr>
   * <tr>
   * <td><code>new {@link Locale#Locale(String, String, String) Locale}("", "", "variant")</code></td>
   * <td><code>{"___variant", ""}</code></td>
   * </tr>
   * </table>
   *
   * @param locale is the {@link Locale}.
   * @return the localization-infixes ordered from most specific to least specific. The returned array will
   *         always contain the empty string as last entry.
   */
  public static String[] getLocaleInfixes(Locale locale) {

    String[] infixes;
    int length = 1;
    StringBuilder infix = new StringBuilder();
    String infixLang = null;
    String language = locale.getLanguage();
    infix.append(SEPARATOR_DEFAULT);
    if ((language != null) && (language.length() == 2)) {
      infix.append(language);
      infixLang = infix.toString();
      length++;
    }
    String infixCountry = null;
    String country = locale.getCountry();
    infix.append(SEPARATOR_DEFAULT);
    if ((country != null) && (country.length() == 2)) {
      infix.append(country);
      infixCountry = infix.toString();
      length++;
    }
    String infixVariant = null;
    String variant = locale.getVariant();
    infix.append(SEPARATOR_DEFAULT);
    if ((variant != null) && (variant.length() > 0)) {
      infix.append(variant);
      infixVariant = infix.toString();
      length++;
    }
    infixes = new String[length];
    int i = 0;
    if (infixVariant != null) {
      infixes[i] = infixVariant;
      i++;
    }
    if (infixCountry != null) {
      infixes[i] = infixCountry;
      i++;
    }
    if (infixLang != null) {
      infixes[i] = infixLang;
      i++;
    }
    infixes[i] = "";
    return infixes;
  }

  /**
   * Create a {@link Locale} from a given {@link String} representation such as {@link Locale#toString()} or
   * {@link Locale#forLanguageTag(String)}.
   *
   * @param locale the {@link String} representation of the {@link Locale}.
   * @return the parsed {@link Locale}.
   * @since 7.3.0
   */
  public static Locale getLocale(String locale) {

    String languageTag = locale.replace(SEPARATOR_DEFAULT, SEPARATOR_FOR_LANGUAGE_TAG);
    if (!languageTag.isEmpty() && (languageTag.charAt(0) == SEPARATOR_FOR_LANGUAGE_TAG)) {
      languageTag = languageTag.substring(1); // tolerant for accepting suffixes like "_en_US" / "-en-US"
    }
    return Locale.forLanguageTag(languageTag);
  }

}
