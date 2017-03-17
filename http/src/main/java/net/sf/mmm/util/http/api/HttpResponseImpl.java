/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api;

import net.sf.mmm.util.http.api.header.AbstractHttpHeaders;

/**
 * This class represents an HTTP response message.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
class HttpResponseImpl extends AbstractHttpResponse {

  /**
   * The constructor.
   *
   * @param version - see {@link HttpMessage#getVersion()}.
   * @param status - see {@link HttpResponse#getStatus()}.
   */
  public HttpResponseImpl(HttpVersion version, HttpResponseStatus status) {

    super(version, status);
  }

  /**
   * The constructor.
   *
   * @param version - see {@link HttpMessage#getVersion()}.
   * @param status - see {@link HttpResponse#getStatus()}.
   * @param headers - see {@link HttpResponse#getHeaders()}.
   */
  public HttpResponseImpl(HttpVersion version, HttpResponseStatus status, AbstractHttpHeaders headers) {

    super(version, status, headers);
  }

}
