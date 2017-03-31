/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

import java.util.Collections;
import java.util.List;

import net.sf.mmm.util.version.base.GenericNameVersionComment;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 8.5.0
 */
public class HttpHeaderServer extends AbstractHttpHeader implements HttpResponseHeader {

  /** An empty instance of {@link HttpHeaderUserAgent} that acts as factory. */
  public static final HttpHeaderServer FACTORY = new HttpHeaderServer();

  /** The {@link #getName() name} of this {@link HttpHeader}. */
  public static final String HEADER = HttpResponseHeader.HEADER_SERVER;

  private List<GenericNameVersionComment> segments;

  /**
   * The constructor.
   */
  public HttpHeaderServer() {
    super();
  }

  /**
   * The constructor.
   *
   * @param value the {@link #getValue() value}.
   */
  public HttpHeaderServer(String value) {
    super(value);
  }

  @Override
  public String getName() {

    return HEADER;
  }

  @Override
  public boolean isRequestHeader() {

    return false;
  }

  @Override
  public boolean isResponseHeader() {

    return true;
  }

  /**
   * @return the {@link GenericNameVersionComment} segments.
   */
  public List<GenericNameVersionComment> getSegments() {

    if (this.segments == null) {
      this.segments = Collections.unmodifiableList(GenericNameVersionComment.ofAll(getValue()));
    }
    return this.segments;
  }

  @Override
  protected HttpHeaderServer withValue(String newValue) {

    return ofValue(newValue);
  }

  /**
   * @param headerValue the header {@link #getValues() value}.
   * @return the parsed {@link HttpHeaderUserAgent} or {@code null} if the given value is {@code null} or
   *         {@link String#isEmpty() empty}.
   */
  public static HttpHeaderServer ofValue(String headerValue) {

    String value = trim(headerValue);
    if (value == null) {
      return null;
    }
    return new HttpHeaderServer(value);
  }

  /**
   * @param headers the {@link HttpHeaders} to get this header from. May be {@code null}.
   * @return the {@link HttpHeaderServer} form the given {@link HttpHeaders} or {@code null} if not present.
   */
  public static HttpHeaderServer get(HttpHeaders headers) {

    if (headers == null) {
      return null;
    }
    HttpHeader header = headers.getHeader(HEADER);
    return (HttpHeaderServer) header;
  }
}
