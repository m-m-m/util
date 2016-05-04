/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

import java.io.InputStream;

import net.sf.mmm.util.component.api.ComponentSpecification;

/**
 * This is the interface for a collection of utility functions to that help deal with <em>encodings</em>. An encoding
 * defines a mapping of {@link Character}s of a {@link java.nio.charset.Charset} to {@link Byte}s and vice versa.
 *
 * @see net.sf.mmm.util.io.base.EncodingUtilImpl
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@ComponentSpecification
public interface EncodingUtil {

  /** The {@link net.sf.mmm.util.component.api.Cdi#CDI_NAME CDI name}. */
  String CDI_NAME = "net.sf.mmm.util.io.api.EncodingUtil";

  /**
   * The default encoding used by this JVM as fallback if no explicit encoding is specified.
   */
  String SYSTEM_DEFAULT_ENCODING = System.getProperty("file.encoding");

  /**
   * The encoding {@code US-ASCII} (American Standard Code for Information Interchange) also just called {@code ASCII}.
   * <br>
   * This is a basic encoding for Java contained in {@code lib/rt.jar}.
   */
  String ENCODING_US_ASCII = "US-ASCII";

  /**
   * The encoding {@code UTF-8}. It is an 8-bit Unicode Transformation Format. <br>
   * This is a basic encoding for Java contained in {@code lib/rt.jar}.
   */
  String ENCODING_UTF_8 = "UTF-8";

  /**
   * The encoding {@code UTF-16}. It is an 16-bit Unicode Transformation Format. The byte-order is determined by an
   * optional {@link ByteOrderMark}. <br>
   * This is a basic encoding for Java contained in {@code lib/rt.jar}.
   */
  String ENCODING_UTF_16 = "UTF-16";

  /**
   * The encoding {@code UTF-16, little-endian}. It is an 16-bit Unicode Transformation Format. <br>
   * This is a basic encoding for Java contained in {@code lib/rt.jar}.
   */
  String ENCODING_UTF_16_LE = "UTF-16LE";

  /**
   * The encoding {@code UTF-16, big-endian}. It is an 16-bit Unicode Transformation Format. <br>
   * This is a basic encoding for Java contained in {@code lib/rt.jar}.
   */
  String ENCODING_UTF_16_BE = "UTF-16BE";

  /**
   * The encoding {@code UTF-32}. It is an 32-bit Unicode Transformation Format. The byte-order is determined by an
   * optional {@link ByteOrderMark}. <br>
   * <b>ATTENTION:</b><br>
   * UTF-32 is NOT yet supported by Java.
   */
  String ENCODING_UTF_32 = "UTF-32";

  /**
   * The encoding {@code UTF-32, little-endian}. It is an 32-bit Unicode Transformation Format. <br>
   * <b>ATTENTION:</b><br>
   * UTF-32 is NOT yet supported by Java.
   */
  String ENCODING_UTF_32_LE = "UTF-32LE";

  /**
   * The encoding {@code UTF-32, big-endian}. It is an 32-bit Unicode Transformation Format. <br>
   * <b>ATTENTION:</b><br>
   * UTF-32 is NOT yet supported by Java.
   */
  String ENCODING_UTF_32_BE = "UTF-32BE";

  /**
   * The encoding {@code ISO-8859-1} also called {@code Latin-1}. It is covering most Western European languages. <br>
   * This is a basic encoding for Java contained in {@code lib/rt.jar}.
   */
  String ENCODING_ISO_8859_1 = "ISO-8859-1";

  /**
   * The encoding {@code ISO-8859-2} also called {@code Latin-2}. It is covering the Central and Eastern European
   * languages that use the Latin alphabet. <br>
   * This is a basic encoding for Java contained in {@code lib/rt.jar}.
   */
  String ENCODING_ISO_8859_2 = "ISO-8859-2";

  /**
   * The encoding {@code ISO-8859-3} also called {@code Latin-3}. It is covering the South European languages. <br>
   * This is an extended encoding for Java contained in {@code lib/charsets.jar}.
   */
  String ENCODING_ISO_8859_3 = "ISO-8859-3";

  /**
   * The encoding {@code ISO-8859-4} also called {@code Latin-4}. It is covering the North European languages. <br>
   * This is a basic encoding for Java contained in {@code lib/rt.jar}.
   */
  String ENCODING_ISO_8859_4 = "ISO-8859-4";

  /**
   * The encoding {@code ISO-8859-5}. It is covering mostly Slavic languages that use a Cyrillic alphabet. <br>
   * This is a basic encoding for Java contained in {@code lib/rt.jar}.
   */
  String ENCODING_ISO_8859_5 = "ISO-8859-5";

  /**
   * The encoding {@code ISO-8859-6}. It is covering common Arabic language characters. <br>
   * This is an extended encoding for Java contained in {@code lib/charsets.jar}.
   */
  String ENCODING_ISO_8859_6 = "ISO-8859-6";

  /**
   * The encoding {@code ISO-8859-7}. It is covering modern Greek. <br>
   * This is a basic encoding for Java contained in {@code lib/rt.jar}.
   */
  String ENCODING_ISO_8859_7 = "ISO-8859-7";

  /**
   * The encoding {@code ISO-8859-8}. It is covering modern Hebrew (used in Israel). <br>
   * This is an extended encoding for Java contained in {@code lib/charsets.jar}.
   */
  String ENCODING_ISO_8859_8 = "ISO-8859-8";

  /**
   * The encoding {@code ISO-8859-9} also called {@code Latin-5}. It is covering Turkish and Kurdish. <br>
   * This is a basic encoding for Java contained in {@code lib/rt.jar}.
   */
  String ENCODING_ISO_8859_9 = "ISO-8859-9";

  /**
   * The encoding {@code ISO-8859-10} also called {@code Latin-6}. It is used for Nordic languages. <br>
   * <b>ATTENTION:</b><br>
   * This encoding is NOT supported by Java.
   */
  String ENCODING_ISO_8859_10 = "ISO-8859-10";

  /**
   * The encoding {@code ISO-8859-11}. The {@link java.nio.charset.Charset#name() canonical name} however is
   * {@code x-iso-8859-11}. It is covering common Thai language characters.
   */
  String ENCODING_ISO_8859_11 = "x-iso-8859-11";

  /**
   * The encoding {@code ISO-8859-12}. The work on this encoding for Devanagari was stopped so it does NOT exist at all.
   */
  @Deprecated
  String ENCODING_ISO_8859_12 = "ISO-8859-12";

  /**
   * The encoding {@code ISO-8859-13} also called {@code Latin-7}. It is covering Baltic languages. <br>
   * This is a basic encoding for Java contained in {@code lib/rt.jar}.
   */
  String ENCODING_ISO_8859_13 = "ISO-8859-13";

  /**
   * The encoding {@code ISO-8859-14} also called {@code Latin-8}. It is covering Celtic languages. <br>
   * This encoding is NOT supported by Java.
   */
  String ENCODING_ISO_8859_14 = "ISO-8859-14";

  /**
   * The encoding {@code ISO-8859-15} also called {@code Latin-9}. It is very similar to {@link #ENCODING_ISO_8859_1
   * Latin-1} but adds the euro-sign and 7 other characters by replacing rarely used ones. <br>
   * This is a basic encoding for Java contained in {@code lib/rt.jar}.
   */
  String ENCODING_ISO_8859_15 = "ISO-8859-15";

  /**
   * The encoding {@code ISO-8859-16} also called {@code Latin-10}. It is covering South-Eastern European languages and
   * includes the euro-sign. <br>
   * This encoding is NOT supported by Java.
   */
  String ENCODING_ISO_8859_16 = "ISO-8859-16";

  /**
   * The encoding {@code KOI8-R}. It is covering Russian and Bulgarian. It is therefore related to
   * {@link #ENCODING_ISO_8859_5} and {@link #ENCODING_WINDOWS_1251}. <br>
   * This is a basic encoding for Java contained in {@code lib/rt.jar}.
   */
  String ENCODING_KOI8_R = "KOI8-R";

  /**
   * The encoding {@code KOI8-U}. It is covering Ukrainian. It is related to {@link #ENCODING_KOI8_R},
   * {@link #ENCODING_ISO_8859_5} and {@link #ENCODING_WINDOWS_1251}. <br>
   * <b>ATTENTION:</b><br>
   * This encoding is NOT supported by Java.
   */
  String ENCODING_KOI8_U = "KOI8-U";

  /**
   * The encoding {@code CP437} also called {@code DOS-US}. It is used by MS-DOS and is based on
   * {@link #ENCODING_US_ASCII} but NOT completely compatible.
   */
  String ENCODING_CP_437 = "IBM437";

  /**
   * The encoding {@code CP737}. It is used by MS-DOS for Greek and is therefore related to {@link #ENCODING_CP_869} and
   * {@link #ENCODING_ISO_8859_7}.
   */
  String ENCODING_CP_737 = "x-IBM737";

  /**
   * The encoding {@code CP850}. It is used by MS-DOS for Western European languages and is therefore related to
   * {@link #ENCODING_ISO_8859_1}.
   */
  String ENCODING_CP_850 = "IBM850";

  /**
   * The encoding {@code CP852}. It is used by MS-DOS for Central European languages and is therefore related to
   * {@link #ENCODING_ISO_8859_2}.
   */
  String ENCODING_CP_852 = "IBM852";

  /**
   * The encoding {@code CP855}. It is used by MS-DOS for Cyrillic letters and is therefore related to
   * {@link #ENCODING_ISO_8859_5}.
   */
  String ENCODING_CP_855 = "IBM855";

  /**
   * The encoding {@code CP857}. It is used by MS-DOS for Turkish and is therefore related to
   * {@link #ENCODING_ISO_8859_9}.
   */
  String ENCODING_CP_857 = "IBM857";

  /**
   * The encoding {@code CP857}. It is used by MS-DOS for Western European languages and is like
   * {@link #ENCODING_CP_850} but replaces one character with the euro-sign. It is therefore related to
   * {@link #ENCODING_ISO_8859_15}.
   */
  String ENCODING_CP_858 = "IBM00858";

  /**
   * The encoding {@code CP860}. It is used by MS-DOS for Portuguese and is therefore related to
   * {@link #ENCODING_ISO_8859_1}.
   */
  String ENCODING_CP_860 = "IBM860";

  /**
   * The encoding {@code CP861}. It is used by MS-DOS for Nordic languages especially for Icelandic and is therefore
   * related to {@link #ENCODING_ISO_8859_10}.
   */
  String ENCODING_CP_861 = "IBM861";

  /**
   * The encoding {@code CP863}. It is used by MS-DOS for French and is therefore related to
   * {@link #ENCODING_ISO_8859_15}.
   */
  String ENCODING_CP_863 = "IBM863";

  /**
   * The encoding {@code CP865}. It is used by MS-DOS for Nordic languages except Icelandic for which
   * {@link #ENCODING_CP_861} is used. It is therefore related to {@link #ENCODING_ISO_8859_10}.
   */
  String ENCODING_CP_865 = "IBM865";

  /**
   * The encoding {@code CP866}. It is used by MS-DOS for Cyrillic letters and is therefore related to
   * {@link #ENCODING_CP_855} and {@link #ENCODING_ISO_8859_5}.
   */
  String ENCODING_CP_866 = "IBM866";

  /**
   * The encoding {@code CP869}. It is used by MS-DOS for Greek and is therefore related to {@link #ENCODING_CP_737} and
   * {@link #ENCODING_ISO_8859_7}.
   */
  String ENCODING_CP_869 = "IBM869";

  /**
   * The encoding {@code CP1250} also called {@code Windows-1250}. It is used by Microsoft Windows for Central European
   * languages and is similar to {@link #ENCODING_ISO_8859_2}. <br>
   * This is a basic encoding for Java contained in {@code lib/rt.jar}.
   */
  String ENCODING_WINDOWS_1250 = "windows-1250";

  /**
   * The encoding {@code CP1251} also called {@code Windows-1251}. It is used by Microsoft Windows for Cyrillic letters
   * and is similar to {@link #ENCODING_ISO_8859_5}. <br>
   * This is a basic encoding for Java contained in {@code lib/rt.jar}.
   */
  String ENCODING_WINDOWS_1251 = "windows-1251";

  /**
   * The encoding {@code CP1252} also called {@code Windows-1252}. It is used by Microsoft Windows for Western European
   * languages and is similar to {@link #ENCODING_ISO_8859_1}. <br>
   * This is a basic encoding for Java contained in {@code lib/rt.jar}.
   */
  String ENCODING_WINDOWS_1252 = "windows-1252";

  /**
   * The encoding {@code CP1253} also called {@code Windows-1253}. It is used by Microsoft Windows for Greek and is
   * similar to {@link #ENCODING_ISO_8859_7}. <br>
   * This is a basic encoding for Java contained in {@code lib/rt.jar}.
   */
  String ENCODING_WINDOWS_1253 = "windows-1253";

  /**
   * The encoding {@code CP1254} also called {@code Windows-1254}. It is used by Microsoft Windows for Turkish and is
   * similar to {@link #ENCODING_ISO_8859_9}. <br>
   * This is a basic encoding for Java contained in {@code lib/rt.jar}.
   */
  String ENCODING_WINDOWS_1254 = "windows-1254";

  /**
   * The encoding {@code CP1255} also called {@code Windows-1255}. It is used by Microsoft Windows for Hebrew and is
   * similar to {@link #ENCODING_ISO_8859_8}.
   */
  String ENCODING_WINDOWS_1255 = "windows-1255";

  /**
   * The encoding {@code CP1256} also called {@code Windows-1256}. It is used by Microsoft Windows for Arabic and is
   * similar to {@link #ENCODING_ISO_8859_6}.
   */
  String ENCODING_WINDOWS_1256 = "windows-1256";

  /**
   * The encoding {@code CP1257} also called {@code Windows-1257}. It is used by Microsoft Windows for Baltic languages
   * and is similar to {@link #ENCODING_ISO_8859_13}. <br>
   * This is a basic encoding for Java contained in {@code lib/rt.jar}.
   */
  String ENCODING_WINDOWS_1257 = "windows-1257";

  /**
   * The encoding {@code CP1258} also called {@code Windows-1258}. It is used by Microsoft Windows for Vietnamese and is
   * similar to {@link #ENCODING_WINDOWS_1252}.
   */
  String ENCODING_WINDOWS_1258 = "windows-1258";

  /**
   * This method creates a new {@link java.io.Reader} for the given {@code inputStream}. The
   * {@link EncodingDetectionReader} automatically detects UTF (Unicode Transformation Format) encodings. If the data
   * provided by {@code inputStream} is NOT in such encoding, it will use the given {@code nonUtfEncoding} as fallback.
   * <br>
   * The {@link EncodingDetectionReader} will behave like {@link java.io.InputStreamReader} but with an encoding that is
   * automatically detected whilst reading. It will use a lookahead buffer to detect the encoding. As long as no UTF
   * characteristic was detected and only ASCII-characters ({@code <128}) are hit, the encoding remains
   * {@link #ENCODING_US_ASCII}. As soon as an UTF sequence was detected (e.g. {@link #ENCODING_UTF_8} or
   * {@link #ENCODING_UTF_16_BE}), the encoding switches to that encoding. If a non-ASCII character is hit and no UTF
   * encoding is detected, the {@link EncodingDetectionReader} switches to the given {@code nonUtfEncoding}.
   *
   * @param inputStream is the {@link InputStream} to decode and read.
   * @param nonUtfEncoding is the encoding to use in case the data is NOT encoded in UTF (e.g.
   *        {@link #ENCODING_ISO_8859_15}). It is pointless to use an UTF-based encoding or {@link #ENCODING_US_ASCII}
   *        here.
   * @return a new {@link EncodingDetectionReader} that can be used to read the {@code inputStream}.
   */
  EncodingDetectionReader createUtfDetectionReader(InputStream inputStream, String nonUtfEncoding);

}
