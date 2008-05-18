/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import net.sf.mmm.util.collection.RankMap;
import net.sf.mmm.util.component.AbstractLoggable;

/**
 * This class is a collection of utility functions to deal with
 * <em>encodings</em>. An encoding defines a mapping of {@link Character}s
 * of a {@link java.nio.charset.Charset} to {@link Byte}s and vice versa.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class EncodingUtil extends AbstractLoggable {

  /**
   * The default encoding used by this JVM as fallback if no explicit encoding
   * is specified.
   */
  public static final String SYSTEM_DEFAULT_ENCODING = System.getProperty("file.encoding");

  /**
   * The encoding <code>US-ASCII</code> (American Standard Code for
   * Information Interchange) also just called <code>ASCII</code>.<br>
   * This is a basic encoding for Java contained in <code>lib/rt.jar</code>.
   */
  public static final String ENCODING_US_ASCII = "US-ASCII";

  /**
   * The encoding <code>UTF-8</code>. It is an 8-bit Unicode Transformation
   * Format.<br>
   * This is a basic encoding for Java contained in <code>lib/rt.jar</code>.
   */
  public static final String ENCODING_UTF_8 = "UTF-8";

  /**
   * The encoding <code>UTF-16</code>. It is an 16-bit Unicode Transformation
   * Format. The byte-order is determined by an optional {@link ByteOrderMark}.<br>
   * This is a basic encoding for Java contained in <code>lib/rt.jar</code>.
   */
  public static final String ENCODING_UTF_16 = "UTF-16";

  /**
   * The encoding <code>UTF-16, little-endian</code>. It is an 16-bit Unicode
   * Transformation Format.<br>
   * This is a basic encoding for Java contained in <code>lib/rt.jar</code>.
   */
  public static final String ENCODING_UTF_16_LE = "UTF-16LE";

  /**
   * The encoding <code>UTF-16, big-endian</code>. It is an 16-bit Unicode
   * Transformation Format.<br>
   * This is a basic encoding for Java contained in <code>lib/rt.jar</code>.
   */
  public static final String ENCODING_UTF_16_BE = "UTF-16BE";

  /**
   * The encoding <code>UTF-32</code>. It is an 32-bit Unicode Transformation
   * Format. The byte-order is determined by an optional {@link ByteOrderMark}.<br>
   * <b>ATTENTION:</b><br>
   * UTF-32 is NOT yet supported by Java.
   */
  public static final String ENCODING_UTF_32 = "UTF-32";

  /**
   * The encoding <code>UTF-32, little-endian</code>. It is an 32-bit Unicode
   * Transformation Format.<br>
   * <b>ATTENTION:</b><br>
   * UTF-32 is NOT yet supported by Java.
   */
  public static final String ENCODING_UTF_32_LE = "UTF-32LE";

  /**
   * The encoding <code>UTF-32, big-endian</code>. It is an 32-bit Unicode
   * Transformation Format.<br>
   * <b>ATTENTION:</b><br>
   * UTF-32 is NOT yet supported by Java.
   */
  public static final String ENCODING_UTF_32_BE = "UTF-32BE";

  /**
   * The encoding <code>ISO-8859-1</code> also called <code>Latin-1</code>.
   * It is covering most Western European languages.<br>
   * This is a basic encoding for Java contained in <code>lib/rt.jar</code>.
   */
  public static final String ENCODING_ISO_8859_1 = "ISO-8859-1";

  /**
   * The encoding <code>ISO-8859-2</code> also called <code>Latin-2</code>.
   * It is covering the Central and Eastern European languages that use the
   * Latin alphabet.<br>
   * This is a basic encoding for Java contained in <code>lib/rt.jar</code>.
   */
  public static final String ENCODING_ISO_8859_2 = "ISO-8859-2";

  /**
   * The encoding <code>ISO-8859-3</code> also called <code>Latin-3</code>.
   * It is covering the South European languages.<br>
   * This is an extended encoding for Java contained in
   * <code>lib/charsets.jar</code>.
   */
  public static final String ENCODING_ISO_8859_3 = "ISO-8859-3";

  /**
   * The encoding <code>ISO-8859-4</code> also called <code>Latin-4</code>.
   * It is covering the North European languages.<br>
   * This is a basic encoding for Java contained in <code>lib/rt.jar</code>.
   */
  public static final String ENCODING_ISO_8859_4 = "ISO-8859-4";

  /**
   * The encoding <code>ISO-8859-5</code>. It is covering mostly Slavic
   * languages that use a Cyrillic alphabet.<br>
   * This is a basic encoding for Java contained in <code>lib/rt.jar</code>.
   */
  public static final String ENCODING_ISO_8859_5 = "ISO-8859-5";

  /**
   * The encoding <code>ISO-8859-6</code>. It is covering common Arabic
   * language characters.<br>
   * This is an extended encoding for Java contained in
   * <code>lib/charsets.jar</code>.
   */
  public static final String ENCODING_ISO_8859_6 = "ISO-8859-6";

  /**
   * The encoding <code>ISO-8859-7</code>. It is covering modern Greek.<br>
   * This is a basic encoding for Java contained in <code>lib/rt.jar</code>.
   */
  public static final String ENCODING_ISO_8859_7 = "ISO-8859-7";

  /**
   * The encoding <code>ISO-8859-8</code>. It is covering modern Hebrew (used
   * in Israel).<br>
   * This is an extended encoding for Java contained in
   * <code>lib/charsets.jar</code>.
   */
  public static final String ENCODING_ISO_8859_8 = "ISO-8859-8";

  /**
   * The encoding <code>ISO-8859-9</code> also called <code>Latin-5</code>.
   * It is covering Turkish and Kurdish.<br>
   * This is a basic encoding for Java contained in <code>lib/rt.jar</code>.
   */
  public static final String ENCODING_ISO_8859_9 = "ISO-8859-9";

  /**
   * The encoding <code>ISO-8859-10</code> also called <code>Latin-6</code>.
   * It is used for Nordic languages.<br>
   * <b>ATTENTION:</b><br>
   * This encoding is NOT supported by Java.
   */
  public static final String ENCODING_ISO_8859_10 = "ISO-8859-10";

  /**
   * The encoding <code>ISO-8859-11</code>. The
   * {@link java.nio.charset.Charset#name() canonical name} however is
   * <code>x-iso-8859-11</code>. It is covering common Thai language
   * characters.
   */
  public static final String ENCODING_ISO_8859_11 = "x-iso-8859-11";

  /**
   * The encoding <code>ISO-8859-12</code>. The work on this encoding for
   * Devanagari was stopped so it does NOT exist at all.
   */
  @Deprecated
  public static final String ENCODING_ISO_8859_12 = "ISO-8859-12";

  /**
   * The encoding <code>ISO-8859-13</code> also called <code>Latin-7</code>.
   * It is covering Baltic languages.<br>
   * This is a basic encoding for Java contained in <code>lib/rt.jar</code>.
   */
  public static final String ENCODING_ISO_8859_13 = "ISO-8859-13";

  /**
   * The encoding <code>ISO-8859-14</code> also called <code>Latin-8</code>.
   * It is covering Celtic languages.<br>
   * This encoding is NOT supported by Java.
   */
  public static final String ENCODING_ISO_8859_14 = "ISO-8859-14";

  /**
   * The encoding <code>ISO-8859-15</code> also called <code>Latin-9</code>.
   * It is very similar to {@link #ENCODING_ISO_8859_1 Latin-1} but adds the
   * euro-sign and 7 other characters by replacing rarely used ones.<br>
   * This is a basic encoding for Java contained in <code>lib/rt.jar</code>.
   */
  public static final String ENCODING_ISO_8859_15 = "ISO-8859-15";

  /**
   * The encoding <code>ISO-8859-16</code> also called <code>Latin-10</code>.
   * It is covering South-Eastern European languages and includes the euro-sign.<br>
   * This encoding is NOT supported by Java.
   */
  public static final String ENCODING_ISO_8859_16 = "ISO-8859-16";

  /**
   * The encoding <code>KOI8-R</code>. It is covering Russian and Bulgarian.
   * It is therefore related to {@link #ENCODING_ISO_8859_5} and
   * {@link #ENCODING_WINDOWS_1251}.<br>
   * This is a basic encoding for Java contained in <code>lib/rt.jar</code>.
   */
  public static final String ENCODING_KOI8_R = "KOI8-R";

  /**
   * The encoding <code>KOI8-U</code>. It is covering Ukrainian. It is
   * related to {@link #ENCODING_KOI8_R}, {@link #ENCODING_ISO_8859_5} and
   * {@link #ENCODING_WINDOWS_1251}.<br>
   * <b>ATTENTION:</b><br>
   * This encoding is NOT supported by Java.
   */
  public static final String ENCODING_KOI8_U = "KOI8-U";

  /**
   * The encoding <code>CP437</code> also called <code>DOS-US</code>. It is
   * used by MS-DOS and is based on {@link #ENCODING_US_ASCII} but NOT
   * completely compatible.
   */
  public static final String ENCODING_CP_437 = "IBM437";

  /**
   * The encoding <code>CP737</code>. It is used by MS-DOS for Greek and is
   * therefore related to {@link #ENCODING_CP_869} and
   * {@link #ENCODING_ISO_8859_7}.
   */
  public static final String ENCODING_CP_737 = "x-IBM737";

  /**
   * The encoding <code>CP850</code>. It is used by MS-DOS for Western
   * European languages and is therefore related to {@link #ENCODING_ISO_8859_1}.
   */
  public static final String ENCODING_CP_850 = "IBM850";

  /**
   * The encoding <code>CP852</code>. It is used by MS-DOS for Central
   * European languages and is therefore related to {@link #ENCODING_ISO_8859_2}.
   */
  public static final String ENCODING_CP_852 = "IBM852";

  /**
   * The encoding <code>CP855</code>. It is used by MS-DOS for Cyrillic
   * letters and is therefore related to {@link #ENCODING_ISO_8859_5}.
   */
  public static final String ENCODING_CP_855 = "IBM855";

  /**
   * The encoding <code>CP857</code>. It is used by MS-DOS for Turkish and is
   * therefore related to {@link #ENCODING_ISO_8859_9}.
   */
  public static final String ENCODING_CP_857 = "IBM857";

  /**
   * The encoding <code>CP857</code>. It is used by MS-DOS for Western
   * European languages and is like {@link #ENCODING_CP_850} but replaces one
   * character with the euro-sign. It is therefore related to
   * {@link #ENCODING_ISO_8859_15}.
   */
  public static final String ENCODING_CP_858 = "IBM00858";

  /**
   * The encoding <code>CP860</code>. It is used by MS-DOS for Portuguese and
   * is therefore related to {@link #ENCODING_ISO_8859_1}.
   */
  public static final String ENCODING_CP_860 = "IBM860";

  /**
   * The encoding <code>CP861</code>. It is used by MS-DOS for Nordic
   * languages especially for Icelandic and is therefore related to
   * {@link #ENCODING_ISO_8859_10}.
   */
  public static final String ENCODING_CP_861 = "IBM861";

  /**
   * The encoding <code>CP863</code>. It is used by MS-DOS for French and is
   * therefore related to {@link #ENCODING_ISO_8859_15}.
   */
  public static final String ENCODING_CP_863 = "IBM863";

  /**
   * The encoding <code>CP865</code>. It is used by MS-DOS for Nordic
   * languages except Icelandic for which {@link #ENCODING_CP_861} is used. It
   * is therefore related to {@link #ENCODING_ISO_8859_10}.
   */
  public static final String ENCODING_CP_865 = "IBM865";

  /**
   * The encoding <code>CP866</code>. It is used by MS-DOS for Cyrillic
   * letters and is therefore related to {@link #ENCODING_CP_855} and
   * {@link #ENCODING_ISO_8859_5}.
   */
  public static final String ENCODING_CP_866 = "IBM866";

  /**
   * The encoding <code>CP869</code>. It is used by MS-DOS for Greek and is
   * therefore related to {@link #ENCODING_CP_737} and
   * {@link #ENCODING_ISO_8859_7}.
   */
  public static final String ENCODING_CP_869 = "IBM869";

  /**
   * The encoding <code>CP1250</code> also called <code>Windows-1250</code>.
   * It is used by Microsoft Windows for Central European languages and is
   * similar to {@link #ENCODING_ISO_8859_2}.<br>
   * This is a basic encoding for Java contained in <code>lib/rt.jar</code>.
   */
  public static final String ENCODING_WINDOWS_1250 = "windows-1250";

  /**
   * The encoding <code>CP1251</code> also called <code>Windows-1251</code>.
   * It is used by Microsoft Windows for Cyrillic letters and is similar to
   * {@link #ENCODING_ISO_8859_5}.<br>
   * This is a basic encoding for Java contained in <code>lib/rt.jar</code>.
   */
  public static final String ENCODING_WINDOWS_1251 = "windows-1251";

  /**
   * The encoding <code>CP1252</code> also called <code>Windows-1252</code>.
   * It is used by Microsoft Windows for Western European languages and is
   * similar to {@link #ENCODING_ISO_8859_1}.<br>
   * This is a basic encoding for Java contained in <code>lib/rt.jar</code>.
   */
  public static final String ENCODING_WINDOWS_1252 = "windows-1252";

  /**
   * The encoding <code>CP1253</code> also called <code>Windows-1253</code>.
   * It is used by Microsoft Windows for Greek and is similar to
   * {@link #ENCODING_ISO_8859_7}.<br>
   * This is a basic encoding for Java contained in <code>lib/rt.jar</code>.
   */
  public static final String ENCODING_WINDOWS_1253 = "windows-1253";

  /**
   * The encoding <code>CP1254</code> also called <code>Windows-1254</code>.
   * It is used by Microsoft Windows for Turkish and is similar to
   * {@link #ENCODING_ISO_8859_9}.<br>
   * This is a basic encoding for Java contained in <code>lib/rt.jar</code>.
   */
  public static final String ENCODING_WINDOWS_1254 = "windows-1254";

  /**
   * The encoding <code>CP1255</code> also called <code>Windows-1255</code>.
   * It is used by Microsoft Windows for Hebrew and is similar to
   * {@link #ENCODING_ISO_8859_8}.
   */
  public static final String ENCODING_WINDOWS_1255 = "windows-1255";

  /**
   * The encoding <code>CP1256</code> also called <code>Windows-1256</code>.
   * It is used by Microsoft Windows for Arabic and is similar to
   * {@link #ENCODING_ISO_8859_6}.
   */
  public static final String ENCODING_WINDOWS_1256 = "windows-1256";

  /**
   * The encoding <code>CP1257</code> also called <code>Windows-1257</code>.
   * It is used by Microsoft Windows for Baltic languages and is similar to
   * {@link #ENCODING_ISO_8859_13}.<br>
   * This is a basic encoding for Java contained in <code>lib/rt.jar</code>.
   */
  public static final String ENCODING_WINDOWS_1257 = "windows-1257";

  /**
   * The encoding <code>CP1258</code> also called <code>Windows-1258</code>.
   * It is used by Microsoft Windows for Vietnamese and is similar to
   * {@link #ENCODING_WINDOWS_1252}.
   */
  public static final String ENCODING_WINDOWS_1258 = "windows-1258";

  /**
   * In an UTF-8 multi-byte-sequence all bytes except the first one have the
   * from <code>10xxxxxx</code>. This is the lower bound to detect such char.
   */
  public static final byte UTF_8_CONTINUATION_BYTE_MIN = (byte) 0x80;

  /**
   * In an UTF-8 multi-byte-sequence all bytes except the first one have the
   * from <code>10xxxxxx</code>. This is the upper bound to detect such char.
   */
  public static final byte UTF_8_CONTINUATION_BYTE_MAX = (byte) 0xBF;

  /**
   * An UTF-8 two-byte-sequence has the form <code>110xxxxx 10xxxxxx</code>.
   * This is the lower bound to detect the first char of such sequence.<br>
   * <b>ATTENTION:</b><br>
   * The bytes <code>0xC0</code> or <code>0xC1</code> would indicate a
   * two-byte-sequence with code-point <= 127 what makes no sense.
   */
  public static final byte UTF_8_TWO_BYTE_MIN = (byte) 0xC2;

  /**
   * An UTF-8 two-byte-sequence has the form <code>110xxxxx 10xxxxxx</code>.
   * This is the upper bound to detect the first char of such sequence.
   */
  public static final byte UTF_8_TWO_BYTE_MAX = (byte) 0xDF;

  /**
   * An UTF-8 thee-byte-sequence has the form
   * <code>1110xxxx 10xxxxxx 10xxxxxx</code>. This is the lower bound to
   * detect the first char of such sequence.
   */
  public static final byte UTF_8_THREE_BYTE_MIN = (byte) 0xE0;

  /**
   * An UTF-8 thee-byte-sequence has the form
   * <code>1110xxxx 10xxxxxx 10xxxxxx</code>. This is the upper bound to
   * detect the first char of such sequence.
   */
  public static final byte UTF_8_THREE_BYTE_MAX = (byte) 0xEF;

  /**
   * An UTF-8 four-byte-sequence has the form
   * <code>11110xxx 10xxxxxx 10xxxxxx 10xxxxxx</code>. This is the lower
   * bound to detect the first char of such sequence.
   */
  public static final byte UTF_8_FOUR_BYTE_MIN = (byte) 0xF0;

  /**
   * An UTF-8 four-byte-sequence has the form
   * <code>11110xxx 10xxxxxx 10xxxxxx 10xxxxxx</code>. This is the upper
   * bound to detect the first char of such sequence.<br>
   * <b>ATTENTION:</b><br>
   * The bytes <code>0xF5</code>, <code>0xF6</code>, or <code>0xF7</code>
   * would lead to a four-byte-sequence with code-point greater than
   * <code>10FFFF</code> which is restricted by <a
   * href="http://tools.ietf.org/html/rfc3629">rfc3629</a>.
   */
  public static final byte UTF_8_FOUR_BYTE_MAX = (byte) 0xF4;

  /**
   * An UTF-16 four-byte-sequence consists of 2 two-byte-sequences called
   * <em>surrogate</em>. The first has the form
   * <code>110110xx xxxxxxxx</code>. This is the lower bound to detect the
   * first char of such sequence.
   */
  public static final byte UTF_16_FIRST_SURROGATE_MIN = (byte) 0xD8;

  /**
   * An UTF-16 four-byte-sequence consists of 2 two-byte-sequences called
   * <em>surrogate</em>. The first has the form
   * <code>110110xx xxxxxxxx</code>. This is the upper bound to detect the
   * first char of such sequence.
   */
  public static final byte UTF_16_FIRST_SURROGATE_MAX = (byte) 0xDB;

  /**
   * An UTF-16 four-byte-sequence consists of 2 two-byte-sequences called
   * <em>surrogate</em>. The second has the form
   * <code>110111xx xxxxxxxx</code>. This is the lower bound to detect the
   * first char of such sequence.
   */
  public static final byte UTF_16_SECOND_SURROGATE_MIN = (byte) 0xDC;

  /**
   * An UTF-16 four-byte-sequence consists of 2 two-byte-sequences called
   * <em>surrogate</em>. The second has the form
   * <code>110111xx xxxxxxxx</code>. This is the upper bound to detect the
   * first char of such sequence.
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
  public EncodingUtil() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link EncodingUtil}.<br>
   * This design is the best compromise between easy access (via this
   * indirection you have direct, static access to all offered functionality)
   * and IoC-style design which allows extension and customization.<br>
   * For IoC usage, simply ignore all static {@link #getInstance()} methods and
   * construct new instances via the container-framework of your choice (like
   * plexus, pico, springframework, etc.). To wire up the dependent components
   * everything is properly annotated using common-annotations (JSR-250). If
   * your container does NOT support this, you should consider using a better
   * one.
   * 
   * @return the singleton instance.
   */
  public static EncodingUtil getInstance() {

    if (instance == null) {
      synchronized (EncodingUtil.class) {
        if (instance == null) {
          EncodingUtil util = new EncodingUtil();
          util.initialize();
          instance = util;
        }
      }
    }
    return instance;
  }

  /**
   * This method creates a new {@link Reader} for the given
   * <code>inputStream</code>. The {@link Reader} automatically detects UTF
   * (Unicode Transformation Format) encodings. If the data provided by
   * <code>inputStream</code> is NOT in such encoding, it will use the given
   * <code>nonUtfEncoding</code> as fallback.<br>
   * The {@link Reader} will behave like {@link InputStreamReader} but with an
   * encoding that is automatically detected whilst reading. It will use a
   * lookahead buffer to detect the encoding. As long as no UTF characteristic
   * was detected and only ASCII-characters (<code>&lt;128</code>) are hit,
   * the encoding remains {@link #ENCODING_US_ASCII}. As soon as an UTF
   * sequence was detected (e.g. {@link #ENCODING_UTF_8} or
   * {@link #ENCODING_UTF_16_BE}), the encoding switches to that encoding. If a
   * non-ASCII character is hit and no UTF encoding is detected, the
   * {@link Reader} switches to the given <code>nonUtfEncoding</code>.
   * 
   * @param inputStream is the {@link InputStream} to decode and read.
   * @param nonUtfEncoding is the encoding to use in case the data is NOT
   *        encoded in UTF (e.g. {@link #ENCODING_ISO_8859_15}). It is
   *        pointless to use an UTF-based encoding or {@link #ENCODING_US_ASCII}
   *        here.
   * @return a new {@link Reader} that can be used to read the
   *         <code>inputStream</code>.
   */
  public EncodingDetectionReader createUtfDetectionReader(InputStream inputStream,
      String nonUtfEncoding) {

    String enc = nonUtfEncoding.toLowerCase();
    if ((enc.startsWith("utf")) || (enc.endsWith("ascii"))) {
      getLogger().warn(
          "using encoding '" + nonUtfEncoding + "' for nonUtfEncoding does NOT really make sense.");
    }
    return new UtfDetectionReader(inputStream, nonUtfEncoding);
  }

  /**
   * This enum contains represents the type of a {@link Surrogate} from an
   * UTF-16 sequence.
   */
  protected static enum Surrogate {
    /** The first, most significant surrogate. Starts with byte <code></code> */
    FIRST,
    /** The second, least significant surrogate. */
    SECOND;
  }

  /**
   * This inner class is used to process the byes from the underlying
   * {@link InputStream} in ASCII mode. It is used as long as no other encoding
   * has been detected.
   */
  protected static class AsciiProcessor implements ByteProcessor {

    /** The character-buffer to fill by the reader. Will be used in ASCII mode. */
    private char[] charBuffer;

    /** The current index in {@link #charBuffer}. */
    private int charOffset;

    /**
     * {@inheritDoc}
     */
    public void process(byte[] buffer, int offset, int length) {

      int len = offset + length;
      for (int i = offset; i < len; i++) {
        this.charBuffer[this.charOffset++] = (char) buffer[i];
      }
    }
  }

  /**
   * This inner class is used to perform the actual UTF detection. It processes
   * the bytes from the underlying {@link InputStream} from a lookahead buffer.
   * It respects a {@link ByteOrderMark}, UTF-8 multi-byte-sequences, UTF-16
   * surrogates, zero-bytes for UTF-16 and UTF-32 ASCII overhead, etc.
   */
  protected static class UtfDetectionProcessor implements ByteProcessor {

    /** The {@link RankMap} for encoding detection. */
    private RankMap<String> encodingRankMap;

    /**
     * The {@link ByteOrderMark} or <code>null</code> if NOT present (or
     * detection NOT started).
     */
    private ByteOrderMark bom;

    /** The encoding to use if encoding is neither UTF nor ASCII. */
    private final String nonUtfEncoding;

    /**
     * <code>false</code> if the data can NOT be ASCII, <code>true</code>
     * otherwise.
     */
    private boolean maybeAscii;

    /**
     * <code>false</code> if the data can NOT be UTF-8, <code>true</code>
     * otherwise.
     */
    private boolean maybeUtf8;

    /**
     * <code>false</code> if the data can NOT be UTF-16, <code>true</code>
     * otherwise.
     */
    private boolean maybeUtf16;

    /** The byte-position in the stream relative to the head. */
    private long bytePosition;

    /** The {@link #bytePosition} where the first non-ascii byte was detected. */
    private long firstNonAsciiPosition;

    /**
     * The number of bytes that have been <code>0</code> for each of the
     * {@link #bytePosition positions} modulo 4.
     */
    private int[] zeroByteCounts;

    /**
     * The last {@link Surrogate}s for each of the
     * {@link #bytePosition positions} modulo 4.
     */
    private Surrogate[] surrogates;

    /**
     * The expected number of UTF-8 continuation bytes to come or <code>0</code>
     * if no UTF-8 multi-byte-sequence is currently processed.
     */
    private int utf8ContinuationByteCount;

    /**
     * The constructor.
     * 
     * @param nonUtfEncoding is the encoding to use if encoding is neither UTF
     *        nor ASCII.
     */
    public UtfDetectionProcessor(String nonUtfEncoding) {

      super();
      this.nonUtfEncoding = nonUtfEncoding;
      this.zeroByteCounts = new int[4];
      this.surrogates = new Surrogate[4];
      this.encodingRankMap = new RankMap<String>();
      this.maybeAscii = true;
      this.maybeUtf8 = true;
      this.maybeUtf16 = true;
    }

    /**
     * {@inheritDoc}
     */
    public void process(byte[] buffer, int offset, int length) {

      int len = offset + length;
      for (int i = offset; i < len; i++) {
        int modulo4 = (int) (this.bytePosition & 3);
        this.surrogates[modulo4] = null;
        byte b = buffer[i];
        if (b < 0) {
          // non ASCII character detected
          if (this.maybeAscii) {
            this.maybeAscii = false;
            this.firstNonAsciiPosition = this.bytePosition;
            this.encodingRankMap.setUnacceptable(ENCODING_US_ASCII);
          }
          if (this.bytePosition == 0) {
            // first read - try to detect encoding by BOM...
            this.bom = ByteOrderMark.detect(buffer, offset);
            if (this.bom != null) {
              String encoding = this.bom.getEncoding();
              // if (getLogger().isTraceEnabled()) {
              // getLogger().trace("BOM detected for '" + encoding + "'.");
              // }
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
                default :
                  // nothing to do...
              }
              int add = this.bom.getLength() - 1;
              i = i + add;
              this.bytePosition = add;
            }
            // no BOM available...
          }
          if (this.maybeUtf8) {
            // start smart UTF-8 detection...
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
          if (this.maybeUtf16) {
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
        } else {
          if (this.utf8ContinuationByteCount > 0) {
            this.utf8ContinuationByteCount = 0;
            this.maybeUtf8 = false;
            this.encodingRankMap.setUnacceptable(ENCODING_UTF_8);
          }
          if (b == 0) {
            this.zeroByteCounts[modulo4]++;
          }
        }
        this.bytePosition++;
      }
    }

    /**
     * This method gets the encoding without taking high-bytes (non-ASCII) into
     * account.
     * 
     * @return the low-byte encoding or <code>null</code> if it looks like
     *         ASCII so far.
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
     * @return the detected encoding or <code>null</code> if the encoding has
     *         NOT yet been detected and it looks like ASCII so far.
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
   * @see EncodingUtil#createUtfDetectionReader(InputStream, String)
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
     * The {@link Reader} to delegate to. Will be <code>null</code> until the
     * first non ASCII-Character is detected.
     */
    private Reader reader;

    /** The number of ASCII bytes available to read from {@link #inputStream}. */
    private int asciiBytesAvailable;

    /** <code>true</code> if end of stream is reached. */
    private boolean eos;

    /**
     * The constructor.
     * 
     * @param inputStream is the {@link InputStream} to read.
     * @param nonUtfEncoding is the encoding to use as fallback if non-ASCII
     *        characters are detected that are NOT encoded in UTF.
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
    public int read(char[] cbuf, int offset, int length) throws IOException {

      int offPlusLen = offset + length;
      if ((offset < 0) || (length < 0) || (offPlusLen < 0) || (cbuf.length < offPlusLen)) {
        throw new IndexOutOfBoundsException();
      } else if (length == 0) {
        return 0;
      }
      int bytesRead;
      if (this.reader != null) {
        bytesRead = this.reader.read(cbuf, offset, length);
      } else {
        int off = offset;
        int len = length;
        while (len > 0) {
          if (this.asciiBytesAvailable == 0) {
            // here we either need to detect the encoding or determine some
            // number of next bytes that are ensured to be ASCII...

            // refill our buffer...
            this.eos = this.inputStream.fill();
            if (this.detectionBuffer.hasNext()) {
              int lookahead = (int) this.detectionBuffer.process(this.detectionProcessor,
                  Integer.MAX_VALUE);
              if ((!this.eos) && (!this.detectionProcessor.maybeAscii)) {
                int nonAsciiOffset = (int) (this.detectionProcessor.bytePosition - this.detectionProcessor.firstNonAsciiPosition);
                if (nonAsciiOffset < REQUIRED_LOOKAHEAD) {
                  this.encoding = this.detectionProcessor.getLowByteEncoding();
                  if (this.encoding == null) {
                    // seems to be ASCII until some byte, but not enough
                    // lookahead to determined encoding after that byte -> empty
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
            this.asciiProcessor.charBuffer = cbuf;
            this.asciiProcessor.charOffset = off;
            int asciiCount = this.asciiBytesAvailable;
            if (asciiCount > len) {
              asciiCount = len;
            }
            int asciiRead = (int) this.inputStream.process(this.asciiProcessor, asciiCount);
            if (asciiRead == 0) {
              break;
            }
            this.asciiBytesAvailable = this.asciiBytesAvailable - asciiRead;
            len = len - asciiRead;
            off = off + asciiRead;
          } else {
            if (getLogger().isTraceEnabled()) {
              getLogger().trace("detected encoding '" + this.encoding + "'");
            }
            this.reader = new InputStreamReader(this.inputStream, this.encoding);
            return this.reader.read(cbuf, off, len);
          }
        }
        bytesRead = length - len;
        if (bytesRead == 0) {
          return -1;
        }
      }
      return bytesRead;
    }
  }
}
