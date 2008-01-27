/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;

/**
 * This class is a collection of utility functions for {@link String} handling
 * and manipulation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class StringUtil {

  /** @see #getInstance() */
  private static StringUtil instance;

  /** a string representing the boolean value <code>true</code> */
  public static final String TRUE = String.valueOf(true);

  /** a string representing the boolean value <code>false</code> */
  public static final String FALSE = String.valueOf(false);

  /** The line-separator string. */
  public static final String LINE_SEPARATOR = System.getProperty("line.separator");

  /** an empty {@link String} array */
  public static final String[] EMPTY_STRING_ARRAY = new String[0];

  /** @see #toCamlCase(String) */
  private static final char[] SEPARATORS = new char[] { ' ', '-', '_', '.' };

  /**
   * The constructor.
   */
  public StringUtil() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link StringUtil}.<br>
   * This design is the best compromise between easy access (via this
   * indirection you have direct, static access to all offered functionality)
   * and IoC-style design which allows extension and customization.<br>
   * For IoC usage, simply ignore all static {@link #getInstance()} methods and
   * construct new instances via the container-framework of your choice (like
   * plexus, pico, springframework, etc.). To wire up the dependent components
   * everything is properly annotated using common-annotations (JSR-250). If
   * your container does NOT support this, you should consider using a better
   * one.
   * 
   * @return the singleton instance.
   */
  public static StringUtil getInstance() {

    if (instance == null) {
      synchronized (StringUtil.class) {
        if (instance == null) {
          instance = new StringUtil();
        }
      }
    }
    return instance;
  }

  /**
   * This method parses a boolean value given as string.
   * 
   * @see Boolean#valueOf(String)
   * 
   * @param booleanValue is the boolean value as string.
   * @return <code>true</code> if the given string
   *         {@link String#equalsIgnoreCase(java.lang.String) equals} to
   *         {@link #TRUE true}, <code>false</code> if it
   *         {@link String#equalsIgnoreCase(java.lang.String) equals} to
   *         {@link #FALSE false} and <code>null</code> in any other case.
   */
  public Boolean parseBoolean(String booleanValue) {

    if (TRUE.equalsIgnoreCase(booleanValue)) {
      return Boolean.TRUE;
    } else if (FALSE.equalsIgnoreCase(booleanValue)) {
      return Boolean.FALSE;
    } else {
      return null;
    }
  }

  /**
   * This method replaces all occurrences of <code>oldChar</code> in the
   * char-array given by <code>string</code> with <code>newChar</code>.
   * 
   * @see String#replace(char, char)
   * 
   * @param string is the char-array where the replacement should take place.
   * @param oldChar is the character to be replaced.
   * @param newChar is the replacement for <code>oldChar</code>.
   */
  public void replace(char[] string, char oldChar, char newChar) {

    for (int i = 0; i < string.length; i++) {
      if (string[i] == oldChar) {
        string[i] = newChar;
      }
    }
  }

  /**
   * This method replaces all occurrences of the string <code>match</code>
   * with the string <code>replace</code> in the given string.
   * 
   * @param string is the string where to replace.
   * @param match is the string that is searched and replaced.
   * @param replace is the string <code>match</code> is substituted with.
   * @return the given string with all occurrences of <code>match</code>
   *         replaced by <code>replace</code>.
   */
  public String replace(String string, String match, String replace) {

    int matchLen = match.length();
    int replaceLen = replace.length();
    int delta = replaceLen - matchLen;
    if (delta < 0) {
      // assume no replacement...
      delta = 0;
    } else {
      // assume two replacements...
      delta = delta + delta;
    }
    StringBuilder result = new StringBuilder(string.length() + delta);
    int oldPos = 0;
    int pos = string.indexOf(match);
    while (pos >= 0) {
      if (pos > oldPos) {
        result.append(string.substring(oldPos, pos));
      }
      result.append(replace);
      oldPos = pos + matchLen;
      pos = string.indexOf(match, oldPos);
    }
    if (oldPos < string.length()) {
      result.append(string.substring(oldPos));
    }
    return result.toString();
  }

  /**
   * This method delegates to
   * {@link #replaceSuffixWithCase(String, int, String, Locale)} using
   * {@link Locale#ENGLISH}.
   * 
   * @see #replaceSuffixWithCase(String, int, String, Locale)
   * 
   * @param string is the string to replace.
   * @param suffixLength is the length of the suffix from <code>string</code>
   *        to replace.
   * @param newSuffixLowerCase is the new suffix for the given
   *        <code>string</code> in {@link String#toLowerCase() lower-case}.
   * @return the given <code>string</code> with the last
   *         <code>suffixLength</code> characters cut off and replaced by
   *         <code>newSuffixLowerCase</code> with respect to the original case
   *         of <code>string</code>.
   */
  public String replaceSuffixWithCase(String string, int suffixLength, String newSuffixLowerCase) {

    return replaceSuffixWithCase(string, suffixLength, newSuffixLowerCase, Locale.ENGLISH);
  }

  /**
   * This method replaces the last <code>suffixLength</code> number of
   * characters from <code>string</code> with the lower-case string
   * <code>newSuffixLowerCase</code> with respect to the original case of the
   * given <code>string</code>.<br>
   * Here are some examples for {@link Locale#ENGLISH}:<br>
   * <table border="1">
   * <tr>
   * <th><code>string</code></th>
   * <th><code>suffixLength</code></th>
   * <th><code>newSuffixLowerCase</code></th>
   * <th><code>{@link #replaceSuffixWithCase(String, int, String)}</code></th>
   * </tr>
   * <tr>
   * <td>foobar</td>
   * <td>3</td>
   * <td>foo</td>
   * <td>foofoo</td>
   * </tr>
   * <tr>
   * <td>FOOBAR</td>
   * <td>3</td>
   * <td>foo</td>
   * <td>FOOFOO</td>
   * </tr>
   * <tr>
   * <td>FooBar</td>
   * <td>3</td>
   * <td>foo</td>
   * <td>FooFoo</td>
   * </tr>
   * <tr>
   * <td>FooBar</td>
   * <td>2</td>
   * <td>foo</td>
   * <td>FooBfoo</td>
   * </tr>
   * </table>
   * 
   * @param string is the string to replace.
   * @param suffixLength is the length of the suffix from <code>string</code>
   *        to replace.
   * @param newSuffixLowerCase is the new suffix for the given
   *        <code>string</code> in {@link String#toLowerCase() lower-case}.
   * @param locale is the locale used for case transformation.
   * @return the given <code>string</code> with the last
   *         <code>suffixLength</code> characters cut off and replaced by
   *         <code>newSuffixLowerCase</code> with respect to the original case
   *         of <code>string</code>.
   */
  public String replaceSuffixWithCase(String string, int suffixLength, String newSuffixLowerCase,
      Locale locale) {

    int stringLength = string.length();
    int newSuffixLength = newSuffixLowerCase.length();
    int replaceIndex = stringLength - suffixLength;
    if (replaceIndex < 0) {
      replaceIndex = 0;
    }
    StringBuilder result = new StringBuilder(replaceIndex + newSuffixLength);
    if (replaceIndex > 0) {
      result.append(string.substring(0, replaceIndex));
    }
    if (suffixLength > 0) {
      char c = string.charAt(replaceIndex);
      // all lower case?
      if (c == Character.toLowerCase(c)) {
        result.append(newSuffixLowerCase);
      } else {
        // first replaced char is upper case!
        if (suffixLength > 1) {
          c = string.charAt(replaceIndex + 1);
          // Capitalized or upper case?
          if (c != Character.toLowerCase(c)) {
            // all upper case
            result.append(newSuffixLowerCase.toUpperCase(locale));
          } else {
            // capitalize
            String capital = newSuffixLowerCase.substring(0, 1).toUpperCase(locale);
            if (capital.length() == 1) {
              result.append(newSuffixLowerCase);
              result.setCharAt(replaceIndex, capital.charAt(0));
            } else {
              result.append(capital);
              result.append(newSuffixLowerCase.substring(1));
            }
          }
        }
      }
    }
    return result.toString();
  }

  /**
   * This method determines if the given string contains no information.
   * 
   * @see #isEmpty(String, boolean)
   * 
   * @param string is the string to check.
   * @return <code>true</code> if the given string is <code>null</code> or
   *         has a trimmed length of zero, <code>false</code> otherwise.
   */
  public boolean isEmpty(String string) {

    return isEmpty(string, true);
  }

  /**
   * This method determines if the given string is empty.
   * 
   * @param string is the string to check.
   * @param trim if whitespaces should be ignored and a string with a trimmed
   *        length of zero is considered as empty.
   * @return <code>true</code> if the given string is <code>null</code> or
   *         has a (trimmed) length of zero, <code>false</code> otherwise.
   */
  public boolean isEmpty(String string, boolean trim) {

    if (string == null) {
      return true;
    }
    String s;
    if (trim) {
      s = string.trim();
    } else {
      s = string;
    }
    return (s.length() == 0);
  }

  /**
   * This method escapes the given <code>string</code> for usage in XML (or
   * HTML, etc.).
   * 
   * @param string is the string to escape.
   * @param escapeQuotations if <code>true</code> also the ASCII quotation
   *        characters (apos <code>'\''</code> and quot <code>'"'</code>)
   *        will be escaped, else if <code>false</code> quotations are
   *        untouched. Set this to <code>true</code> if you are writing the
   *        value of an attribute.
   * @return the escaped string.
   */
  public String escapeXml(String string, boolean escapeQuotations) {

    try {
      StringWriter writer = new StringWriter(string.length() + 8);
      escapeXml(string, writer, escapeQuotations);
      return writer.toString();
    } catch (IOException e) {
      throw new IllegalStateException("Internal error!", e);
    }
  }

  /**
   * This method writes the given <code>string</code> to the
   * <code>writer</code> while escaping special characters for XML (or HTML,
   * etc.).
   * 
   * @param string is the string to escape.
   * @param writer is where to write the string to.
   * @param escapeQuotations if <code>true</code> also the ASCII quotation
   *        characters (apos <code>'\''</code> and quot <code>'"'</code>)
   *        will be escaped, else if <code>false</code> quotations are
   *        untouched. Set this to <code>true</code> if you are writing the
   *        value of an attribute.
   * @throws IOException if the <code>writer</code> produced an I/O error.
   */
  public void escapeXml(String string, Writer writer, boolean escapeQuotations) throws IOException {

    char[] chars = string.toCharArray();
    for (char c : chars) {
      if (c >= 128) {
        writer.append("&#");
        writer.append(Integer.toString(c));
        writer.append(";");
      } else if (c == '&') {
        writer.append("&amp;");
      } else if (c == '<') {
        writer.append("&lt;");
      } else if (c == '>') {
        writer.append("&gt;");
      } else if (escapeQuotations && (c == '\'')) {
        // writer.append("&apos;");
        writer.append("&#39;");
      } else if (escapeQuotations && (c == '"')) {
        writer.append("&quot;");
      } else {
        writer.append(c);
      }
    }
  }

  /**
   * This method formats a positive number to a string with at least the given
   * number of digits padding it with leading zeros.<br>
   * Examples:
   * <ul>
   * <li><code>padNumber(5, 3)</code> will return <code>"005"</code></li>
   * <li><code>padNumber(25, 3)</code> will return <code>"025"</code></li>
   * <li><code>padNumber(100, 3)</code> will return <code>"100"</code></li>
   * </ul>
   * 
   * @param number is the positive number to format.
   * @param digits is the (minimum) number of digits required.
   * @return the number as string with the length of (at least)
   *         <code>digits</code>. If the number is less, leading zeros are
   *         appended.
   */
  public String padNumber(long number, int digits) {

    return padNumber(number, digits, 10);
  }

  /**
   * This method formats a positive number to a string using the given
   * <code>radix</code> with at least the given number of digits padding it
   * with leading zeros.<br>
   * Examples:
   * <ul>
   * <li><code>padNumber(31, 3, 16)</code> will return <code>"01f"</code></li>
   * <li><code>padNumber(5, 6, 2)</code> will return <code>"000101"</code></li>
   * </ul>
   * 
   * To pad a number to a constant length you can use the following value for
   * <code>digits</code>: <br>
   * <table border="1">
   * <tr>
   * <th>Radix</th>
   * <th>byte</th>
   * <th>short</th>
   * <th>int</th>
   * <th>long</th>
   * </tr>
   * <tr>
   * <th>2</th>
   * <td>8</td>
   * <td>16</td>
   * <td>32</td>
   * <td>64</td>
   * </tr>
   * <tr>
   * <th>8</th>
   * <td>3</td>
   * <td>6</td>
   * <td>9</td>
   * <td>12</td>
   * </tr>
   * <tr>
   * <th>10</th>
   * <td>3</td>
   * <td>5</td>
   * <td>9</td>
   * <td>19</td>
   * </tr>
   * <tr>
   * <th>16</th>
   * <td>2</td>
   * <td>4</td>
   * <td>8</td>
   * <td>16</td>
   * </tr>
   * </table>
   * 
   * @param number is the positive number to format.
   * @param digits is the (minimum) number of digits required.
   * @param radix is the radix to use.
   * @return the number as string with the length of (at least)
   *         <code>digits</code>. If the number is less, leading zeros are
   *         appended.
   */
  public String padNumber(long number, int digits, int radix) {

    String result = Long.toString(number, radix);
    int leadingZeros = digits - result.length();
    if (leadingZeros > 0) {
      int capacity = result.length() + leadingZeros;
      StringBuffer buffer = new StringBuffer(capacity);
      buffer.append(result);
      while (leadingZeros > 0) {
        buffer.append('0');
        leadingZeros--;
      }
      result = buffer.toString();
    }
    return result;
  }

  /**
   * This method converts the given <code>string</code> to caml-case syntax
   * using the default separators <code>' '</code>, <code>'-'</code>,
   * <code>'_'</code> and <code>'.'</code>.
   * 
   * @see #toCamlCase(String, char[])
   * 
   * @param string is the string to convert.
   * @return the given <code>string</code> in caml-case syntax.
   */
  public String toCamlCase(String string) {

    return toCamlCase(string, SEPARATORS);
  }

  /**
   * This method converts the given <code>string</code> to caml-case syntax.<br>
   * In caml-case syntax words are written without a separator but each new word
   * starts with a capitalized letter. This method removes all characters from
   * the given <code>string</code> that are in the list given by
   * <code>separators</code> and capitalizes the first character of the
   * following word.<br>
   * Examples for separators ' ', '-', '_', '.': <br>
   * <table border="1">
   * <tr>
   * <th>string</th>
   * <th>toCamlCase(string)</th>
   * </tr>
   * <tr>
   * <td><code>foo bar</code></td>
   * <td><code>fooBar</code></td>
   * </tr>
   * <tr>
   * <td><code>aAa-bBB-CcC</code></td>
   * <td><code>aAaBBBCcC</code></td>
   * </tr>
   * <tr>
   * <td><code>X--m_._l..</code></td>
   * <td><code>XML</code></td>
   * </tr>
   * </table>
   * 
   * @param string is the string to convert.
   * @param separators is the list of characters that are treated as
   *        word-separators.
   * @return the given <code>string</code> in caml-case syntax.
   */
  public String toCamlCase(String string, char... separators) {

    char[] chars = string.toCharArray();
    StringBuffer buffer = new StringBuffer(chars.length);
    int pos = 0;
    boolean lastSeparator = false;
    for (int i = 0; i < chars.length; i++) {
      char c = chars[i];
      boolean isSeparator = false;
      for (char s : separators) {
        if (c == s) {
          isSeparator = true;
          break;
        }
      }
      if (isSeparator) {
        lastSeparator = true;
        if (pos < i) {
          buffer.append(chars, pos, i - pos);
        }
        pos = i + 1;
      } else if (lastSeparator) {
        buffer.append(Character.toUpperCase(c));
        pos++;
        lastSeparator = false;
      }
    }
    if (!lastSeparator) {
      buffer.append(chars, pos, chars.length - pos);
    }
    return buffer.toString();
  }

}
