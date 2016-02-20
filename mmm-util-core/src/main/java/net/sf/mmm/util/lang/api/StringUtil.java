/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.util.Collection;
import java.util.List;
import java.util.Locale;

import net.sf.mmm.util.component.api.ComponentSpecification;
import net.sf.mmm.util.value.api.ValueConverter;

/**
 * This is the interface for a collection of utility functions that help with {@link String} handling and manipulation.
 *
 * @see net.sf.mmm.util.lang.base.StringUtilImpl#getInstance()
 * @see net.sf.mmm.util.text.api.UnicodeUtil
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
@ComponentSpecification
public interface StringUtil {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.util.lang.api.StringUtil";

  /** a string representing the boolean value <code>true</code> */
  String TRUE = String.valueOf(true);

  /** a string representing the boolean value <code>false</code> */
  String FALSE = String.valueOf(false);

  /** a string representing the undefined value <code>null</code>. */
  String NULL = "null";

  /** The line-separator with a single carriage-return. */
  String LINE_SEPARATOR_CR = "\r";

  /** The Unix line-separator (line-feed). */
  String LINE_SEPARATOR_LF = "\n";

  /** The Windows line-separator (carriage-return line-feed). */
  String LINE_SEPARATOR_CRLF = "\r\n";

  /** The Mac line-separator (line-feed carriage-return). */
  String LINE_SEPARATOR_LFCR = "\n\r";

  /**
   * The platform-specific line-separator string. Should be one of {@link #LINE_SEPARATOR_LF},
   * {@link #LINE_SEPARATOR_CRLF}, {@link #LINE_SEPARATOR_LFCR}, {@link #LINE_SEPARATOR_CR}.
   */
  String LINE_SEPARATOR = GwtHelper.LINE_SEPARATOR;

  /**
   * The {@link System#getProperty(String) system property name} for {@link #LINE_SEPARATOR}.
   *
   * @since 3.1.0
   */
  String SYSTEM_PROPERTY_LINE_SEPARATOR = "line.separator";

  /** An empty {@link String} array. */
  String[] EMPTY_STRING_ARRAY = new String[0];

  /**
   * An empty char array.
   *
   * @since 2.0.0
   */
  char[] EMPTY_CHAR_ARRAY = new char[0];

  /**
   * The platform-specific line-separator string. Should be one of {@link #LINE_SEPARATOR_LF},
   * {@link #LINE_SEPARATOR_CRLF}, {@link #LINE_SEPARATOR_LFCR}, {@link #LINE_SEPARATOR_CR}.
   *
   * @return the line separator.
   */
  String getLineSeparator();

  /**
   * This method parses a boolean value given as string.
   *
   * @see Boolean#valueOf(String)
   *
   * @param booleanValue is the boolean value as string.
   * @return <code>true</code> if the given string {@link String#equalsIgnoreCase(java.lang.String) equals} to
   *         {@link #TRUE true}, <code>false</code> if it {@link String#equalsIgnoreCase(java.lang.String) equals} to
   *         {@link #FALSE false} and <code>null</code> in any other case.
   */
  Boolean parseBoolean(String booleanValue);

  /**
   * This method replaces all occurrences of <code>oldChar</code> in the char-array given by <code>string</code> with
   * <code>newChar</code>.
   *
   * @see String#replace(char, char)
   *
   * @param string is the char-array where the replacement should take place.
   * @param oldChar is the character to be replaced.
   * @param newChar is the replacement for <code>oldChar</code>.
   */
  void replace(char[] string, char oldChar, char newChar);

  /**
   * This method replaces all occurrences of the string <code>match</code> with the string <code>replace</code> in the
   * given string.
   *
   * @param string is the string where to replace.
   * @param match is the string that is searched and replaced.
   * @param replace is the string <code>match</code> is substituted with.
   * @return the given string with all occurrences of <code>match</code> replaced by <code>replace</code>.
   */
  String replace(String string, String match, String replace);

  /**
   * This method delegates to {@link #replaceSuffixWithCase(String, int, String, Locale)} using {@link Locale#ENGLISH}.
   *
   * @see #replaceSuffixWithCase(String, int, String, Locale)
   *
   * @param string is the string to replace.
   * @param suffixLength is the length of the suffix from <code>string</code> to replace.
   * @param newSuffixLowerCase is the new suffix for the given <code>string</code> in {@link String#toLowerCase()
   *        lower-case}.
   * @return the given <code>string</code> with the last <code>suffixLength</code> characters cut off and replaced by
   *         <code>newSuffixLowerCase</code> with respect to the original case of <code>string</code>.
   */
  String replaceSuffixWithCase(String string, int suffixLength, String newSuffixLowerCase);

  /**
   * This method replaces the last <code>suffixLength</code> number of characters from <code>string</code> with the
   * lower-case string <code>newSuffixLowerCase</code> with respect to the original case of the given
   * <code>string</code>. <br>
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
   * @param suffixLength is the length of the suffix from <code>string</code> to replace.
   * @param newSuffixLowerCase is the new suffix for the given <code>string</code> in {@link String#toLowerCase()
   *        lower-case}.
   * @param locale is the locale used for case transformation.
   * @return the given <code>string</code> with the last <code>suffixLength</code> characters cut off and replaced by
   *         <code>newSuffixLowerCase</code> with respect to the original case of <code>string</code>.
   */
  String replaceSuffixWithCase(String string, int suffixLength, String newSuffixLowerCase, Locale locale);

  /**
   * This method determines if the given string contains no information.
   *
   * @see #isEmpty(String, boolean)
   *
   * @param string is the string to check.
   * @return <code>true</code> if the given string is <code>null</code> or has a trimmed length of zero,
   *         <code>false</code> otherwise.
   */
  boolean isEmpty(String string);

  /**
   * This method determines if the given string is empty.
   *
   * @param string is the string to check.
   * @param trim if whitespaces should be ignored and a string with a trimmed length of zero is considered as empty.
   * @return <code>true</code> if the given string is <code>null</code> or has a (trimmed) length of zero,
   *         <code>false</code> otherwise.
   */
  boolean isEmpty(String string, boolean trim);

  /**
   * Determines if all characters of the given <code>string</code> are in {@link Character#toUpperCase(char) upper case}
   * .
   * <table border="1">
   * <tr>
   * <th>string</th>
   * <th>{@link #isAllUpperCase(String) isAllUpperCase}(string)</th>
   * </tr>
   * <tr>
   * <td>UPPER_CASE</td>
   * <td>true</td>
   * </tr>
   * <tr>
   * <td>lower_case</td>
   * <td>false</td>
   * </tr>
   * <tr>
   * <td>CamlCase</td>
   * <td>false</td>
   * </tr>
   * </table>
   *
   * @param string the {@link String} to check.
   * @return <code>true</code> if all characters are in {@link Character#toUpperCase(char) upper case},
   *         <code>false</code> otherwise.
   * @since 6.0.0
   */
  boolean isAllUpperCase(String string);

  /**
   * Determines if all characters of the given <code>string</code> are in {@link Character#toLowerCase(char) lower case}
   * .
   * <table border="1">
   * <tr>
   * <th>string</th>
   * <th>{@link #isAllLowerCase(String) isAllLowerCase}(string)</th>
   * </tr>
   * <tr>
   * <td>UPPER_CASE</td>
   * <td>false</td>
   * </tr>
   * <tr>
   * <td>lower_case</td>
   * <td>true</td>
   * </tr>
   * <tr>
   * <td>CamlCase</td>
   * <td>false</td>
   * </tr>
   * </table>
   *
   * @param string the {@link String} to check.
   * @return <code>true</code> if all characters are in {@link Character#toLowerCase(char) lower case},
   *         <code>false</code> otherwise.
   * @since 6.0.0
   */
  boolean isAllLowerCase(String string);

  /**
   * This method formats a positive number to a string with at least the given number of digits padding it with leading
   * zeros. <br>
   * Examples:
   * <ul>
   * <li><code>padNumber(5, 3)</code> will return <code>"005"</code></li>
   * <li><code>padNumber(25, 3)</code> will return <code>"025"</code></li>
   * <li><code>padNumber(100, 3)</code> will return <code>"100"</code></li>
   * <li><code>padNumber(1234, 3)</code> will return <code>"1234"</code></li>
   * </ul>
   *
   * @param number is the positive number to format.
   * @param digits is the (minimum) number of digits required.
   * @return the number as string with the length of (at least) <code>digits</code>. If the number is less, leading
   *         zeros are appended.
   */
  String padNumber(long number, int digits);

  /**
   * This method formats a positive number to a string using the given <code>radix</code> with at least the given number
   * of digits padding it with leading zeros. <br>
   * Examples:
   * <ul>
   * <li><code>padNumber(31, 3, 16)</code> will return <code>"01f"</code></li>
   * <li><code>padNumber(5, 6, 2)</code> will return <code>"000101"</code></li>
   * </ul>
   *
   * To pad a number to a constant length you can use the following value for <code>digits</code>: <br>
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
   * @return the number as string with the length of (at least) <code>digits</code>. If the number is less, leading
   *         zeros are appended.
   */
  String padNumber(long number, int digits, int radix);

  /**
   * This method converts the given <code>string</code> to caml-case syntax using the default separators
   * <code>' '</code>, <code>'-'</code>, <code>'_'</code> and <code>'.'</code>.
   *
   * @see #toCamlCase(String, char[])
   *
   * @param string is the string to convert.
   * @return the given <code>string</code> in caml-case syntax.
   */
  String toCamlCase(String string);

  /**
   * This method converts the given <code>string</code> to caml-case syntax. <br>
   * In caml-case syntax words are written without a separator but each new word starts with a capitalized letter. This
   * method removes all characters from the given <code>string</code> that are in the list given by
   * <code>separators</code> and capitalizes the first character of the following word. <br>
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
   * @param separators is the list of characters that are treated as word-separators.
   * @return the given <code>string</code> in caml-case syntax.
   */
  String toCamlCase(String string, char... separators);

  /**
   * This method converts the given <code>string</code> from caml-case syntax to lower-case using the given
   * <code>separator</code> as word-boundary. <br>
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
   * <tr>
   * <td>FOO_BAR</td>
   * <td>*</td>
   * <td>foo_bar</td>
   * </tr>
   * </table>
   *
   * @see #toCamlCase(String, char...)
   *
   * @param string is the string to convert.
   * @param separator is the character to insert at word-boundaries indicated by a switch from lower- to upper-case.
   * @return the given <code>string</code> in lower-case with the given <code>separator</code> inserted at
   *         word-boundaries.
   */
  String fromCamlCase(String string, char separator);

  /**
   * This method checks if the given <code>string</code> {@link String#contains(CharSequence) contains} the given
   * <code>substring</code> at the given <code>offset</code>. <br>
   * This is an efficient implementation of
   *
   * <pre>
   * string.indexOf(substring, offset) == offset
   * </pre>
   *
   * or
   *
   * <pre>
   * string.substring(offset).beginsWith(substring)
   * </pre>
   *
   * @param string is the string potentially containing <code>substring</code>.
   * @param substring is the substring that should be contained in <code>string</code> at the given <code>offset</code>.
   * @param offset is the offset in <code>string</code> where to check for <code>substring</code>.
   * @return <code>true</code> if the given <code>string</code> {@link String#contains(CharSequence) contains} the given
   *         <code>substring</code> at the given <code>offset</code> and <code>false</code> otherwise.
   */
  boolean isSubstring(String string, String substring, int offset);

  /**
   * This method checks if the given <code>string</code> {@link String#contains(CharSequence) contains} the given
   * <code>substring</code> at the given <code>offset</code>. <br>
   * This is an efficient implementation of
   *
   * <pre>
   * string.indexOf(substring, offset) == offset
   * </pre>
   *
   * or
   *
   * <pre>
   * string.substring(offset).beginsWith(substring)
   * </pre>
   *
   * @param string is the char[] representing the string potentially containing <code>substring</code>.
   * @param substring is the substring that should be contained in <code>string</code> at the given <code>offset</code>.
   * @param offset is the offset in <code>string</code> where to check for <code>substring</code>.
   * @return <code>true</code> if the given <code>string</code> {@link String#contains(CharSequence) contains} the given
   *         <code>substring</code> at the given <code>offset</code> and <code>false</code> otherwise.
   */
  boolean isSubstring(char[] string, String substring, int offset);

  /**
   * This method formats the elements given by <code>collection</code> to a string where each value is formatted to its
   * {@link Object#toString() string representation} and separated by <code>separator</code>. <br>
   * Examples:
   * <table border="1">
   * <tr>
   * <th>collection</th>
   * <th>separator</th>
   * <th>{@link StringSyntax#getEscape() syntax.escape}</th>
   * <th>{@link StringSyntax#getQuoteStart() syntax.quoteStart}</th>
   * <th>{@link StringSyntax#getQuoteEnd() syntax.quoteEnd}</th>
   * <th>{@link #toSeparatedString(Collection, String, StringSyntax)}</th>
   * </tr>
   * <tr>
   * <td><code>{"abc;", "a,b,c", Integer.valueOf(123), Character.valueOf('\'')}</code></td>
   * <td><code>","</code></td>
   * <td><code>'\\'</code></td>
   * <td><code>'\0'</code></td>
   * <td><code>'\0'</code></td>
   * <td><code>"abc;,a\\,b\\,c,123,'"</code></td>
   * </tr>
   * <tr>
   * <td><code>{"abc;", "a,b,c", Integer.valueOf(123), Character.valueOf('\'')}</code></td>
   * <td><code>","</code></td>
   * <td><code>'\\'</code></td>
   * <td><code>'\''</code></td>
   * <td><code>'\''</code></td>
   * <td><code>"'abc;','a,b,c','123','\\''"</code></td>
   * </tr>
   * <tr>
   * <td><code>{"abc;", "a,b,c", Integer.valueOf(123), Character.valueOf('\'')}</code></td>
   * <td><code>"; "</code></td>
   * <td><code>'\\'</code></td>
   * <td><code>'['</code></td>
   * <td><code>']'</code></td>
   * <td><code>"[abc;]; [a,b,c]; [123]; [']"</code></td>
   * </tr>
   * </table>
   * Please note that {@link Collection}s with heterogeneous elements can NOT be converted back from {@link String}.
   *
   * @param collection is the {@link Collection} with the elements to format as separated string. May be
   *        {@link Collection#isEmpty() empty}.
   * @param separator is the {@link String} used to separate elements. It is appended after each but the last element.
   *        Typically this should be a specific character that may be followed by a whitespace. Common separators are
   *        comma (,) or semicolon (;) often also followed by a whitespace ( <code>", "</code>).
   * @param syntax is the {@link StringSyntax} defining {@link StringSyntax#getEscape() escape} as well as
   *        {@link StringSyntax#getQuoteStart() start} and {@link StringSyntax#getQuoteEnd() end} of elements.
   * @return the formatted string.
   * @since 3.0.0
   */
  String toSeparatedString(Collection<?> collection, String separator, StringSyntax syntax);

  /**
   * This method is like {@link #toSeparatedString(Collection, String, StringSyntax)} but allows to specify an explicit
   * {@link Formatter} to use instead of {@link Object#toString()}.
   *
   * @param <E> is the generic type of the elements in the collection.
   *
   * @param collection is the {@link Collection} with the elements to format as separated string. May be
   *        {@link Collection#isEmpty() empty}.
   * @param separator is the {@link String} used to separate elements. It is appended after each but the last element.
   *        Typically this should be a specific character that may be followed by a whitespace. Common separators are
   *        comma (,) or semicolon (;) often also followed by a whitespace ( <code>", "</code>).
   * @param syntax is the {@link StringSyntax} defining {@link StringSyntax#getEscape() escape} as well as
   *        {@link StringSyntax#getQuoteStart() start} and {@link StringSyntax#getQuoteEnd() end} of elements.
   * @param formatter is the {@link Formatter} to use.
   * @return the formatted string.
   * @since 3.0.0
   */
  <E> String toSeparatedString(Collection<E> collection, String separator, StringSyntax syntax,
      Formatter<E> formatter);

  /**
   * This method is like {@link #toSeparatedString(Collection, String, StringSyntax)} but allows to specify an explicit
   * {@link Formatter} to use instead of {@link Object#toString()}.
   *
   * @param <E> is the generic type of the elements in the collection.
   *
   * @param collection is the {@link Collection} with the elements to format as separated string. May be
   *        {@link Collection#isEmpty() empty}.
   * @param separator is the {@link String} used to separate elements. It is appended after each but the last element.
   *        Typically this should be a specific character that may be followed by a whitespace. Common separators are
   *        comma (,) or semicolon (;) often also followed by a whitespace ( <code>", "</code>).
   * @param syntax is the {@link StringSyntax} defining {@link StringSyntax#getEscape() escape} as well as
   *        {@link StringSyntax#getQuoteStart() start} and {@link StringSyntax#getQuoteEnd() end} of elements.
   * @param formatter is the {@link Formatter} to use.
   * @param buffer is where the separated string is {@link Appendable#append(CharSequence) appended} to.
   * @since 3.0.0
   */
  <E> void toSeparatedString(Collection<E> collection, String separator, StringSyntax syntax,
      Formatter<E> formatter, Appendable buffer);

  /**
   * This method is like {@link #fromSeparatedString(CharSequence, String, StringSyntax, Collection, ValueConverter)}
   * but expects elements of the type {@link String} that do not need additional custom conversion.
   *
   * @param separatedString is the separated {@link String} of elements to add to the collection.
   * @param separator is the {@link String} used to separate the individual elements in <code>separatedString</code>.
   *        There should be no <code>separator</code> after the last element in <code>separatedString</code>.
   * @param syntax is the {@link StringSyntax} defining {@link StringSyntax#getEscape() escape} as well as
   *        {@link StringSyntax#getQuoteStart() start} and {@link StringSyntax#getQuoteEnd() end} of elements.
   * @return the {@link List} of elements from <code>separatedString</code>.
   * @since 3.0.0
   */
  List<String> fromSeparatedString(CharSequence separatedString, String separator, StringSyntax syntax);

  /**
   * This method is like {@link #fromSeparatedString(CharSequence, String, StringSyntax, Collection, ValueConverter)}
   * but expects elements of the type {@link String} that do not need additional custom conversion.
   *
   * @param separatedString is the separated {@link String} of elements to add to the collection.
   * @param separator is the {@link String} used to separate the individual elements in <code>separatedString</code>.
   *        There should be no <code>separator</code> after the last element in <code>separatedString</code>.
   * @param syntax is the {@link StringSyntax} defining {@link StringSyntax#getEscape() escape} as well as
   *        {@link StringSyntax#getQuoteStart() start} and {@link StringSyntax#getQuoteEnd() end} of elements.
   * @param collection is where to add the elements to. This should be initially empty.
   * @since 3.0.0
   */
  void fromSeparatedString(CharSequence separatedString, String separator, StringSyntax syntax,
      Collection<String> collection);

  /**
   * This method parses the given <code>separatedString</code> that contains elements separated with
   * <code>separator</code> and the given <code>syntax</code> and {@link Collection#add(Object) adds} these elements to
   * the given <code>collection</code>. <br>
   * This is the inverse operation of {@link #toSeparatedString(Collection, String, StringSyntax, Formatter)}.
   *
   * @param <E> is the generic type of the elements in the collection.
   *
   * @param separatedString is the separated {@link String} of elements to add to the collection.
   * @param separator is the {@link String} used to separate the individual elements in <code>separatedString</code>.
   *        There should be no <code>separator</code> after the last element in <code>separatedString</code>.
   * @param syntax is the {@link StringSyntax} defining {@link StringSyntax#getEscape() escape} as well as
   *        {@link StringSyntax#getQuoteStart() start} and {@link StringSyntax#getQuoteEnd() end} of elements.
   * @param collection is where to add the elements to. This should be initially empty.
   * @param converter is used to parse the given elements from {@link String} to their actual type ( {@literal <E>}).
   * @since 3.0.0
   */
  <E> void fromSeparatedString(CharSequence separatedString, String separator, StringSyntax syntax,
      Collection<E> collection, ValueConverter<String, E> converter);

  /**
   * This method parses the given <code>separatedString</code> that contains elements separated with
   * <code>separator</code> and the given <code>syntax</code> and {@link Collection#add(Object) adds} these elements to
   * the given <code>collection</code>. <br>
   * This is the inverse operation of {@link #toSeparatedString(Collection, String, StringSyntax, Formatter)}.
   *
   * @param <E> is the generic type of the elements in the collection.
   *
   * @param separatedString is the separated {@link String} of elements to add to the collection.
   * @param separator is the {@link String} used to separate the individual elements in <code>separatedString</code>.
   *        There should be no <code>separator</code> after the last element in <code>separatedString</code>.
   * @param syntax is the {@link StringSyntax} defining {@link StringSyntax#getEscape() escape} as well as
   *        {@link StringSyntax#getQuoteStart() start} and {@link StringSyntax#getQuoteEnd() end} of elements.
   * @param collection is where to add the elements to. This should be initially empty.
   * @param converter is used to parse the given elements from {@link String} to their actual type ( {@literal <E>}).
   *        May be the {@code GenericValueConverter}.
   * @param type is the {@link Class} reflecting the elements to add to <code>collection</code>.
   * @since 3.0.0
   */
  <E> void fromSeparatedString(CharSequence separatedString, String separator, StringSyntax syntax,
      Collection<E> collection, ValueConverter<? super String, ? super E> converter, Class<E> type);

}
