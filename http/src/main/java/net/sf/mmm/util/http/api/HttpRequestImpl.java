/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api;

import java.net.URI;

import net.sf.mmm.util.http.api.header.AbstractHttpHeaders;

/**
 * This class represents an HTTP request message.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
class HttpRequestImpl extends AbstractHttpRequest {

  /**
   * The constructor.
   *
   * @param version - see {@link HttpMessage#getVersion()}.
   * @param method - see {@link HttpRequest#getMethod()}.
   * @param uri - see {@link HttpRequest#getUri()}.
   */
  public HttpRequestImpl(HttpVersion version, String method, URI uri) {

    super(version, method, uri);
  }

  /**
   * The constructor.
   *
   * @param version - see {@link HttpMessage#getVersion()}.
   * @param method - see {@link HttpRequest#getMethod()}.
   * @param uri - see {@link HttpRequest#getUri()}.
   * @param headers - see {@link HttpMessage#getHeaders()}.
   */
  public HttpRequestImpl(HttpVersion version, String method, URI uri, AbstractHttpHeaders headers) {

    super(version, method, uri, headers);
  }

}
