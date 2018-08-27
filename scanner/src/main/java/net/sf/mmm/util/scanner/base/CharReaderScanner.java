/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.scanner.base;

import java.io.IOException;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
      throw new IllegalStateException("Read error.", e);
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
      throw new IllegalStateException("Read error.", e);
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
  protected boolean isEob() {

    if (this.reader != null) {
      return false;
    }
    if (this.lookaheadLimit > 0) {
      return false;
    }
    return true;
  }

  @Override
  public boolean isEot() {

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
    if (!isEos()) {
      verifyLookahead(expected);
    }
    if (!hasNext()) {
      return false;
    }
    if (!isEos() && (this.offset + expectedLength > this.limit)) {
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
      char exp = expectedChars[expectedIndex++];
      if (c != exp) {
        if (!ignoreCase) {
          return false;
        }
        if (Character.toLowerCase(c) != exp) {
          return false;
        }
      }
      if ((myOffset >= myLimit) && (expectedIndex < expectedLength)) {
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
  protected void verifyLookahead(String substring) {

    int subLength = substring.length();
    if (subLength > this.buffer.length) {
      throw new IllegalArgumentException("The string '" + substring + "' exceeds the available lookahead buffer size: " + this.buffer.length);
    }
  }

  @Override
  protected boolean expectRestWithLookahead(char[] stopChars, boolean ignoreCase, Runnable appender, boolean skip) {

    int myCharsIndex = this.offset + 1;
    int subCharsIndex = 1;
    while (subCharsIndex < stopChars.length) {
      if (myCharsIndex == this.limit) { // lookahead required?
        if (!fillLookahead()) {
          if (skip) {
            this.offset = this.limit;
          }
          return false;
        }
        int lookaheadIndex = 0;
        while (subCharsIndex < stopChars.length) {
          char c = this.lookaheadBuffer[lookaheadIndex++];
          char stopChar = stopChars[subCharsIndex++];
          if (c != stopChar && (!ignoreCase || (Character.toLowerCase(c) != stopChar))) {
            return false;
          }
        }
        if (appender != null) {
          appender.run();
        }
        if (skip) {
          shiftLookahead();
          this.offset = lookaheadIndex;
        }
        return true;
      } else {
        char c = this.buffer[myCharsIndex++];
        char stopChar = stopChars[subCharsIndex++];
        if (c != stopChar && (!ignoreCase || (Character.toLowerCase(c) != stopChar))) {
          return false;
        }
      }
    }
    if (skip) {
      this.offset = myCharsIndex;
    }
    return true;
  }

}
