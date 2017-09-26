/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.scanner.base;

import java.io.IOException;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.mmm.util.exception.api.ValueOutOfRangeException;
import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.lang.api.BasicHelper;
import net.sf.mmm.util.scanner.api.CharStreamScanner;

/**
 * Implementation of {@link CharStreamScanner} that adapts a {@link Reader} to read and parse textual data.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.5.0
 */
public class CharReaderScanner extends AbstractCharStreamScannerImpl {

  private static final Logger LOG = LoggerFactory.getLogger(CharReaderScanner.class);

  private Reader reader;

  private char[] lookaheadBuffer;

  private int lookaheadLimit;

  /**
   * The constructor.
   */
  public CharReaderScanner() {

    this(4096);
  }

  /**
   * The constructor.
   *
   * @param capacity the buffer capacity.
   */
  public CharReaderScanner(int capacity) {

    super(capacity);
  }

  /**
   * The constructor.
   *
   * @param capacity the buffer capacity.
   * @param reader the (initial) {@link Reader}.
   */
  public CharReaderScanner(int capacity, Reader reader) {

    this(capacity);
    this.reader = reader;
  }

  /**
   * Resets this buffer for reuse with a new {@link Reader}.
   *
   * @param reader the new {@link Reader} to set. May be {@code null} to entirely clear this buffer.
   */
  public void setReader(Reader reader) {

    reset();
    this.reader = reader;
  }

  @Override
  protected boolean fill() {

    if (this.lookaheadLimit > 0) {
      shiftLookahead();
      return true;
    }
    if (this.reader == null) {
      this.offset = this.limit;
      return false;
    }
    this.offset = 0;
    try {
      this.limit = 0;
      while (this.limit == 0) {
        this.limit = this.reader.read(this.buffer);
      }
      if (this.limit == -1) {
        close();
        this.limit = 0;
        return false;
      }
      return true;
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
  }

  private boolean fillLookahead() {

    if (this.lookaheadLimit > 0) {
      return true;
    }
    if (this.reader == null) {
      return false;
    }
    if (this.lookaheadBuffer == null) {
      this.lookaheadBuffer = new char[this.buffer.length];
    }
    try {
      this.lookaheadLimit = 0;
      while (this.lookaheadLimit == 0) {
        this.lookaheadLimit = this.reader.read(this.lookaheadBuffer);
      }
      if (this.lookaheadLimit == -1) {
        close();
        this.lookaheadLimit = 0;
        return false;
      }
      return true;
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
  }

  private void shiftLookahead() {

    char[] tmp = this.lookaheadBuffer;
    this.lookaheadBuffer = this.buffer;
    this.buffer = tmp;
    this.limit = this.lookaheadLimit;
    this.lookaheadLimit = 0;
  }

  private void close() {

    try {
      this.reader.close();
    } catch (IOException e) {
      LOG.warn("Failed to close reader.", e);
    }
    this.reader = null;
  }

  @Override
  public boolean isEos() {

    return (this.reader == null);
  }

  @Override
  protected boolean isEot() {

    if (this.offset < this.limit) {
      return false;
    }
    if (this.reader != null) {
      return false;
    }
    if (this.lookaheadLimit > 0) {
      return false;
    }
    return true;
  }

  @Override
  public boolean expectStrict(String expected, boolean ignoreCase) {

    int expectedLength = expected.length();
    if (expectedLength == 0) {
      return true;
    }
    if ((expectedLength > this.buffer.length) && !isEos()) {
      throw new ValueOutOfRangeException(Integer.valueOf(expectedLength), Integer.valueOf(0), Integer.valueOf(this.buffer.length), expected);
    }
    if (!hasNext()) {
      return false;
    }
    if (!isEos() && (this.offset + expectedLength >= this.limit)) {
      return false;
    }
    char[] expectedChars;
    if (ignoreCase) {
      expectedChars = BasicHelper.toLowerCase(expected).toCharArray();
    } else {
      expectedChars = expected.toCharArray();
    }
    int myOffset = this.offset;
    int myLimit = this.limit;
    char[] myBuffer = this.buffer;
    int expectedIndex = 0;
    while (expectedIndex < expectedLength) {
      char c = myBuffer[myOffset++];
      char exp = expectedChars[expectedIndex];
      if (c != exp) {
        if (!ignoreCase) {
          return false;
        }
        if (Character.toLowerCase(c) != exp) {
          return false;
        }
      }
      if (myOffset >= myLimit) {
        if (myBuffer != this.buffer) {
          throw new IllegalStateException();
        }
        if (!fillLookahead()) {
          return false;
        }
        myBuffer = this.lookaheadBuffer;
        myOffset = 0;
        myLimit = this.lookaheadLimit;
      }
    }
    if (myBuffer == this.lookaheadBuffer) {
      shiftLookahead();
    }
    this.offset = myOffset;
    return true;
  }

  @Override
  public boolean skipOver(String substring, boolean ignoreCase, CharFilter stopFilter) {

    int subLength = substring.length();
    if (subLength == 0) {
      return true;
    }
    if (subLength > this.buffer.length) {
      throw new ValueOutOfRangeException(Integer.valueOf(subLength), Integer.valueOf(0), Integer.valueOf(this.buffer.length), substring);
    }
    if (!hasNext()) {
      return false;
    }
    char[] subChars;
    if (ignoreCase) {
      subChars = BasicHelper.toLowerCase(substring).toCharArray();
    } else {
      subChars = substring.toCharArray();
    }
    char first = subChars[0];
    while (true) {
      int max = this.limit;
      if (isEos()) {
        // we can only find the substring at a position
        // until where enough chars are left to go...
        max -= subLength;
      }
      while (this.offset <= max) {
        char c = this.buffer[this.offset++];
        if ((stopFilter != null) && stopFilter.accept(c)) {
          return false;
        }
        if (ignoreCase) {
          c = Character.toLowerCase(c);
        }
        if (c == first) {
          // found first character
          int myCharsIndex = this.offset;
          int subCharsIndex = 1;
          boolean found = true;
          while (subCharsIndex < subLength) {
            if (myCharsIndex == this.limit) {
              if (!fillLookahead()) {
                return false;
              }
              int lookaheadIndex = 0;
              while (subCharsIndex < subLength) {
                c = this.lookaheadBuffer[lookaheadIndex++];
                if (ignoreCase) {
                  c = Character.toLowerCase(c);
                }
                if (c != subChars[subCharsIndex++]) {
                  found = false;
                  break;
                }
              }
              if (found) {
                shiftLookahead();
                this.offset = lookaheadIndex;
                return true;
              }
            } else {
              c = this.buffer[myCharsIndex++];
              if (ignoreCase) {
                c = Character.toLowerCase(c);
              }
              if (c != subChars[subCharsIndex++]) {
                found = false;
                break;
              }
            }
          }
          if (found) {
            this.offset = myCharsIndex;
            return true;
          }
        }
      }
      if (!fill()) {
        // substring not found (EOT)
        this.offset = this.limit;
        return false;
      }
    }
  }

}
