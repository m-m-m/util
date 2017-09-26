/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.scanner.base;

import java.util.NoSuchElementException;

import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.exception.api.NlsParseException;
import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.scanner.api.CharScannerSyntax;
import net.sf.mmm.util.scanner.api.CharStreamScanner;

/**
 * Abstract implementation of {@link CharStreamScanner}.<br>
 * <b>ATTENTION:</b><br>
 * This implementation and its sub-classes are NOT thread-safe and have no intention to be thread-safe.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 7.5.0
 */
public abstract class AbstractCharStreamScannerImpl extends AbstractCharStreamScanner {

  /** The internal buffer with character data. */
  protected char[] buffer;

  /** The start position in the {@link #buffer} from where reading operations consumer data from. */
  protected int offset;

  /**
   * The limit in the {@link #buffer}. If the offset has reached this position no further reading (not even
   * the current {@link #offset}) from the {@link #buffer} is allowed.
   */
  protected int limit;

  /** A {@link StringBuilder} instance that can be shared and reused. May initially be <code>null</code>. */
  private StringBuilder sb;

  /**
   * The constructor.
   *
   * @param capacity the capacity of the internal buffer in {@code char}s.
   */
  public AbstractCharStreamScannerImpl(int capacity) {

    this(new char[capacity]);
  }

  /**
   * The constructor.
   *
   * @param buffer the internal {@code char[]} buffer.
   */
  public AbstractCharStreamScannerImpl(char[] buffer) {

    super();
    this.buffer = buffer;
    this.offset = 0;
    this.limit = 0;
  }

  /**
   * Resets the internal state.
   */
  protected void reset() {

    this.offset = 0;
    this.limit = 0;
    if (this.sb != null) {
      this.sb.setLength(0);
    }
  }

  /**
   * @param builder a local {@link StringBuilder} variable to be allocated lazily.
   * @return the given {@link StringBuilder} if not {@code null} or otherwise a reused {@link StringBuilder}
   *         instance that has been reset.
   */
  protected StringBuilder builder(StringBuilder builder) {

    if (builder != null) {
      return builder;
    }
    if (this.sb == null) {
      this.sb = new StringBuilder(64);
    }
    this.sb.setLength(0);
    return this.sb;
  }

  /**
   * @param builder the local {@link StringBuilder} instance where data may already have been appended to. May
   *        be {@code null}.
   * @param start the start index in the underlying buffer to append.
   * @param end the limit index in the underlying buffer pointing to the next position after the last
   *        character to append.
   * @return the {@link String} with the underlying buffer data from {@code start} to {@code end-1}
   *         potentially appended to the given {@link StringBuilder} if not {@code null}.
   */
  protected String getAppended(StringBuilder builder, int start, int end) {

    if (builder == null) {
      return new String(this.buffer, start, end - start);
    } else {
      builder.append(this.buffer, start, end - start);
      return builder.toString();
    }
  }

  @Override
  public boolean hasNext() {

    if (this.offset < this.limit) {
      return true;
    }
    return fill();
  }

  /**
   * @return {@code true} if the end of a potentially underlying stream has been reached, {@code false}
   *         otherwise. EOS stands for end of stream and in that case the {@link #buffer} contains the entire
   *         rest of the data to scan. So if all data is consumed from the buffer and EOS is reached, then
   *         also {@link #isEot() EOT} has been reached.
   */
  protected boolean isEos() {

    return true;
  }

  /**
   * @return {@code true} if end of text (EOT) is known to been reached, {@code false} otherwise. The returned
   *         result will be almost the same as <code>!{@link #hasNext()}</code> but this method will modify
   *         the state of this scanner (read additional data, modify buffers, etc.). However, if the
   *         underlying stream is already consumed without returning {@code -1} to signal {@link #isEos() EOS}
   *         this method may return {@code false} even though the next call of {@link #hasNext()} may also
   *         return {@code false}.
   */
  protected boolean isEot() {

    return (this.offset >= this.limit);
  }

  /**
   * Fills the internal {@link #buffer} with further data (if available from underlying source such as a
   * stream/reader). If the {@link #isEos() end of the stream} has not been reached, all buffers should be
   * filled now.
   *
   * @return {@code true} if data was filled, {@code false} if {@link #isEos() EOS}.
   */
  protected boolean fill() {

    return false;
  }

  @Override
  public char next() {

    if (hasNext()) {
      return this.buffer[this.offset++];
    }
    throw new NoSuchElementException();
  }

  @Override
  public char forceNext() {

    if (hasNext()) {
      return this.buffer[this.offset++];
    }
    return 0;
  }

  @Override
  public char peek() {

    if (hasNext()) {
      return this.buffer[this.offset];
    }
    throw new NoSuchElementException();
  }

  @Override
  public char forcePeek() {

    if (hasNext()) {
      return this.buffer[this.offset];
    }
    return 0;
  }

  private String eot(StringBuilder builder, boolean acceptEot) {

    if (acceptEot) {
      if (builder == null) {
        return "";
      }
      return builder.toString();
    }
    return null;
  }

  @Override
  public String readUntil(char stop, boolean acceptEot) {

    if (!hasNext()) {
      eot(null, acceptEot);
    }
    StringBuilder builder = null;
    while (true) {
      int start = this.offset;
      while (this.offset < this.limit) {
        char c = this.buffer[this.offset++];
        if (c == stop) {
          return getAppended(builder, start, this.offset - 1);
        }
      }
      builder = builder(builder);
      builder.append(this.buffer, start, this.limit - start);
      if (!fill()) {
        return eot(builder, acceptEot);
      }
    }
  }

  @Override
  public String readUntil(final char stop, boolean acceptEot, CharScannerSyntax syntax) {

    CharFilter filter = new CharFilter() {

      @Override
      public boolean accept(char c) {

        return (c == stop);
      }
    };
    return readUntil(filter, acceptEot, syntax);
  }

  @Override
  public String readUntil(CharFilter filter, boolean acceptEot, CharScannerSyntax syntax) {

    if (!hasNext()) {
      return eot(null, acceptEot);
    }
    CharScannerSyntaxState state = new CharScannerSyntaxState(syntax, filter);
    while (true) {
      // int end = this.limit;
      while (this.offset < this.limit) {
        char c = this.buffer[this.offset++];
        state.parse(c);
        if (state.done) {
          return state.builder.toString();
        }
      }
      boolean eot = isEot();
      if (!eot || acceptEot) {
        int len = this.limit - state.start;
        if (state.quoteEscapeActive) {
          if (state.activeQuoteEscape == state.activeQuoteEnd) {
            // omit quote on appending of rest
            len--;
          }
        }
        if (len > 0) {
          // append rest
          StringBuilder builder;
          if (state.activeEntityEnd == 0) {
            builder = state.builder;
          } else {
            builder = state.getEntityBuilder();
          }
          builder.append(this.buffer, state.start, len);
        }
      }
      state.start = 0;
      eot = !hasNext(); // fill buffers and get sure about EOT
      if (eot) {
        return eot(state.builder, acceptEot);
      }
    }
  }

  @Override
  public String readUntil(char stop, boolean acceptEot, char escape) {

    if (!hasNext()) {
      eot(null, acceptEot);
    }
    StringBuilder builder = null;
    while (true) {
      int start = this.offset;
      while (this.offset < this.limit) {
        char c = this.buffer[this.offset++];
        if (c == escape) {
          builder = builder(builder);
          builder.append(this.buffer, start, this.offset - start - 1);
          // lookahead
          if (this.offset >= this.limit) {
            if (!fill()) {
              return eot(builder, acceptEot);
            }
          }
          c = this.buffer[this.offset];
          if ((escape == stop) && (c != stop)) {
            return builder.toString();
          } else {
            // escape character
            builder.append(c);
            this.offset++;
            start = this.offset;
          }
        } else if (c == stop) {
          return getAppended(builder, start, this.offset - 1);
        }
      }
      builder = builder(builder);
      builder.append(this.buffer, start, this.limit - start);
      if (!fill()) {
        return eot(builder, acceptEot);
      }
    }
  }

  @Override
  public String readUntil(CharFilter filter, boolean acceptEot) {

    if (!hasNext()) {
      eot(null, acceptEot);
    }
    while (true) {
      StringBuilder builder = null;
      int start = this.offset;
      while (this.offset < this.limit) {
        if (filter.accept(this.buffer[this.offset++])) {
          return getAppended(builder, start, this.offset - 1);
        }
      }
      builder = builder(builder);
      builder.append(this.buffer, start, this.limit - start);
      if (!fill()) {
        if (acceptEot) {
          return builder.toString();
        }
        return null;
      }
    }
  }

  @Override
  public void require(char expected) throws NlsParseException {

    String value = "";
    if (hasNext()) {
      if (this.buffer[this.offset] == expected) {
        this.offset++;
        return;
      }
      value = Character.toString(this.buffer[this.offset]);
    }
    throw new NlsParseException(value, Character.toString(expected), Character.class, new String(this.buffer, this.offset, this.limit));
  }

  @Override
  public void require(String expected, boolean ignoreCase) throws NlsParseException {

    if (!expect(expected, ignoreCase)) {
      throw new NlsParseException(new String(this.buffer, 0, this.limit), expected, String.class);
    }
  }

  @Override
  public boolean expect(char expected) {

    if (!hasNext()) {
      return false;
    }
    if (this.buffer[this.offset] == expected) {
      this.offset++;
      return true;
    }
    return false;
  }

  @Override
  public boolean expect(String expected, boolean ignoreCase) {

    int len = expected.length();
    for (int i = 0; i < len; i++) {
      if (!hasNext()) {
        return false;
      }
      char c = this.buffer[this.offset];
      char exp = expected.charAt(i);
      if (c != exp) {
        if (!ignoreCase) {
          return false;
        }
        if (Character.toLowerCase(c) != Character.toLowerCase(exp)) {
          return false;
        }
      }
      this.offset++;
    }
    return true;
  }

  @Override
  public String readLine() {

    if (!hasNext()) {
      return null;
    }
    StringBuilder builder = null;
    while (true) {
      int start = this.offset;
      while (this.offset < this.limit) {
        char c = this.buffer[this.offset++];
        if (c == '\r') {
          if (this.offset < this.limit) {
            int end = this.offset - 1;
            if (this.buffer[this.offset] == '\n') {
              this.offset++;
            }
            return getAppended(builder, start, end);
          } else { // EOL insanity...
            builder = builder(builder);
            builder.append(this.buffer, start, this.offset - start - 1);
            if (fill()) {
              start = this.offset;
              if (this.buffer[this.offset] == '\n') {
                this.offset++;
              }
            }
            return builder.toString();
          }
        } else if (c == '\n') {
          return getAppended(builder, start, this.offset - 1);
        }
      }
      builder = builder(builder);
      builder.append(this.buffer, start, this.limit - start);
      if (!fill()) {
        return builder.toString();
      }
    }

  }

  @Override
  public String read(int count) {

    if (!hasNext()) {
      return "";
    }
    int len = this.limit - this.offset;
    int remain = count;
    if ((count > len) && isEos()) {
      remain = len;
    }
    if (len >= remain) {
      String string = new String(this.buffer, this.offset, remain);
      this.offset += remain;
      return string;
    }
    StringBuilder builder = builder(null);
    while (remain > 0) {
      builder.append(this.buffer, this.offset, len);
      remain -= len;
      if (fill()) {
        len = this.limit - this.offset;
      } else {
        return builder.toString();
      }
    }
    return builder.toString();
  }

  @Override
  public int readDigit() {

    int result = -1;
    if (hasNext()) {
      char c = this.buffer[this.offset];
      if ((c >= '0') && (c <= '9')) {
        result = c - '0';
        this.offset++;
      }
    }
    return result;
  }

  /**
   * Consumes the characters of a decimal number (double or float).
   *
   * @return the decimal number as {@link String}.
   */
  @Override
  protected String consumeDecimal() {

    StringBuilder builder = null;
    boolean noSign = false;
    boolean noExponent = false;
    boolean noDot = false;
    boolean done = false;
    char c = 0;
    hasNext();
    while (true) {
      int start = this.offset;
      while (this.offset < this.limit) {
        c = this.buffer[this.offset];
        if ((c == '+') || (c == '-')) {
          if (noSign) {
            done = true;
            break;
          } else {
            noSign = true;
          }
        } else if (c == 'e') {
          if (noExponent) {
            done = true;
            break;
          } else {
            noExponent = true;
            noSign = false;
            noDot = true;
          }
        } else if (c == '.') {
          if (noDot) {
            done = true;
            break;
          } else {
            noDot = true;
          }
        } else if ((c >= '0') && (c <= '9')) {
          noSign = true;
        } else {
          done = true;
          break;
        }
        this.offset++;
      }
      int len = this.offset - start;
      if (done) {
        return getAppended(builder, start, this.offset);
      }
      if (len > 0) {
        builder = builder(builder);
        builder.append(this.buffer, start, len);
      }
      if (done || !fill()) {
        if (builder == null) {
          throw new NlsParseException(Character.toString(c), "([+-0-9.e]+", Number.class);
        }
        return builder.toString();
      }
    }
  }

  @Override
  public long readLong(int maxDigits) throws NumberFormatException {

    if (maxDigits <= 0) {
      throw new NlsIllegalArgumentException(Integer.toString(maxDigits), "maxDigits");
    }
    StringBuilder builder = null;
    if (this.offset >= this.limit) {
      fill();
    }
    int remain = maxDigits;
    while (true) {
      int start = this.offset;
      int end = this.offset + remain;
      if (end > this.limit) {
        end = this.limit;
      }
      char c = 0;
      while (this.offset < end) {
        c = this.buffer[this.offset];
        if ((c < '0') || (c > '9')) {
          break;
        }
        this.offset++;
      }
      int len = this.offset - start;
      remain -= len;
      if ((this.offset < end) || (remain == 0) || (start == this.limit)) {
        if ((len == 0) && (builder == null)) {
          throw new NlsParseException(Character.toString(c), "[0-9]+", Number.class);
        }
        String number = getAppended(builder, start, this.offset);
        return Long.parseLong(number);
      } else {
        builder = builder(builder);
        builder.append(this.buffer, start, this.offset - start);
        fill();
      }
    }
  }

  @Override
  public boolean skipUntil(char stop) {

    while (hasNext()) {
      while (this.offset < this.limit) {
        if (this.buffer[this.offset++] == stop) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public boolean skipUntil(char stop, char escape) {

    boolean escapeActive = false;
    while (hasNext()) {
      while (this.offset < this.limit) {
        char c = this.buffer[this.offset++];
        if (c == escape) {
          escapeActive = !escapeActive;
        } else {
          if ((c == stop) && (!escapeActive)) {
            return true;
          }
          escapeActive = false;
        }
      }
    }
    return false;
  }

  @Override
  public int skipWhile(char c) {

    int count = 0;
    while (hasNext()) {
      int start = this.offset;
      while (this.offset < this.limit) {
        if (this.buffer[this.offset] != c) {
          return count + (this.offset - start);
        }
        this.offset++;
      }
      count += (this.offset - start);
    }
    return count;
  }

  @Override
  public int skipWhile(CharFilter filter, int max) {

    if (max < 0) {
      throw new IllegalArgumentException("Max must NOT be negative: " + max);
    }
    int remain = max;
    while (hasNext()) {
      int start = this.offset;
      int end = start + remain;
      if (end < 0) { // overflow?
        end = remain;
      }
      if (end > this.limit) {
        end = this.limit;
      }
      boolean notAccepted = false;
      while (this.offset < end) {
        if (!filter.accept(this.buffer[this.offset])) {
          notAccepted = true;
          break;
        }
        this.offset++;
      }
      int len = this.offset - start;
      remain -= len;
      if (notAccepted || (remain == 0)) {
        break;
      }
    }
    return (max - remain);
  }

  @Override
  public String readWhile(CharFilter filter, int max) {

    if (max < 0) {
      throw new IllegalArgumentException("Max must NOT be negative: " + max);
    }
    StringBuilder builder = null;
    if (this.offset >= this.limit) {
      fill();
    }
    int remain = max;
    while (true) {
      int start = this.offset;
      int end = start + remain;
      if (end < 0) { // overflow?
        end = remain;
      }
      if (end > this.limit) {
        end = this.limit;
      }
      while (this.offset < end) {
        if (!filter.accept(this.buffer[this.offset])) {
          return getAppended(builder, start, this.offset);
        }
        this.offset++;
      }
      int len = this.offset - start;
      remain -= len;
      builder = builder(builder);
      builder.append(this.buffer, start, this.offset);
      if ((remain == 0) || !fill()) {
        return builder.toString();
      }
    }
  }

  @Override
  public String toString() {

    if (this.offset < this.limit) {
      return new String(this.buffer, this.offset, this.limit - this.offset);
    } else {
      return "";
    }
  }

  private class CharScannerSyntaxState {

    private final CharScannerSyntax syntax;

    private final CharFilter filter;

    private final char quoteStart;

    private final char quoteEnd;

    private final char escape;

    private final char quoteEscape;

    private final boolean quoteEscapeLazy;

    private final char altQuoteStart;

    private final char altQuoteEnd;

    private final char altQuoteEscape;

    private final boolean altQuoteEscapeLazy;

    private final char entityStart;

    private final char entityEnd;

    private int start;

    private boolean escapeActive;

    private char activeQuoteEnd;

    private char activeQuoteEscape;

    private char activeQuoteLazyEnd;

    private boolean activeQuoteLazy;

    private char activeEntityEnd;

    private StringBuilder builder;

    private StringBuilder entityBuilder;

    private boolean done;

    private boolean quoteEscapeActive;

    private CharScannerSyntaxState(CharScannerSyntax syntax, CharFilter filter) {

      super();
      this.syntax = syntax;
      this.filter = filter;
      // copy to avoid method calls and boost performance
      this.escape = syntax.getEscape();
      this.quoteStart = syntax.getQuoteStart();
      this.quoteEnd = syntax.getQuoteEnd();
      this.quoteEscape = syntax.getQuoteEscape();
      this.quoteEscapeLazy = syntax.isQuoteEscapeLazy();
      this.altQuoteStart = syntax.getAltQuoteStart();
      this.altQuoteEnd = syntax.getAltQuoteEnd();
      this.altQuoteEscape = syntax.getAltQuoteEscape();
      this.altQuoteEscapeLazy = syntax.isAltQuoteEscapeLazy();
      this.entityStart = syntax.getEntityStart();
      this.entityEnd = syntax.getEntityEnd();
      // init state
      this.builder = builder(null);
      this.start = AbstractCharStreamScannerImpl.this.offset;
      this.escapeActive = false;
      this.activeQuoteEnd = 0;
      this.activeQuoteEscape = 0;
      this.activeQuoteLazy = false;
      this.activeQuoteLazyEnd = 0;
      this.activeEntityEnd = 0;
      this.quoteEscapeActive = false;
      this.done = false;
    }

    public StringBuilder getEntityBuilder() {

      if (this.entityBuilder == null) {
        this.entityBuilder = new StringBuilder(4);
      }
      return this.entityBuilder;
    }

    private void parse(char c) {

      boolean append = false;
      if (this.escapeActive) {
        // current character c was escaped
        // it will be taken as is on the next append
        this.escapeActive = false;
      } else if (this.activeQuoteEnd != 0) {
        // parse quote
        if ((this.activeQuoteLazyEnd != 0) && (c == this.activeQuoteLazyEnd)) {
          this.activeQuoteEnd = 0;
          this.builder.append(c); // quote (was escaped lazily)
          this.start = AbstractCharStreamScannerImpl.this.offset;
        } else if (this.quoteEscapeActive) {
          this.quoteEscapeActive = false;
          if (c == this.activeQuoteEnd) {
            this.builder.append(c); // quote (was escaped)
            this.start = AbstractCharStreamScannerImpl.this.offset;
          } else if (this.activeQuoteEscape == this.activeQuoteEnd) {
            // quotation done
            this.activeQuoteEnd = 0;
            this.start = AbstractCharStreamScannerImpl.this.offset - 1;
          }
        } else if (c == this.activeQuoteEscape) {
          // escape in quote
          append = true;
          this.quoteEscapeActive = true;
        } else if (c == this.activeQuoteEnd) {
          // quotation done
          this.activeQuoteEnd = 0;
          append = true;
        }
        this.activeQuoteLazyEnd = 0;
      } else if (this.activeEntityEnd != 0) {
        // parse entity
        if (c == this.activeEntityEnd) {
          // entity end detected...
          this.activeEntityEnd = 0;
          int len = AbstractCharStreamScannerImpl.this.offset - this.start - 1;
          String entity;
          if (this.entityBuilder == null) {
            entity = new String(AbstractCharStreamScannerImpl.this.buffer, this.start, len);
          } else {
            this.entityBuilder.append(AbstractCharStreamScannerImpl.this.buffer, this.start, len);
            entity = this.entityBuilder.toString();
            this.entityBuilder = null;
          }
          this.builder.append(this.syntax.resolveEntity(entity));
          this.start = AbstractCharStreamScannerImpl.this.offset;
        }
      } else if (this.filter.accept(c)) {
        append = true;
        this.done = true;
      } else if (c == this.escape) {
        append = true;
        this.escapeActive = true;
      } else if (c == this.entityStart) {
        this.activeEntityEnd = this.entityEnd;
        append = true;
      } else {
        if (c == this.quoteStart) {
          this.activeQuoteEnd = this.quoteEnd;
          this.activeQuoteEscape = this.quoteEscape;
          this.activeQuoteLazy = this.quoteEscapeLazy;
        } else if (c == this.altQuoteStart) {
          this.activeQuoteEnd = this.altQuoteEnd;
          this.activeQuoteEscape = this.altQuoteEscape;
          this.activeQuoteLazy = this.altQuoteEscapeLazy;
        }
        if (this.activeQuoteEnd != 0) {
          this.quoteEscapeActive = false;
          append = true;
          if (this.activeQuoteLazy && (this.activeQuoteEnd == this.activeQuoteEscape) && (c == this.activeQuoteEscape)) {
            this.activeQuoteLazyEnd = this.activeQuoteEnd;
          }
        }
      }
      if (append) {
        int len = AbstractCharStreamScannerImpl.this.offset - this.start - 1;
        if (len > 0) {
          this.builder.append(AbstractCharStreamScannerImpl.this.buffer, this.start, len);
        }
        this.start = AbstractCharStreamScannerImpl.this.offset;
      }
    }

  }

}
