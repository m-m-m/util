/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api;

import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory used to create instances of {@link HttpRequest} and {@link HttpResponse}.
 *
 * @author hohwille
 * @since 8.5.0
 */
class HttpFactory implements Http {

  private static final Map<String, HttpFactory> FACTORY_MAP = new HashMap<>();

  static final HttpFactory HTTP_1_0 = new HttpFactory(HttpVersion.HTTP_1_0, true);

  static final HttpFactory HTTP_1_1 = new HttpFactory(HttpVersion.HTTP_1_1, true);

  static final HttpFactory HTTP_2_0 = new HttpFactory(HttpVersion.HTTP_2_0, true);

  private final HttpVersion version;

  /**
   * The constructor.
   *
   * @param version - see {@link #getVersion()}.
   * @param register - {@code true} to register to internal {@link Map}, {@code false} otherwise.
   */
  private HttpFactory(HttpVersion version, boolean register) {
    super();
    this.version = version;
  }

  @Override
  public HttpVersion getVersion() {

    return this.version;
  }

  @Override
  public HttpRequest request(String method, URI uri) {

    return new HttpRequestImpl(this.version, method, uri);
  }

  @Override
  public HttpRequest request(InputStream in) {

    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public HttpResponse response(HttpResponseStatus status) {

    return new HttpResponseImpl(this.version, status);
  }

  @Override
  public HttpResponse response(InputStream in) {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @param version the {@link HttpVersion}.
   * @return the requested {@link Http} instance.
   */
  public static HttpFactory of(HttpVersion version) {

    HttpFactory factory = FACTORY_MAP.get(version);
    if (factory == null) {
      factory = new HttpFactory(version, false);
    }
    return factory;
  }

}
