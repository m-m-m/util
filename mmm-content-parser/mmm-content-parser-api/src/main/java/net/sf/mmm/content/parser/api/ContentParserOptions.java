/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.api;

/**
 * This is the interface with additional options for the {@link ContentParser}.
 * 
 * @see ContentParser#parse(java.io.InputStream, long, ContentParserOptions)
 * @see net.sf.mmm.content.parser.base.ContentParserOptionsBean
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface ContentParserOptions {

  /** The default maximum is 1 MB. */
  int MAXIMUM_BUFFER_SIZE_DEFAULT = 1024 * 1024;

  /**
   * This method gets the maximum size used for buffers when parsing content.
   * Please note that <code>char</code>s will use at least two bytes so e.g.
   * {@link StringBuffer} s shall be {@link StringBuffer#StringBuffer(int)
   * allocated} with a {@link StringBuffer#capacity() capacity} at most
   * <code>maxBytes/2</code>. Anyways the amount of memory allocated will
   * typically be at least twice a much as <code>maxBytes</code>. E.g. a
   * {@link StringBuilder} is created with a {@link StringBuilder#capacity()
   * capacity} of <code>maxBytes/2</code> then {@link StringBuilder#toString()}
   * may allocate another array of about that size. This is a legal behavior of
   * the {@link ContentParser}. So if you want to limit your heap-size use a
   * reasonable lower value for <code>maxBytes</code>. Please do NOT use values
   * lower than 1024 (You do NOT want to parse Word-Documents with J2ME on your
   * toaster, do you?).
   * 
   * @see #MAXIMUM_BUFFER_SIZE_DEFAULT
   * 
   * @return the maximum buffer size in bytes.
   */
  int getMaximumBufferSize();

  /**
   * This method gets the encoding used as fallback, if it can NOT be derived or
   * detected automatically. Parsers for formats that make encoding explicit
   * (e.g. XML) will completely ignore this parameter. For textual formats
   * heuristics will be used to detect unicode (UTF*) if
   * {@link #isDisableUtfDetection() not disabled}. By default
   * {@link #isDisableUtfDetection() UTF detection is enabled} and the encoding
   * is The default is
   * {@link net.sf.mmm.util.io.api.EncodingUtil#SYSTEM_DEFAULT_ENCODING} but
   * replaced by
   * {@link net.sf.mmm.util.io.api.EncodingUtil#ENCODING_ISO_8859_15} if
   * UTF-based.
   * 
   * @return the fallback encoding.
   */
  String getEncoding();

  /**
   * This method determines if the automatic detection of UTF-encodings
   * (unicode) shall be disabled. If this is disabled, textual files are read
   * using the configured {@link #getEncoding() encoding}. Otherwise it will be
   * treaded as fallback if textual files do not seem to be unicode (UTF).
   * 
   * @return <code>true</code> if unicode detection shall be disabled,
   *         <code>false</code> if enabled (default).
   */
  boolean isDisableUtfDetection();

}
