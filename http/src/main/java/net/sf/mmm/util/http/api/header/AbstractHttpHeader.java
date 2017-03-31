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
import net.sf.mmm.util.http.api.AbstractHttpObject;

/**
 * This is the abstract base implementation of {@link HttpHeader}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public abstract class AbstractHttpHeader extends AbstractHttpObject implements HttpHeader {

  private static final DateTimeFormatter RFC_1123_FORMATTER = DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneId.of("UTC"));

  private static final Logger LOG = LoggerFactory.getLogger(AbstractHttpHeader.class);

  private static Map<String, AbstractHttpHeader> FACTORY_MAP;

  private static final Set<String> MULTI_VALUE_HEADERS = new HashSet<>(Arrays.asList(HttpRequestHeader.HEADER_ACCEPT_CHARSET,
      HttpRequestHeader.HEADER_ACCEPT_ENCODING, HttpRequestHeader.HEADER_ACCEPT_LANGUAGE, HttpResponseHeader.HEADER_ACCEPT_RANGES, HEADER_CACHE_CONTROL,
      HEADER_CONNECTION, HttpResponseHeader.HEADER_CONTENT_ENCODING, HttpResponseHeader.HEADER_CONTENT_LANGUAGE, HttpRequestHeader.HEADER_EXPECT,
      HttpRequestHeader.HEADER_IF_MATCH, HttpRequestHeader.HEADER_IF_NONE_MATCH, HttpHeader.HEADER_PRAGMA, HttpResponseHeader.HEADER_PROXY_AUTHENTICATE,
      HttpResponseHeader.HEADER_TRAILER, HEADER_TRANSFER_ENCODING, HttpRequestHeader.HEADER_UPGRADE, HttpResponseHeader.HEADER_VARY, HEADER_VIA, HEADER_WARNING,
      HttpResponseHeader.HEADER_WWW_AUTHENTICATE));

  private String value;

  private AbstractHttpHeader next;

  /**
   * The constructor.
   */
  public AbstractHttpHeader() {
    super();
  }

  /**
   * The constructor.
   *
   * @param value the {@link #getValue() value}.
   */
  public AbstractHttpHeader(String value) {
    super();
    this.value = value;
  }

  private static Map<String, AbstractHttpHeader> getFactoryMap() {

    if (FACTORY_MAP == null) {
      Map<String, AbstractHttpHeader> map = new HashMap<>();
      register(HttpHeaderServer.FACTORY, map);
      register(HttpHeaderUserAgent.FACTORY, map);
      register(HttpHeaderCacheControl.FACTORY, map);
      register(HttpHeaderContentDisposition.FACTORY, map);
      register(HttpHeaderContentType.FACTORY, map);
      register(HttpHeaderPragma.FACTORY, map);
      for (String header : GenericHttpRequestHeader.HEADERS) {
        register(new GenericHttpRequestHeader(header, ""), map, false);
      }
      for (String header : GenericHttpResponseHeader.HEADERS) {
        register(new GenericHttpResponseHeader(header, ""), map, false);
      }
      for (String header : GenericHttpRequestResponseHeader.HEADERS) {
        register(new GenericHttpRequestResponseHeader(header, ""), map, false);
      }
      FACTORY_MAP = Collections.unmodifiableMap(map);
    }
    return FACTORY_MAP;
  }

  private static void register(AbstractHttpHeader factory, Map<String, AbstractHttpHeader> map) {

    register(factory, map, true);
  }

  private static void register(AbstractHttpHeader factory, Map<String, AbstractHttpHeader> map, boolean override) {

    String name = factory.getName();
    String key = name.toLowerCase(Locale.US);
    if (!override && map.containsKey(key)) {
      return;
    }
    AbstractHttpHeader old = map.put(key, factory);
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

    AbstractHttpHeader nextHeader = this.next;
    if (nextHeader == null) {
      return Arrays.asList(getValue());
    }
    List<String> values = new ArrayList<>();
    values.add(getValue());
    while (nextHeader != null) {
      values.add(nextHeader.getValue());
      nextHeader = nextHeader.next;
    }
    return values;
  }

  /**
   * @return {@code true} if this {@link HttpHeader} accepts multiple {@link #getValues() values} separated by comma.
   */
  @Override
  public boolean isSupportingMultiValue() {

    return MULTI_VALUE_HEADERS.contains(getName());
  }

  @Override
  public AbstractHttpHeader getNext() {

    return this.next;
  }

  /**
   * @param <H> the type of the requested {@link HttpHeader}.
   * @param headerType the {@link Class} reflecting the requested {@link HttpHeader}.
   * @return the {@link #getNext() next header} casted to the requested type or {@code null} if not possible.
   */
  protected <H extends AbstractHttpHeader> H getNextValue(Class<H> headerType) {

    if ((this.next == null) || !headerType.isAssignableFrom(this.next.getClass())) {
      return null;
    }
    return headerType.cast(this.next);
  }

  @Override
  public AbstractHttpHeader setImmutable() {

    super.setImmutable();
    if (this.next != null) {
      this.next.setImmutable();
    }
    return this;
  }

  @Override
  public final String getValue() {

    if (this.value == null) {
      this.value = calculateValue();
    }
    return this.value;
  }

  /**
   * @return the calculated value.
   */
  protected String calculateValue() {

    throw new IllegalStateException();
  }

  /**
   * @param newValue the header {@link #getValue() value}.
   * @return the new {@link AbstractHttpHeader} with the same {@link #getName() name} and {@link #getClass() type} as
   *         this header but with the given value.
   */
  protected abstract AbstractHttpHeader withValue(String newValue);

  void append(AbstractHttpHeader nextHeader) {

    Objects.requireNonNull(nextHeader, "next");
    String name = getName();
    if (!name.equals(nextHeader.getName())) {
      throw new ObjectMismatchException(nextHeader.getName(), name, nextHeader);
    }
    requireMutable(name);
    String nextHeaderValue = nextHeader.getValue();
    AbstractHttpHeader current = this;
    while (current.next != null) {
      if (current.getValue().equals(nextHeaderValue)) {
        return;
      }
      current = current.next;
    }
    current.next = nextHeader;
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
    if (!Objects.equals(this.next, other.next)) {
      return false;
    }
    return true;
  }

  @Override
  public final int hashCode() {

    return Objects.hash(getName(), getValue());
  }

  @Override
  protected void doWrite(Appendable buffer, boolean fromToString) throws IOException {

    String name = getName();
    int valueCount = 0;
    AbstractHttpHeader header = this;
    boolean csv = isSupportingMultiValue();
    while (header != null) {
      if ((valueCount == 0) || !csv) {
        if (valueCount > 0) {
          buffer.append(CRLF);
        }
        buffer.append(name);
        buffer.append(": ");
      } else if (valueCount > 0) {
        buffer.append(", ");
      }
      if (this.value != null) {
        buffer.append(this.value);
      }
      header = header.next;
      valueCount++;
    }
    if (!fromToString) {
      buffer.append(CRLF);
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
   * @return the specified {@link AbstractHttpHeader} instance.
   */
  public static AbstractHttpHeader of(String name, String value) {

    AbstractHttpHeader factory = getFactoryMap().get(name);
    if (factory == null) {
      String key = name.toLowerCase(Locale.US);
      factory = getFactoryMap().get(key);
      if (factory == null) {
        // fallback for unknown header...
        return new GenericHttpRequestResponseHeader(name, value);
      }
    }
    return factory.withValue(value);
  }

  /**
   * @param name the {@link HttpHeader#getName() name} of the {@link HttpHeader}.
   * @param values the {@link HttpHeader#getValues() values} of the {@link HttpHeader}.
   * @return the specified {@link AbstractHttpHeader} instance, or {@code null} if the given {@link List} is
   *         {@code null} or {@link List#isEmpty() empty}.
   */
  public static AbstractHttpHeader of(String name, List<String> values) {

    if ((values == null) || values.isEmpty()) {
      return null;
    }
    AbstractHttpHeader header = null;
    AbstractHttpHeader next = null;
    for (String value : values) {
      AbstractHttpHeader current = of(name, value);
      if (header == null) {
        header = current;
        next = current;
      } else {
        Objects.requireNonNull(next);
        next.append(current);
        next = current;
      }
    }
    return header;
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

}
