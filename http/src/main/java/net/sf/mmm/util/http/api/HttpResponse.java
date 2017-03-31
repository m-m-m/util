/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api;

/**
 * This is the interface for an {@link HttpResponse}. It is the {@link HttpMessage} send as answer to a
 * {@link HttpRequest} back from the server to the client. For further details consult
 * <a href="https://tools.ietf.org/html/rfc1945#section-6">RFC 1945, section 6</a>.
 *
 * @see javax.servlet.http.HttpServletResponse
 *
 * @author hohwille
 * @since 8.5.0
 */
public interface HttpResponse extends HttpMessage {

  /**
   * @return the {@link HttpResponseStatus} according to <a href="https://tools.ietf.org/html/rfc7231#section-6">RFC
   *         7231, section 6</a>.
   */
  HttpResponseStatus getStatus();

}
