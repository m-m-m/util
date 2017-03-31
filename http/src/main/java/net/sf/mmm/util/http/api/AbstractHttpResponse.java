/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api;

import java.io.IOException;
import java.util.Objects;

import net.sf.mmm.util.http.api.header.AbstractHttpHeaders;

/**
 * This class represents an HTTP response message.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 8.5.0
 */
public abstract class AbstractHttpResponse extends AbstractHttpMessage implements HttpResponse {

  private final HttpResponseStatus status;

  /**
   * The constructor.
   *
   * @param version - see {@link #getVersion()}.
   * @param status - see {@link #getStatus()}.
   */
  public AbstractHttpResponse(HttpVersion version, HttpResponseStatus status) {

    this(version, status, null);
  }

  /**
   * The constructor.
   *
   * @param version - see {@link #getVersion()}.
   * @param status - see {@link #getStatus()}.
   * @param headers - see {@link #getHeaders()}.
   */
  public AbstractHttpResponse(HttpVersion version, HttpResponseStatus status, AbstractHttpHeaders headers) {

    super(version, headers);
    Objects.requireNonNull(status, "status");
    this.status = status;
  }

  @Override
  public HttpResponseStatus getStatus() {

    return this.status;
  }

  @Override
  protected void writeFirstLine(Appendable appendable) throws IOException {

    getVersion().write(appendable);
    appendable.append(' ');
    appendable.append(Integer.toString(this.status.getCode()));
    appendable.append(' ');
    appendable.append(this.status.getReasonPhrase());
    appendable.append(CRLF);
  }

}
