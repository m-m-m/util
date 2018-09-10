/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * Simple static helper for dealing with escaped characters.
 *
 * @author hohwille
 * @since 7.6.0
 */
public class CharEscapeHelper {

  private static final Character CHAR_TAB = Character.valueOf('\t');

  private static final Character CHAR_BACKSLASH = Character.valueOf('\\');

  private static final Character CHAR_CR = Character.valueOf('\r');

  private static final Character CHAR_LF = Character.valueOf('\n');

  private static final Character CHAR_SINGLE_QUOTE = Character.valueOf('\'');

  private static final Character CHAR_DOUBLE_QUOTE = Character.valueOf('\"');

  private static final Character CHAR_FF = Character.valueOf('\f');

  private static final Character CHAR_BS = Character.valueOf('\b');

  private static final Character CHAR_NUL = Character.valueOf('\0');

  private static final Character CHAR_SOH = Character.valueOf('\1');

  private static final Character CHAR_STX = Character.valueOf('\2');

  private static final Character CHAR_ETX = Character.valueOf('\3');

  private static final Character CHAR_EOT = Character.valueOf('\4');

  private static final Character CHAR_ENQ = Character.valueOf('\5');

  private static final Character CHAR_ACK = Character.valueOf('\6');

  private static final Character CHAR_BEL = Character.valueOf('\7');

  /**
   * @param c the character that was escaped (e.g. 't' for tab, 'n' for line feed, 'r' for carriage return,
   *        '0' for NUL, etc.)
   * @return the resolved (unescaped) character according to JLS 3.10.6 or {@code null} for invalid escape
   *         character.
   * @see #resolveEscape(String)
   */
  public static Character resolveEscape(char c) {

    switch (c) {
      case '0':
        return CHAR_NUL;
      case 't':
        return CHAR_TAB;
      case '\\':
        return CHAR_BACKSLASH;
      case 'r':
        return CHAR_CR;
      case 'n':
        return CHAR_LF;
      case '\'':
        return CHAR_SINGLE_QUOTE;
      case '\"':
        return CHAR_DOUBLE_QUOTE;
      case 'f':
        return CHAR_FF;
      case 'b':
        return CHAR_BS;
      case '1':
        return CHAR_SOH;
      case '2':
        return CHAR_STX;
      case '3':
        return CHAR_ETX;
      case '4':
        return CHAR_EOT;
      case '5':
        return CHAR_ENQ;
      case '6':
        return CHAR_ACK;
      case '7':
        return CHAR_BEL;
    }
    return null;
  }

  /**
   * @param sequence the sequence of characters that has been escaped (e.g. "u000A" for line feed, or "u00df"
   *        for szlig/ß, etc.)
   * @return the resolved (unescaped) character according to JLS 3.10.6 or {@code null} for invalid escape
   *         sequence.
   * @see #resolveEscape(char)
   */
  public static Character resolveEscape(String sequence) {

    if (sequence == null) {
      return null;
    }
    int length = sequence.length();
    if (length == 0) {
      return null;
    } else if (length == 1) {
      return resolveEscape(sequence.charAt(0));
    } else if (length < 4) { // octal C compatibility legacy stuff
      for (int i = 0; i < length; i++) {
        char c = sequence.charAt(i);
        if ((c < '0') || (c > '7')) {
          return null;
        }
      }
      int value = Integer.parseInt(sequence, 8);
      if (value <= 0377) {
        return Character.valueOf((char) value);
      }
    } else if (length >= 5) {
      int start = length - 4;
      for (int i = start - 1; i >= 0; i--) {
        if (sequence.charAt(i) != 'u') {
          return null;
        }
      }
      int value = Integer.parseInt(sequence.substring(start), 16);
      return Character.valueOf((char) value);
    }
    return null;
  }
}
