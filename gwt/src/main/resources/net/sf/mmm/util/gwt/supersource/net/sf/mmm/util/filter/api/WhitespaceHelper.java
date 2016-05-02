/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.filter.api;

/**
 * GWT compatible variant of {@code net.sf.mmm.util.filter.api.WhitespaceHelper}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
final class WhitespaceHelper {

  /** @see #isWhitespace(char) */
  private static final char[] WHITESPACES = new char[] { 0x00009, 0x0000A, 0x0000B, 0x0000C, 0x0000D, 0x0001C, 0x0001D,
      0x0001E, 0x0001F, 0x00020 };

  /**
   * Construction prohibited.
   */
  private WhitespaceHelper() {

    super();
  }

  /**
   * @param c is the character to check.
   * @return {@code true} if {@link Character#isWhitespace(char) whitespace}, {@code false}
   *         otherwise.
   */
  public static boolean isWhitespace(char c) {

    for (char whitespace : WHITESPACES) {
      if (c == whitespace) {
        return true;
      }
    }
    return false;
  }

}
