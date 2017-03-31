/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;

/**
 * This is the interface for the <em>HyperText Transfer Protocol (HTTP)</em>. It acts as {@link #version(HttpVersion)
 * factory} to create instances of {@link HttpRequest} (see {@link #request(String, URI)}) and {@link HttpResponse} (see
 * {@link #response(HttpResponseStatus)}).
 *
 * @author hohwille
 * @since 8.5.0
 */
public interface Http {

  /**
   * @return the {@link HttpVersion}.
   */
  HttpVersion getVersion();

  /**
   * Creates a new {@link HttpRequest}.
   *
   * @param method the {@link HttpRequest#getMethod() method}.
   * @param uri the {@link HttpRequest#getUri() request URI}.
   * @return the new {@link HttpRequest} as specified.
   */
  HttpRequest request(String method, URI uri);

  /**
   * Parses an {@link HttpRequest} from the given {@link InputStream}. The {@link HttpVersion} will be taken from the
   * data of the {@link InputStream} (and not from {@link #getVersion()}).
   *
   * @param in the {@link InputStream} to parse.
   * @return the new {@link HttpRequest} from the given {@link InputStream}.
   */
  HttpRequest request(InputStream in);

  /**
   * @param method the {@link HttpRequest#getMethod() method}.
   * @param uri the {@link URI} as {@link String}.
   * @see #request(String, URI)
   * @return the new {@link HttpRequest} as specified.
   */
  default HttpRequest request(String method, String uri) {

    try {
      return request(null, new URI(uri));
    } catch (URISyntaxException e) {
      throw new NlsIllegalArgumentException(uri, "URI", e);
    }
  }

  /**
   * {@link #request(String, URI) Creates} a new {@link HttpRequest#METHOD_GET GET} {@link HttpRequest}.
   *
   * @param uri the {@link HttpRequest#getUri() request URI}.
   * @return the new {@link HttpRequest} as specified.
   */
  default HttpRequest GET(URI uri) {

    return request(HttpRequest.METHOD_GET, uri);
  }

  /**
   * {@link #request(String, URI) Creates} a new {@link HttpRequest#METHOD_POST POST} {@link HttpRequest}.
   *
   * @param uri the {@link HttpRequest#getUri() request URI}.
   * @return the new {@link HttpRequest} as specified.
   */
  default HttpRequest POST(URI uri) {

    return request(HttpRequest.METHOD_POST, uri);
  }

  /**
   * {@link #request(String, URI) Creates} a new {@link HttpRequest#METHOD_PUT PUT} {@link HttpRequest}.
   *
   * @param uri the {@link HttpRequest#getUri() request URI}.
   * @return the new {@link HttpRequest} as specified.
   */
  default HttpRequest PUT(URI uri) {

    return request(HttpRequest.METHOD_PUT, uri);
  }

  /**
   * {@link #request(String, URI) Creates} a new {@link HttpRequest#METHOD_TRACE TRACE} {@link HttpRequest}.
   *
   * @param uri the {@link HttpRequest#getUri() request URI}.
   * @return the new {@link HttpRequest} as specified.
   */
  default HttpRequest TRACE(URI uri) {

    return request(HttpRequest.METHOD_TRACE, uri);
  }

  /**
   * {@link #request(String, URI) Creates} a new {@link HttpRequest#METHOD_DELETE DELETE} {@link HttpRequest}.
   *
   * @param uri the {@link HttpRequest#getUri() request URI}.
   * @return the new {@link HttpRequest} as specified.
   */
  default HttpRequest DELETE(URI uri) {

    return request(HttpRequest.METHOD_DELETE, uri);
  }

  /**
   * {@link #request(String, URI) Creates} a new {@link HttpRequest#METHOD_HEAD HEAD} {@link HttpRequest}.
   *
   * @param uri the {@link HttpRequest#getUri() request URI}.
   * @return the new {@link HttpRequest} as specified.
   */
  default HttpRequest HEAD(URI uri) {

    return request(HttpRequest.METHOD_HEAD, uri);
  }

  /**
   * @param status the {@link HttpResponseStatus}.
   * @return the new {@link HttpResponse} with the given {@link HttpResponse#getStatus() status}.
   */
  HttpResponse response(HttpResponseStatus status);

  /**
   * Parses an {@link HttpResponse} from the given {@link InputStream}. The {@link HttpVersion} will be taken from the
   * data of the {@link InputStream} (and not from {@link #getVersion()}).
   *
   * @param in the {@link InputStream} to parse.
   * @return the new {@link HttpResponse} from the given {@link InputStream}.
   */
  HttpResponse response(InputStream in);

  /**
   * @param statusCode the {@link HttpResponseStatus#getCode() HTTP status code}.
   * @return the new {@link HttpResponse} with the given {@link HttpResponse#getStatus() status}.
   */
  default HttpResponse response(int statusCode) {

    return response(HttpResponseStatus.of(statusCode));
  }

  /**
   * @param version the {@link HttpVersion}.
   * @return the {@link Http} instance that can be used as builder for {@link HttpRequest}s and {@link HttpResponse}s.
   */
  static Http version(HttpVersion version) {

    return HttpFactory.of(version);
  }

  /**
   * Factory method for {@link HttpVersion#HTTP_1_1}.
   *
   * @return the {@link Http} instance that can be used as builder for {@link HttpRequest}s and {@link HttpResponse}s.
   */
  static Http version1_1() {

    return version(HttpVersion.HTTP_1_1);
  }

  /**
   * Factory method for {@link HttpVersion#HTTP_2_0}.
   *
   * @return the {@link Http} instance that can be used as builder for {@link HttpRequest}s and {@link HttpResponse}s.
   */
  static Http version2_0() {

    return version(HttpVersion.HTTP_2_0);
  }

}
