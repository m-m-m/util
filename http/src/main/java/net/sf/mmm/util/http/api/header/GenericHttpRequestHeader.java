/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

import java.util.Arrays;
import java.util.List;

/**
 * This is the generic implementation of {@link HttpRequestHeader}.
 *
 * @author hohwille
 * @since 8.4.0
 */
final class GenericHttpRequestHeader extends GenericHttpHeader implements HttpRequestHeader {

  static final List<String> HEADERS = Arrays.asList(HttpRequestHeader.HEADER_ACCEPT, HttpRequestHeader.HEADER_ACCEPT_CHARSET,
      HttpRequestHeader.HEADER_ACCEPT_ENCODING, HttpRequestHeader.HEADER_ACCEPT_LANGUAGE, HttpRequestHeader.HEADER_AUTHORIZATION,
      HttpRequestHeader.HEADER_COOKIE, HttpRequestHeader.HEADER_DNT, HttpRequestHeader.HEADER_EXPECT, HttpRequestHeader.HEADER_HOST,
      HttpRequestHeader.HEADER_IF_MATCH, HttpRequestHeader.HEADER_IF_MODIFIED_SINCE, HttpRequestHeader.HEADER_IF_NONE_MATCH, HttpRequestHeader.HEADER_IF_RANGE,
      HttpRequestHeader.HEADER_IF_UNMODIFIED_SINCE, HttpRequestHeader.HEADER_MAX_FORWARDS, HttpRequestHeader.HEADER_PROXY_AUTHORIZATION,
      HttpRequestHeader.HEADER_RANGE, HttpRequestHeader.HEADER_REFERER, HttpRequestHeader.HEADER_TE, HttpRequestHeader.HEADER_UPGRADE,
      HttpRequestHeader.HEADER_USER_AGENT, HttpRequestHeader.HEADER_X_CSRF_TOKEN, HttpRequestHeader.HEADER_X_FORWARDED_FOR,
      HttpRequestHeader.HEADER_X_FORWARDED_HOST, HttpRequestHeader.HEADER_X_FORWARDED_PORT, HttpRequestHeader.HEADER_X_FORWARDED_PROTO,
      HttpRequestHeader.HEADER_X_REQUESTED_WITH);

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name} of this {@link HttpHeader}.
   * @param value the {@link #getValue() value} of this {@link HttpHeader}.
   */
  GenericHttpRequestHeader(String name, String value) {
    super(name, value);
  }

  @Override
  public boolean isRequestHeader() {

    return true;
  }

  @Override
  public boolean isResponseHeader() {

    return false;
  }

  @Override
  protected AbstractHttpHeader withValue(String value) {

    return new GenericHttpRequestHeader(getName(), value);
  }

}
