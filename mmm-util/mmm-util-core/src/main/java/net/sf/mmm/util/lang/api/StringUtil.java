/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.io.IOException;
import java.io.Writer;
import java.util.Locale;

/**
 * This is the interface for a collection of utility functions that help with
 * {@link String} handling and manipulation.
 * 
 * @see net.sf.mmm.util.lang.base.StringUtilImpl#getInstance()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface StringUtil {

  /** a string representing the boolean value <code>true</code> */
  String TRUE = String.valueOf(true);

  /** a string representing the boolean value <code>false</code> */
  String FALSE = String.valueOf(false);

  /** The line-separator string. */
  String LINE_SEPARATOR = System.getProperty("line.separator");

  /** an empty {@link String} array */
  String[] EMPTY_STRING_ARRAY = new String[0];

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
  Boolean parseBoolean(String booleanValue);

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
  void replace(char[] string, char oldChar, char newChar);

  /**
   * This method replaces all occurrences of the string <code>match</code> with
   * the string <code>replace</code> in the given string.
   * 
   * @param string is the string where to replace.
   * @param match is the string that is searched and replaced.
   * @param replace is the string <code>match</code> is substituted with.
   * @return the given string with all occurrences of <code>match</code>
   *         replaced by <code>replace</code>.
   */
  String replace(String string, String match, String replace);

  /**
   * This method delegates to
   * {@link #replaceSuffixWithCase(String, int, String, Locale)} using
   * {@link Locale#ENGLISH}.
   * 
   * @see #replaceSuffixWithCase(String, int, String, Locale)
   * 
   * @param string is the string to replace.
   * @param suffixLength is the length of the suffix from <code>string</code> to
   *        replace.
   * @param newSuffixLowerCase is the new suffix for the given
   *        <code>string</code> in {@link String#toLowerCase() lower-case}.
   * @return the given <code>string</code> with the last
   *         <code>suffixLength</code> characters cut off and replaced by
   *         <code>newSuffixLowerCase</code> with respect to the original case
   *         of <code>string</code>.
   */
  String replaceSuffixWithCase(String string, int suffixLength, String newSuffixLowerCase);

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
   * @param suffixLength is the length of the suffix from <code>string</code> to
   *        replace.
   * @param newSuffixLowerCase is the new suffix for the given
   *        <code>string</code> in {@link String#toLowerCase() lower-case}.
   * @param locale is the locale used for case transformation.
   * @return the given <code>string</code> with the last
   *         <code>suffixLength</code> characters cut off and replaced by
   *         <code>newSuffixLowerCase</code> with respect to the original case
   *         of <code>string</code>.
   */
  String replaceSuffixWithCase(String string, int suffixLength, String newSuffixLowerCase,
      Locale locale);

  /**
   * This method determines if the given string contains no information.
   * 
   * @see #isEmpty(String, boolean)
   * 
   * @param string is the string to check.
   * @return <code>true</code> if the given string is <code>null</code> or has a
   *         trimmed length of zero, <code>false</code> otherwise.
   */
  boolean isEmpty(String string);

  /**
   * This method determines if the given string is empty.
   * 
   * @param string is the string to check.
   * @param trim if whitespaces should be ignored and a string with a trimmed
   *        length of zero is considered as empty.
   * @return <code>true</code> if the given string is <code>null</code> or has a
   *         (trimmed) length of zero, <code>false</code> otherwise.
   */
  boolean isEmpty(String string, boolean trim);

  /**
   * This method escapes the given <code>string</code> for usage in XML (or
   * HTML, etc.).
   * 
   * @param string is the string to escape.
   * @param escapeQuotations if <code>true</code> also the ASCII quotation
   *        characters (apos <code>'\''</code> and quot <code>'"'</code>) will
   *        be escaped, else if <code>false</code> quotations are untouched. Set
   *        this to <code>true</code> if you are writing the value of an
   *        attribute.
   * @return the escaped string.
   */
  String escapeXml(String string, boolean escapeQuotations);

  /**
   * This method writes the given <code>string</code> to the <code>writer</code>
   * while escaping special characters for XML (or HTML, etc.).
   * 
   * @param string is the string to escape.
   * @param writer is where to write the string to.
   * @param escapeQuotations if <code>true</code> also the ASCII quotation
   *        characters (apos <code>'\''</code> and quot <code>'"'</code>) will
   *        be escaped, else if <code>false</code> quotations are untouched. Set
   *        this to <code>true</code> if you are writing the value of an
   *        attribute.
   * @throws IOException if the <code>writer</code> produced an I/O error.
   */
  void escapeXml(String string, Writer writer, boolean escapeQuotations) throws IOException;

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
  String padNumber(long number, int digits);

  /**
   * This method formats a positive number to a string using the given
   * <code>radix</code> with at least the given number of digits padding it with
   * leading zeros.<br>
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
  String padNumber(long number, int digits, int radix);

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
  String toCamlCase(String string);

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
  String toCamlCase(String string, char... separators);

  /**
   * This method converts the given <code>string</code> from caml-case syntax to
   * lower-case using the given <code>separator</code> as word-boundary.<br>
   * Example:<br>
   * <table border="1">
   * <tr>
   * <th>string</th>
   * <th>separator</th>
   * <th>{@link #fromCamlCase(String, char) fromCamlCase}(string, separator)</th>
   * </tr>
   * <tr>
   * <td>FooBar</td>
   * <td>-</td>
   * <td>foo-bar</td>
   * </tr>
   * <tr>
   * <td>someWordMix</td>
   * <td>.</td>
   * <td>some.word.mix</td>
   * </tr>
   * <tr>
   * <td>AbbreviationsLikeXMLshouldNotBeCapitalized</td>
   * <td>_</td>
   * <td>abbreviations_like_xmlshould_not_be_capitalized</td>
   * </tr>
   * </table>
   * 
   * @see #toCamlCase(String, char...)
   * 
   * @param string is the string to convert.
   * @param separator is the character to insert at word-boundaries indicated by
   *        a switch from lower- to upper-case.
   * @return the given <code>string</code> in lower-case with the given
   *         <code>separator</code> inserted at word-boundaries.
   */
  String fromCamlCase(String string, char separator);

}
