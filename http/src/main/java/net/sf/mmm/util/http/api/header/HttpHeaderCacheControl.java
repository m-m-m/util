/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

import java.util.Map;

/**
 * This class represents the HTTP {@link #HEADER_PRAGMA Pragma} header according to
 * <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9">RFC 2616 (section 14.9)</a>. It is used to
 * control the caching of data.
 *
 * @see HttpHeaderPragma
 *
 * @author hohwille
 * @since 8.5.0
 */
public class HttpHeaderCacheControl extends AbstractParameterizedHttpHeader implements HttpRequestHeader, HttpResponseHeader {

  /** An empty instance of {@link HttpHeaderUserAgent} that acts as factory. */
  public static final HttpHeaderCacheControl FACTORY = new HttpHeaderCacheControl();

  /** The {@link #getName() name} of this {@link HttpHeader}. */
  public static final String HEADER = HttpHeader.HEADER_CACHE_CONTROL;

  /**
   * The {@link #getParameterAsBooleanFlag(String) parameter flag}
   * <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.1">no-cache</a>.
   */
  public static final String PARAMETER_NO_CACHE = "no-cache";

  /**
   * The {@link #getParameterAsBooleanFlag(String) parameter flag}
   * <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.2">no-store</a>.
   */
  public static final String PARAMETER_NO_STORE = "no-store";

  /**
   * The {@link #getParameterAsLong(String) parameter}
   * <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.3">max-age</a> (age in seconds).
   */
  public static final String PARAMETER_MAX_AGE = "max-age";

  /**
   * The {@link HttpRequestHeader request} {@link #getParameterAsLong(String) parameter}
   * <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.3">max-stale</a> (age in seconds).
   */
  public static final String PARAMETER_MAX_STALE = "max-stale";

  /**
   * The {@link HttpRequestHeader request} {@link #getParameterAsLong(String) parameter}
   * <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.3">min-fresh</a> (age in seconds).
   */
  public static final String PARAMETER_MIN_FRESH = "min-fresh";

  /**
   * The {@link #getParameterAsBooleanFlag(String) parameter flag}
   * <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.5">no-transform</a>.
   */
  public static final String PARAMETER_NO_TRANSFORM = "no-transform";

  /**
   * The {@link HttpRequestHeader request} {@link #getParameterAsBooleanFlag(String) parameter flag}
   * <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.4">only-if-cached</a>.
   */
  public static final String PARAMETER_ONLY_IF_CACHED = "only-if-cached";

  /**
   * The {@link HttpResponseHeader response} {@link #getParameterAsBooleanFlag(String) parameter flag}
   * <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.1">public</a>.
   */
  public static final String PARAMETER_PUBLIC = "public";

  /**
   * The {@link HttpResponseHeader response} {@link #getParameter(String) parameter}
   * <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.1">private</a>.
   */
  public static final String PARAMETER_PRIVATE = "private";

  /**
   * The {@link HttpResponseHeader response} {@link #getParameterAsBooleanFlag(String) parameter flag}
   * <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.4">must-revalidate</a>.
   */
  public static final String PARAMETER_MUST_REVALIDATE = "must-revalidate";

  /**
   * The {@link HttpResponseHeader response} {@link #getParameterAsBooleanFlag(String) parameter flag}
   * <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.4">proxy-revalidate</a>.
   */
  public static final String PARAMETER_PROXY_REVALIDATE = "proxy-revalidate";

  /**
   * The {@link #getParameterAsLong(String) parameter}
   * <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.9.3">s-maxage</a> (age in seconds).
   */
  public static final String PARAMETER_S_MAX_AGE = "s-maxage";

  /**
   * The constructor.
   */
  private HttpHeaderCacheControl() {
    super();
  }

  /**
   * The constructor.
   */
  private HttpHeaderCacheControl(Map<String, Object> parameters) {
    super(parameters);
  }

  @Override
  public String getName() {

    return HEADER;
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
   * @return {@code true} if {@link #PARAMETER_NO_CACHE} is present, {@code false} otherwise.
   */
  public boolean isNoCache() {

    return getParameterAsBooleanFlag(PARAMETER_NO_CACHE);
  }

  /**
   * @return {@code true} if {@link #PARAMETER_NO_STORE} is present, {@code false} otherwise.
   */
  public boolean isNoStore() {

    return getParameterAsBooleanFlag(PARAMETER_NO_STORE);
  }

  @Override
  protected AbstractHttpHeader withValue(String value) {

    return ofValue(value);
  }

  /**
   * @param headerValue the header {@link #getValues() value}.
   * @return the parsed {@link HttpHeaderCacheControl}.
   */
  public static HttpHeaderCacheControl ofValue(String headerValue) {

    String value = trim(headerValue);
    if (value == null) {
      return null;
    }
    Map<String, Object> parameters = FACTORY.parseParameters(value);
    return new HttpHeaderCacheControl(parameters);
  }

  /**
   * @param headers the {@link HttpHeader} to get this header from. May be {@code null}.
   * @return the {@link HttpHeaderCacheControl} form the given {@link HttpHeaders} or {@code null} if not present.
   */
  public static HttpHeaderCacheControl get(HttpHeaders headers) {

    if (headers == null) {
      return null;
    }
    HttpHeader header = headers.getHeader(HEADER);
    return (HttpHeaderCacheControl) header;
  }

}
