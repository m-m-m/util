/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

import java.util.Arrays;
import java.util.List;

/**
 * This is the generic implementation of both {@link HttpRequestHeader} and {@link HttpResponseHeader}.
 *
 * @author hohwille
 * @since 8.4.0
 */
final class GenericHttpResponseHeader extends GenericHttpHeader implements HttpResponseHeader {

  static final List<String> HEADERS = Arrays.asList(HttpResponseHeader.HEADER_ACCEPT_RANGES, HttpResponseHeader.HEADER_AGE, HttpResponseHeader.HEADER_ALLOW,
      HttpResponseHeader.HEADER_CONTENT_DESCRIPTION, HttpResponseHeader.HEADER_CONTENT_ENCODING, HttpResponseHeader.HEADER_CONTENT_LANGUAGE,
      HttpResponseHeader.HEADER_CONTENT_LOCATION, HttpResponseHeader.HEADER_CONTENT_RANGE, HttpResponseHeader.HEADER_CONTENT_SECURITY_POLICY,
      HttpResponseHeader.HEADER_ETAG, HttpResponseHeader.HEADER_EXPIRES, HttpResponseHeader.HEADER_LAST_MODIFIED, HttpResponseHeader.HEADER_LINK,
      HttpResponseHeader.HEADER_LOCATION, HttpResponseHeader.HEADER_PROXY_AUTHENTICATE, HttpResponseHeader.HEADER_REFRESH,
      HttpResponseHeader.HEADER_RETRY_AFTER, HttpResponseHeader.HEADER_SERVER, HttpResponseHeader.HEADER_SET_COOKIE, HttpResponseHeader.HEADER_TRAILER,
      HttpResponseHeader.HEADER_VARY, HttpResponseHeader.HEADER_WWW_AUTHENTICATE, HttpResponseHeader.HEADER_X_POWERED_BY,
      HttpResponseHeader.HEADER_X_UA_COMPATIBLE, HttpResponseHeader.HEADER_X_XSS_PROTECTION);

  /**
   * The constructor.
   *
   * @param name the {@link #getName() name} of this {@link HttpHeader}.
   * @param value the {@link #getValue() value} of this {@link HttpHeader}.
   */
  GenericHttpResponseHeader(String name, String value) {
    super(name, value);
  }

  @Override
  public boolean isRequestHeader() {

    return true;
  }

  @Override
  public boolean isResponseHeader() {

    return true;
  }

  @Override
  protected AbstractHttpHeader withValue(String value) {

    return new GenericHttpResponseHeader(getName(), value);
  }

}
