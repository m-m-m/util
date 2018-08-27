/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.scanner.base;

import net.sf.mmm.util.filter.api.CharFilter;

/**
 * This class represents a {@link String} or better a sequence of characters ( {@code char[]}) together with a
 * {@link #getCurrentIndex() position} in that sequence. <br>
 * It has various useful methods for scanning the sequence. This scanner is designed to be fast on long
 * sequences and therefore internally {@link String#toCharArray() converts} {@link String}s to a char array
 * instead of frequently calling {@link String#charAt(int)}. <br>
 * <b>ATTENTION:</b><br>
 * This implementation is NOT and has no intention to be thread-safe.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CharSequenceScanner extends AbstractCharStreamScannerImpl {

  private String string;

  /** The initial {@link #offset} in the {@link #buffer}. */
  private final int initialOffset;

  /**
   * The constructor.
   *
   * @param charSequence is the {@link #getOriginalString() string} to scan.
   */
  public CharSequenceScanner(CharSequence charSequence) {

    this(charSequence.toString());
  }

  /**
   * The constructor.
   *
   * @param string is the {@link #getOriginalString() string} to parse.
   */
  public CharSequenceScanner(String string) {

    this(string.toCharArray());
    this.string = string;
  }

  /**
   * The constructor.
   *
   * @param characters is an array containing the characters to scan.
   */
  public CharSequenceScanner(char[] characters) {

    this(characters, 0, characters.length);
  }

  /**
   * The constructor.
   *
   * @param characters is an array containing the characters to scan.
   * @param offset is the index of the first char to scan in {@code characters} (typically {@code 0} to start
   *        at the beginning of the array).
   * @param length is the {@link #getLength() number of characters} to scan from {@code characters} starting
   *        at {@code offset} (typically <code>characters.length - offset</code>).
   */
  public CharSequenceScanner(char[] characters, int offset, int length) {

    super(characters);
    if (offset < 0) {
      throw new IndexOutOfBoundsException(Integer.toString(offset));
    }
    if (length < 0) {
      throw new IndexOutOfBoundsException(Integer.toString(length));
    }
    if (offset > characters.length - length) {
      throw new IndexOutOfBoundsException(Integer.toString(offset + length));
    }
    this.offset = offset;
    // this.length = length;
    this.initialOffset = offset;
    this.limit = offset + length;
    this.offset = this.initialOffset;
  }

  /**
   * @see java.lang.CharSequence#charAt(int)
   *
   * @param index is the index of the requested character.
   * @return the character at the given {@code index}.
   */
  public char charAt(int index) {

    return this.buffer[this.initialOffset + index];
  }

  /**
   * @see java.lang.CharSequence#length()
   *
   * @return the total length of the {@link #getOriginalString() string to parse}.
   */
  public int getLength() {

    return this.limit - this.initialOffset;
  }

  /**
   * @see String#substring(int, int)
   * @see #appendSubstring(StringBuffer, int, int)
   *
   * @param start the start index, inclusive.
   * @param end the end index, exclusive.
   * @return the specified substring.
   */
  public String substring(int start, int end) {

    return new String(this.buffer, this.initialOffset + start, end - start);
  }

  /**
   * This method gets the {@link #getOriginalString() original string} where the {@link #substring(int, int)
   * substring} specified by {@code start} and {@code end} is replaced by {@code substitute}.
   *
   * @param substitute is the string used as replacement.
   * @param start is the inclusive start index of the substring to replace.
   * @param end is the exclusive end index of the substring to replace.
   * @return the {@link #getOriginalString() original string} with the specified substring replaced by
   *         {@code substitute}.
   */
  public String getReplaced(String substitute, int start, int end) {

    int restLength = this.limit - end;
    StringBuilder builder = builder(null);
    builder.append(this.buffer, this.initialOffset, start);
    builder.append(substitute);
    builder.append(this.buffer, this.initialOffset + end, restLength);
    return builder.toString();
  }

  /**
   * @param appendable is the buffer where to append the substring to.
   * @param start the start index, inclusive.
   * @param end the end index, exclusive.
   * @deprecated use {@link #appendSubstring(StringBuilder, int, int)}
   */
  @Deprecated
  public void appendSubstring(StringBuffer appendable, int start, int end) {

    appendable.append(this.buffer, this.initialOffset + start, end - start);
  }

  /**
   * This method appends the {@link #substring(int, int) substring} specified by {@code start} and {@code end}
   * to the given {@code buffer}. <br>
   * This avoids the overhead of creating a new string and copying the char array.
   *
   * @param appendable is the buffer where to append the substring to.
   * @param start the start index, inclusive.
   * @param end the end index, exclusive.
   * @since 7.5.0
   */
  public void appendSubstring(StringBuilder appendable, int start, int end) {

    appendable.append(this.buffer, this.initialOffset + start, end - start);
  }

  /**
   * This method gets the current position in the stream to scan. It will initially be {@code 0}. In other
   * words this method returns the number of characters that have already been {@link #next() consumed}.
   *
   * @return the current index position.
   */
  public int getCurrentIndex() {

    return this.offset - this.initialOffset;
  }

  /**
   * This method sets the {@link #getCurrentIndex() current index}.
   *
   * @param index is the next index position to set. The value has to be greater or equal to {@code 0} and
   *        less or equal to {@link #getLength()} .
   */
  public void setCurrentIndex(int index) {

    // yes, index == getLength() is allowed - that is the state when the end is reached and
    // setCurrentIndex(getCurrentPosition()) should NOT cause an exception...
    if ((index < 0) || (index > getLength())) {
      throw new IndexOutOfBoundsException(Integer.toString(index));
    }
    this.offset = this.initialOffset + index;
  }

  @Override
  public boolean hasNext() {

    return (this.offset < this.limit);
  }

  @Override
  public char next() {

    return this.buffer[this.offset++];
  }

  @Override
  public char forceNext() {

    if (this.offset < this.limit) {
      return this.buffer[this.offset++];
    } else {
      return 0;
    }
  }

  @Override
  public char peek() {

    return this.buffer[this.offset];
  }

  @Override
  public char forcePeek() {

    if (this.offset < this.limit) {
      return this.buffer[this.offset];
    } else {
      return 0;
    }
  }

  /**
   * This method peeks the number of {@link #peek() next characters} given by {@code count} and returns them
   * as string. If there are less characters {@link #hasNext() available} the returned string will be shorter
   * than {@code count} and only contain the available characters. Unlike {@link #read(int)} this method does
   * NOT consume the characters and will therefore NOT change the state of this scanner.
   *
   * @param count is the number of characters to peek. You may use {@link Integer#MAX_VALUE} to peek until the
   *        end of text (EOT) if the data-size is suitable.
   * @return a string with the given number of characters or all available characters if less than
   *         {@code count}. Will be the empty string if no character is {@link #hasNext() available} at all.
   * @since 3.0.0
   */
  public String peek(int count) {

    int len = this.limit - this.offset;
    if (len > count) {
      len = count;
    }
    String result = new String(this.buffer, this.offset, len);
    return result;
  }

  /**
   * This method decrements the {@link #getCurrentIndex() index} by one. If the {@link #getCurrentIndex()
   * index} is {@code 0} this method will have no effect. <br>
   * E.g. use this method if you read a character too much.
   */
  public void stepBack() {

    if (this.offset > this.initialOffset) {
      this.offset--;
    }
  }

  @Override
  public String readUntil(CharFilter filter, boolean acceptEot) {

    int start = this.offset;
    while (this.offset < this.limit) {
      if (filter.accept(this.buffer[this.offset])) {
        return new String(this.buffer, start, this.offset - start);
      }
      this.offset++;
    }
    if (acceptEot) {
      int len = this.offset - start;
      if (len > 0) {
        return new String(this.buffer, start, len);
      } else {
        return "";
      }
    } else {
      return null;
    }
  }

  @Override
  protected boolean expectRestWithLookahead(char[] stopChars, boolean ignoreCase, Runnable appender, boolean skip) {

    int myCharsIndex = this.offset + 1;
    int stopCharsIndex = 1;
    while (stopCharsIndex < stopChars.length) {
      char c = this.buffer[myCharsIndex++];
      char stopChar = stopChars[stopCharsIndex++];
      if ((c != stopChar) && (!ignoreCase || (Character.toLowerCase(c) != stopChar))) {
        return false;
      }
    }
    if (skip) {
      this.offset = myCharsIndex;
    }
    return true;
  }

  @Override
  public boolean expectStrict(String expected, boolean ignoreCase) {

    int len = expected.length();
    int newPos = this.offset;
    for (int i = 0; i < len; i++) {
      if (newPos >= this.limit) {
        return false;
      }
      char c = this.buffer[newPos];
      char exp = expected.charAt(i);
      if (c != exp) {
        if (!ignoreCase) {
          return false;
        }
        if (Character.toLowerCase(c) != Character.toLowerCase(exp)) {
          return false;
        }
      }
      newPos++;
    }
    this.offset = newPos;
    return true;
  }

  /**
   * This method gets the tail of this scanner without changing the state.
   *
   * @return the tail of this scanner.
   */
  protected String getTail() {

    String tail = "";
    if (this.offset < this.limit) {
      tail = new String(this.buffer, this.offset, this.limit - this.offset + 1);
    }
    return tail;
  }

  /**
   * This method gets the tail of this scanner limited (truncated) to the given {@code maximum} number of
   * characters without changing the state.
   *
   * @param maximum is the maximum number of characters to return from the {@link #getTail() tail}.
   * @return the tail of this scanner.
   */
  protected String getTail(int maximum) {

    String tail = "";
    if (this.offset < this.limit) {
      int count = this.limit - this.offset + 1;
      if (count > maximum) {
        count = maximum;
      }
      tail = new String(this.buffer, this.offset, count);
    }
    return tail;
  }

  @Override
  public void require(String expected, boolean ignoreCase) {

    if (!expectStrict(expected, ignoreCase)) {
      throw new IllegalStateException("Expecting '" + expected + "' but found: " + getTail(expected.length()));
    }
  }

  @Override
  public String readWhile(CharFilter filter, int max) {

    int currentPos = this.offset;
    int len = skipWhile(filter, max);
    if (len == 0) {
      return "";
    } else {
      return new String(this.buffer, currentPos, len);
    }
  }

  /**
   * This method gets the original string to parse.
   *
   * @see CharSequenceScanner#CharSequenceScanner(String)
   *
   * @return the original string.
   */
  public String getOriginalString() {

    if (this.string != null) {
      this.string = new String(this.buffer, this.initialOffset, getLength());
    }
    return this.string;
  }

}
