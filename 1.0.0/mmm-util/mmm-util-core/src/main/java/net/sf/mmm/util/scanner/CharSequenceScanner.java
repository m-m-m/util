/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.scanner;

import java.util.Locale;

import net.sf.mmm.util.filter.CharFilter;

/**
 * This class represents a {@link String} or better a sequence of characters (<code>char[]</code>)
 * together with a {@link #getCurrentIndex() position} in that sequence.<br>
 * It has various useful methods for scanning the sequence. This scanner is
 * designed to be fast on long sequences and therefore internally
 * {@link String#toCharArray() converts} {@link String}s to a char array
 * instead of frequently calling {@link String#charAt(int)}.<br>
 * <b>ATTENTION:</b><br>
 * This implementation is NOT and has no intention to be thread-safe.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class CharSequenceScanner implements CharStreamScanner {

  /** @see #getOriginalString() */
  private String str;

  /** the string to parse as char array */
  private char[] chars;

  /** @see #getCurrentIndex() */
  private int pos;

  /** The start-index in {@link #chars}. */
  private final int startIndex;

  /** The exclusive end-index in {@link #chars}. */
  private final int endIndex;

  /**
   * The length of the char-sequence:
   * <code>{@link #endIndex} - {@link #startIndex}</code>.
   */
  private final int length;

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
    this.str = string;
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
   * @param offset is the index of the first char to scan in
   *        <code>characters</code> (typically <code>0</code> to start at
   *        the beginning of the array).
   * @param length is the {@link #getLength() number of characters} to scan from
   *        <code>characters</code> starting at <code>offset</code>
   *        (typically <code>characters.length - offset</code>).
   */
  public CharSequenceScanner(char[] characters, int offset, int length) {

    super();
    if (offset < 0) {
      throw new IndexOutOfBoundsException(Integer.toString(offset));
    }
    if (length < 0) {
      throw new IndexOutOfBoundsException(Integer.toString(length));
    }
    if (offset > characters.length - length) {
      throw new IndexOutOfBoundsException(Integer.toString(offset + length));
    }
    this.chars = characters;
    this.length = length;
    this.startIndex = offset;
    this.endIndex = offset + this.length;
    this.pos = this.startIndex;
  }

  /**
   * @see java.lang.CharSequence#charAt(int)
   * 
   * @param index is the index of the requested character.
   * @return the character at the given <code>index</code>.
   */
  public char charAt(int index) {

    return this.chars[this.startIndex + index];
  }

  /**
   * @see java.lang.CharSequence#length()
   * 
   * @return the total length of the
   *         {@link #getOriginalString() string to parse}.
   */
  public int getLength() {

    return this.length;
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

    return new String(this.chars, this.startIndex + start, end - start);
  }

  /**
   * This method gets the {@link #getOriginalString() original string} where the
   * {@link #substring(int, int) substring} specified by <code>start</code>
   * and <code>end</code> is replaced by <code>substitute</code>.
   * 
   * @param substitute is the string used as replacement.
   * @param start is the inclusive start index of the substring to replace.
   * @param end is the exclusive end index of the substring to replace.
   * @return the {@link #getOriginalString() original string} with the specified
   *         substring replaced by <code>substitute</code>.
   */
  public String getReplaced(String substitute, int start, int end) {

    int restLength = this.endIndex - end;
    StringBuffer buffer = new StringBuffer(start + restLength + substitute.length());
    buffer.append(this.chars, this.startIndex, start);
    buffer.append(substitute);
    buffer.append(this.chars, this.startIndex + end, restLength);
    return buffer.toString();
  }

  /**
   * This method appends the {@link #substring(int, int) substring} specified by
   * <code>start</code> and <code>end</code> to the given
   * <code>buffer</code>.<br>
   * This avoids the overhead of creating a new string and copying the char
   * array.
   * 
   * @param buffer is the buffer where to append the substring to.
   * @param start the start index, inclusive.
   * @param end the end index, exclusive.
   */
  public void appendSubstring(StringBuffer buffer, int start, int end) {

    buffer.append(this.chars, this.startIndex + start, end - start);
  }

  /**
   * {@inheritDoc}
   */
  public int getCurrentIndex() {

    return this.pos - this.startIndex;
  }

  /**
   * This method sets the {@link #getCurrentIndex() current index}.
   * 
   * @param index is the next index position to set. The value has to be greater
   *        or equal to <code>0</code> and less or equal to
   *        {@link #getLength()}.
   */
  public void setCurrentIndex(int index) {

    // yes, index == this.length is allowed - that is the state when the end
    // is reached and setCurrentIndex(getCurrentPosition()) should NOT cause an
    // exception...
    if ((index < 0) || (index > this.length)) {
      throw new IndexOutOfBoundsException(Integer.toString(index));
    }
    this.pos = this.startIndex + index;
  }

  /**
   * {@inheritDoc}
   */
  public boolean hasNext() {

    return (this.pos < this.endIndex);
  }

  /**
   * {@inheritDoc}
   */
  public char next() {

    return this.chars[this.pos++];
  }

  /**
   * {@inheritDoc}
   */
  public char forceNext() {

    if (this.pos < this.endIndex) {
      return this.chars[this.pos++];
    } else {
      return 0;
    }
  }

  /**
   * {@inheritDoc}
   */
  public char peek() {

    return this.chars[this.pos];
  }

  /**
   * {@inheritDoc}
   */
  public char forcePeek() {

    if (this.pos < this.endIndex) {
      return this.chars[this.pos];
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

    if (this.pos > this.startIndex) {
      this.pos--;
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean skipUntil(char stop) {

    while (this.pos < this.endIndex) {
      if (this.chars[this.pos++] == stop) {
        return true;
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  public String readUntil(char stop, boolean acceptEof) {

    int start = this.pos;
    while (this.pos < this.endIndex) {
      if (this.chars[this.pos++] == stop) {
        return new String(this.chars, start, this.pos - start - 1);
      }
    }
    if (acceptEof) {
      int len = this.pos - start;
      if (len > 0) {
        return new String(this.chars, start, len);
      } else {
        return "";
      }
    } else {
      return null;
    }
  }

  /**
   * {@inheritDoc}
   */
  public String readUntil(char stop, boolean acceptEof, CharScannerSyntax syntax) {

    StringBuilder result = new StringBuilder();
    char escape = syntax.getEscape();
    char quoteStart = syntax.getQuoteStart();
    char altQuoteStart = syntax.getAltQuoteStart();
    char entityStart = syntax.getEntityStart();
    boolean escapeActive = false;
    boolean done = false;
    char quoteEnd = 0;
    char quoteEscape = 0;
    char entityEnd = 0;
    boolean quoteLazy = false;
    int index = this.pos;
    int restIndex = this.endIndex;
    while (this.pos < this.endIndex) {
      char c = this.chars[this.pos++];
      boolean append = false;
      boolean newEscapeActive = false;
      if (quoteEnd != 0) {
        // in quotation
        if (escapeActive) {
          // current character c was escaped
          // it will be taken as is on the next append
        } else if (c == quoteEscape) {
          // escape in quote --> lookahead
          if (this.pos < this.endIndex) {
            c = this.chars[this.pos];
            if (c == quoteEnd) {
              // quoteEnd was escaped
              append = true;
              newEscapeActive = true;
            } else if (quoteEscape == quoteEnd) {
              // quotation done
              quoteEnd = 0;
              append = true;
            }
          } else {
            // end reached without stop char
            if (quoteEscape == quoteEnd) {
              // omit quote on appending of rest
              restIndex--;
            }
            break;
          }
        } else if (c == quoteEnd) {
          // quotation done
          quoteEnd = 0;
          append = true;
        }
      } else if (entityEnd != 0) {
        if (c == entityEnd) {
          // entity end detected...
          entityEnd = 0;
          int len = this.pos - index - 1;
          String entity = new String(this.chars, index, len);
          result.append(syntax.resolveEntity(entity));
          index = this.pos;
        }
      } else if (escapeActive) {
        // current character c was escaped
        // it will be taken as is on the next append
      } else if (c == stop) {
        append = true;
        done = true;
      } else if (c == escape) {
        append = true;
        newEscapeActive = true;
      } else if (c == entityStart) {
        entityEnd = syntax.getEntityEnd();
        append = true;
      } else {
        if (c == quoteStart) {
          quoteEnd = syntax.getQuoteEnd();
          quoteEscape = syntax.getQuoteEscape();
          quoteLazy = syntax.isQuoteEscapeLazy();
        } else if (c == altQuoteStart) {
          quoteEnd = syntax.getAltQuoteEnd();
          quoteEscape = syntax.getAltQuoteEscape();
          quoteLazy = syntax.isAltQuoteEscapeLazy();
        }
        if (quoteEnd != 0) {
          append = true;
          if ((quoteEnd == quoteEscape) && (c == quoteEscape) && (quoteLazy)) {
            // lazy quotation mode active --> lookahead
            if (this.pos < this.endIndex) {
              if (this.chars[this.pos] == quoteEscape) {
                // lazy quotation detected
                quoteEnd = 0;
                newEscapeActive = true;
              }
            }
          }
        }
      }
      if (append) {
        int len = this.pos - index - 1;
        if (len > 0) {
          result.append(this.chars, index, len);
        }
        if (done) {
          return result.toString();
        }
        index = this.pos;
      }
      escapeActive = newEscapeActive;
    }
    if (acceptEof) {
      int len = restIndex - index;
      if (len > 0) {
        // append rest
        result.append(this.chars, index, len);
      }
      return result.toString();
    } else {
      return null;
    }
  }

  /**
   * {@inheritDoc}
   */
  public String read(int count) {

    int len = this.endIndex - this.pos;
    if (len > count) {
      len = count;
    }
    String result = new String(this.chars, this.pos, len);
    this.pos += len;
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public int readDigit() {

    int result = -1;
    if (this.pos < this.endIndex) {
      char c = this.chars[this.pos];
      if ((c >= '0') && (c <= '9')) {
        result = c - '0';
        this.pos++;
      }
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public long readLong(int maxDigits) throws NumberFormatException {

    if (maxDigits <= 0) {
      throw new IllegalArgumentException();
    }
    int index = this.pos;
    int end = this.pos + maxDigits;
    if (end > this.endIndex) {
      end = this.endIndex;
    }
    while (this.pos < end) {
      char c = this.chars[this.pos];
      if ((c < '0') || (c > '9')) {
        break;
      }
      this.pos++;
    }
    int len = this.pos - index;
    String number = new String(this.chars, index, len);
    return Long.parseLong(number);
  }

  /**
   * {@inheritDoc}
   */
  public boolean skipOver(String substring, boolean ignoreCase) {

    return skipOver(substring, ignoreCase, null);
  }

  /**
   * {@inheritDoc}
   */
  public boolean skipOver(String substring, boolean ignoreCase, CharFilter stopFilter) {

    int subLength = substring.length();
    if (subLength == 0) {
      return true;
    }
    char[] subChars;
    if (ignoreCase) {
      subChars = substring.toLowerCase(Locale.ENGLISH).toCharArray();
    } else {
      subChars = substring.toCharArray();
    }
    // we can only find the substring at a position
    // until where enough chars are left to go...
    int max = this.endIndex - subLength;
    char first = subChars[0];
    while (this.pos <= max) {
      char c = this.chars[this.pos++];
      if (stopFilter != null) {
        if (stopFilter.accept(c)) {
          return false;
        }
      }
      if (ignoreCase) {
        c = Character.toLowerCase(c);
      }
      if (c == first) {
        // found first character
        int myCharsIndex = this.pos;
        int subCharsIndex = 1;
        boolean found = true;
        while (subCharsIndex < subLength) {
          c = this.chars[myCharsIndex++];
          if (ignoreCase) {
            c = Character.toLowerCase(c);
          }
          if (c != subChars[subCharsIndex++]) {
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
    this.pos = this.endIndex;
    return false;
  }

  /**
   * {@inheritDoc}
   */
  public boolean expect(String exprected, boolean ignoreCase) {

    int len = exprected.length();
    for (int i = 0; i < len; i++) {
      if (this.pos >= this.endIndex) {
        return false;
      }
      char c = this.chars[this.pos];
      char exp = exprected.charAt(i);
      if (c != exp) {
        if (!ignoreCase) {
          return false;
        }
        if (Character.toLowerCase(c) != Character.toLowerCase(exp)) {
          return false;
        }
      }
      this.pos++;
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  public boolean expect(char expected) {

    if (this.pos < this.endIndex) {
      if (this.chars[this.pos] == expected) {
        this.pos++;
        return true;
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  public boolean skipUntil(char stop, char escape) {

    boolean escapeActive = false;
    while (this.pos < this.endIndex) {
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
   * {@inheritDoc}
   */
  public int skipWhile(char c) {

    int currentPos = this.pos;
    while (this.pos < this.endIndex) {
      if (this.chars[this.pos] != c) {
        break;
      }
      this.pos++;
    }
    return this.pos - currentPos;
  }

  /**
   * {@inheritDoc}
   */
  public int skipWhile(CharFilter filter) {

    return skipWhile(filter, Integer.MAX_VALUE);
  }

  /**
   * {@inheritDoc}
   */
  public int skipWhile(CharFilter filter, int max) {

    if (max < 0) {
      throw new IllegalArgumentException("Max must NOT be negative: " + max);
    }
    int currentPos = this.pos;
    int end = currentPos + max;
    if (end < 0) {
      end = max;
    }
    if (this.endIndex < end) {
      end = this.endIndex;
    }
    while (this.pos < end) {
      char c = this.chars[this.pos];
      if (!filter.accept(c)) {
        break;
      }
      this.pos++;
    }
    return this.pos - currentPos;
  }

  /**
   * {@inheritDoc}
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
   * {@inheritDoc}
   */
  public String readWhile(CharFilter filter, int max) {

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
   * @see CharSequenceScanner#CharSequenceScanner(String)
   * 
   * @return the original string.
   */
  public String getOriginalString() {

    if (this.str != null) {
      this.str = new String(this.chars, this.startIndex, this.length);
    }
    return this.str;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    if (this.pos < this.endIndex) {
      return new String(this.chars, this.pos, this.endIndex - this.pos);
    } else {
      return "";
    }
  }

}
