/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.header;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.function.Function;

import net.sf.mmm.util.exception.api.ReadOnlyException;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;

/**
 * Extends {@link AbstractHttpHeader} with {@link #getParameter(String) parameters}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public abstract class AbstractParameterizedHttpHeader extends AbstractHttpHeader {

  /** @see #getParameterSeparator() */
  protected static final char PARAMETER_SEPARATOR_SEMICOLON = ';';

  /** @see #getParameterSeparator() */
  protected static final char PARAMETER_SEPARATOR_COMMA = ',';

  /** @see #getParameter(String) */
  protected final TreeMap<String, Object> parameters;

  /**
   * The constructor.
   */
  public AbstractParameterizedHttpHeader() {
    super();
    this.parameters = new TreeMap<>();
  }

  /**
   * The constructor.
   *
   * @param parameters the {@link Map} with the {@link #getParameter(String) parameters}.
   */
  public AbstractParameterizedHttpHeader(Map<String, Object> parameters) {
    this();
    for (Entry<String, Object> entry : parameters.entrySet()) {
      this.parameters.put(entry.getKey().toLowerCase(Locale.US), entry.getValue());
    }
  }

  /**
   * @param key the name of the requested parameter. Case will be ignored.
   * @return the value of the requested parameter or {@code null} if no such parameter exists.
   */
  public Object getParameter(String key) {

    if (key == null) {
      return null;
    }
    Object value = this.parameters.get(key);
    if (value != null) {
      return value;
    }
    String keyLowerCase = key.toLowerCase(Locale.US);
    value = this.parameters.get(keyLowerCase);
    AbstractParameterizedHttpHeader current = this;
    while ((value == null) && (current != null)) {
      current = getNextValue(AbstractParameterizedHttpHeader.class);
      if (current != null) {
        value = current.parameters.get(keyLowerCase);
      }
    }
    return value;
  }

  /**
   * @return {@code true} if there is at least one {@link #getParameter(String) parameter} available, {@code false}
   *         otherwise.
   */
  boolean hasParameters() {

    return !this.parameters.isEmpty();
  }

  /**
   * @param <T> the generic type of the requested parameter.
   * @param key the name of the requested parameter. Case will be ignored.
   * @param type the {@link Class} with the expected type of the requested parameter.
   * @return the value of the requested parameter or {@code null} if no such parameter exists.
   */
  public <T> T getParameterAs(String key, Class<T> type) {

    Object value = getParameter(key);
    if (value == null) {
      return null;
    }
    return type.cast(value);
  }

  /**
   * @param key the name of the requested parameter. Case will be ignored.
   * @return the value of the requested parameter or {@code null} if no such parameter exists.
   */
  public Long getParameterAsLong(String key) {

    Object value = getParameter(key);
    if (value == null) {
      return null;
    }
    if (value instanceof Long) {
      return (Long) value;
    } else if (value instanceof Number) {
      return Long.valueOf(((Number) value).longValue());
    } else {
      return Long.valueOf(value.toString().trim());
    }
  }

  /**
   * @param key the name of the requested parameter. Case will be ignored.
   * @return the value of the requested parameter or {@code null} if no such parameter exists.
   */
  public String getParameterAsString(String key) {

    Object value = getParameter(key);
    if (value == null) {
      return null;
    }
    if (value instanceof TemporalAccessor) {
      return formatDateTime((TemporalAccessor) value);
    } else {
      return value.toString();
    }
  }

  /**
   * @param key the name of the requested parameter. Case will be ignored.
   * @return {@code true} if the parameter is present, {@code false} otherwise.
   */
  public boolean getParameterAsBooleanFlag(String key) {

    Object value = getParameter(key);
    if (Boolean.TRUE.equals(value)) {
      return true;
    } else if (value instanceof String) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * @param key the name of the requested parameter. Case will be ignored.
   * @return the value of the requested parameter or {@code null} if no such parameter exists.
   */
  public Instant getParameterAsInstant(String key) {

    Object value = getParameter(key);
    if (value == null) {
      return null;
    }
    if (value instanceof Instant) {
      return (Instant) value;
    } else {
      return Instant.from(DateTimeFormatter.RFC_1123_DATE_TIME.parse(value.toString()));
    }
  }

  /**
   * @param key the {@link java.util.Map.Entry#getKey() key}.
   * @param value the {@link java.util.Map.Entry#getValue() value}.
   * @return the previous value - see {@link Map#put(Object, Object)}.
   */
  protected Object setParameter(String key, Object value) {

    if (isImmutable()) {
      throw new ReadOnlyException(this, key);
    }
    String keyLowerCase = key.toLowerCase(Locale.US);
    if (value == null) {
      return this.parameters.remove(keyLowerCase);
    }
    return this.parameters.put(keyLowerCase, value);
  }

  /**
   * @param value the parameters as {@code key=value} pairs separated by {@link #getParameterSeparator() parameter
   *        separator}.
   * @return the parsed {@link Map} of parameters.
   */
  protected Map<String, Object> parseParameters(String value) {

    return parseParameters(value, Function.identity());
  }

  /**
   * @param parametersAsString the parameters as {@code key=value} pairs separated by {@link #getParameterSeparator()
   *        parameter separator}.
   * @param noValueParameterFunction is {@link Function#apply(Object) called} for each parameter that has no value (no
   *        equal sign) or {@code null} to add such parameters to the {@link Map}.
   * @return the parsed {@link Map} of parameters.
   */
  protected Map<String, Object> parseParameters(String parametersAsString, Function<String, String> noValueParameterFunction) {

    String[] segments = parametersAsString.split(Character.toString(getParameterSeparator()));
    Map<String, Object> parameterMap = null;
    for (String segment : segments) {
      segment = segment.trim();
      int equalsIndex = segment.indexOf('=');
      String key;
      Object value;
      if (equalsIndex < 0) {
        value = Boolean.TRUE;
        key = noValueParameterFunction.apply(segment.trim());
      } else {
        key = segment.substring(0, equalsIndex).trim();
        String valueAsString = segment.substring(equalsIndex + 1).trim();
        value = valueAsString;
        String unquotedValue = unquote(valueAsString);
        if (value == unquotedValue) {
          try {
            value = Long.valueOf(valueAsString);
          } catch (NumberFormatException e) {
            // ignore
          }
        } else {
          value = unquotedValue;
        }
      }
      if (key != null) {
        if (parameterMap == null) {
          parameterMap = new HashMap<>();
        }
        parameterMap.put(key.toLowerCase(Locale.US), value);
      }
    }
    return parameterMap;
  }

  /**
   * @param buffer the {@link Appendable} where to {@link Appendable#append(CharSequence) append} the parameters.
   */
  protected void formatParameters(Appendable buffer) {

    try {
      String separator = null;
      for (String key : this.parameters.keySet()) {
        Object value = this.parameters.get(key);
        if (!Boolean.FALSE.equals(value)) {
          if (separator == null) {
            separator = getParameterSeparator() + " ";
          } else {
            buffer.append(separator);
          }
          buffer.append(key);
          if ((value != null) && (value != Boolean.TRUE)) {
            String quote = "\"";
            if (value instanceof Number) {
              quote = "";
            }
            buffer.append('=');
            buffer.append(quote);
            if (value instanceof TemporalAccessor) {
              buffer.append(formatDateTime((TemporalAccessor) value));
            } else {
              buffer.append(value.toString());
            }
            buffer.append(quote);
          }
        }
      }
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.WRITE);
    }
  }

  /**
   * @see #getParameterSeparator()
   * @return {@code true} to use {@link #PARAMETER_SEPARATOR_COMMA}, {@code false} to use
   *         {@link #PARAMETER_SEPARATOR_SEMICOLON}.
   */
  protected boolean isSeparateParametersByComma() {

    return true;
  }

  /**
   * @see #formatParameters(Appendable)
   * @see #isSeparateParametersByComma()
   * @return the {@link Character} to separate {@link #getParameter(String) parameters}.
   */
  protected char getParameterSeparator() {

    if (isSeparateParametersByComma()) {
      return PARAMETER_SEPARATOR_COMMA;
    } else {
      return PARAMETER_SEPARATOR_SEMICOLON;
    }
  }

  @Override
  protected String calculateValue() {

    StringBuilder buffer = new StringBuilder();
    calculateValueStart(buffer);
    formatParameters(buffer);
    return buffer.toString();
  }

  /**
   * @param buffer the {@link StringBuilder} where to {@link StringBuilder#append(String) append} initial data for
   *        {@link #getValue()}.
   */
  protected void calculateValueStart(StringBuilder buffer) {

    // nothing to do by default, override to extend...
  }

}
