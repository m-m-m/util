/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

/**
 * {@link HttpHeader} of an {@link javax.servlet.http.HttpServletResponse HTTP-response}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public interface HttpResponseHeader extends HttpHeader {

  /** The {@link #getName() name} of the <em>Accept-Ranges</em> header. */
  String HEADER_ACCEPT_RANGES = "Accept-Ranges";

  /** The {@link #getName() name} of the <em>Age</em> header. */
  String HEADER_AGE = "Age";

  /** The {@link #getName() name} of the <em>Allow</em> header. */
  String HEADER_ALLOW = "Allow";

  /** The {@link #getName() name} of the <em>Content-Description</em> header. */
  String HEADER_CONTENT_DESCRIPTION = "Content-Description";

  /** The {@link #getName() name} of the <em>Content-Encoding</em> header. */
  String HEADER_CONTENT_ENCODING = "Content-Encoding";

  /** The {@link #getName() name} of the <em>Content-Language</em> header. */
  String HEADER_CONTENT_LANGUAGE = "Content-Language";

  /** The {@link #getName() name} of the <em>Content-Location</em> header. */
  String HEADER_CONTENT_LOCATION = "Content-Location";

  /** The {@link #getName() name} of the <em>Content-Range</em> header. */
  String HEADER_CONTENT_RANGE = "Content-Range";

  /** The {@link #getName() name} of the <em>Content-Security-Policy</em> header. */
  String HEADER_CONTENT_SECURITY_POLICY = "Content-Security-Policy";

  /** The {@link #getName() name} of the <em>ETag</em> header. */
  String HEADER_ETAG = "ETag";

  /** The {@link #getName() name} of the <em>Expires</em> header. */
  String HEADER_EXPIRES = "Expires";

  /** The {@link #getName() name} of the <em>Last-Modified</em> header. */
  String HEADER_LAST_MODIFIED = "Last-Modified";

  /** The {@link #getName() name} of the <em>Link</em> header. */
  String HEADER_LINK = "Link";

  /** The {@link #getName() name} of the <em>Location</em> header. */
  String HEADER_LOCATION = "Location";

  /** The {@link #getName() name} of the <em>Proxy-Authenticate</em> header. */
  String HEADER_PROXY_AUTHENTICATE = "Proxy-Authenticate";

  /** The {@link #getName() name} of the <em>Refresh</em> header. */
  String HEADER_REFRESH = "Refresh";

  /** The {@link #getName() name} of the <em>Retry-After</em> header. */
  String HEADER_RETRY_AFTER = "Retry-After";

  /** The {@link #getName() name} of the <em>Server</em> header. */
  String HEADER_SERVER = "Server";

  /** The {@link #getName() name} of the <em>Set-Cookie</em> header. */
  String HEADER_SET_COOKIE = "Set-Cookie";

  /** The {@link #getName() name} of the <em>Trailer</em> header. */
  String HEADER_TRAILER = "Trailer";

  /** The {@link #getName() name} of the <em>Vary</em> header. */
  String HEADER_VARY = "Vary";

  /** The {@link #getName() name} of the <em>WWW-Authenticate</em> header. */
  String HEADER_WWW_AUTHENTICATE = "WWW-Authenticate";

  /** The {@link #getName() name} of the <em>X-Powered-By</em> header. */
  String HEADER_X_POWERED_BY = "X-Powered-By";

  /** The {@link #getName() name} of the <em>X-UA-Compatible</em> header. */
  String HEADER_X_UA_COMPATIBLE = "X-UA-Compatible";

  /** The {@link #getName() name} of the <em>X-XSS-Protection</em> header. */
  String HEADER_X_XSS_PROTECTION = "X-XSS-Protection";

  @Override
  default boolean isResponseHeader() {

    return true;
  }

}
