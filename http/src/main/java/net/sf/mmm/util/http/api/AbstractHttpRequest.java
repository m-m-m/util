/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;

import net.sf.mmm.util.http.api.header.AbstractHttpHeaders;

/**
 * This is the abstract base implementation of {@link HttpRequest}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 8.4.0
 */
public abstract class AbstractHttpRequest extends AbstractHttpMessage implements HttpRequest {

  private final URI uri;

  private final String method;

  /**
   * The constructor.
   *
   * @param version - see {@link HttpMessage#getVersion()}.
   * @param method - see {@link HttpRequest#getMethod()}.
   * @param uri - see {@link HttpRequest#getUri()}.
   */
  public AbstractHttpRequest(HttpVersion version, String method, URI uri) {
    this(version, method, uri, null);
  }

  /**
   * The constructor.
   *
   * @param version - see {@link HttpMessage#getVersion()}.
   * @param method - see {@link HttpRequest#getMethod()}.
   * @param uri - see {@link HttpRequest#getUri()}.
   * @param headers - see {@link HttpMessage#getHeaders()}.
   */
  public AbstractHttpRequest(HttpVersion version, String method, URI uri, AbstractHttpHeaders headers) {

    super(version, headers);
    Objects.requireNonNull(method, "method");
    Objects.requireNonNull(uri, "URI");
    this.method = method;
    this.uri = uri;
  }

  @Override
  public String getMethod() {

    return this.method;
  }

  @Override
  public URI getUri() {

    return this.uri;
  }

  @Override
  protected void writeFirstLine(Appendable appendable) throws IOException {

    appendable.append(this.method);
    appendable.append(' ');
    appendable.append(this.uri.toString());
    appendable.append(' ');
    getVersion().write(appendable);
    appendable.append(CRLF);
  }

}
