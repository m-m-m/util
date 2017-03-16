/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.util.Locale;

/**
 * This class is used as indirection for code that is NOT GWT compatible. For GWT this code gets super-sourced.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 * @deprecated use {@link BasicHelper} instead. This class will be deleted.
 */
@Deprecated
public final class GwtHelper {

  /**
   * The system specific line separator.
   */
  static final String LINE_SEPARATOR = System.getProperty(StringUtil.SYSTEM_PROPERTY_LINE_SEPARATOR);

  /**
   * Construction prohibited.
   */
  private GwtHelper() {

    super();
  }

  /**
   * Indirection for {@link System#getProperty(String)}.
   *
   * @param propertyName is the name of the requested property.
   * @return the result of {@link System#getProperty(String)}.
   */
  public static String getSystemProperty(String propertyName) {

    return System.getProperty(propertyName);
  }

  /**
   * Indirection for {@link String#toLowerCase(Locale)} with {@link Locale#US}.
   *
   * @param string is the {@link String}.
   * @return the result of {@link String#toLowerCase(Locale)}.
   * @since 4.0.0
   */
  public static String toLowerCase(String string) {

    return string.toLowerCase(Locale.US);
  }

  /**
   * Indirection for {@link String#toLowerCase(Locale)}.
   *
   * @param string is the {@link String}.
   * @param locale is the {@link Locale}.
   * @return the result of {@link String#toLowerCase(Locale)}.
   */
  public static String toLowerCase(String string, Locale locale) {

    return string.toLowerCase(locale);
  }

  /**
   * Indirection for {@link String#toUpperCase(Locale)}.
   *
   * @param string is the {@link String}.
   * @param locale is the {@link Locale}.
   * @return the result of {@link String#toUpperCase(Locale)}.
   */
  public static String toUpperCase(String string, Locale locale) {

    return string.toUpperCase(locale);
  }

  /**
   * Indirection for {@link String#toUpperCase(Locale)} with {@link Locale#US}.
   *
   * @param string is the {@link String}.
   * @return the result of {@link String#toUpperCase(Locale)}.
   */
  public static String toUpperCase(String string) {

    return string.toUpperCase(Locale.US);
  }

}
