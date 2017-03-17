/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api;

import java.net.URI;

/**
 * This is the interface for an {@link HttpRequest}. It is the {@link HttpMessage} send from a client to a server.
 * Depending on the {@link #getMethod() method} it is used to request data from the server or to upload data to the
 * server. For further details consult <a href="https://tools.ietf.org/html/rfc1945#section-5">RFC 1945, section 5</a>.
 *
 * @see Http#request(String, URI)
 * @see HttpRequestBuilder
 * @see javax.servlet.http.HttpServletRequest
 *
 * @author hohwille
 * @since 8.4.0
 */
public interface HttpRequest extends HttpMessage {

  /** The GET {@link #getMethod() method}. */
  String METHOD_GET = "GET";

  /** The POST {@link #getMethod() method}. */
  String METHOD_HEAD = "HEAD";

  /** The POST {@link #getMethod() method}. */
  String METHOD_POST = "POST";

  /** The PUT {@link #getMethod() method}. */
  String METHOD_PUT = "PUT";

  /** The DELETE {@link #getMethod() method}. */
  String METHOD_DELETE = "DELETE";

  /** The OPTIONS {@link #getMethod() method}. */
  String METHOD_OPTIONS = "OPTIONS";

  /**
   * The <a href="http://www.webdav.org/specs/rfc2518.html#METHOD_PROPFIND">PROPFIND</a> {@link #getMethod() method}
   * used to retrieve properties in WebDAV.
   */
  String METHOD_PROPFIND = "PROPFIND";

  /**
   * The <a href="http://www.webdav.org/specs/rfc2518.html#METHOD_PROPPATCH">PROPPATCH</a> {@link #getMethod() method}
   * used to modify properties in WebDAV.
   */
  String METHOD_PROPPATCH = "PROPPATCH";

  /**
   * The <a href="http://www.webdav.org/specs/rfc2518.html#METHOD_MKCOL">MKCOL</a> {@link #getMethod() method} used to
   * create a collection (directory) in WebDAV.
   */
  String METHOD_MKCOL = "MKCOL";

  /**
   * The <a href="http://www.webdav.org/specs/rfc2518.html#METHOD_COPY">COPY</a> {@link #getMethod() method} used to
   * copy a resource in WebDAV.
   */
  String METHOD_COPY = "COPY";

  /** The TRACE {@link #getMethod() method}. */
  String METHOD_TRACE = "TRACE";

  /** The CONNECT {@link #getMethod() method}. */
  String METHOD_CONNECT = "CONNECT";

  /**
   * @return the HTTP method of this {@link HttpRequest}. The default is {@link #METHOD_GET GET}. Other typical methods
   *         are {@link #METHOD_POST POST}, {@link #METHOD_PUT PUT}, and {@link #METHOD_HEAD HEAD}.
   */
  String getMethod();

  /**
   * @return the Request-URI. This is the URI identifying the resource upon which to apply the request (e.g. "/").
   */
  URI getUri();

}
