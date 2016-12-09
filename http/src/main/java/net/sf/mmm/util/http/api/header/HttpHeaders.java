/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;

/**
 * This class represents the {@link HttpHeader}s of an HTTP {@link javax.servlet.http.HttpServletRequest request} or
 * {@link javax.servlet.http.HttpServletResponse response}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class HttpHeaders {

  private final Map<String, AbstractHttpHeader> headerMap;

  /**
   * The constructor.
   */
  public HttpHeaders() {
    super();
    this.headerMap = new HashMap<>();
  }

  /**
   * @param name the {@link HttpHeader#getName() name} of the requested {@link HttpHeader}. Case will be ignored.
   * @return the requested {@link HttpHeader} or {@code null} if not present.
   */
  public AbstractHttpHeader getHeader(String name) {

    if (name == null) {
      return null;
    }
    String key = name.toLowerCase(Locale.US);
    return this.headerMap.get(key);
  }

  /**
   * @param name the {@link HttpHeader#getName() name} of the requested {@link HttpHeader}. Case will be ignored.
   * @return the {@link HttpHeader#getValue() value} of the requested {@link HttpHeader} or {@code null} if no such
   *         {@link HttpHeader} exists.
   */
  public String getHeaderValue(String name) {

    AbstractHttpHeader header = getHeader(name);
    if (header == null) {
      return null;
    }
    return header.getValue();
  }

  /**
   * @return the {@link HttpHeaderContentDisposition} or {@code null} if not present.
   */
  public HttpHeaderContentDisposition getContentDisposition() {

    return (HttpHeaderContentDisposition) getHeader(HttpHeader.HEADER_CONTENT_DISPOSITION);
  }

  /**
   * @return the {@link #getHeaderValue(String) value} of the {@link HttpRequestHeader#HEADER_HOST Host header}.
   */
  public String getHost() {

    return getHeaderValue(HttpRequestHeader.HEADER_HOST);
  }

  /**
   * @param header the {@link AbstractHttpHeader} to add. If a {@link HttpHeader} already exists for the same
   *        {@link HttpHeader#getName() name}, the given {@code header} will be {@link HttpHeader#getNextValue()
   *        appended} to the existing header.
   */
  public void addHeader(AbstractHttpHeader header) {

    String key = header.getName().toLowerCase(Locale.US);
    AbstractHttpHeader existingHeader = this.headerMap.get(key);
    if (existingHeader == null) {
      this.headerMap.put(key, header);
    } else {
      existingHeader.append(header);
    }
  }

  @Override
  public String toString() {

    StringBuilder buffer = new StringBuilder(this.headerMap.size() * 20);
    toString(buffer);
    return buffer.toString();
  }

  /**
   * @see #toString()
   * @param buffer the {@link StringBuilder} where to {@link StringBuilder#append(String) append} to.
   */
  public void toString(Appendable buffer) {

    for (AbstractHttpHeader header : this.headerMap.values()) {
      header.toString(buffer, true);
    }
  }

  /**
   * This method parses and consumes the HTTP header from the given {@link BufferedReader}. After the call of this
   * method the {@link BufferedReader} will point to the body (content) of the HTTP message.
   *
   * @param reader the {@link BufferedReader} with the HTTP message to parse.
   * @return the parsed {@link HttpHeaders}.
   */
  public static HttpHeaders parse(BufferedReader reader) {

    try {
      HttpHeaders headers = new HttpHeaders();
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

}
