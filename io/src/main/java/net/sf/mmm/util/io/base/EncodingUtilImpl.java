/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Locale;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.collection.base.RankMap;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.io.api.ByteOrderMark;
import net.sf.mmm.util.io.api.ByteProcessor;
import net.sf.mmm.util.io.api.EncodingDetectionReader;
import net.sf.mmm.util.io.api.EncodingUtil;
import net.sf.mmm.util.io.api.ProcessableByteArrayBuffer;
import net.sf.mmm.util.io.impl.BufferInputStream;

/**
 * This is the implementation of the {@link EncodingUtil} interface.
 *
 * @see #getInstance()
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@Singleton
@Named(EncodingUtil.CDI_NAME)
public class EncodingUtilImpl extends AbstractLoggableComponent implements EncodingUtil {

  /**
   * In an UTF-8 multi-byte-sequence all bytes except the first one have the from {@code 10xxxxxx}. This is the
   * lower bound to detect such char.
   */
  public static final byte UTF_8_CONTINUATION_BYTE_MIN = (byte) 0x80;

  /**
   * In an UTF-8 multi-byte-sequence all bytes except the first one have the from {@code 10xxxxxx}. This is the
   * upper bound to detect such char.
   */
  public static final byte UTF_8_CONTINUATION_BYTE_MAX = (byte) 0xBF;

  /**
   * An UTF-8 two-byte-sequence has the form {@code 110xxxxx 10xxxxxx}. This is the lower bound to detect the first
   * char of such sequence. <br>
   * <b>ATTENTION:</b><br>
   * The bytes {@code 0xC0} or {@code 0xC1} would indicate a two-byte-sequence with code-point <= 127 what
   * makes no sense.
   */
  public static final byte UTF_8_TWO_BYTE_MIN = (byte) 0xC2;

  /**
   * An UTF-8 two-byte-sequence has the form {@code 110xxxxx 10xxxxxx}. This is the upper bound to detect the first
   * char of such sequence.
   */
  public static final byte UTF_8_TWO_BYTE_MAX = (byte) 0xDF;

  /**
   * An UTF-8 thee-byte-sequence has the form {@code 1110xxxx 10xxxxxx 10xxxxxx}. This is the lower bound to detect
   * the first char of such sequence.
   */
  public static final byte UTF_8_THREE_BYTE_MIN = (byte) 0xE0;

  /**
   * An UTF-8 thee-byte-sequence has the form {@code 1110xxxx 10xxxxxx 10xxxxxx}. This is the upper bound to detect
   * the first char of such sequence.
   */
  public static final byte UTF_8_THREE_BYTE_MAX = (byte) 0xEF;

  /**
   * An UTF-8 four-byte-sequence has the form {@code 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx}. This is the lower bound
   * to detect the first char of such sequence.
   */
  public static final byte UTF_8_FOUR_BYTE_MIN = (byte) 0xF0;

  /**
   * An UTF-8 four-byte-sequence has the form {@code 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx}. This is the upper bound
   * to detect the first char of such sequence. <br>
   * <b>ATTENTION:</b><br>
   * The bytes {@code 0xF5}, {@code 0xF6}, or {@code 0xF7} would lead to a four-byte-sequence with
   * code-point greater than {@code 10FFFF} which is restricted by <a
   * href="http://tools.ietf.org/html/rfc3629">rfc3629</a>.
   */
  public static final byte UTF_8_FOUR_BYTE_MAX = (byte) 0xF4;

  /**
   * An UTF-16 four-byte-sequence consists of 2 two-byte-sequences called <em>surrogate</em>. The first has the form
   * {@code 110110xx xxxxxxxx}. This is the lower bound to detect the first char of such sequence.
   */
  public static final byte UTF_16_FIRST_SURROGATE_MIN = (byte) 0xD8;

  /**
   * An UTF-16 four-byte-sequence consists of 2 two-byte-sequences called <em>surrogate</em>. The first has the form
   * {@code 110110xx xxxxxxxx}. This is the upper bound to detect the first char of such sequence.
   */
  public static final byte UTF_16_FIRST_SURROGATE_MAX = (byte) 0xDB;

  /**
   * An UTF-16 four-byte-sequence consists of 2 two-byte-sequences called <em>surrogate</em>. The second has the form
   * {@code 110111xx xxxxxxxx}. This is the lower bound to detect the first char of such sequence.
   */
  public static final byte UTF_16_SECOND_SURROGATE_MIN = (byte) 0xDC;

  /**
   * An UTF-16 four-byte-sequence consists of 2 two-byte-sequences called <em>surrogate</em>. The second has the form
   * {@code 110111xx xxxxxxxx}. This is the upper bound to detect the first char of such sequence.
   */
  public static final byte UTF_16_SECOND_SURROGATE_MAX = (byte) 0xDF;

  /** The rank gain if a proper {@link ByteOrderMark} was detected. */
  private static final int RANK_BOM = 20;

  /** The rank gain if a proper UTF-8 multi-byte sequence was detected. */
  private static final int RANK_UTF8_SEQUNCE = 10;

  /** The rank gain if an UTF-16 surrogate pair was detected. */
  private static final int RANK_UTF16_SURROGATE = 6;

  /** @see #getInstance() */
  private static EncodingUtil instance;

  /**
   * The constructor.
   */
  public EncodingUtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link EncodingUtilImpl}. <br>
   * <b>ATTENTION:</b><br>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
   *
   * @return the singleton instance.
   */
  public static EncodingUtil getInstance() {

    if (instance == null) {
      synchronized (EncodingUtilImpl.class) {
        if (instance == null) {
          EncodingUtilImpl util = new EncodingUtilImpl();
          util.initialize();
          instance = util;
        }
      }
    }
    return instance;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EncodingDetectionReader createUtfDetectionReader(InputStream inputStream, String nonUtfEncoding) {

    String encoding = nonUtfEncoding;
    if (encoding == null) {
      encoding = Charset.defaultCharset().name();
      String enc = encoding.toLowerCase(Locale.US);
      if ((enc.startsWith("utf")) || (enc.endsWith("ascii"))) {
        encoding = ENCODING_ISO_8859_1;
      }
    } else {
      String enc = encoding.toLowerCase(Locale.US);
      if ((enc.startsWith("utf")) || (enc.endsWith("ascii"))) {
        getLogger().info("using encoding '" + encoding + "' for 'nonUtfEncoding' does NOT really make sense.");
      }
    }
    return new UtfDetectionReader(inputStream, encoding);
  }

  /**
   * This enum contains represents the type of a {@link Surrogate} from an UTF-16 sequence.
   */
  protected static enum Surrogate {
    /** The first, most significant surrogate. Starts with byte {@code } */
    FIRST,
    /** The second, least significant surrogate. */
    SECOND;
  }

  /**
   * This inner class is used to process the byes from the underlying {@link InputStream} in ASCII mode. It is used as
   * long as no other encoding has been detected.
   */
  protected static class AsciiProcessor implements ByteProcessor {

    /** The character-buffer to fill by the reader. Will be used in ASCII mode. */
    private char[] charBuffer;

    /** The current index in {@link #charBuffer}. */
    private int charOffset;

    /**
     * The constructor.
     */
    public AsciiProcessor() {

      super();
      this.charBuffer = null;
      this.charOffset = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int process(byte[] buffer, int offset, int length) {

      int len = offset + length;
      for (int i = offset; i < len; i++) {
        this.charBuffer[this.charOffset++] = (char) buffer[i];
      }
      return length;
    }
  }

  /**
   * This inner class is used to perform the actual UTF detection. It processes the bytes from the underlying
   * {@link InputStream} from a lookahead buffer. It respects a {@link ByteOrderMark}, UTF-8 multi-byte-sequences,
   * UTF-16 surrogates, zero-bytes for UTF-16 and UTF-32 ASCII overhead, etc.
   */
  protected static class UtfDetectionProcessor implements ByteProcessor {

    /** The {@link RankMap} for encoding detection. */
    private RankMap<String> encodingRankMap;

    /**
     * The {@link ByteOrderMark} or {@code null} if NOT present (or detection NOT started).
     */
    private ByteOrderMark bom;

    /** The encoding to use if encoding is neither UTF nor ASCII. */
    private final String nonUtfEncoding;

    /**
     * {@code false} if the data can NOT be ASCII, {@code true} otherwise.
     */
    private boolean maybeAscii;

    /**
     * {@code false} if the data can NOT be UTF-8, {@code true} otherwise.
     */
    private boolean maybeUtf8;

    /**
     * {@code false} if the data can NOT be UTF-16, {@code true} otherwise.
     */
    private boolean maybeUtf16;

    /** The byte-position in the stream relative to the head. */
    private long bytePosition;

    /** The {@link #bytePosition} where the first non-ascii byte was detected. */
    private long firstNonAsciiPosition;

    /**
     * The number of bytes that have been {@code 0} for each of the {@link #bytePosition positions} modulo 4.
     */
    private int[] zeroByteCounts;

    /**
     * The last {@link Surrogate}s for each of the {@link #bytePosition positions} modulo 4.
     */
    private Surrogate[] surrogates;

    /**
     * The expected number of UTF-8 continuation bytes to come or {@code 0} if no UTF-8 multi-byte-sequence is
     * currently processed.
     */
    private int utf8ContinuationByteCount;

    /**
     * The constructor.
     *
     * @param nonUtfEncoding is the encoding to use if encoding is neither UTF nor ASCII.
     */
    public UtfDetectionProcessor(String nonUtfEncoding) {

      super();
      this.nonUtfEncoding = nonUtfEncoding;
      this.zeroByteCounts = new int[4];
      this.surrogates = new Surrogate[4];
      this.encodingRankMap = new RankMap<>();
      this.maybeAscii = true;
      this.maybeUtf8 = true;
      this.maybeUtf16 = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int process(byte[] buffer, int offset, int length) {

      int len = offset + length;
      for (int i = offset; i < len; i++) {
        byte b = buffer[i];
        if (b < 0) {
          // non ASCII character detected
          if (this.maybeAscii) {
            this.maybeAscii = false;
            this.firstNonAsciiPosition = this.bytePosition;
            this.encodingRankMap.setUnacceptable(ENCODING_US_ASCII);
          }
          if (this.bytePosition == 0) {
            i = processBom(buffer, offset, i);
          }
          if (this.maybeUtf8) {
            processUtf8Detection(b);
          }
          if (this.maybeUtf16) {
            processUtf16Detection(b);
          }
        } else {
          // potential ASCII character
          if (this.utf8ContinuationByteCount > 0) {
            this.utf8ContinuationByteCount = 0;
            this.maybeUtf8 = false;
            this.encodingRankMap.setUnacceptable(ENCODING_UTF_8);
          }
          if (b == 0) {
            int modulo4 = (int) (this.bytePosition & 3);
            this.zeroByteCounts[modulo4]++;
          }
        }
        this.bytePosition++;
      }
      return length;
    }

    /**
     * Heuristic analysis to detect UTF-16 indications.
     *
     * @param b is the single byte to process.
     */
    private void processUtf16Detection(byte b) {

      int modulo4 = (int) (this.bytePosition & 3);
      this.surrogates[modulo4] = null;
      if ((b >= UTF_16_FIRST_SURROGATE_MIN) && (b <= UTF_16_SECOND_SURROGATE_MAX)) {
        if (b <= UTF_16_FIRST_SURROGATE_MAX) {
          this.surrogates[modulo4] = Surrogate.FIRST;
        } else {
          this.surrogates[modulo4] = Surrogate.SECOND;
        }
        int last = (modulo4 - 2) & 3;
        if (this.surrogates[last] != null) {
          if (this.surrogates[last] == this.surrogates[modulo4]) {
            // duplicate surrogate high-byte --> can NOT be any UTF-16*
            this.maybeUtf16 = false;
            this.encodingRankMap.setUnacceptable(ENCODING_UTF_16_LE);
            this.encodingRankMap.setUnacceptable(ENCODING_UTF_16_BE);
          } else {
            if ((modulo4 & 1) == 0) {
              this.encodingRankMap.addRank(ENCODING_UTF_16_BE, RANK_UTF16_SURROGATE);
            } else {
              this.encodingRankMap.addRank(ENCODING_UTF_16_LE, RANK_UTF16_SURROGATE);
            }
          }
        }
      }
    }

    /**
     * Heuristic analysis to detect UTF-8 indications.
     *
     * @param b is the single byte to process.
     */
    private void processUtf8Detection(byte b) {

      if (this.utf8ContinuationByteCount > 0) {
        if ((b >= UTF_8_CONTINUATION_BYTE_MIN) && (b <= UTF_8_CONTINUATION_BYTE_MAX)) {
          this.utf8ContinuationByteCount--;
          if (this.utf8ContinuationByteCount == 0) {
            this.encodingRankMap.addRank(ENCODING_UTF_8, RANK_UTF8_SEQUNCE);
          }
        } else {
          this.utf8ContinuationByteCount = 0;
          this.maybeUtf8 = false;
          this.encodingRankMap.setUnacceptable(ENCODING_UTF_8);
        }
      } else {
        if ((b >= UTF_8_TWO_BYTE_MIN) && (b <= UTF_8_TWO_BYTE_MAX)) {
          // 110xxxxx --> UTF-8 two-byte sequence?
          this.utf8ContinuationByteCount = 1;
        } else if ((b >= UTF_8_THREE_BYTE_MIN) && (b <= UTF_8_THREE_BYTE_MAX)) {
          // 1110xxxx --> UTF-8 three-byte sequence?
          this.utf8ContinuationByteCount = 2;
        } else if ((b >= UTF_8_FOUR_BYTE_MIN) && (b <= UTF_8_FOUR_BYTE_MAX)) {
          // 1110xxxx --> UTF-8 three-byte sequence?
          this.utf8ContinuationByteCount = 3;
        } else {
          this.maybeUtf8 = false;
          this.encodingRankMap.setUnacceptable(ENCODING_UTF_8);
        }
      }
    }

    /**
     * Detects if a {@link ByteOrderMark} (BOM) is available as hint for the encoding.
     *
     * @param buffer is the buffer of the raw data.
     * @param offset is the current offset
     * @param i is the current index.
     * @return the new index. Will be the same as {@code i} or greater if bytes (for detected BOM) have been
     *         consumed.
     */
    private int processBom(byte[] buffer, int offset, int i) {

      // first read - try to detect encoding by BOM...
      int resultIndex = i;
      this.bom = ByteOrderMark.detect(buffer, offset);
      if (this.bom != null) {
        String encoding = this.bom.getEncoding();
        this.encodingRankMap.addRank(encoding, RANK_BOM);
        switch (this.bom) {
          case UTF_8:
            this.maybeUtf16 = false;
            break;
          case UTF_16_BE:
            this.maybeUtf8 = false;
            this.encodingRankMap.setUnacceptable(ENCODING_UTF_16_LE);
            break;
          case UTF_16_LE:
            this.maybeUtf8 = false;
            this.encodingRankMap.setUnacceptable(ENCODING_UTF_16_BE);
            break;
          case UTF_32_BE:
            this.maybeUtf8 = false;
            this.maybeUtf16 = false;
            this.encodingRankMap.setUnacceptable(ENCODING_UTF_32_LE);
            break;
          case UTF_32_LE:
            this.maybeUtf8 = false;
            this.maybeUtf16 = false;
            this.encodingRankMap.setUnacceptable(ENCODING_UTF_32_BE);
            break;
          default:
            // nothing to do...
        }
        int add = this.bom.getLength() - 1;
        resultIndex = resultIndex + add;
        this.bytePosition = add;
      }
      return resultIndex;
    }

    /**
     * This method gets the encoding without taking high-bytes (non-ASCII) into account.
     *
     * @return the low-byte encoding or {@code null} if it looks like ASCII so far.
     */
    public String getLowByteEncoding() {

      int evenZeroCount = this.zeroByteCounts[0] + this.zeroByteCounts[2];
      int oddZeroCount = this.zeroByteCounts[1] + this.zeroByteCounts[3];
      int zeroCount = evenZeroCount + oddZeroCount;
      if (zeroCount > 0) {
        // will ASCII files contain zero bytes???
        if (this.maybeUtf16) {
          if (evenZeroCount == 0) {
            return ENCODING_UTF_16_LE;
          }
          if (oddZeroCount == 0) {
            return ENCODING_UTF_16_BE;
          }
        }
        int highZeroCount = this.zeroByteCounts[0] + this.zeroByteCounts[1];
        if (highZeroCount == 0) {
          return ENCODING_UTF_32_LE;
        }
        int lowZeroCount = this.zeroByteCounts[2] + this.zeroByteCounts[3];
        if (lowZeroCount == 0) {
          return ENCODING_UTF_32_BE;
        }
      }
      return null;
    }

    /**
     * This method gets the detected encoding from the currently processed data.
     *
     * @return the detected encoding or {@code null} if the encoding has NOT yet been detected and it looks like
     *         ASCII so far.
     */
    public String getEncoding() {

      String encoding;
      if (this.maybeAscii) {
        encoding = getLowByteEncoding();
      } else {
        encoding = this.encodingRankMap.getBest();
        if (encoding == null) {
          encoding = this.nonUtfEncoding;
        }
      }
      return encoding;
    }
  }

  /**
   * @see EncodingUtilImpl#createUtfDetectionReader(InputStream, String)
   */
  protected class UtfDetectionReader extends EncodingDetectionReader {

    /** The required lookahead size. */
    private static final int REQUIRED_LOOKAHEAD = 1024;

    /** The used buffer size. */
    private static final int BUFFER_SIZE = REQUIRED_LOOKAHEAD * 2;

    /** The input-stream to read. */
    private final BufferInputStream inputStream;

    /** The processor for ASCII-mode. */
    private final AsciiProcessor asciiProcessor;

    /** The processor to detect encoding by lookahead. */
    private final UtfDetectionProcessor detectionProcessor;

    /** The lookahead buffer used to detect encoding. */
    private final ProcessableByteArrayBuffer detectionBuffer;

    /** @see #getEncoding() */
    private String encoding;

    /**
     * The {@link Reader} to delegate to. Will be {@code null} until the first non ASCII-Character is detected.
     */
    private Reader reader;

    /** The number of ASCII bytes available to read from {@link #inputStream}. */
    private int asciiBytesAvailable;

    /** {@code true} if end of stream is reached. */
    private boolean eos;

    /**
     * The constructor.
     *
     * @param inputStream is the {@link InputStream} to read.
     * @param nonUtfEncoding is the encoding to use as fallback if non-ASCII characters are detected that are NOT
     *        encoded in UTF.
     */
    public UtfDetectionReader(InputStream inputStream, String nonUtfEncoding) {

      super();
      this.inputStream = new BufferInputStream(inputStream, BUFFER_SIZE);
      if (nonUtfEncoding == null) {
        throw new NullPointerException();
      }
      this.asciiProcessor = new AsciiProcessor();
      this.detectionProcessor = new UtfDetectionProcessor(nonUtfEncoding);
      this.detectionBuffer = this.inputStream.createLookaheadBuffer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEncoding() {

      if ((this.encoding == null) && this.eos) {
        return ENCODING_US_ASCII;
      }
      return this.encoding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {

      this.inputStream.close();
      if (this.reader != null) {
        this.reader.close();
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int read(char[] buffer, int offset, int length) throws IOException {

      int offPlusLen = offset + length;
      if ((offset < 0) || (length < 0) || (offPlusLen < 0) || (buffer.length < offPlusLen)) {
        throw new IndexOutOfBoundsException();
      } else if (length == 0) {
        return 0;
      }
      int bytesRead;
      if (this.reader == null) {
        // prevent modifying parameters
        int off = offset;
        int lengthRest = length;
        // start detection
        while (lengthRest > 0) {
          if (this.asciiBytesAvailable == 0) {
            // here we either need to detect the encoding or determine some
            // number of next bytes that are ensured to be ASCII...

            // refill our buffer...
            this.eos = this.inputStream.fill();
            if (this.detectionBuffer.hasNext()) {
              int lookahead = (int) this.detectionBuffer.process(this.detectionProcessor, Integer.MAX_VALUE);
              if ((!this.eos) && (!this.detectionProcessor.maybeAscii)) {
                int nonAsciiOffset = (int) (this.detectionProcessor.bytePosition - this.detectionProcessor.firstNonAsciiPosition);
                if (nonAsciiOffset < REQUIRED_LOOKAHEAD) {
                  this.encoding = this.detectionProcessor.getLowByteEncoding();
                  if (this.encoding == null) {
                    // seems to be ASCII until some byte, but not enough
                    // lookahead to determine encoding after that byte -> empty
                    // ASCII bytes from buffer and refill via loop.
                    this.asciiBytesAvailable = lookahead - nonAsciiOffset - 1;
                  }
                }
              }
              if (this.asciiBytesAvailable == 0) {
                this.encoding = this.detectionProcessor.getEncoding();
                if (this.encoding == null) {
                  // ASCII so far...
                  this.asciiBytesAvailable = lookahead;
                }
              }
            } else {
              assert (this.eos);
              break;
            }
          }
          if (this.encoding == null) {
            assert (this.asciiBytesAvailable > 0);
            this.asciiProcessor.charBuffer = buffer;
            this.asciiProcessor.charOffset = off;
            int asciiCount = this.asciiBytesAvailable;
            if (asciiCount > lengthRest) {
              asciiCount = lengthRest;
            }
            int asciiRead = (int) this.inputStream.process(this.asciiProcessor, asciiCount);
            if (asciiRead == 0) {
              break;
            }
            this.asciiBytesAvailable = this.asciiBytesAvailable - asciiRead;
            lengthRest = lengthRest - asciiRead;
            off = off + asciiRead;
          } else {
            if (getLogger().isTraceEnabled()) {
              getLogger().trace("detected encoding '" + this.encoding + "'");
            }
            this.reader = new InputStreamReader(this.inputStream, this.encoding);
            return this.reader.read(buffer, off, lengthRest);
          }
        }
        bytesRead = length - lengthRest;
        if (bytesRead == 0) {
          assert (this.eos);
          return -1;
        }
      } else {
        bytesRead = this.reader.read(buffer, offset, length);
      }
      return bytesRead;
    }
  }
}
