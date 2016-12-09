/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.mmm.util.exception.api.NlsParseException;
import net.sf.mmm.util.exception.api.ObjectMismatchException;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;

/**
 * This is the abstract base implementation of {@link HttpHeader}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public abstract class AbstractHttpHeader implements HttpHeader {

  private static final DateTimeFormatter RFC_1123_FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneId.of("UTC"));

  private static final Logger LOG = LoggerFactory.getLogger(AbstractHttpHeader.class);

  private static final Set<String> MULTI_VALUE_HEADERS = new HashSet<>(Arrays.asList(HttpRequestHeader.HEADER_ACCEPT_CHARSET,
      HttpRequestHeader.HEADER_ACCEPT_ENCODING, HttpRequestHeader.HEADER_ACCEPT_LANGUAGE, HttpResponseHeader.HEADER_ACCEPT_RANGES, HEADER_CACHE_CONTROL,
      HEADER_CONNECTION, HttpResponseHeader.HEADER_CONTENT_ENCODING, HttpResponseHeader.HEADER_CONTENT_LANGUAGE, HttpRequestHeader.HEADER_EXPECT,
      HttpRequestHeader.HEADER_IF_MATCH, HttpRequestHeader.HEADER_IF_NONE_MATCH, HttpHeader.HEADER_PRAGMA, HttpResponseHeader.HEADER_PROXY_AUTHENTICATE,
      HttpResponseHeader.HEADER_TRAILER, HEADER_TRANSFER_ENCODING, HttpRequestHeader.HEADER_UPGRADE, HttpResponseHeader.HEADER_VARY, HEADER_VIA, HEADER_WARNING,
      HttpResponseHeader.HEADER_WWW_AUTHENTICATE));

  private static final Map<String, AbstractHttpHeaderFactory> FACTORY_MAP = createFactoryMap();

  private AbstractHttpHeader nextValue;

  private static Map<String, AbstractHttpHeaderFactory> createFactoryMap() {

    Map<String, AbstractHttpHeaderFactory> map = new HashMap<>();
    register(new HttpHeaderCacheControl.Factory(), map);
    register(new HttpHeaderContentDisposition.Factory(), map);
    register(new HttpHeaderPragma.Factory(), map);
    register(new HttpHeaderUserAgent.Factory(), map);
    for (String header : GenericHttpRequestHeader.HEADERS) {
      register(new GenericHttpRequestHeader.Factory(header), map);
    }
    for (String header : GenericHttpResponseHeader.HEADERS) {
      register(new GenericHttpResponseHeader.Factory(header), map);
    }
    for (String header : GenericHttpRequestResponseHeader.HEADERS) {
      register(new GenericHttpRequestResponseHeader.Factory(header), map);
    }
    return Collections.unmodifiableMap(map);
  }

  private static void register(AbstractHttpHeaderFactory factory, Map<String, AbstractHttpHeaderFactory> map) {

    String name = factory.getName();
    AbstractHttpHeaderFactory old = map.put(name.toLowerCase(Locale.US), factory);
    if (old != null) {
      LOG.warn("Duplicate factory for header {} - replaced {} with {}", name, old, factory);
    }
  }

  /**
   * @param value the {@link String} to check.
   * @return {@code true} if the given {@link String} is {@code null} or {@link String#isEmpty() empty}, {@code false}
   *         otherwise.
   */
  protected static boolean isEmpty(String value) {

    if ((value == null) || (value.isEmpty())) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * @param value the {@link String} to {@link String#trim() trim}.
   * @return the {@link String#trim() trimmed} {@link String} or {@code null} if the given {@link String} was
   *         {@code null} or the result would be {@link String#isEmpty() empty}.
   */
  protected static String trim(String value) {

    if (value == null) {
      return null;
    }
    String result = value.trim();
    if (result.isEmpty()) {
      return null;
    }
    return result;
  }

  /**
   * @param value the value to parse.
   * @return the parsed value or {@code null} if {@code value} is invalid.
   */
  protected static Long parseLong(String value) {

    if (value == null) {
      return null;
    }
    try {
      return Long.valueOf(value.trim());
    } catch (NumberFormatException e) {
      LOG.debug("Illegal number format {}", value, e);
      return null;
    }
  }

  /**
   * @param value the value to parse.
   * @param defaultValue the default value to use as fallback.
   * @return the parsed value or {@code defaultValue} if {@code value} is invalid.
   */
  protected static long parseLong(String value, long defaultValue) {

    if (value == null) {
      return defaultValue;
    }
    try {
      return Long.parseLong(value);
    } catch (NumberFormatException e) {
      LOG.debug("Illegal number format {}", value, e);
      return defaultValue;
    }
  }

  /**
   * @param value the value to parse.
   * @return the parsed value or {@code null} if {@code value} is invalid.
   */
  protected static Instant parseInstant(String value) {

    if (value == null) {
      return null;
    }
    try {
      return Instant.from(DateTimeFormatter.RFC_1123_DATE_TIME.parse(value));
    } catch (DateTimeParseException e) {
      LOG.debug("Illegal date format {}", value, e);
      return null;
    }
  }

  /**
   * @param date the {@link Instant} for format. May be {@code null}.
   * @return the {@link Instant} formatted according to RFC 1123 or {@code null} if the given {@link Instant} was
   *         {@code null}.
   */
  protected static String formatDateTime(TemporalAccessor date) {

    if (date == null) {
      return null;
    }
    return RFC_1123_FORMATTER.format(date);
  }

  /**
   * @param value the value that is potentially quoted (e.g. "value" or 'value').
   * @return the value without quotes.
   */
  protected static String unquote(String value) {

    int length = value.length();
    if (length >= 2) {
      char quote = value.charAt(0);
      if ((quote == '"') || (quote == '\'')) {
        int end = length - 1;
        if (quote == value.charAt(end)) {
          return value.substring(1, end);
        }
      }
    }
    return value;
  }

  @Override
  public List<String> getValues() {

    AbstractHttpHeader next = this.nextValue;
    if (next == null) {
      return Arrays.asList(getValue());
    }
    List<String> values = new ArrayList<>();
    values.add(getValue());
    while (next != null) {
      values.add(next.getValue());
      next = next.nextValue;
    }
    return values;
  }

  /**
   * @return {@code true} if this {@link HttpHeader} accepts multiple {@link #getValues() values} separated by comma.
   */
  public boolean isSupportingMultiValue() {

    return MULTI_VALUE_HEADERS.contains(getName());
  }

  @Override
  public AbstractHttpHeader getNextValue() {

    return this.nextValue;
  }

  /**
   * @param <H> the type of the requested {@link HttpHeader}.
   * @param headerType the {@link Class} reflecting the requested {@link HttpHeader}.
   * @return the {@link #getNextValue() next header} casted to the requested type or {@code null} if not possible.
   */
  protected <H extends AbstractHttpHeader> H getNextValue(Class<H> headerType) {

    if ((this.nextValue == null) || !headerType.isAssignableFrom(this.nextValue.getClass())) {
      return null;
    }
    return headerType.cast(this.nextValue);
  }

  void append(AbstractHttpHeader next) {

    Objects.requireNonNull(next, "next");
    if (!getName().equals(next.getName())) {
      throw new ObjectMismatchException(next.getName(), getName(), next);
    }
    AbstractHttpHeader current = this;
    while (current.nextValue != null) {
      current = current.nextValue;
    }
    current.nextValue = next;
  }

  @Override
  public final boolean equals(Object obj) {

    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (!(obj instanceof AbstractHttpHeader)) {
      return false;
    }
    AbstractHttpHeader other = (AbstractHttpHeader) obj;
    if (!Objects.equals(getName(), other.getName())) {
      return false;
    }
    if (!Objects.equals(getValue(), other.getValue())) {
      return false;
    }
    if (!Objects.equals(this.nextValue, other.nextValue)) {
      return false;
    }
    return true;
  }

  @Override
  public final int hashCode() {

    return Objects.hash(getName(), getValue());
  }

  @Override
  public String toString() {

    StringBuilder buffer = new StringBuilder(getName().length() + 16);
    toString(buffer);
    return buffer.toString();
  }

  /**
   * @see #toString()
   * @param buffer the {@link StringBuilder} where to {@link StringBuilder#append(String) append} to.
   */
  public void toString(Appendable buffer) {

    toString(buffer, false);
  }

  /**
   * @see #toString()
   * @param buffer the {@link StringBuilder} where to {@link StringBuilder#append(String) append} to.
   * @param newline - {@code true} if the header should be terminated by a {@link #NEWLINE}, {@code false} otherwise.
   */
  protected void toString(Appendable buffer, boolean newline) {

    try {
      String name = getName();
      int valueCount = 0;
      HttpHeader header = this;
      boolean csv = isSupportingMultiValue();
      while (header != null) {
        if ((valueCount == 0) || !csv) {
          if (valueCount > 0) {
            buffer.append(NEWLINE);
          }
          buffer.append(name);
          buffer.append(": ");
        } else if (valueCount > 0) {
          buffer.append(", ");
        }
        buffer.append(getValue());
        header = header.getNextValue();
        valueCount++;
      }
      if (newline) {
        buffer.append(NEWLINE);
      }
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.WRITE);
    }
  }

  /**
   * @param header the {@link HttpHeader#toString() string representation} of the {@link HttpHeader}. May be
   *        {@code null}.
   * @return the parsed {@link HttpHeader} or {@code null} if the given {@code header} was {@code null}.
   */
  public static AbstractHttpHeader ofHeader(String header) {

    if (header == null) {
      return null;
    }
    int colonIndex = header.indexOf(':');
    if (colonIndex <= 1) {
      throw new NlsParseException(header, HttpHeader.class);
    }
    String name = header.substring(0, colonIndex).trim();
    String value = header.substring(colonIndex + 1).trim();
    return of(name, value);
  }

  /**
   * @param name the {@link HttpHeader#getName() name} of the {@link HttpHeader}.
   * @param value the {@link HttpHeader#getValue() value} of the {@link HttpHeader}.
   * @return the parsed {@link HttpHeader} or {@code null} if the given {@code header} was {@code null}.
   */
  public static AbstractHttpHeader of(String name, String value) {

    AbstractHttpHeaderFactory factory = FACTORY_MAP.get(name);
    if (factory == null) {
      String key = name.toLowerCase(Locale.US);
      factory = FACTORY_MAP.get(key);
      if (factory == null) {
        // fallback for unknown header...
        return new GenericHttpRequestResponseHeader(name, value);
      }
    }
    return factory.create(value);
  }

  /**
   * @param name the {@link HttpHeader#getName() name} of the {@link HttpHeader}.
   * @param httpHeaders the {@link Map} of the HTTP headers.
   * @return the parsed {@link AbstractHttpHeader}.
   */
  public static AbstractHttpHeader ofSingleValueHeaders(String name, Map<String, String> httpHeaders) {

    if (httpHeaders == null) {
      return null;
    }
    return of(name, httpHeaders.get(name));
  }

  /**
   * @param name the {@link HttpHeader#getName() name} of the {@link HttpHeader}.
   * @param httpHeaders the {@link Map} of the HTTP headers.
   * @return the parsed {@link AbstractHttpHeader}.
   */
  @SuppressWarnings("null")
  public static AbstractHttpHeader ofMultiValueHeaders(String name, Map<String, List<String>> httpHeaders) {

    if (httpHeaders == null) {
      return null;
    }
    List<String> values = httpHeaders.get(HEADER_CONTENT_DISPOSITION);
    AbstractHttpHeader result = null;
    AbstractHttpHeader next = null;
    for (String value : values) {
      AbstractHttpHeader header = of(name, value);
      if (result == null) {
        result = header;
        next = header;
      } else {
        next.append(header);
        next = header;
      }
    }
    return result;
  }

  abstract static class AbstractHttpHeaderFactory {

    protected final String name;

    AbstractHttpHeaderFactory(String name) {
      super();
      this.name = name;
    }

    String getName() {

      return this.name;
    }

    abstract AbstractHttpHeader create(String value);

  }

}
