/* $Id$ */
package net.sf.mmm.util;

import java.util.regex.Pattern;

/**
 * This class is a collection of utility functions for {@link String} handling
 * and manipulation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class StringUtil {

  /**
   * Forbidden constructor.
   */
  private StringUtil() {

    super();
  }

  /** a string representing the boolean value <code>true</code> */
  public static final String TRUE = String.valueOf(true);

  /** a string representing the boolean value <code>false</code> */
  public static final String FALSE = String.valueOf(false);

  /**
   * This method parses a boolean value given as string.
   * 
   * @param booleanValue
   *        is the boolean value as string.
   * @return <code>true</code> if the given string
   *         {@link String#equalsIgnoreCase(java.lang.String) equals} to
   *         {@link #TRUE true}, <code>false</code> if it
   *         {@link String#equalsIgnoreCase(java.lang.String) equals} to
   *         {@link #FALSE false} and <code>null</code> in any other case.
   */
  public static Boolean parseBoolean(String booleanValue) {

    if (TRUE.equalsIgnoreCase(booleanValue)) {
      return Boolean.TRUE;
    } else if (FALSE.equalsIgnoreCase(booleanValue)) {
      return Boolean.FALSE;
    } else {
      return null;
    }
  }

  /**
   * For each argument with the index <code><b>i</b></code> this method
   * will replace all occurences of the string <code>${<b>i</b>}</code> in
   * the given string with that argument. <br>
   * E.g.
   * <code>fillArguments("${0} ${1}!", new String[] {"Hello", "world"})</code>
   * will return the String <code>"Hello World!"</code>.
   * 
   * @param string
   *        is the string where to fill in the arguments.
   * @param arguments
   *        are the arguments to fill.
   * @return the string will the arguments filled in.
   */
  public static String fillInArguments(String string, Object[] arguments) {

    if (arguments.length == 0) {
      return string;
    }
    String result = string;
    for (int i = 0; i < arguments.length; i++) {
      result = replace(result, "${" + i + "}", arguments[i].toString());
    }
    return result;
  }

  /**
   * This method compares two given string for equality. In addition to
   * calling the <code>equals</code> on one of the strings this method also
   * handles the case that this string may be <code>null</code>. If both
   * strings are <code>null</code> the result will also be <code>true</code>.
   * 
   * @param string1
   *        the first string
   * @param string2
   *        the second string
   * @return <code>true</code> if both strings are <code>null</code> or if
   *         both strings are not <code>null</code> and fulfill the
   *         <code>equals</code> method, <code>false</code> otherwise.
   */
  public static boolean isEqual(String string1, String string2) {

    if (string1 == null) {
      return (string2 == null);
    } else {
      return string1.equals(string2);
    }
  }

  /**
   * This method replaces all occurences of the string <code>match</code>
   * with the string <code>replace</code> in the given string.
   * 
   * @param string
   *        is the string where to replace.
   * @param match
   *        is the string that is searched and replaced.
   * @param replace
   *        is the string <code>match</code> is substituted with.
   * @return the given string whith all occurences of <code>match</code>
   *         replaced by <code>replace</code>.
   */
  public static String replace(String string, String match, String replace) {

    StringBuffer result = new StringBuffer();
    int matchLen = match.length();
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
      result.append(string.substring(oldPos, string.length()));
    }
    return result.toString();
  }

  /**
   * This method does a glob pattern match.
   * 
   * @param pattern
   *        is the glob pattern (a string that may contain the special
   *        matching characters * and ?. The character * represents a
   *        sub-string of any lenght (including 0) and ? represents the
   *        occurence of any character.
   * @param patternIndex
   *        is the index in the pattern where to start the process.
   * @param string
   *        is the string to be matched against the pattern.
   * @param stringIndex
   *        is the index in the string where to start the process.
   * @return true if the string matches the pattern.
   */
  public static boolean matchGlobPattern(String pattern, int patternIndex, String string,
      int stringIndex) {

    while (patternIndex < pattern.length()) {
      char patternChar = pattern.charAt(patternIndex++);
      if (patternChar == '*') {
        if (patternIndex == pattern.length()) {
          return true;
        }
        while (stringIndex < string.length()) {
          if (matchGlobPattern(pattern, patternIndex, string, stringIndex++)) {
            return true;
          }
        }
        return false;
      } else {
        if (stringIndex >= string.length()) {
          return false;
        }
        char stringChar = string.charAt(stringIndex++);
        if ((patternChar != stringChar) && (patternChar != '?')) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * This method does a glob pattern match.
   * 
   * @param pattern
   *        is the glob pattern (a string that may contain the special
   *        matching characters * and ?. The character * represents a
   *        sub-string of any lenght (including 0) and ? represents the
   *        occurence of any character (string of length 1).
   * @param string
   *        is the string to be matched against the pattern.
   * @return true if the string matches the pattern.
   */
  public static boolean matchGlobPattern(String pattern, String string) {

    if (pattern.length() == 0) {
      return (string.length() == 0);
    }
    return matchGlobPattern(pattern, 0, string, 0);
  }

  /**
   * This method formats a positive number to a string with at least the given
   * number of digits by filling up with leading zeros.
   * 
   * @param number
   *        is the positive number to format.
   * @param digits
   *        is the (minimum) number of digits required.
   * @return the number as string with the length of (at least)
   *         <code>digits</code>. If the number is less, leading zeros are
   *         appended.
   */
  public static String format(long number, int digits) {

    String result = Long.toString(number);
    int leadingZeros = digits - result.length();
    while (leadingZeros > 0) {
      result = "0" + result;
      leadingZeros--;
    }
    return result;
  }

  /**
   * IsEmpty with trim set to <code>true</code>.
   * 
   * @see #isEmpty(String, boolean)
   * 
   * @param string
   *        is the string to check.
   * @return <code>true</code> if the given string is <code>null</code> or
   *         has a trimmed length of zero, <code>false</code> otherwise.
   */
  public static boolean isEmpty(String string) {

    return isEmpty(string, true);
  }

  /**
   * This method determines if the given string contains no information.
   * 
   * @param string
   *        is the string to check.
   * @param trim
   *        if whitespaces should be ignored and a string with a trimmed
   *        length of zero is considered as emtpy.
   * @return <code>true</code> if the given string is <code>null</code> or
   *         has a (trimmed) length of zero, <code>false</code> otherwise.
   */
  public static boolean isEmpty(String string, boolean trim) {

    if (string == null) {
      return true;
    }
    if (trim) {
      string = string.trim();
    }
    return (string.length() == 0);
  }

  /**
   * This method compiles the given <code>pattern</code> to a
   * {@link java.util.regex.Pattern} interpreting it as glob-pattern. In a
   * glob-pattern only the characters "*" and "?" are treated special. The
   * wildcard ("*") can match any string including the empty string and the
   * XXX ("?") can match any single character.
   * 
   * @param pattern
   *        is the glob pattern to compile.
   * @return the compiled pattern.
   */
  public static Pattern compileGlobPattern(String pattern) {

    char[] chars = pattern.toCharArray();
    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < chars.length; i++) {
      char c = chars[i];
      if (c == '*') {
        buffer.append(".*");
      } else if (c == '?') {
        buffer.append('.');
      } else if (c == '.') {
        buffer.append("\\.");
      } else if (c == '\\') {
        buffer.append("\\\\.");
      } else {
        buffer.append(c);
      }
    }
    return Pattern.compile(buffer.toString());
  }

}
