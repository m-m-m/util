/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.scanner.api;

import net.sf.mmm.util.exception.api.NlsParseException;
import net.sf.mmm.util.filter.api.CharFilter;

/**
 * This is the interface for a scanner that can be used to parse a stream or sequence of characters.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface CharStreamScanner {

  /**
   * This method gets the current position in the stream to scan. It will initially be {@code 0}. In
   * other words this method returns the number of characters that have already been {@link #next() consumed}.
   *
   * @return the current index position.
   */
  int getCurrentIndex();

  /**
   * This method determines if there is at least one more character available.
   *
   * @return {@code true} if there is at least one character available, {@code false} if the end has
   *         been reached.
   */
  boolean hasNext();

  /**
   * This method reads the current character and increments the {@link #getCurrentIndex() index} stepping to
   * the next character. You need to {@link #hasNext() check} if a character is available before calling this
   * method.
   *
   * @return the current character.
   */
  char next();

  /**
   * Like {@link #next()} this method reads the {@link #getCurrentIndex() current} character and increments
   * the {@link #getCurrentIndex() index}. If there is no character {@link #hasNext() available} this method
   * will do nothing and returns {@code 0} (the NULL character and NOT {@code '0'}).
   *
   * @return the current character or {@code 0} if none is {@link #hasNext() available}.
   */
  char forceNext();

  /**
   * This method reads the current character without incrementing the {@link #getCurrentIndex() index}. You
   * need to {@link #hasNext() check} if a character is available before calling this method.
   *
   * @return the current character.
   */
  char peek();

  /**
   * This method reads the {@link #getCurrentIndex() current} character without incrementing the
   * {@link #getCurrentIndex() index}. If there is no character {@link #hasNext() available} this method will
   * return {@code 0} (the NULL character and NOT {@code '0'}).
   *
   * @return the current character or {@code 0} if none is {@link #hasNext() available}.
   */
  char forcePeek();

  /**
   * This method reads the {@link #next() next character} if it is a digit. Else the state remains unchanged.
   *
   * @return the numeric value of the next Latin digit (e.g. {@code 0} if {@code '0'}) or
   *         {@code -1} if the {@link #peek() current character} is no Latin digit.
   */
  int readDigit();

  /**
   * This method reads the long starting at the {@link #getCurrentIndex() current position} by reading as many
   * Latin digits as available but at maximum the given {@code maxDigits} and returns its
   * {@link Long#parseLong(String) parsed} value. <br>
   * <b>ATTENTION:</b><br>
   * This method does NOT treat signs ({@code +} or {@code -}) to do so, scan them yourself before
   * and negate the result as needed.
   *
   * @param maxDigits is the maximum number of digits that will be read. The value has to be positive (greater
   *        than zero). Use {@code 19} or higher to be able to read any long number.
   * @return the parsed number.
   * @throws NumberFormatException if the current {@link #getCurrentIndex() current position} does NOT point
   *         to a number.
   */
  long readLong(int maxDigits) throws NumberFormatException;

  /**
   * This method reads the double value (decimal number) starting at the {@link #getCurrentIndex() current
   * position} by reading as many matching characters as available and returns its
   * {@link Double#parseDouble(String) parsed} value. <br>
   *
   * @return the parsed number.
   * @throws NumberFormatException if the current {@link #getCurrentIndex() current position} does NOT point
   *         to a number.
   * @since 4.0.0
   */
  double readDouble() throws NumberFormatException;

  /**
   * This method reads the float value (decimal number) starting at the {@link #getCurrentIndex() current
   * position} by reading as many matching characters as available and returns its
   * {@link Float#parseFloat(String) parsed} value. <br>
   *
   * @return the parsed number.
   * @throws NumberFormatException if the current {@link #getCurrentIndex() current position} does NOT point
   *         to a number.
   * @since 4.0.0
   */
  float readFloat() throws NumberFormatException;

  /**
   * This method reads the number of {@link #next() next characters} given by {@code count} and returns
   * them as string. If there are less characters {@link #hasNext() available} the returned string will be
   * shorter than {@code count} and only contain the available characters.
   *
   * @param count is the number of characters to read. You may use {@link Integer#MAX_VALUE} to read until the
   *        end of data if the data-size is suitable.
   * @return a string with the given number of characters or all available characters if less than
   *         {@code count}. Will be the empty string if no character is {@link #hasNext() available} at
   *         all.
   */
  String read(int count);

  /**
   * This method skips all {@link #next() next characters} as long as they equal to the according character of
   * the {@code expected} string. <br>
   * If a character differs this method stops and the parser points to the first character that differs from
   * {@code expected}. Except for the latter circumstance, this method behaves like the following code:
   *
   * <pre>
   * {@link #read(int) read}(expected.length).equals[IgnoreCase](expected)
   * </pre>
   *
   * <b>ATTENTION:</b><br>
   * Be aware that if already the first character differs, this method will NOT change the state of the
   * scanner. So take care NOT to produce infinity loops.
   *
   * @param expected is the expected string.
   * @param ignoreCase - if {@code true} the case of the characters is ignored when compared.
   * @return {@code true} if the {@code expected} string was successfully consumed from this
   *         scanner, {@code false} otherwise.
   */
  boolean expect(String expected, boolean ignoreCase);

  /**
   * This method acts as {@link #expect(String, boolean)} but if the expected String is NOT completely
   * present, no character is {@link #next() consumed} and the state of the scanner remains unchanged.
   *
   * @param expected is the expected string.
   * @param ignoreCase - if {@code true} the case of the characters is ignored when compared.
   * @return {@code true} if the {@code expected} string was successfully consumed from this
   *         scanner, {@code false} otherwise.
   */
  boolean expectStrict(String expected, boolean ignoreCase);

  /**
   * This method checks that the {@link #next() current character} is equal to the given {@code expected}
   * character. <br>
   * If the current character was as expected, the parser points to the next character. Otherwise its position
   * will remain unchanged.
   *
   * @param expected is the expected character.
   * @return {@code true} if the current character is the same as {@code expected},
   *         {@code false} otherwise.
   */
  boolean expect(char expected);

  /**
   * This method verifies that the {@code expected} string gets consumed from this scanner with respect
   * to {@code ignoreCase}. Otherwise an exception is thrown indicating the problem. <br>
   * This method behaves functionally equivalent to the following code:
   *
   * <pre>
   * if (!scanner.{@link #expectStrict(String, boolean) expectStrict}(expected, ignoreCase)) {
   *   throw new {@link NlsParseException}(scanner.read(expected.length), expected);
   * }
   * </pre>
   *
   * @param expected is the expected string.
   * @param ignoreCase - if {@code true} the case of the characters is ignored during comparison.
   * @throws NlsParseException if the {@code expected} string was NOT found.
   * @since 3.0.0
   */
  void require(String expected, boolean ignoreCase) throws NlsParseException;

  /**
   * This method verifies that the {@link #next() current character} is equal to the given
   * {@code expected} character. <br>
   * If the current character was as expected, the parser points to the next character. Otherwise an exception
   * is thrown indicating the problem.
   *
   * @param expected is the expected character.
   * @throws NlsParseException if the {@code expected} character was NOT found.
   * @since 3.0.0
   */
  void require(char expected) throws NlsParseException;

  /**
   * This method skips all {@link #next() next characters} until the given {@code stop} character or the
   * end is reached. If the {@code stop} character was reached, this scanner will point to the next
   * character after {@code stop} when this method returns.
   *
   * @param stop is the character to read until.
   * @return {@code true} if the first occurrence of the given {@code stop} character has been
   *         passed, {@code false} if there is no such character.
   */
  boolean skipUntil(char stop);

  /**
   * This method reads all {@link #next() next characters} until the given {@code stop} character or the
   * end of the string to parse is reached. In advance to {@link #skipUntil(char)}, this method will read over
   * the {@code stop} character if it is escaped with the given {@code escape} character.
   *
   * @param stop is the character to read until.
   * @param escape is the character used to escape the stop character (e.g. '\').
   * @return {@code true} if the first occurrence of the given {@code stop} character has been
   *         passed, {@code false} if there is no such character.
   */
  boolean skipUntil(char stop, char escape);

  /**
   * This method reads all {@link #next() next characters} until the given {@code stop} character or the
   * end is reached. <br>
   * After the call of this method, the {@link #getCurrentIndex() current index} will point to the next
   * character after the (first) {@code stop} character or to the end if NO such character exists.
   *
   * @param stop is the character to read until.
   * @param acceptEof if {@code true} EOF will be treated as {@code stop}, too.
   * @return the string with all read characters excluding the {@code stop} character or
   *         {@code null} if there was no {@code stop} character and {@code acceptEof} is
   *         {@code false}.
   */
  String readUntil(char stop, boolean acceptEof);

  /**
   * This method reads all {@link #next() next characters} until the first character
   * {@link CharFilter#accept(char) accepted} by the given {@code filter} or the end is reached. <br>
   * After the call of this method, the {@link #getCurrentIndex() current index} will point to the next
   * character after the first {@link CharFilter#accept(char) accepted} character or to the end if NO such
   * character exists and {@code acceptEof} is {@code true}. Otherwise, if {@code null} is
   * returned, the {@link #getCurrentIndex() current index} will remain unchanged.
   *
   * @param filter is used to {@link CharFilter#accept(char) decide} where to stop.
   * @param acceptEof if {@code true} EOF will be treated as {@code stop}, too.
   * @return the string with all read characters not {@link CharFilter#accept(char) accepted} by the given
   *         {@code filter} character or {@code null} if there was no
   *         {@link CharFilter#accept(char) accepted} character and {@code acceptEof} is
   *         {@code false}.
   * @since 7.0.0
   */
  String readUntil(CharFilter filter, boolean acceptEof);

  /**
   * This method reads all {@link #next() next characters} until the given (un-escaped) {@code stop}
   * character or the end is reached. <br>
   * In advance to {@link #readUntil(char, boolean)}, this method allows that the {@code stop} character
   * may be used in the input-string by adding the given {@code escape} character. After the call of this
   * method, the {@link #getCurrentIndex() current index} will point to the next character after the (first)
   * {@code stop} character or to the end if NO such character exists. <br>
   * This method is especially useful when quoted strings should be parsed. E.g.:
   *
   * <pre>
   * {@link CharStreamScanner} scanner = getScanner();
   * doSomething();
   * char c = scanner.{@link #forceNext()};
   * if ((c == '"') || (c == '\'')) {
   *   char escape = c; // may also be something like '\'
   *   String quote = scanner.{@link #readUntil(char, boolean, char) readUntil}(c, false, escape)
   * } else {
   *   doOtherThings();
   * }
   * </pre>
   *
   * @param stop is the character to read until.
   * @param acceptEof if {@code true} EOF will be treated as {@code stop}, too.
   * @param escape is the character used to escape the {@code stop} character. To add an occurrence of
   *        the {@code escape} character it has to be duplicated (occur twice). The {@code escape}
   *        character may also be equal to the {@code stop} character. If other regular characters are
   *        escaped the {@code escape} character is simply ignored.
   * @return the string with all read characters excluding the {@code stop} character or
   *         {@code null} if there was no {@code stop} character and {@code acceptEof} is
   *         {@code false}.
   */
  String readUntil(char stop, boolean acceptEof, char escape);

  /**
   * This method reads all {@link #next() next characters} until the given {@code stop} character or the
   * end of the string to parse is reached. In advance to {@link #readUntil(char, boolean)}, this method will
   * scan the input using the given {@code syntax} which e.g. allows to
   * {@link CharScannerSyntax#getEscape() escape} the stop character. <br>
   * After the call of this method, the {@link #getCurrentIndex() current index} will point to the next
   * character after the (first) {@code stop} character or to the end of the string if NO such character
   * exists.
   *
   * @param stop is the character to read until.
   * @param acceptEof if {@code true} EOF will be treated as {@code stop}, too.
   * @param syntax contains the characters specific for the syntax to read.
   * @return the string with all read characters excluding the {@code stop} character or
   *         {@code null} if there was no {@code stop} character.
   */
  String readUntil(char stop, boolean acceptEof, CharScannerSyntax syntax);

  /**
   * This method reads all {@link #next() next characters} that are {@link CharFilter#accept(char) accepted}
   * by the given {@code filter}. <br>
   * After the call of this method, the {@link #getCurrentIndex() current index} will point to the next
   * character that was NOT {@link CharFilter#accept(char) accepted} by the given {@code filter} or to
   * the end if NO such character exists.
   *
   * @see #skipWhile(CharFilter)
   *
   * @param filter is used to {@link CharFilter#accept(char) decide} which characters should be accepted.
   * @return a string with all characters {@link CharFilter#accept(char) accepted} by the given
   *         {@code filter}. Will be the empty string if no character was accepted.
   */
  String readWhile(CharFilter filter);

  /**
   * This method reads all {@link #next() next characters} that are {@link CharFilter#accept(char) accepted}
   * by the given {@code filter}. <br>
   * After the call of this method, the {@link #getCurrentIndex() current index} will point to the next
   * character that was NOT {@link CharFilter#accept(char) accepted} by the given {@code filter}. If the
   * next {@code max} characters or the characters left until the {@link #hasNext() end} of this scanner
   * are {@link CharFilter#accept(char) accepted}, only that amount of characters are skipped.
   *
   * @see #skipWhile(char)
   *
   * @param filter is used to {@link CharFilter#accept(char) decide} which characters should be accepted.
   * @param max is the maximum number of characters that should be read.
   * @return a string with all characters {@link CharFilter#accept(char) accepted} by the given
   *         {@code filter} limited to the length of {@code max} and the {@link #hasNext() end} of
   *         this scanner. Will be the empty string if no character was accepted.
   */
  String readWhile(CharFilter filter, int max);

  /**
   * This method reads all {@link #next() next characters} until the given {@code substring} has been
   * detected. <br>
   * After the call of this method, the {@link #getCurrentIndex() current index} will point to the next
   * character after the first occurrence of {@code substring} or to the end of the string if the given
   * {@code substring} was NOT found. <br>
   *
   * @param substring is the substring to search and skip over starting at the {@link #getCurrentIndex()
   *        current index}.
   * @param ignoreCase - if {@code true} the case of the characters is ignored when compared with
   *        characters from {@code substring}.
   * @return {@code true} if the given {@code substring} occurred and has been passed and
   *         {@code false} if the end of the string has been reached without any occurrence of the given
   *         {@code substring}.
   */
  boolean skipOver(String substring, boolean ignoreCase);

  /**
   * This method reads all {@link #next() next characters} until the given {@code substring} has been
   * detected. <br>
   * If a {@link CharFilter#accept(char) stop character} is detected by the given {@code stopFilter} this
   * method returns {@code false} pointing to the character next to that stop character. Otherwise after
   * this method, the {@link #getCurrentIndex() current index} will point to the next character after the
   * first occurrence of {@code substring} or to the end of the string if the given
   * {@code substring} was NOT found. <br>
   *
   * @param substring is the substring to search and skip over starting at the {@link #getCurrentIndex()
   *        current index}.
   * @param ignoreCase - if {@code true} the case of the characters is ignored when compared with
   *        characters from {@code substring}.
   * @param stopFilter is the filter used to {@link CharFilter#accept(char) detect} stop characters. If such
   *        character was detected, the skip is stopped and the parser points to the character after the stop
   *        character. The {@code substring} should NOT contain a {@link CharFilter#accept(char) stop
   *        character}.
   * @return {@code true} if the given {@code substring} occurred and has been passed and
   *         {@code false} if a stop character has been detected or the end of the string has been
   *         reached without any occurrence of the given {@code substring} or stop character.
   */
  boolean skipOver(String substring, boolean ignoreCase, CharFilter stopFilter);

  /**
   * This method reads all {@link #next() next characters} that are identical to the character given by
   * {@code c}. <br>
   * E.g. use {@link #skipWhile(char) readWhile(' ')} to skip all blanks from the
   * {@link #getCurrentIndex() current index}. After the call of this method, the {@link #getCurrentIndex()
   * current index} will point to the next character that is different to {@code c} or to the end if NO
   * such character exists.
   *
   * @param c is the character to read over.
   * @return the number of characters that have been skipped.
   */
  int skipWhile(char c);

  /**
   * This method reads all {@link #next() next characters} that are {@link CharFilter#accept(char) accepted}
   * by the given {@code filter}. <br>
   * After the call of this method, the {@link #getCurrentIndex() current index} will point to the next
   * character that was NOT {@link CharFilter#accept(char) accepted} by the given {@code filter} or to
   * the end if NO such character exists.
   *
   * @see #skipWhile(char)
   *
   * @param filter is used to {@link CharFilter#accept(char) decide} which characters should be accepted.
   * @return the number of characters {@link CharFilter#accept(char) accepted} by the given
   *         {@code filter} that have been skipped.
   */
  int skipWhile(CharFilter filter);

  /**
   * This method reads all {@link #next() next characters} that are {@link CharFilter#accept(char) accepted}
   * by the given {@code filter}. <br>
   * After the call of this method, the {@link #getCurrentIndex() current index} will point to the next
   * character that was NOT {@link CharFilter#accept(char) accepted} by the given {@code filter}. If the
   * next {@code max} characters or the characters left until the {@link #hasNext() end} of this scanner
   * are {@link CharFilter#accept(char) accepted}, only that amount of characters are skipped.
   *
   * @see #skipWhile(char)
   *
   * @param filter is used to {@link CharFilter#accept(char) decide} which characters should be accepted.
   * @param max is the maximum number of characters that should be skipped.
   * @return the number of skipped characters.
   */
  int skipWhile(CharFilter filter, int max);

}
