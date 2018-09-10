/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.json.api;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;

import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is a helper class with static methods to deal with JSON.
 *
 * @author hohwille
 * @since 7.4.0
 */
public interface JsonUtil {

  /**
   * A generic implementation of {@link JsonSupport#toJson(JsonGenerator)} for a given {@code value}.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write the
   *        {@code value}).
   * @param value the value to write as JSON. May be {@code null}.
   */
  void write(JsonGenerator json, String property, Object value);

  /**
   * An implementation of {@link JsonSupport#toJson(JsonGenerator)} for a given {@link String} value.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write the
   *        {@code value}).
   * @param value the value to write as JSON. May be {@code null}.
   */
  void write(JsonGenerator json, String property, String value);

  /**
   * An implementation of {@link JsonSupport#toJson(JsonGenerator)} for a given {@link Boolean} value.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write the
   *        {@code value}).
   * @param value the value to write as JSON. May be {@code null}.
   */
  void write(JsonGenerator json, String property, boolean value);

  /**
   * A generic implementation of {@link JsonSupport#toJson(JsonGenerator)} for a given {@link Number}.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write the
   *        {@code value}).
   * @param value the value to write as JSON. May be {@code null}.
   */
  void write(JsonGenerator json, String property, Number value);

  /**
   * An implementation of {@link JsonSupport#toJson(JsonGenerator)} for a given {@link Long}.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write the
   *        {@code value}).
   * @param value the value to write as JSON. May be {@code null}.
   */
  void write(JsonGenerator json, String property, long value);

  /**
   * An implementation of {@link JsonSupport#toJson(JsonGenerator)} for a given {@link Double}.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write the
   *        {@code value}).
   * @param value the value to write as JSON. May be {@code null}.
   */
  void write(JsonGenerator json, String property, double value);

  /**
   * An implementation of {@link JsonSupport#toJson(JsonGenerator)} for a given {@link BigDecimal}.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write the
   *        {@code value}).
   * @param value the value to write as JSON. May be {@code null}.
   */
  void write(JsonGenerator json, String property, BigDecimal value);

  /**
   * An implementation of {@link JsonSupport#toJson(JsonGenerator)} for a given {@link BigInteger}.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write the
   *        {@code value}).
   * @param value the value to write as JSON. May be {@code null}.
   */
  void write(JsonGenerator json, String property, BigInteger value);

  /**
   * An implementation of {@link JsonSupport#toJson(JsonGenerator)} for a given {@link Integer}.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write the
   *        {@code value}).
   * @param value the value to write as JSON. May be {@code null}.
   */
  void write(JsonGenerator json, String property, int value);

  /**
   * An implementation of {@link JsonSupport#toJson(JsonGenerator)} to write the {@code null} value.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write
   *        {@code null}).
   */
  void writeNull(JsonGenerator json, String property);

  /**
   * An implementation of {@link JsonSupport#toJson(JsonGenerator)} for a given {@link Iterable}.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write the
   *        {@code value}).
   * @param value the value to write as JSON. May be {@code null}.
   */
  void write(JsonGenerator json, String property, Iterable<?> value);

  /**
   * A implementation of {@link JsonSupport#toJson(JsonGenerator)} for a given {@link Map}.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write the
   *        {@code value}).
   * @param value the value to write as JSON. May be {@code null}.
   */
  void write(JsonGenerator json, String property, Map<?, ?> value);

  /**
   * A implementation of {@link JsonSupport#toJson(JsonGenerator)} for a given {@code array}.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write the
   *        {@code value}).
   * @param value the value to write as JSON array. May be {@code null}.
   */
  void write(JsonGenerator json, String property, Object[] value);

  /**
   * @param json the {@link JsonParser} to get the {@link JsonParser#next() next} {@link Event} from.
   * @param event the expected {@link Event}.
   * @throws IllegalStateException if the expected {@link Event} does not match.
   */
  void expectEvent(JsonParser json, Event event);

  /**
   * @param actual the actual {@link Event} received from {@link JsonParser}.
   * @param expected the expected {@link Event}.
   * @throws IllegalStateException if the expected {@link Event} does not match.
   */
  void expectEvent(Event actual, Event expected);

  /**
   * @param <T> the generic type of the object to parse.
   * @param json the {@link JsonParser}.
   * @param type the {@link GenericType} of the requested object to parse.
   * @return the parsed value.
   */
  <T> T read(JsonParser json, GenericType<T> type);

  /**
   * @param <T> the generic type of the object to parse.
   * @param json the {@link JsonParser}.
   * @param type the {@link GenericType} of the requested object to parse.
   * @param e the current {@link Event}.
   * @return the parsed value.
   */
  <T> T read(JsonParser json, GenericType<T> type, Event e);

  /**
   * @param <E> the generic type of the {@link Enum} to parse.
   * @param json the {@link JsonParser}.
   * @param enumType the {@link Class} reflecting the {@link Enum} to parse.
   * @return the parsed {@link Enum} constant.
   * @throws IllegalStateException if no such {@link Enum} constant exists.
   */
  <E extends Enum<E>> E readEnum(JsonParser json, Class<E> enumType);

  /**
   * @param <C> the generic type of the {@link Collection}.
   * @param json the {@link JsonParser}.
   * @param type the {@link GenericType} of the {@link Collection}.
   * @return the parsed {@link Collection}.
   */
  <C extends Collection<?>> C readCollection(JsonParser json, GenericType<C> type);

  /**
   * @param <E> the generic type of the {@link Collection#add(Object) elements} in the {@link Collection}.
   * @param json the {@link JsonParser}.
   * @param collection the {@link Collection} where to {@link Collection#add(Object) add} the elements parsed
   *        from JSON.
   * @param type the {@link GenericType} of the {@link Collection#add(Object) elements} in the
   *        {@link Collection}.
   */
  <E> void readCollection(JsonParser json, Collection<E> collection, GenericType<E> type);

  /**
   * @param <K> the generic type of the {@link Map} key.
   * @param <V> the generic type of the {@link Map} value.
   * @param <M> the generic type of the {@link Map}.
   * @param json the {@link JsonParser}.
   * @param type the {@link GenericType} of the {@link Map}.
   * @return the parsed {@link Map}.
   */
  <K, V, M extends Map<K, V>> M readMap(JsonParser json, GenericType<M> type);

  /**
   * @param <K> the generic type of the {@link Map} key.
   * @param <V> the generic type of the {@link Map} value.
   * @param json the {@link JsonParser}.
   * @param map the {@link Map} where to add the parsed data.
   * @param keyType the {@link GenericType} of the {@link Map} key.
   * @param valueType the {@link GenericType} of the {@link Map} value.
   */
  <K, V> void readMap(JsonParser json, Map<K, V> map, GenericType<K> keyType, GenericType<V> valueType);

  /**
   * @param e the JSON {@link Event}.
   * @return the coressponding {@link Boolean} value.
   */
  boolean readBoolean(Event e);

  /**
   * @param <N> the generic type of the {@link Number} to parse.
   * @param json the {@link JsonParser} with {@link JsonParser#next() current event} being
   *        {@link Event#VALUE_NUMBER}.
   * @param type the {@link Class} reflecting the type of {@link Number} to parse.
   * @return the parsed value.
   */
  public <N extends Number> N readNumber(JsonParser json, Class<N> type);

  /**
   * Skips the current value from the {@link JsonParser}.
   *
   * @param json the {@link JsonParser}.
   */
  void skipValue(JsonParser json);

  /**
   * Skips the current value from the {@link JsonParser} when the current {@link Event} was already
   * {@link JsonParser#next() read} and is given.
   *
   * @param json the {@link JsonParser}.
   * @param e the {@link Event} that was already {@link JsonParser#next() read} before.
   */
  void skipValue(JsonParser json, Event e);
}
