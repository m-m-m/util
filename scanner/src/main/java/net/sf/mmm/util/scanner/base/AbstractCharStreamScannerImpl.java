/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.scanner.base;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.lang.api.BasicHelper;
import net.sf.mmm.util.lang.api.CharEscapeHelper;
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

  private static final Logger LOG = LoggerFactory.getLogger(AbstractCharStreamScannerImpl.class);

  private static final CharFilter FILTER_SINGLE_QUOTE = new CharFilter() {

    @Override
    public boolean accept(char c) {

      return c == '\'';
    }
  };

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
   * @param builder a local {@link StringBuilder} variable to be allocated lazily.
   * @param start the start index in the underlying buffer to append.
   * @param end the limit index in the underlying buffer pointing to the next position after the last
   *        character to append.
   * @return the given {@link StringBuilder} if not {@code null} or otherwise a reused {@link StringBuilder}
   *         instance that has been reset.
   */
  protected StringBuilder append(StringBuilder builder, int start, int end) {

    int len = end - start;
    if (len <= 0) {
      return builder;
    }
    StringBuilder b = builder(builder);
    b.append(this.buffer, start, len);
    return b;
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

    int len = end - start;
    if (len <= 0) {
      return eot(builder, true);
    }
    if (builder == null) {
      return new String(this.buffer, start, len);
    } else {
      builder.append(this.buffer, start, len);
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

  @Override
  public boolean isEos() {

    return true;
  }

  /**
   * @return {@code true} if end of buffer (EOB) or in other words no data is available after the current
   *         {@link #buffer}, {@code false} otherwise (e.g. if not {@link #isEos() EOS}).
   */
  protected boolean isEob() {

    return true;
  }

  @Override
  public boolean isEot() {

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

  /**
   * @param builder the optional {@link StringBuilder} where data may have already been appended.
   * @param acceptEot {@code true} to accept {@link #isEot() EOT}, {@code false} otherwise.
   * @return {@code null} if {@code acceptEot} is {@code false}, otherwise the {@link String} from the given
   *         {@link StringBuilder} or the empty {@link String} in case the {@link StringBuilder} was
   *         {@code null}.
   */
  protected String eot(StringBuilder builder, boolean acceptEot) {

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
      builder = append(builder, start, this.limit);
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
          builder = append(builder, start, this.offset - 1);
          // lookahead
          if (this.offset >= this.limit) {
            if (!fill()) {
              return eot(builder, acceptEot);
            }
          }
          c = this.buffer[this.offset];
          if ((escape == stop) && (c != stop)) {
            return eot(builder, true);
          } else {
            // escape character
            builder = builder(builder);
            builder.append(c);
            this.offset++;
            start = this.offset;
          }
        } else if (c == stop) {
          return getAppended(builder, start, this.offset - 1);
        }
      }
      builder = append(builder, start, this.limit);
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
    StringBuilder builder = null;
    while (true) {
      int start = this.offset;
      while (this.offset < this.limit) {
        if (filter.accept(this.buffer[this.offset])) {
          return getAppended(builder, start, this.offset - 1);
        }
        this.offset++;
      }
      builder = append(builder, start, this.limit);
      if (!fill()) {
        return eot(builder, acceptEot);
      }
    }
  }

  @Override
  public String readUntil(CharFilter stopFilter, boolean acceptEot, String stop, boolean ignoreCase, final boolean trim) {

    int stopLength = stop.length();
    if (stopLength == 0) {
      return "";
    }
    verifyLookahead(stop);
    if (!hasNext()) {
      return eot(null, acceptEot);
    }
    if (trim) {
      skipWhile(' ');
    }
    char[] stopChars;
    if (ignoreCase) {
      stopChars = BasicHelper.toLowerCase(stop).toCharArray();
    } else {
      stopChars = stop.toCharArray();
    }
    char first = stopChars[0];
    Appender appender = newAppender(trim);
    while (true) {
      appender.start = this.offset;
      appender.trimEnd = this.offset;
      int max = this.limit;
      if (isEos()) {
        // we can only find the substring at a position
        // until where enough chars are left to go...
        max -= stopLength;
      }
      while (this.offset < max) {
        char c = this.buffer[this.offset];
        if (stopFilter.accept(c)) {
          return appender.getAppended();
        }
        if (c == first || (ignoreCase && (Character.toLowerCase(c) == first))) {
          // found first character
          boolean found = expectRestWithLookahead(stopChars, ignoreCase, appender, false);
          if (found) {
            return appender.getAppended(this.offset);
          }
        }
        if (trim && (c != ' ')) {
          appender.foundNonSpace();
        }
        this.offset++;
      }
      appender.append(this.offset);
      if (!fill()) {
        // substring not found (EOT)
        this.offset = this.limit;
        return appender.toString();
      }
    }
  }

  /**
   * @param substring the substring to match without consuming what requires a lookahead.
   */
  protected void verifyLookahead(String substring) {
    // nothing by default
  }

  /**
   * @param stopChars the stop {@link String} as {@link String#toCharArray() char[]}. If {@code ignoreCase} is
   *        {@code true} in lower case.
   * @param ignoreCase - {@code true} to (also) compare chars in {@link Character#toLowerCase(char) lower
   *        case}, {@code false} otherwise.
   * @param appender an optional lambda to {@link Runnable#run() run} before shifting buffers to append data.
   * @param skip - {@code true} to update buffers and offset such that on success this scanner points after
   *        the expected stop {@link String}, {@code false} otherwise (to not consume any character in any
   *        case).
   * @return {@code true} if the stop {@link String} ({@code stopChars}) was found and consumed, {@code false}
   *         otherwise (and no data consumed).
   * @see #readUntil(CharFilter, boolean, String, boolean)
   * @see #skipOver(String, boolean, CharFilter)
   */
  protected abstract boolean expectRestWithLookahead(char[] stopChars, boolean ignoreCase, Runnable appender, boolean skip);

  @Override
  public void require(char expected) {

    if (hasNext()) {
      if (this.buffer[this.offset] == expected) {
        this.offset++;
        return;
      }
    }
    throw new IllegalStateException("Expecting '" + expected + "' but found: " + new String(this.buffer, this.offset, this.limit));
  }

  @Override
  public void require(String expected, boolean ignoreCase) {

    if (!expect(expected, ignoreCase)) {
      throw new IllegalStateException("Expecting '" + expected + "' but found: " + new String(this.buffer, this.offset, this.limit));
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
  public String readLine(boolean trim) {

    if (!hasNext()) {
      return null;
    }
    if (trim) {
      skipWhile(' ');
    }
    Appender appender = newAppender(trim);
    while (true) {
      appender.start = this.offset;
      appender.trimEnd = this.offset;
      while (this.offset < this.limit) {
        char c = this.buffer[this.offset];
        if (c == '\r') {
          int end = this.offset;
          this.offset++;
          if (this.offset < this.limit) {
            if (this.buffer[this.offset] == '\n') {
              this.offset++;
            }
            return appender.getAppended(end);
          } else { // EOL insanity...
            appender.append(end);
            if (fill()) {
              if (this.buffer[this.offset] == '\n') {
                this.offset++;
              }
            }
            return appender.toString();
          }
        } else if (c == '\n') {
          String result = appender.getAppended();
          this.offset++;
          return result;
        } else if (c != ' ') {
          appender.foundNonSpace();
        }
        this.offset++;
      }
      appender.append(this.limit);
      if (!fill()) {
        return appender.toString();
      }
    }
  }

  @Override
  public String readJavaStringLiteral(boolean tolerant) {

    if (!hasNext()) {
      return null;
    }
    if (this.buffer[this.offset] != '"') {
      return null;
    }
    this.offset++;
    StringBuilder builder = null;
    while (hasNext()) {
      int start = this.offset;
      while (this.offset < this.limit) {
        char c = this.buffer[this.offset++];
        if (c == '"') {
          return getAppended(builder, start, this.offset - 1);
        } else if (c == '\\') {
          builder = append(builder, start, this.offset - 1);
          builder = builder(builder);
          parseEscapeSequence(builder, tolerant);
          start = this.offset;
        }
      }
      builder = append(builder, start, this.offset);
    }
    String value = "";
    if (builder != null) {
      value = builder.toString();
    }
    if (tolerant) {
      LOG.debug("Tolerating unterminated string literal: {}", value);
      return value;
    }
    throw new IllegalStateException("Java string literal not terminated: \"" + value);
  }

  @Override
  public Character readJavaCharLiteral(boolean tolerant) {

    if (expect('\'')) {
      StringBuilder error = null;
      char c = forceNext();
      char next = 0;
      if (c == '\\') {
        c = forceNext();
        if (c == 'u') {
          c = parseUnicodeEscapeSequence(tolerant);
          if (expect('\'')) {
            return Character.valueOf(c);
          }
          error = createUnicodeLiteralError(c);
        } else {
          next = forceNext();
          if (next == '\'') {
            Character character = CharEscapeHelper.resolveEscape(c);
            if (character != null) {
              return character;
            }
          } else if (CharFilter.OCTAL_DIGIT_FILTER.accept(c) && CharFilter.OCTAL_DIGIT_FILTER.accept(next)) {
            int value = ((c - '0') * 8) + (next - '0');
            char last = forceNext();
            if (CharFilter.OCTAL_DIGIT_FILTER.accept(last) && (value <= 37)) {
              value = (value * 8) + (last - '0');
              last = forceNext();
            }
            if (last == '\'') {
              return Character.valueOf((char) value);
            }
            error = new StringBuilder("'\\");
            error.append(Integer.toString(value, 8));
            error.append(last);
          }
          if (error == null) {
            error = new StringBuilder("'\\");
            error.append(c);
            error.append(next);
          }
        }
      } else if (expect('\'')) {
        return Character.valueOf(c);
      } else {
        error = new StringBuilder("'");
        if (c != 0) {
          error.append(c);
        }
      }
      if (next != '\'') {
        String rest = readUntil(FILTER_SINGLE_QUOTE, true);
        error.append(rest);
        if (expect('\'')) {
          error.append('\'');
        }
      }
      if (tolerant) {
        LOG.debug("Tolerating invalid char literal: {}", error.toString());
        return Character.valueOf('?');
      }
      throw new IllegalStateException("Invalid Java character literal: " + error.toString());
    }
    return null;

  }

  private StringBuilder createUnicodeLiteralError(char c) {

    StringBuilder error;
    error = new StringBuilder("'\\");
    error.append('u');
    String hex = Integer.toString(c, 16);
    int length = hex.length();
    if (length == 1) {
      hex = "000" + hex;
    } else if (length == 2) {
      hex = "00" + hex;
    } else if (length == 3) {
      hex = "0" + hex;
    }
    error.append(hex);
    return error;
  }

  private void parseEscapeSequence(StringBuilder builder, boolean tolerant) {

    char c = forceNext();
    if (c == 'u') { // unicode
      char value = parseUnicodeEscapeSequence(tolerant);
      builder.append(value);
    } else if (CharFilter.OCTAL_DIGIT_FILTER.accept(c)) { // octal C legacy stuff
      int value = c - '0';
      c = forcePeek();
      if (CharFilter.OCTAL_DIGIT_FILTER.accept(c)) {
        next();
        value = (8 * value) + (c - '0');
        if (value <= 31) {
          c = forcePeek();
          if (CharFilter.OCTAL_DIGIT_FILTER.accept(c)) {
            next();
            value = (8 * value) + (c - '0');
          }
        }
      }
      builder.append((char) value);
    } else {
      Character resolved = CharEscapeHelper.resolveEscape(c);
      if (resolved == null) {
        if (tolerant) {
          builder.append(c);
        } else {
          throw new IllegalStateException("Illegal escape sequence: \\" + c);
        }
      } else {
        builder.append(resolved.charValue());
      }
    }
  }

  private char parseUnicodeEscapeSequence(boolean tolerant) {

    skipWhile('u');
    int i = 0;
    int value = 0;
    int radix = 16;
    while (i < 4) {
      int digit = readDigit(radix);
      if (digit < 0) {
        String hexString;
        if (i == 0) {
          hexString = "";
        } else {
          hexString = Integer.toString(value, radix);
          while (hexString.length() < i) {
            hexString = "0" + hexString;
          }
        }
        if (tolerant) {
          LOG.debug("Tolerating invalid unicode escape sequence: {}", hexString);
          return '?';
        } else {
          throw new IllegalStateException("Illegal Java unicode escape sequence: \\u" + hexString);
        }
      }
      value = (value * radix) + digit;
      i++;
    }
    return (char) value;
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
  public int readDigit(int radix) {

    int result = -1;
    if (hasNext()) {
      char c = this.buffer[this.offset];
      int value = -1;
      if ((c >= '0') && (c <= '9')) {
        value = c - '0';
      }
      if ((c >= 'a') && (c <= 'z')) {
        value = (c - 'a') + 10;
      }
      if ((c >= 'A') && (c <= 'Z')) {
        value = (c - 'A') + 10;
      }
      if ((value >= 0) && (value < radix)) {
        result = value;
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
      if (done) {
        return getAppended(builder, start, this.offset);
      }
      int len = this.offset - start;
      if (len > 0) {
        builder = append(builder, start, this.offset);
      }
      if (done || !fill()) {
        if (builder == null) {
          throw new IllegalStateException("Invalid character for decimal number: " + c);
        }
        return builder.toString();
      }
    }
  }

  @Override
  public long readLong(int maxDigits) throws NumberFormatException {

    if (maxDigits <= 0) {
      throw new IllegalArgumentException(Integer.toString(maxDigits));
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
          throw new IllegalStateException("Invalid character for long number: " + c);
        }
        String number = getAppended(builder, start, this.offset);
        return Long.parseLong(number);
      } else {
        builder = append(builder, start, this.offset);
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
  public boolean skipOver(String substring, boolean ignoreCase, CharFilter stopFilter) {

    int subLength = substring.length();
    if (subLength == 0) {
      return true;
    }
    verifyLookahead(substring);
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
        char c = this.buffer[this.offset];
        if ((stopFilter != null) && stopFilter.accept(c)) {
          return false;
        }
        if (c == first || (ignoreCase && (Character.toLowerCase(c) == first))) {
          // found first character
          boolean found = expectRestWithLookahead(subChars, ignoreCase, null, true);
          if (found) {
            return true;
          }
        }
        this.offset++;
      }
      if (!fill()) {
        // substring not found (EOT)
        this.offset = this.limit;
        return false;
      }
    }
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
      builder = append(builder, start, this.offset);
      if ((remain == 0) || !fill()) {
        return eot(builder, true);
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

  private Appender newAppender(boolean trim) {

    if (trim) {
      return new TrimmingAppender();
    } else {
      return new PlainAppender();
    }
  }

  private abstract class Appender implements Runnable {

    protected StringBuilder builder;

    protected int start;

    protected int trimEnd;

    protected abstract void append(int end);

    protected abstract String getAppended(int end);

    protected abstract String getAppended();

    protected void foundNonSpace() {
      // nothing by default, can be overridden with custom handling...
    }

    @Override
    public String toString() {

      if (this.builder == null) {
        return "";
      }
      return this.builder.toString();
    }
  }

  private class PlainAppender extends Appender {

    @Override
    protected void append(int end) {

      this.builder = AbstractCharStreamScannerImpl.this.append(this.builder, this.start, end);
    }

    @Override
    protected String getAppended(int end) {

      return AbstractCharStreamScannerImpl.this.getAppended(this.builder, this.start, end);
    }

    @Override
    protected String getAppended() {

      return AbstractCharStreamScannerImpl.this.getAppended(this.builder, this.start, AbstractCharStreamScannerImpl.this.offset);
    }

    @Override
    public void run() {

      this.builder = AbstractCharStreamScannerImpl.this.append(this.builder, this.start, AbstractCharStreamScannerImpl.this.offset);
    }
  }

  private class TrimmingAppender extends Appender {

    private int spaceCount;

    @Override
    protected void foundNonSpace() {

      this.trimEnd = AbstractCharStreamScannerImpl.this.offset + 1;
      if (this.spaceCount > 0) {
        this.builder = builder(this.builder);
        while (this.spaceCount > 0) {
          this.builder.append(' ');
          this.spaceCount--;
        }
      }
    }

    @Override
    protected void append(int end) {

      this.spaceCount += end - this.trimEnd;
      this.builder = AbstractCharStreamScannerImpl.this.append(this.builder, this.start, this.trimEnd);
    }

    @Override
    protected String getAppended(int end) {

      return AbstractCharStreamScannerImpl.this.getAppended(this.builder, this.start, this.trimEnd);
    }

    @Override
    protected String getAppended() {

      return AbstractCharStreamScannerImpl.this.getAppended(this.builder, this.start, this.trimEnd);
    }

    @Override
    public void run() {

      this.builder = AbstractCharStreamScannerImpl.this.append(this.builder, this.start, this.trimEnd);
    }
  }

}
