/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util;

import java.util.Locale;

/**
 * This class represents a string together with a
 * {@link #getCurrentIndex() index position} in that string.<br>
 * It has various useful methods for parsing strings. This parser is designed to
 * be fast on long strings and therefore internally
 * {@link String#toCharArray() converts} the given string to a char array
 * instead of calling {@link String#charAt(int)}.<br>
 * <b>ATTENTION:</b><br>
 * This implementation is NOT thread-safe.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class StringParser {

  /** @see #getOriginalString() */
  private String str;

  /** the string to parse as char array */
  private char[] chars;

  /** the string to parse in upper case as char array */
  private char[] upperChars;

  /** @see #getCurrentIndex() */
  private int pos;

  /**
   * The constructor
   * 
   * @param string
   *        is the {@link #getOriginalString() string} to parse.
   */
  public StringParser(String string) {

    super();
    this.str = string;
    this.chars = string.toCharArray();
    this.upperChars = null;
    this.pos = 0;
  }

  /**
   * This method gets the {@link #upperChars}. On the first call they are
   * created.
   * 
   * @return the chars in upper case.
   */
  private char[] getUpperChars() {

    if (this.upperChars == null) {
      this.upperChars = this.str.toUpperCase(Locale.ENGLISH).toCharArray();
    }
    return this.upperChars;
  }

  /**
   * @see java.lang.CharSequence#charAt(int)
   * 
   * @param index
   *        is the index of the requested character.
   * @return the character at the given <code>index</code>.
   */
  public char charAt(int index) {

    return this.chars[index];
  }

  /**
   * @see java.lang.CharSequence#length()
   * 
   * @return the total length of the
   *         {@link #getOriginalString() string to parse}.
   */
  public int getLength() {

    return this.chars.length;
  }

  /**
   * @see String#substring(int, int)
   * @see #appendSubstring(StringBuffer, int, int)
   * 
   * @param start
   *        the start index, inclusive.
   * @param end
   *        the end index, exclusive.
   * @return the specified substring.
   */
  public String substring(int start, int end) {

    return new String(this.chars, start, end - start);
  }

  /**
   * This method gets the {@link #getOriginalString() original string} where the
   * {@link #substring(int, int) substring} specified by <code>start</code>
   * and <code>end</code> is replaced by <code>substitute</code>.
   * 
   * @param substitute
   *        is the string used as replacement.
   * @param start
   *        is the inclusive start index of the substring to replace.
   * @param end
   *        is the exclusive end index of the substring to replace.
   * @return the {@link #getOriginalString() original string} with the specified
   *         substring replaced by <code>substitute</code>.
   */
  public String getReplaced(String substitute, int start, int end) {

    int restLength = this.chars.length - end;
    StringBuffer buffer = new StringBuffer(start + restLength + substitute.length());
    buffer.append(this.chars, 0, start);
    buffer.append(substitute);
    buffer.append(this.chars, end, restLength);
    return buffer.toString();
  }

  /**
   * This method appends the {@link #substring(int, int) substring} specified by
   * <code>start</code> and <code>end</code> to the given
   * <code>buffer</code>.<br>
   * This avoids the overhead of creating a new string and copying the char
   * array.
   * 
   * @param buffer
   *        is the buffer where to append the substring to.
   * @param start
   *        the start index, inclusive.
   * @param end
   *        the end index, exclusive.
   */
  public void appendSubstring(StringBuffer buffer, int start, int end) {

    buffer.append(this.chars, start, end - start);
  }

  /**
   * This method gets the current {@link String#charAt(int) position} in the
   * string to parse. It will initially be <code>0</code>.
   * 
   * @return the current index position.
   */
  public int getCurrentIndex() {

    return this.pos;
  }

  /**
   * This method sets the {@link #getCurrentIndex() current index}.
   * 
   * @param index
   *        is the next index position to set. The value has to be greater or
   *        equal to <code>0</code> and less or equal to {@link #getLength()}.
   */
  public void setCurrentIndex(int index) {

    if ((index < 0) || (index > this.chars.length)) {
      throw new IndexOutOfBoundsException("" + index);
    }
    this.pos = index;
  }

  /**
   * This method determines if there is at least one character available.
   * 
   * @return <code>true</code> if there is at least one character available,
   *         <code>false</code> if the end of the string has been reached.
   */
  public boolean hasNext() {

    return (this.pos < this.chars.length);
  }

  /**
   * This method reads the {@link #getCurrentIndex() current}
   * {@link #charAt(int) character} without incementing the
   * {@link #getCurrentIndex() index}. You need to {@link #hasNext() check} if
   * a character is available before calling this method.
   * 
   * @return the current character.
   */
  public char peek() {

    return this.chars[this.pos];
  }

  /**
   * This method reads the {@link #getCurrentIndex() current}
   * {@link #charAt(int) character} without incementing the
   * {@link #getCurrentIndex() index}. If there is no character
   * {@link #hasNext() available} this method will return <code>0</code> (the
   * NULL character and NOT <code>'0'</code>).
   * 
   * @return the current character or <code>0</code> if none is
   *         {@link #hasNext() available}.
   */
  public char forcePeek() {

    if (this.pos < this.chars.length) {
      return this.chars[this.pos];
    } else {
      return 0;
    }
  }

  /**
   * This method reads the {@link #getCurrentIndex() current}
   * {@link #charAt(int) character} and increments the
   * {@link #getCurrentIndex() index}. You need to {@link #hasNext() check} if
   * a character is available before calling this method.
   * 
   * @return the current character.
   */
  public char next() {

    return this.chars[this.pos++];
  }

  /**
   * This method reads the {@link #getCurrentIndex() current}
   * {@link #charAt(int) character} and increments the
   * {@link #getCurrentIndex() index}. If there is no character
   * {@link #hasNext() available} this method will do nothing and returns
   * <code>0</code> (the NULL character and NOT <code>'0'</code>).
   * 
   * @return the current character or <code>0</code> if none is
   *         {@link #hasNext() available}.
   */
  public char forceNext() {

    if (this.pos < this.chars.length) {
      return this.chars[this.pos++];
    } else {
      return 0;
    }
  }

  /**
   * This method decrements the {@link #getCurrentIndex() index} by one. If the
   * {@link #getCurrentIndex() index} is <code>0</code> this method will have
   * no effect.<br>
   * E.g. use this method if you read a character too much.
   */
  public void stepBack() {

    if (this.pos > 0) {
      this.pos--;
    }
  }

  /**
   * This method skips all {@link #next() next characters} until the given
   * <code>stop</code> character or the end of the string to parse is reached.
   * 
   * @param stop
   *        is the character to read until.
   * @return <code>true</code> if the first occurence of the given
   *         <code>stop</code> character has been passed, <code>false</code>
   *         if there is no such character.
   */
  public boolean skipUntil(char stop) {

    while (this.pos < this.chars.length) {
      if (this.chars[this.pos++] == stop) {
        return true;
      }
    }
    return false;
  }

  /**
   * This method reads all {@link #next() next characters} until the given
   * <code>stop</code> character or the end of the string to parse is reached.<br>
   * After the call of this method, the {@link #getCurrentIndex() current index}
   * will point to the next character after the (first) <code>stop</code>
   * character or to the end of the string if NO such character exists.
   * 
   * @param stop
   *        is the character to read until.
   * @param acceptEof
   *        if <code>true</code> EOF will be treated as <code>stop</code>,
   *        too.
   * @return the string with all read characters excluding the <code>stop</code>
   *         character or <code>null</code> if there was no <code>stop</code>
   *         character and <code>acceptEof</code> is <code>false</code>.
   */
  public String readUntil(char stop, boolean acceptEof) {

    int startIndex = this.pos;
    while (this.pos < this.chars.length) {
      if (this.chars[this.pos++] == stop) {
        return new String(this.chars, startIndex, this.pos - startIndex - 1);
      }
    }
    if (acceptEof) {
      int len = this.pos - startIndex;
      if (len > 0) {
        return new String(this.chars, startIndex, len);
      } else {
        return "";
      }
    } else {
      return null;
    }
  }

  /**
   * This method reads the number of {@link #next() next characters} given by
   * <code>count</code> and returns them as string. If there are less
   * characters {@link #hasNext() available} the returned string will be shorter
   * than <code>count</code> and only contain the available characters.
   * 
   * @param count
   *        is the number of characters to read. Use {@link Integer#MAX_VALUE}
   *        to read until the end of of the parsers data.
   * @return a string with the given number of characters or all available
   *         characters if less than <code>count</code>. Will be the empty
   *         string if no character is {@link #hasNext() available} at all.
   */
  public String read(int count) {

    int length = this.chars.length - this.pos;
    if (length > count) {
      length = count;
    }
    String result = new String(this.chars, this.pos, length);
    this.pos += length;
    return result;
  }

  /**
   * This method reads all {@link #next() next characters} until the given
   * <code>substring</code> has been detected.<br>
   * After the call of this method, the {@link #getCurrentIndex() current index}
   * will point to the next character after the first occurence of
   * <code>substring</code> after the {@link #getCurrentIndex() index} before
   * this method has been called or to the end of the string if the given
   * <code>substring</code> was NOT found.<br>
   * <b>ATTENTION:</b><br>
   * If <code>ignoreCase</code> is <code>true</code> the complete
   * {@link #getOriginalString() parse string} is duplicated into
   * {@link String#toUpperCase(java.util.Locale) upper-case} and stored in this
   * parser instance. This may be expensive when the
   * {@link #getOriginalString() parse string} is extremly large.
   * 
   * @param substring
   * @param ignoreCase -
   *        if <code>true</code> the case of the characters is ignored when
   *        compared with characters from <code>substring</code>.
   * @return <code>true</code> if the given <code>substring</code> occured
   *         and has been passed and <code>false</code> if the end of the
   *         string has been reached without any occurence of the given
   *         <code>substring</code>.
   */
  public boolean skipOver(String substring, boolean ignoreCase) {

    return skipOver(substring, ignoreCase, null);
  }

  /**
   * This method reads all {@link #next() next characters} until the given
   * <code>substring</code> has been detected.<br>
   * After the call of this method, the {@link #getCurrentIndex() current index}
   * will point to the next character after the first occurence of
   * <code>substring</code> after the {@link #getCurrentIndex() index} before
   * this method has been called or to the end of the string if the given
   * <code>substring</code> was NOT found.<br>
   * <b>ATTENTION:</b><br>
   * If <code>ignoreCase</code> is <code>true</code> the complete
   * {@link #getOriginalString() parse string} is duplicated into
   * {@link String#toUpperCase(java.util.Locale) upper-case} and stored in this
   * parser instance. This may be expensive when the
   * {@link #getOriginalString() parse string} is extremly large.
   * 
   * @param substring
   *        is the substring to search and skip over starting at the
   *        {@link #getCurrentIndex() current index}.
   * @param ignoreCase -
   *        if <code>true</code> the case of the characters is ignored when
   *        compared with characters from <code>substring</code>.
   * @param stopFilter
   *        is the filter used to {@link CharFilter#accept(char) detect} stop
   *        characters. If such character was detected, the skip is stopped and
   *        the parser points to the character after the stop character. The
   *        <code>substring</code> should NOT contain a
   *        {@link CharFilter#accept(char) stop character}.
   * @return <code>true</code> if the given <code>substring</code> occured
   *         and has been passed and <code>false</code> if a stop character
   *         has been detected or the end of the string has been reached without
   *         any occurence of the given <code>substring</code>.
   */
  public boolean skipOver(String substring, boolean ignoreCase, CharFilter stopFilter) {

    int subLength = substring.length();
    if (subLength == 0) {
      return true;
    }
    char[] myChars;
    char[] subChars;
    if (ignoreCase) {
      myChars = getUpperChars();
      subChars = substring.toUpperCase(Locale.ENGLISH).toCharArray();
    } else {
      myChars = this.chars;
      subChars = substring.toCharArray();
    }
    // we can only find the substring at a position
    // until where enough chars are left to go...
    int max = myChars.length - subLength;
    char first = subChars[0];
    while (this.pos <= max) {
      if (stopFilter != null) {
        if (stopFilter.accept(this.chars[this.pos])) {
          this.pos++;
          return false;
        }
      }
      if (myChars[this.pos++] == first) {
        // found first character
        int myCharsIndex = this.pos;
        int subCharsIndex = 1;
        boolean found = true;
        while (subCharsIndex < subLength) {
          if (myChars[myCharsIndex++] != subChars[subCharsIndex++]) {
            found = false;
            break;
          }
        }
        if (found) {
          this.pos = myCharsIndex;
          return true;
        }
      }
    }
    // substring not found (EOF)
    this.pos = this.chars.length;
    return false;
  }

  /**
   * This method skips all {@link #next() next characters} as long as they equal
   * to the according character of the <code>expected</code> string.<br>
   * If a character differs this method stops and the parser points to the first
   * character that differs from <code>expected</code>. Except for this
   * circumstance, this method behaves like the following code snipplet:
   * 
   * <pre>
   * {@link #read(int) read}(expected.length).equals[IgnoreCase](expected)
   * </pre>
   * 
   * <b>ATTENTION:</b><br>
   * Be aware that if already the first character differs, this method will NOT
   * change the parsers state. So take care NOT to produce infinity loops.
   * 
   * @param exprected
   *        is the expected string.
   * @param ignoreCase -
   *        if <code>true</code> the case of the characters is ignored when
   *        compared.
   * @return <code>true</code> if the
   */
  public boolean expect(String exprected, boolean ignoreCase) {

    int len = exprected.length();
    for (int i = 0; i < len; i++) {
      if (this.pos >= this.chars.length) {
        return false;
      }
      char c = this.chars[this.pos];
      char exp = exprected.charAt(i);
      if (c != exp) {
        if (!ignoreCase) {
          return false;
        }
        if (Character.toUpperCase(c) != Character.toUpperCase(exp)) {
          return false;
        }
      }
      this.pos++;
    }
    return true;
  }

  /**
   * This method checks that the {@link #next() current character} is equal to
   * the given character <code>c</code>.<br>
   * If the current character was as expected, the parser points to the next
   * character. Otherwise its position will remain unchanged.
   * 
   * @param expected
   *        is the expected character.
   * @return <code>true</code> if the current character is the same as
   *         <code>expected</code>, <code>false</code> otherwise.
   */
  public boolean expect(char expected) {

    if (this.pos < this.chars.length) {
      if (this.chars[this.pos] == expected) {
        this.pos++;
        return true;
      }
    }
    return false;
  }

  /**
   * This method reads all {@link #next() next characters} until the given
   * <code>stop</code> character or the end of the string to parse is reached.
   * In advance to {@link #skipUntil(char)}, this method will read over the
   * <code>stop</code> character if it is properly escaped.
   * 
   * @param stop
   *        is the character to read until.
   * @param escape
   *        is the character used to escape the stop character (e.g. '\').
   * @return <code>true</code> if the first occurence of the given
   *         <code>stop</code> character has been passed, <code>false</code>
   *         if there is no such character.
   */
  public boolean skipUntil(char stop, char escape) {

    boolean escapeActive = false;
    while (this.pos < this.chars.length) {
      char c = this.chars[this.pos++];
      if (c == escape) {
        escapeActive = !escapeActive;
      } else {
        if ((c == stop) && (!escapeActive)) {
          return true;
        }
        escapeActive = false;
      }
    }
    return false;
  }

  /**
   * This method reads all {@link #next() next characters} until the given
   * <code>stop</code> character or the end of the string to parse is reached.
   * In advance to {@link #readUntil(char, boolean)}, this method will read
   * over the <code>stop</code> character if it is properly escaped. <br>
   * After the call of this method, the {@link #getCurrentIndex() current index}
   * will point to the next character after the (first) <code>stop</code>
   * character or to the end of the string if NO such character exists.
   * 
   * @param stop
   *        is the character to read until.
   * @param acceptEof
   *        if <code>true</code> EOF will be treated as <code>stop</code>,
   *        too.
   * @param escape
   *        is the character used to escape the stop character (e.g. '\').
   * @return the string with all read characters excluding the <code>stop</code>
   *         character or <code>null</code> if there was no <code>stop</code>
   *         character.
   */
  public String readUntil(char stop, boolean acceptEof, char escape) {

    StringBuffer result = new StringBuffer();
    boolean escapeActive = false;
    int index = this.pos;
    while (this.pos < this.chars.length) {
      char c = this.chars[this.pos++];
      if (c == escape) {
        if (escapeActive) {
          result.append(escape);
        } else {
          int len = this.pos - index - 1;
          if (len > 0) {
            result.append(this.chars, index, len);
          }
        }
        index = this.pos;
        escapeActive = !escapeActive;
      } else {
        if ((c == stop) && (!escapeActive)) {
          int len = this.pos - index - 1;
          if (len > 0) {
            result.append(this.chars, index, len);
          }
          return result.toString();
        }
        escapeActive = false;
      }
    }
    return null;
  }

  /**
   * This method reads all {@link #next() next characters} that are identical to
   * the character given by <code>c</code>.<br>
   * E.g. use <code>{@link #skipWhile(char) readWhile}(' ')</code> to skip
   * all blanks from the {@link #getCurrentIndex() current index}. After the
   * call of this method, the {@link #getCurrentIndex() current index} will
   * point to the next character that is different to <code>c</code> or to the
   * end of the string if NO such character exists.
   * 
   * @param c
   *        is the character to read over.
   * @return the number of characters that have been skipped.
   */
  public int skipWhile(char c) {

    int currentPos = this.pos;
    while (this.pos < this.chars.length) {
      if (this.chars[this.pos] != c) {
        break;
      }
      this.pos++;
    }
    return this.pos - currentPos;
  }

  /**
   * This method reads all {@link #next() next characters} that are
   * {@link CharFilter#accept(char) accepted} by the given <code>filter</code>.<br>
   * After the call of this method, the {@link #getCurrentIndex() current index}
   * will point to the next character that was NOT
   * {@link CharFilter#accept(char) accepted} by the given <code>filter</code>
   * or to the end of the string if NO such character exists.
   * 
   * @see #skipWhile(char)
   * 
   * @param filter
   *        is used to {@link CharFilter#accept(char) decide} which characters
   *        should be accepted.
   * @return the number of characters {@link CharFilter#accept(char) accepted}
   *         by the given <code>filter</code> that have been skipped.
   */
  public int skipWhile(CharFilter filter) {

    int currentPos = this.pos;
    while (this.pos < this.chars.length) {
      char c = this.chars[this.pos];
      if (!filter.accept(c)) {
        break;
      }
      this.pos++;
    }
    return this.pos - currentPos;
  }

  /**
   * This method reads all {@link #next() next characters} that are
   * {@link CharFilter#accept(char) accepted} by the given <code>filter</code>.<br>
   * After the call of this method, the {@link #getCurrentIndex() current index}
   * will point to the next character that was NOT
   * {@link CharFilter#accept(char) accepted} by the given <code>filter</code>
   * or to the end of the string if NO such character exists.
   * 
   * @see #skipWhile(CharFilter)
   * 
   * @param filter
   *        is used to {@link CharFilter#accept(char) decide} which characters
   *        should be accepted.
   * @return a string with all characters
   *         {@link CharFilter#accept(char) accepted} by the given
   *         <code>filter</code>.
   */
  public String readWhile(CharFilter filter) {

    int currentPos = this.pos;
    int len = skipWhile(filter);
    if (len == 0) {
      return "";
    } else {
      return new String(this.chars, currentPos, len);
    }
  }

  /**
   * This method gets the original string to parse.
   * 
   * @see StringParser#StringParser(String)
   * 
   * @return the original string.
   */
  public String getOriginalString() {

    return this.str;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    if (this.pos < this.chars.length) {
      return new String(this.chars, this.pos, this.chars.length - this.pos);
    } else {
      return "";
    }
  }

}
