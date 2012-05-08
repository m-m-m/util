/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter.api;

/**
 * This is the interface for a filter that {@link #accept(char) decides} if a given character is acceptable or
 * should be filtered.<br>
 * It is used by {@link net.sf.mmm.util.scanner.base.CharSequenceScanner} and avoids conversion of
 * <code>char</code> to {@link Character} for performance reasons. In other cases please prefer to use
 * {@link Filter}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface CharFilter {

  /**
   * This method determines if the given character <code>c</code> should be accepted.
   * 
   * @param c is the character to check.
   * @return <code>true</code> if the given character <code>c</code> is acceptable, <code>false</code> if it
   *         should be filtered.
   */
  boolean accept(char c);

  /**
   * A filter that only {@link #accept(char) accepts} characters valid for a technical identifier-string (e.g.
   * literal oder variable-name). This means accepted characters are Latin digits, ASCII letters or one of
   * '.', '_' or '-'.
   * 
   * @since 1.1.1
   */
  CharFilter IDENTIFIER_FILTER = new CharFilter() {

    public boolean accept(char c) {

      return (((c >= '0') && (c <= '9')) || ((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')) || (c == '_')
          || (c == '-') || (c == '.'));
    }
  };

  /**
   * A filter that only {@link #accept(char) accepts} the Latin digits '0'-'9' or ASCII letters 'a'-'z' and
   * 'A'-'Z'.
   * 
   * @since 1.1.0
   */
  CharFilter LATIN_DIGIT_OR_LETTER_FILTER = new CharFilter() {

    public boolean accept(char c) {

      return (((c >= '0') && (c <= '9')) || ((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')));
    }
  };

  /**
   * A filter that only {@link #accept(char) accepts} the Latin digits '0'-'9'.
   */
  CharFilter LATIN_DIGIT_FILTER = new CharFilter() {

    public boolean accept(char c) {

      return ((c >= '0') && (c <= '9'));
    }
  };

  /**
   * A filter that only {@link #accept(char) accepts} the Latin ASCII letters 'a'-'z' and 'A'-'Z'.
   */
  CharFilter ASCII_LETTER_FILTER = new CharFilter() {

    public boolean accept(char c) {

      return (((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')));
    }
  };

  /**
   * A filter that only {@link #accept(char) accepts} the lower case Latin ASCII letters 'a'-'z'.
   * 
   * @since 2.0.0
   */
  CharFilter ASCII_LOWER_CASE_LETTER_FILTER = new CharFilter() {

    public boolean accept(char c) {

      return (((c >= 'a') && (c <= 'z')));
    }
  };

  /**
   * A filter that only {@link #accept(char) accepts} the upper case Latin ASCII letters 'A'-'Z'.
   * 
   * @since 2.0.0
   */
  CharFilter ASCII_UPPER_CASE_LETTER_FILTER = new CharFilter() {

    public boolean accept(char c) {

      return (((c >= 'A') && (c <= 'Z')));
    }
  };

  /**
   * A filter that {@link #accept(char) accepts} only {@link Character#isWhitespace(char) whitespaces}.
   */
  CharFilter WHITESPACE_FILTER = new CharFilter() {

    public boolean accept(char c) {

      return Character.isWhitespace(c);
    }
  };

}
