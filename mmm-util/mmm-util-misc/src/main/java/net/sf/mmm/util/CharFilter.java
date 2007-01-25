/* $Id$ */
package net.sf.mmm.util;

/**
 * This is the interface for a filter that {@link #accept(char) decides} if a
 * given character is acceptable or should be filtered.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface CharFilter {

  /**
   * This method determines if the given character <code>c</code> should be
   * accepted.
   * 
   * @param c
   *        is the character to check.
   * @return <code>true</code> if the given character <code>c</code> is
   *         acceptable, <code>false</code> if it should be filtered.
   */
  boolean accept(char c);

  /**
   * A filter that {@link #accept(char) accepts} only the latin digits '0'-'9'.
   */
  CharFilter LATIN_DIGIT_FILTER = new CharFilter() {

    public boolean accept(char c) {

      return ((c >= '0') && (c <= '9'));
    }
  };

  /**
   * A filter that {@link #accept(char) accepts} only the latin ASCII letters
   * 'a'-'z' and 'A'-'Z'.
   */
  CharFilter ASCII_LETTER_FILTER = new CharFilter() {

    public boolean accept(char c) {

      return (((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c >= 'Z')));
    }
  };

  /**
   * A filter that {@link #accept(char) accepts} only
   * {@link Character#isWhitespace(char) whitespaces}.
   */
  CharFilter WHITESPACE_FILTER = new CharFilter() {

    public boolean accept(char c) {

      return Character.isWhitespace(c);
    }
  };

}
