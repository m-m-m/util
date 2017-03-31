/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

import java.util.Arrays;
import java.util.List;

/**
 * This is the generic implementation of {@link HttpResponseHeader}.
 *
 * @author hohwille
 * @since 8.5.0
 */
final class GenericHttpRequestResponseHeader extends GenericHttpHeader implements HttpResponseHeader {

  static final List<String> HEADERS = Arrays.asList(HttpHeader.HEADER_CACHE_CONTROL, HttpHeader.HEADER_CONNECTION, HttpHeader.HEADER_CONTENT_DISPOSITION,
      HttpHeader.HEADER_CONTENT_LENGTH, HttpHeader.HEADER_CONTENT_MD5, HttpHeader.HEADER_CONTENT_TYPE, HttpHeader.HEADER_DATE,
      HttpHeader.HEADER_TRANSFER_ENCODING, HttpHeader.HEADER_PRAGMA, HttpHeader.HEADER_VIA, HttpHeader.HEADER_WARNING, HttpHeader.HEADER_X_CORRELATION_ID,
      HttpHeader.HEADER_X_REQUEST_ID, HttpHeader.HEADER_X_ROBOTS_TAG);

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name} of this {@link HttpHeader}.
   * @param value the {@link #getValue() value} of this {@link HttpHeader}.
   */
  GenericHttpRequestResponseHeader(String name, String value) {
    super(name, value);
  }

  @Override
  public boolean isRequestHeader() {

    return false;
  }

  @Override
  public boolean isResponseHeader() {

    return true;
  }

  @Override
  protected AbstractHttpHeader withValue(String value) {

    return new GenericHttpRequestResponseHeader(getName(), value);
  }

}
