/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import net.sf.mmm.util.exception.api.ReadOnlyException;
import net.sf.mmm.util.http.api.AbstractHttpObject;

/**
 * This is the abstract base implementation of {@link HttpHeaders}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public abstract class AbstractHttpHeaders extends AbstractHttpObject implements HttpHeaders {

  private final Map<String, AbstractHttpHeader> headerMap;

  /**
   * The constructor.
   */
  public AbstractHttpHeaders() {
    super();
    this.headerMap = new HashMap<>();
  }

  @Override
  public AbstractHttpHeader getHeader(String name) {

    if (name == null) {
      return null;
    }
    String key = name.toLowerCase(Locale.US);
    return this.headerMap.get(key);
  }

  /**
   * @param header the {@link AbstractHttpHeader} to add. If a {@link HttpHeader} already exists for the same
   *        {@link HttpHeader#getName() name}, the given {@code header} will be {@link HttpHeader#getNext() appended} to
   *        the existing header.
   * @return this object itself for fluent API calls.
   * @throws ReadOnlyException if this object is {@link #isImmutable() immutable}.
   */
  public AbstractHttpHeaders addHeader(AbstractHttpHeader header) throws ReadOnlyException {

    Objects.requireNonNull(header, "header");
    String name = header.getName();
    requireMutable(name);
    String key = name.toLowerCase(Locale.US);
    AbstractHttpHeader existingHeader = this.headerMap.get(key);
    if (existingHeader == null) {
      this.headerMap.put(key, header);
    } else {
      existingHeader.append(header);
    }
    return this;
  }

  /**
   * @param headers the {@link AbstractHttpHeader}s to {@link #addHeader(AbstractHttpHeader) add}.
   * @return this object itself for fluent API calls.
   * @throws ReadOnlyException if this object is {@link #isImmutable() immutable}.
   */
  public AbstractHttpHeaders addHeaders(AbstractHttpHeader... headers) throws ReadOnlyException {

    for (AbstractHttpHeader header : headers) {
      addHeader(header);
    }
    return this;
  }

  /**
   * @param name the {@link HttpHeader#getName() name} of the {@link HttpHeader}.
   * @param value the {@link HttpHeader#getValue() value} of the {@link HttpHeader}.
   * @return this object itself for fluent API calls.
   * @throws ReadOnlyException if this object is {@link #isImmutable() immutable}.
   */
  public AbstractHttpHeaders addHeader(String name, String value) throws ReadOnlyException {

    return addHeader(AbstractHttpHeader.of(name, value));
  }

  /**
   * <b>Attention:</b><br>
   * This method will replace a potentially existing {@link HttpHeader} of the same {@link HttpHeader#getName() name}
   * with all its {@link HttpHeader#getValues()}. In many cases you probably want to use
   * {@link #addHeader(AbstractHttpHeader)} instead.
   *
   * @param header the {@link AbstractHttpHeader} to add. If a {@link HttpHeader} already exists for the same
   *        {@link HttpHeader#getName() name}, the given {@code header} will be {@link HttpHeader#getNext() appended} to
   *        the existing header.
   * @return this object itself for fluent API calls.
   * @throws ReadOnlyException if this object is {@link #isImmutable() immutable}.
   */
  public AbstractHttpHeaders setHeader(AbstractHttpHeader header) throws ReadOnlyException {

    Objects.requireNonNull(header, "header");
    String name = header.getName();
    requireMutable(name);
    String key = name.toLowerCase(Locale.US);
    this.headerMap.put(key, header);
    return this;
  }

  /**
   * <b>Attention:</b><br>
   * This method will replace a potentially existing {@link HttpHeader} of the same {@link HttpHeader#getName() name}
   * with all its {@link HttpHeader#getValues()}. In many cases you probably want to use
   * {@link #addHeader(String, String)} instead.
   *
   * @param name the {@link HttpHeader#getName() name} of the {@link HttpHeader}.
   * @param value the {@link HttpHeader#getValue() value} of the {@link HttpHeader}.
   * @return this object itself for fluent API calls.
   * @throws ReadOnlyException if this object is {@link #isImmutable() immutable}.
   */
  public AbstractHttpHeaders setHeader(String name, String value) throws ReadOnlyException {

    return setHeader(AbstractHttpHeader.of(name, value));
  }

  @Override
  protected void doWrite(Appendable appendable, boolean fromToString) throws IOException {

    for (AbstractHttpHeader header : this.headerMap.values()) {
      header.write(appendable);
    }
  }

  @Override
  public AbstractHttpHeaders setImmutable() {

    if (!isImmutable()) {
      super.setImmutable();
      for (AbstractHttpHeader header : this.headerMap.values()) {
        header.setImmutable();
      }
    }
    return this;
  }

  /**
   * This method parses and consumes the HTTP header from the given {@link BufferedReader}. After the call of this
   * method the {@link BufferedReader} will point to the body (content) of the HTTP message.
   *
   * @param reader the {@link BufferedReader} with the HTTP message to parse.
   * @return the {@link #isImmutable() immutable} parsed {@link AbstractHttpHeaders}.
   */
  public static AbstractHttpHeaders parse(BufferedReader reader) {

    return HttpHeadersImpl.parse(reader).setImmutable();
  }

  /**
   * @return a new instance of {@link AbstractHttpHeaders}. It will be mutable (not {@link #isImmutable() immutable})
   *         and allow {@link #addHeader(AbstractHttpHeader) modifications}.
   */
  public static AbstractHttpHeaders newInstance() {

    return new HttpHeadersImpl();
  }

  /**
   * @param httpHeaders the single valued {@link Map} of the {@link HttpHeader}s given as {@link String}s.
   * @return the {@link #isImmutable() immutable} {@link AbstractHttpHeaders} instance for the given {@link Map}.
   */
  public static AbstractHttpHeaders ofSingleValueHeaders(Map<String, String> httpHeaders) {

    if (httpHeaders == null) {
      return null;
    }
    return HttpHeadersImpl.ofSingle(httpHeaders).setImmutable();
  }

  /**
   * @param httpHeaders the {@link Map} of the {@link HttpHeader}s with their {@link HttpHeader#getValues() values}
   *        given as {@link List} of {@link String}s.
   * @return the {@link #isImmutable() immutable} {@link AbstractHttpHeaders} instance for the given {@link Map}.
   */
  public static AbstractHttpHeaders ofMultiValueHeaders(Map<String, List<String>> httpHeaders) {

    if (httpHeaders == null) {
      return null;
    }
    return HttpHeadersImpl.ofMulti(httpHeaders).setImmutable();
  }
}
