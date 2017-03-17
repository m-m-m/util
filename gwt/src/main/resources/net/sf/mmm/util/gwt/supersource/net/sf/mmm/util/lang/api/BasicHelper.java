/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.util.Locale;

/**
 * This class is used as indirection for code that is NOT GWT compatible. For GWT this code gets
 * super-sourced.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.4.0
 */
public final class BasicHelper {

  /**
   * The system specific line separator.
   */
  static final String LINE_SEPARATOR = "\n";

  /**
   * Construction prohibited.
   */
  private BasicHelper() {

    super();
  }

  /**
   * Indirection for {@link System#getProperty(String)}.
   *
   * @param propertyName is the name of the requested property.
   * @return the result of {@link System#getProperty(String)}.
   */
  public static String getSystemProperty(String propertyName) {

    if ("line.separator".equals(propertyName)) {
      return "\n";
    }
    return null;
  }
  /**
   * Indirection for {@link String#toLowerCase(Locale)}.
   *
   * @param string is the {@link String}.
   * @return the result of {@link String#toLowerCase(Locale)}.
   */
  public static String toLowerCase(String string) {

    return string.toLowerCase();
  }

  /**
   * Indirection for {@link String#toLowerCase(Locale)}.
   *
   * @param string is the {@link String}.
   * @param locale is the {@link Locale}.
   * @return the result of {@link String#toLowerCase(Locale)}.
   */
  public static String toLowerCase(String string, Locale locale) {

    return string.toLowerCase();
  }

  /**
   * Indirection for {@link String#toUpperCase(Locale)}.
   *
   * @param string is the {@link String}.
   * @return the result of {@link String#toUpperCase(Locale)}.
   */
  public static String toUpperCase(String string) {

    return string.toUpperCase();
  }

  /**
   * Indirection for {@link String#toUpperCase(Locale)}.
   *
   * @param string is the {@link String}.
   * @param locale is the {@link Locale}.
   * @return the result of {@link String#toUpperCase(Locale)}.
   */
  public static String toUpperCase(String string, Locale locale) {

    return string.toUpperCase();
  }

}
