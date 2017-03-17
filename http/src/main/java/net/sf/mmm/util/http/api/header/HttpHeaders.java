/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

import net.sf.mmm.util.lang.api.StringWritable;

/**
 * This is the interface for a block of (multiple) {@link HttpHeader}s. In case you want to get a specific typed header
 * look for a static {@code get} method in the {@link HttpHeader} implementation such as
 * {@link HttpHeaderContentDisposition#get(HttpHeaders)}.
 *
 * @see AbstractHttpHeaders
 * @see net.sf.mmm.util.http.api.HttpMessage
 * @see net.sf.mmm.util.http.api.HttpMessage#getHeaders()
 *
 * @author hohwille
 * @since 8.4.0
 */
public interface HttpHeaders extends StringWritable {

  /**
   * @param name the {@link HttpHeader#getName() name} of the requested {@link HttpHeader}. Case will be ignored.
   * @return the requested {@link HttpHeader} or {@code null} if not present.
   */
  HttpHeader getHeader(String name);

  /**
   * @param name the {@link HttpHeader#getName() name} of the requested {@link HttpHeader}. Case will be ignored.
   * @return the {@link HttpHeader#getValue() value} of the requested {@link HttpHeader} or {@code null} if no such
   *         {@link HttpHeader} exists.
   */
  default String getHeaderValue(String name) {

    HttpHeader header = getHeader(name);
    if (header == null) {
      return null;
    }
    return header.getValue();
  }

  /**
   * @return the {@link #getHeaderValue(String) value} of the {@link HttpRequestHeader#HEADER_HOST Host header}.
   */
  default String getHost() {

    return getHeaderValue(HttpRequestHeader.HEADER_HOST);
  }

}
