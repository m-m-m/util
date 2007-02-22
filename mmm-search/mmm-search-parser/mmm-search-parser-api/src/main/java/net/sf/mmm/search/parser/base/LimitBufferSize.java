/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.parser.base;

/**
 * This interface should be implemented by the
 * {@link net.sf.mmm.search.parser.api.ContentParserService parser-service} and
 * can be implemented by
 * {@link net.sf.mmm.search.parser.api.ContentParser parsers}. It allows to
 * {@link #setMaximumBufferSize(int) configure} the maximum size used for
 * buffers when parsing the content.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface LimitBufferSize {

  /** The default maximum is 100 KB */
  int DEFAULT_MAX_BUFFER_SIZE = 100 * 1024;

  /**
   * This method can be called to configure the maximum size used for buffers
   * when parsing a content. Please note that <code>char</code>s will use at
   * least two bytes so e.g. {@link StringBuffer}s shall be
   * {@link StringBuffer#StringBuffer(int) allocated} with a
   * {@link StringBuffer#capacity() capacity} at most <code>maxBytes/2</code>.
   * Anyways the amount of memory allocated will typically be at least twice a
   * much as <code>maxBytes</code>. E.g. a {@link StringBuffer} is created
   * with a {@link StringBuffer#capacity() capacity} of <code>maxBytes/2</code>
   * then {@link StringBuffer#toString()} may allocate another array of about
   * that size. This is a legal behaviour of the
   * {@link net.sf.mmm.search.parser.api.ContentParser parser}. So if you want
   * to limit your heap-size use a reasonable lower value for
   * <code>maxBytes</code>. Please do NOT use values lower than 1024 (You do
   * NOT want to parse Word-Documents with J2ME on your mobile phone, do you?).<br>
   * This method is intended to be called during initialization. Implementations
   * of this method may NOT be thread-safe.
   * 
   * @see #DEFAULT_MAX_BUFFER_SIZE
   * 
   * @param maxBytes
   *        is the maximum number of bytes to allocate.
   */
  void setMaximumBufferSize(int maxBytes);

  /**
   * This method gets the maximum buffer size.
   * 
   * @see #setMaximumBufferSize(int)
   * 
   * @return the maximum buffer size.
   */
  int getMaximumBufferSize();

}
