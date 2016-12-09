/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

import java.util.Map;

import net.sf.mmm.util.lang.api.GenericBean;

/**
 * This class represents the HTTP {@link #HEADER_PRAGMA Pragma} header according to
 * <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.32">RFC 2616 (section 14.32)</a>. It is used
 * to control the caching of data.
 *
 * @see HttpHeaderCacheControl
 *
 * @author hohwille
 * @since 8.4.0
 */
public class HttpHeaderPragma extends AbstractParameterizedHttpHeader implements HttpRequestHeader, HttpResponseHeader {

  /**
   * The value to disable caching (if present in a request message, an application SHOULD forward the request toward the
   * origin server even if it has a cached copy of what is being requested).
   */
  public static final String VALUE_NO_CACHE = "no-cache";

  private static final HttpHeaderPragma NO_CACHE = new HttpHeaderPragma();

  private final boolean noCache;

  /**
   * The constructor.
   */
  private HttpHeaderPragma() {
    super();
    this.noCache = true;
  }

  /**
   * The constructor.
   */
  private HttpHeaderPragma(boolean noCache, Map<String, Object> parameters) {
    super(parameters);
    this.noCache = noCache;
  }

  @Override
  public String getName() {

    return HEADER_PRAGMA;
  }

  @Override
  public boolean isRequestHeader() {

    return true;
  }

  @Override
  public boolean isResponseHeader() {

    return true;
  }

  /**
   * @return {@code true} if {@link #VALUE_NO_CACHE} is present, {@code false} otherwise.
   */
  public boolean isNoCache() {

    return this.noCache;
  }

  @Override
  public String getValue() {

    StringBuilder buffer = new StringBuilder();
    if (this.noCache) {
      buffer.append(VALUE_NO_CACHE);
      if (hasParameters()) {
        buffer.append(getParameterSeparator());
        buffer.append(' ');
      }
    }
    formatParameters(buffer);
    return buffer.toString();
  }

  /**
   * @param headerValue the value of the {@link #HEADER_PRAGMA Pragma HTTP header}.
   * @return the parsed {@link HttpHeaderPragma}.
   */
  public static HttpHeaderPragma ofValue(String headerValue) {

    String value = trim(headerValue);
    if (value == null) {
      return null;
    }
    final GenericBean<Boolean> receiver = new GenericBean<>();
    Map<String, Object> parameters = NO_CACHE.parseParameters(value, x -> {
      if (VALUE_NO_CACHE.equals(x)) {
        receiver.setValue(Boolean.TRUE);
        return null;
      } else {
        return x;
      }
    });
    boolean noCache = (receiver.getValue() == Boolean.TRUE);
    if (!noCache && (parameters == null)) {
      return null;
    }
    if (noCache && (parameters == null)) {
      return NO_CACHE;
    }
    return new HttpHeaderPragma(noCache, parameters);
  }

  static class Factory extends AbstractHttpHeaderFactory {

    Factory() {
      super(HEADER_PRAGMA);
    }

    @Override
    AbstractHttpHeader create(String value) {

      return ofValue(value);
    }
  }

}
