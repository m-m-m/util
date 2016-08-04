/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.json.api;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.exception.api.ObjectMismatchException;
import net.sf.mmm.util.exception.api.WrongValueTypeException;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.reflect.api.CollectionReflectionUtil;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is a helper class with static methods to deal with JSON.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface JsonUtil {

  /**
   * A generic implementation of {@link JsonSupport#toJson(JsonGenerator)} for a given {@code value}.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write the {@code value}).
   * @param value the value to write as JSON. May be {@code null}.
   */
  default void write(JsonGenerator json, String property, Object value) {

    if (value == null) {
      writeNull(json, property);
    } else if (value instanceof JsonSupport) {
      if (property != null) {
        json.writeStartObject(property);
      }
      ((JsonSupport) value).toJson(json);
      if (property != null) {
        json.writeEnd();
      }
    } else if (value instanceof Number) {
      write(json, property, (Number) value);
    } else if (value instanceof Boolean) {
      boolean booleanValue = ((Boolean) value).booleanValue();
      write(json, property, booleanValue);
    } else if (value.getClass().isArray()) {
      int length = Array.getLength(value);
      if (property == null) {
        json.writeStartArray();
      } else {
        json.writeStartArray(property);
      }
      for (int i = 0; i < length; i++) {
        write(json, null, Array.get(value, i));
      }
      json.writeEnd();
    } else if (value instanceof Iterable) {
      write(json, property, (Iterable<?>) value);
    } else if (value instanceof Map) {
      write(json, property, (Map<?, ?>) value);
    } else {
      String string = value.toString();
      write(json, property, string);
    }
  }

  /**
   * An implementation of {@link JsonSupport#toJson(JsonGenerator)} for a given {@link String} value.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write the {@code value}).
   * @param value the value to write as JSON. May be {@code null}.
   */
  default void write(JsonGenerator json, String property, String value) {

    if (value == null) {
      writeNull(json, property);
      return;
    }
    if (property == null) {
      json.write(value);
    } else {
      json.write(property, value);
    }
  }

  /**
   * An implementation of {@link JsonSupport#toJson(JsonGenerator)} for a given {@link Boolean} value.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write the {@code value}).
   * @param value the value to write as JSON. May be {@code null}.
   */
  default void write(JsonGenerator json, String property, boolean value) {

    if (property == null) {
      json.write(value);
    } else {
      json.write(property, value);
    }
  }

  /**
   * A generic implementation of {@link JsonSupport#toJson(JsonGenerator)} for a given {@link Number}.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write the {@code value}).
   * @param value the value to write as JSON. May be {@code null}.
   */
  default void write(JsonGenerator json, String property, Number value) {

    if (value instanceof Long) {
      write(json, property, value.longValue());
    } else if ((value instanceof Integer) || (value instanceof Short) || (value instanceof Byte)) {
      write(json, property, value.intValue());
    } else if (value instanceof BigDecimal) {
      write(json, property, (BigDecimal) value);
    } else if (value instanceof BigInteger) {
      write(json, property, (BigInteger) value);
    } else {
      if (value == null) {
        writeNull(json, property);
        return;
      }
      write(json, property, value.doubleValue());
    }
  }

  /**
   * An implementation of {@link JsonSupport#toJson(JsonGenerator)} for a given {@link Long}.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write the {@code value}).
   * @param value the value to write as JSON. May be {@code null}.
   */
  default void write(JsonGenerator json, String property, long value) {

    if (property == null) {
      json.write(value);
    } else {
      json.write(property, value);
    }
  }

  /**
   * An implementation of {@link JsonSupport#toJson(JsonGenerator)} for a given {@link Double}.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write the {@code value}).
   * @param value the value to write as JSON. May be {@code null}.
   */
  default void write(JsonGenerator json, String property, double value) {

    if (property == null) {
      json.write(value);
    } else {
      json.write(property, value);
    }
  }

  /**
   * An implementation of {@link JsonSupport#toJson(JsonGenerator)} for a given {@link BigDecimal}.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write the {@code value}).
   * @param value the value to write as JSON. May be {@code null}.
   */
  default void write(JsonGenerator json, String property, BigDecimal value) {

    if (value == null) {
      writeNull(json, property);
      return;
    }
    if (property == null) {
      json.write(value);
    } else {
      json.write(property, value);
    }
  }

  /**
   * An implementation of {@link JsonSupport#toJson(JsonGenerator)} for a given {@link BigInteger}.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write the {@code value}).
   * @param value the value to write as JSON. May be {@code null}.
   */
  default void write(JsonGenerator json, String property, BigInteger value) {

    if (value == null) {
      writeNull(json, property);
      return;
    }
    if (property == null) {
      json.write(value);
    } else {
      json.write(property, value);
    }
  }

  /**
   * An implementation of {@link JsonSupport#toJson(JsonGenerator)} for a given {@link Integer}.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write the {@code value}).
   * @param value the value to write as JSON. May be {@code null}.
   */
  default void write(JsonGenerator json, String property, int value) {

    if (property == null) {
      json.write(value);
    } else {
      json.write(property, value);
    }
  }

  /**
   * An implementation of {@link JsonSupport#toJson(JsonGenerator)} to write the {@code null} value.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write {@code null}).
   */
  default void writeNull(JsonGenerator json, String property) {

    if (property == null) {
      json.writeNull();
    }
  }

  /**
   * An implementation of {@link JsonSupport#toJson(JsonGenerator)} for a given {@link Iterable}.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write the {@code value}).
   * @param value the value to write as JSON. May be {@code null}.
   */
  default void write(JsonGenerator json, String property, Iterable<?> value) {

    if (value == null) {
      writeNull(json, property);
      return;
    }
    if (property == null) {
      json.writeStartArray();
    } else {
      json.writeStartArray(property);
    }
    for (Object item : value) {
      write(json, null, item);
    }
    json.writeEnd();
  }

  /**
   * A implementation of {@link JsonSupport#toJson(JsonGenerator)} for a given {@link Map}.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write the {@code value}).
   * @param value the value to write as JSON. May be {@code null}.
   */
  default void write(JsonGenerator json, String property, Map<?, ?> value) {

    if (value == null) {
      writeNull(json, property);
      return;
    }
    if (property == null) {
      json.writeStartArray();
    } else {
      json.writeStartArray(property);
    }
    for (Entry<?, ?> entry : value.entrySet()) {
      Object key = entry.getKey();
      String keyString = "null";
      if (key != null) {
        keyString = key.toString();
      }
      write(json, keyString, entry.getValue());
    }
    json.writeEnd();
  }

  /**
   * A implementation of {@link JsonSupport#toJson(JsonGenerator)} for a given {@code array}.
   *
   * @param json the {@link JsonGenerator}.
   * @param property the name of the JSON property or {@code null} for no property (just to write the {@code value}).
   * @param value the value to write as JSON array. May be {@code null}.
   */
  default void write(JsonGenerator json, String property, Object[] value) {

    if (value == null) {
      writeNull(json, property);
      return;
    }
    if (property == null) {
      json.writeStartArray();
    } else {
      json.writeStartArray(property);
    }
    for (Object item : value) {
      write(json, null, item);
    }
    json.writeEnd();
  }

  /**
   * @param json the {@link JsonParser} to get the {@link JsonParser#next() next} {@link Event} from.
   * @param event the expected {@link Event}.
   * @throws ObjectMismatchException if the expected {@link Event} does not match.
   */
  default void expectEvent(JsonParser json, Event event) throws ObjectMismatchException {

    Event e = json.next();
    expectEvent(e, event);
  }

  /**
   * @param actual the actual {@link Event} received from {@link JsonParser}.
   * @param expected the expected {@link Event}.
   * @throws ObjectMismatchException if the expected {@link Event} does not match.
   */
  default void expectEvent(Event actual, Event expected) {

    if (actual != expected) {
      throw new ObjectMismatchException(actual, expected);
    }
  }

  /**
   * @param <T> the generic type of the object to parse.
   * @param json the {@link JsonParser}.
   * @param type the {@link GenericType} of the requested object to parse.
   * @return the parsed value.
   */
  default <T> T read(JsonParser json, GenericType<T> type) {

    return read(json, type, json.next());
  }

  /**
   * @param <T> the generic type of the object to parse.
   * @param json the {@link JsonParser}.
   * @param type the {@link GenericType} of the requested object to parse.
   * @param e the current {@link Event}.
   * @param collectionReflectionUtil the {@link CollectionReflectionUtil} instance.
   * @param stringUtil the {@link StringUtil} instance.
   * @return the parsed value.
   */
  <T> T read(JsonParser json, GenericType<T> type, Event e);

  /**
   * @param <E> the generic type of the {@link Enum} to parse.
   * @param json the {@link JsonParser}.
   * @param enumType the {@link Class} reflecting the {@link Enum} to parse.
   * @param stringUtil the {@link StringUtil} instance.
   * @return the parsed {@link Enum} constant.
   * @throws IllegalCaseException if no such {@link Enum} constant exists.
   */
  <E extends Enum<E>> E readEnum(JsonParser json, Class<E> enumType) throws IllegalCaseException;

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
   * @param collection the {@link Collection} where to {@link Collection#add(Object) add} the elements parsed from JSON.
   * @param type the {@link GenericType} of the {@link Collection#add(Object) elements} in the {@link Collection}.
   */
  <E> void readCollection(JsonParser json, Collection<E> collection, GenericType<E> type);

  /**
   * @param <M> the generic type of the {@link Map}.
   * @param json the {@link JsonParser}.
   * @param type the {@link GenericType} of the {@link Map}.
   * @return the parsed {@link Map}.
   */
  <M extends Map<?, ?>> M readMap(JsonParser json, GenericType<M> type);

  /**
   * @param e the JSON {@link Event}.
   * @return the coressponding {@link Boolean} value.
   */
  default boolean readBoolean(Event e) {

    if (e == Event.VALUE_TRUE) {
      return true;
    } else if (e == Event.VALUE_FALSE) {
      return false;
    }
    throw new WrongValueTypeException(e, Boolean.class);
  }

  /**
   * @param <N> the generic type of the {@link Number} to parse.
   * @param json the {@link JsonParser} with {@link JsonParser#next() current event} being {@link Event#VALUE_NUMBER}.
   * @param type the {@link Class} reflecting the type of {@link Number} to parse.
   * @return the parsed value.
   */
  @SuppressWarnings("unchecked")
  default <N extends Number> N readNumber(JsonParser json, Class<N> type) {

    Number result;
    String value = json.getString();
    if ((type == Long.class) || (type == long.class)) {
      result = Long.valueOf(value);
    } else if ((type == Integer.class) || (type == int.class)) {
      result = Integer.valueOf(value);
    } else if ((type == Double.class) || (type == double.class)) {
      result = Double.valueOf(value);
    } else if ((type == Float.class) || (type == float.class)) {
      result = Float.valueOf(value);
    } else if ((type == Short.class) || (type == short.class)) {
      result = Short.valueOf(value);
    } else if ((type == Byte.class) || (type == byte.class)) {
      result = Byte.valueOf(value);
    } else if (type == BigInteger.class) {
      result = new BigInteger(value);
    } else if (type == BigDecimal.class) {
      result = new BigDecimal(value);
    } else {
      throw new IllegalArgumentException(type.getName());
    }
    return (N) result;
  }

  /**
   * Skips the current value from the {@link JsonParser}.
   *
   * @param json the {@link JsonParser}.
   */
  default void skipValue(JsonParser json) {

    skipValue(json, json.next());
  }

  /**
   * Skips the current value from the {@link JsonParser} when the current {@link Event} was already
   * {@link JsonParser#next() read} and is given.
   *
   * @param json the {@link JsonParser}.
   * @param e the {@link Event} that was already {@link JsonParser#next() read} before.
   */
  default void skipValue(JsonParser json, Event e) {

    Event event;
    switch (e) {
      case VALUE_FALSE:
      case VALUE_NULL:
      case VALUE_NUMBER:
      case VALUE_STRING:
      case VALUE_TRUE:
        break;
      case START_ARRAY:
        event = json.next();
        while (event != Event.END_ARRAY) {
          skipValue(json, event);
        }
        break;
      case START_OBJECT:
        event = json.next();
        while (event != Event.END_OBJECT) {
          expectEvent(event, Event.KEY_NAME);
          skipValue(json);
        }
        break;
      default :
        throw new IllegalCaseException(Event.class, e);
    }
  }

}