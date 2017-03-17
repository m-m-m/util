/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;

/**
 * This class represents the {@link HttpHeader}s of an HTTP {@link javax.servlet.http.HttpServletRequest request} or
 * {@link javax.servlet.http.HttpServletResponse response}.
 *
 * @author hohwille
 * @since 8.4.0
 */
class HttpHeadersImpl extends AbstractHttpHeaders {

  /**
   * The constructor.
   */
  protected HttpHeadersImpl() {
    super();
  }

  /**
   * This method parses and consumes the HTTP header from the given {@link BufferedReader}. After the call of this
   * method the {@link BufferedReader} will point to the body (content) of the HTTP message.
   *
   * @param reader the {@link BufferedReader} with the HTTP message to parse.
   * @return the parsed {@link HttpHeaders}.
   */
  public static HttpHeadersImpl parse(BufferedReader reader) {

    try {
      HttpHeadersImpl headers = new HttpHeadersImpl();
      String line = reader.readLine();
      while ((line != null) && !line.isEmpty()) {
        AbstractHttpHeader header = AbstractHttpHeader.ofHeader(line);
        headers.addHeader(header);
        line = reader.readLine();
      }
      return headers;
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.READ);
    }
  }

  /**
   * @param httpHeaders the single valued {@link Map} of the {@link HttpHeader}s given as {@link String}s.
   * @return the {@link AbstractHttpHeaders} instance.
   */
  public static HttpHeadersImpl ofSingle(Map<String, String> httpHeaders) {

    HttpHeadersImpl headers = new HttpHeadersImpl();
    for (Entry<String, String> entry : httpHeaders.entrySet()) {
      AbstractHttpHeader header = AbstractHttpHeader.of(entry.getKey(), entry.getValue());
      headers.addHeader(header);
    }
    return headers;
  }

  /**
   * @param httpHeaders the {@link Map} of the {@link HttpHeader}s with their {@link HttpHeader#getValues() values}
   *        given as {@link List} of {@link String}s.
   * @return the {@link AbstractHttpHeaders} instance.
   */
  public static AbstractHttpHeaders ofMulti(Map<String, List<String>> httpHeaders) {

    HttpHeadersImpl headers = new HttpHeadersImpl();
    for (Entry<String, List<String>> entry : httpHeaders.entrySet()) {
      AbstractHttpHeader header = AbstractHttpHeader.of(entry.getKey(), entry.getValue());
      headers.addHeader(header);
    }
    return headers;
  }

}
