/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

/**
 * {@link HttpHeader} of an {@link javax.servlet.http.HttpServletRequest HTTP-request}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public interface HttpRequestHeader extends HttpHeader {

  /** The {@link #getName() name} of the <em>Accept</em> header. */
  String HEADER_ACCEPT = "Accept";

  /** The {@link #getName() name} of the <em>Accept-Charset</em> header. */
  String HEADER_ACCEPT_CHARSET = "Accept-Charset";

  /** The {@link #getName() name} of the <em>Accept-Encoding</em> header. */
  String HEADER_ACCEPT_ENCODING = "Accept-Encoding";

  /** The {@link #getName() name} of the <em>Accept-Language</em> header. */
  String HEADER_ACCEPT_LANGUAGE = "Accept-Language";

  /** The {@link #getName() name} of the <em>Authorization</em> header. */
  String HEADER_AUTHORIZATION = "Authorization";

  /** The {@link #getName() name} of the <em>Cookie</em> header. */
  String HEADER_COOKIE = "Cookie";

  /**
   * The {@link #getName() name} of the
   * <a href="http://www.webdav.org/specs/rfc2518.html#rfc.section.9.3">Destination</a> header.
   */
  String HEADER_DESTINATION = "Destination";

  /**
   * The {@link #getName() name} of the <a href="http://www.webdav.org/specs/rfc2518.html#rfc.section.9.6">Overwrite</a>
   * header.
   */
  String HEADER_OVERWRITE = "Overwrite";

  /**
   * The {@link #getName() name} of the
   * <a href="http://www.webdav.org/specs/rfc2518.html#rfc.section.9.5">Lock-Token</a> header.
   */
  String HEADER_LOCK_TOKEN = "Lock-Token";

  /**
   * The {@link #getName() name} of the <a href="https://en.wikipedia.org/wiki/Do_Not_Track"><em>DNT</em></a> (Do Not
   * Track) header.
   */
  String HEADER_DNT = "DNT";

  /** The {@link #getName() name} of the <em>Expect</em> header. */
  String HEADER_EXPECT = "Expect";

  /** The {@link #getName() name} of the <em>Host</em> header. */
  String HEADER_HOST = "Host";

  /** The {@link #getName() name} of the <em>If-Match</em> header. */
  String HEADER_IF_MATCH = "If-Match";

  /** The {@link #getName() name} of the <em>If-Modified-Since</em> header. */
  String HEADER_IF_MODIFIED_SINCE = "If-Modified-Since";

  /** The {@link #getName() name} of the <em>If-None-Match</em> header. */
  String HEADER_IF_NONE_MATCH = "If-None-Match";

  /** The {@link #getName() name} of the <em>If-Range</em> header. */
  String HEADER_IF_RANGE = "If-Range";

  /** The {@link #getName() name} of the <em>If-Unmodified-Since</em> header. */
  String HEADER_IF_UNMODIFIED_SINCE = "If-Unmodified-Since";

  /** The {@link #getName() name} of the <em>Max-Forwards</em> header. */
  String HEADER_MAX_FORWARDS = "Max-Forwards";

  /** The {@link #getName() name} of the <em>Proxy-Authorization</em> header. */
  String HEADER_PROXY_AUTHORIZATION = "Proxy-Authorization";

  /** The {@link #getName() name} of the <em>Range</em> header. */
  String HEADER_RANGE = "Range";

  /** The {@link #getName() name} of the <em>Referer</em> header. */
  String HEADER_REFERER = "Referer";

  /** The {@link #getName() name} of the <em>TE</em> header (see {@link #HEADER_TRANSFER_ENCODING}). */
  String HEADER_TE = "TE";

  /** The {@link #getName() name} of the <em>Upgrade</em> header. */
  String HEADER_UPGRADE = "Upgrade";

  /** The {@link #getName() name} of the <em>User-Agent</em> header. */
  String HEADER_USER_AGENT = "User-Agent";

  /** The {@link #getName() name} of the <em>X-Requested-With</em> header. */
  String HEADER_X_REQUESTED_WITH = "X-Requested-With";

  /**
   * The {@link #getName() name} of the
   * <a href="https://en.wikipedia.org/wiki/X-Forwarded-For"><em>X-Forwarded-For</em></a> header.
   */
  String HEADER_X_FORWARDED_FOR = "X-Forwarded-For";

  /** The {@link #getName() name} of the <em>X-Forwarded-Host</em> header. */
  String HEADER_X_FORWARDED_HOST = "X-Forwarded-Host";

  /** The {@link #getName() name} of the <em>X-Forwarded-Port</em> header. */
  String HEADER_X_FORWARDED_PORT = "X-Forwarded-Port";

  /** The {@link #getName() name} of the <em>X-Forwarded-Proto</em> header. */
  String HEADER_X_FORWARDED_PROTO = "X-Forwarded-Proto";

  /** The {@link #getName() name} of the <em>X-Csrf-Token</em> header. */
  String HEADER_X_CSRF_TOKEN = "X-Csrf-Token";

  @Override
  default boolean isRequestHeader() {

    return true;
  }

}
