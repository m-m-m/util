/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.json.base;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import net.sf.mmm.util.component.base.AbstractComponent;
import net.sf.mmm.util.exception.api.ObjectMismatchException;
import net.sf.mmm.util.exception.api.ValueConvertException;
import net.sf.mmm.util.exception.api.WrongValueTypeException;
import net.sf.mmm.util.json.api.JsonSupport;
import net.sf.mmm.util.json.api.JsonUtil;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.lang.base.StringUtilImpl;
import net.sf.mmm.util.reflect.api.CollectionReflectionUtil;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.base.CollectionReflectionUtilImpl;

/**
 * The implementation of {@link JsonUtil}.
 *
 * @see #getInstance()
 *
 * @author hohwille
 * @since 7.4.0
 */
public class JsonUtilImpl extends AbstractComponent implements JsonUtil {

  private static JsonUtil instance;

  private StringUtil stringUtil;

  private CollectionReflectionUtil collectionReflectionUtil;

  /**
   * The constructor.
   */
  public JsonUtilImpl() {

    super();
  }

  /**
   * @param stringUtil is the {@link StringUtil} to {@link Inject}.
   */
  @Inject
  public void setStringUtil(StringUtil stringUtil) {

    this.stringUtil = stringUtil;
  }

  /**
   * @param collectionReflectionUtil is the {@link CollectionReflectionUtil} to {@link Inject}.
   */
  @Inject
  public void setCollectionReflectionUtil(CollectionReflectionUtil collectionReflectionUtil) {

    this.collectionReflectionUtil = collectionReflectionUtil;
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.stringUtil == null) {
      this.stringUtil = StringUtilImpl.getInstance();
    }
    if (this.collectionReflectionUtil == null) {
      this.collectionReflectionUtil = CollectionReflectionUtilImpl.getInstance();
    }
  }

  @Override
  protected void doInitialized() {

    super.doInitialized();
    if (instance == null) {
      instance = this;
    }
  }

  /**
   * This method gets the singleton instance of this {@link JsonUtilImpl}. <br>
   * <b>ATTENTION:</b><br>
   * Please prefer dependency-injection instead of using this method.
   *
   * @return the singleton instance.
   */
  public static JsonUtil getInstance() {

    if (instance == null) {
      synchronized (JsonUtilImpl.class) {
        if (instance == null) {
          JsonUtilImpl impl = new JsonUtilImpl();
          impl.initialize();
        }
      }
    }
    return instance;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public <T> T read(JsonParser json, GenericType<T> type, Event e) {

    if (e == Event.VALUE_NULL) {
      return null;
    }
    Class<?> valueClass = type.getAssignmentClass();
    Object result;
    if ((valueClass == String.class) || (valueClass == CharSequence.class)) {
      expectEvent(e, Event.VALUE_STRING);
      result = json.getString();
    } else if (Number.class.isAssignableFrom(valueClass)) {
      expectEvent(e, Event.VALUE_NUMBER);
      result = readNumber(json, (Class) valueClass);
    } else if ((valueClass == Boolean.class) || (valueClass == boolean.class)) {
      result = Boolean.valueOf(readBoolean(e));
    } else if (valueClass.isEnum()) {
      expectEvent(e, Event.VALUE_STRING);
      result = readEnum(json, (Class<? extends Enum>) valueClass);
    } else if (Collection.class.isAssignableFrom(valueClass)) {
      expectEvent(e, Event.START_ARRAY);
      result = readCollection(json, (GenericType<? extends Collection<?>>) type);
    } else if (valueClass.isArray()) {
      expectEvent(e, Event.START_ARRAY);
      GenericType<?> componentType = type.getComponentType();
      List<Object> list = new ArrayList<>();
      readCollection(json, (Collection) list, componentType);
      result = Array.newInstance(componentType.getRetrievalClass(), list.size());
      result = list.toArray((Object[]) result);
    } else if (Map.class.isAssignableFrom(valueClass)) {
      expectEvent(e, Event.START_OBJECT);
      result = readMap(json, (GenericType) type);
    } else {
      throw new IllegalArgumentException(type.toString());
    }
    return (T) result;
  }

  @Override
  public <E extends Enum<E>> E readEnum(JsonParser json, Class<E> enumType) {

    String value = json.getString();
    return convertEnum(value, enumType);
  }

  /**
   * @param <E> the generic type of the {@link Enum} to parse.
   * @param value the value to convert to the {@link Enum}.
   * @param enumType the {@link Class} reflecting the {@link Enum} to parse.
   * @return the parsed {@link Enum} constant.
   * @throws IllegalStateException if no such {@link Enum} constant exists.
   */
  protected <E extends Enum<E>> E convertEnum(String value, Class<E> enumType) {

    E[] constants = enumType.getEnumConstants();
    for (E e : constants) {
      if (e.name().equals(value)) {
        return e;
      }
    }
    String deCamlCased = this.stringUtil.fromCamlCase(value.toString(), '_').toUpperCase(Locale.US);
    for (E e : constants) {
      if (e.name().equals(deCamlCased)) {
        return e;
      }
    }
    throw new IllegalStateException(value.toString() + "@" + enumType.getName());
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public <C extends Collection<?>> C readCollection(JsonParser json, GenericType<C> type) {

    C collection = this.collectionReflectionUtil.create(type.getAssignmentClass());
    readCollection(json, (Collection) collection, type.getComponentType());
    return collection;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public <E> void readCollection(JsonParser json, Collection<E> collection, GenericType<E> type) {

    Collection c = collection;
    Event e = json.next();
    while (e != Event.END_ARRAY) {
      Object item = read(json, type, e);
      c.add(item);
      e = json.next();
    }
  }

  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public <K, V, M extends Map<K, V>> M readMap(JsonParser json, GenericType<M> type) {

    M map = this.collectionReflectionUtil.createMap(type.getAssignmentClass());
    readMap(json, map, (GenericType) type.getKeyType(), (GenericType) type.getComponentType());
    return map;
  }

  @Override
  public <K, V> void readMap(JsonParser json, Map<K, V> map, GenericType<K> keyType, GenericType<V> valueType) {

    Event e = json.next();
    while (e != Event.END_OBJECT) {
      expectEvent(e, Event.KEY_NAME);
      String keyString = json.getString();
      K key = convert(keyString, keyType);
      V value = read(json, valueType);
      map.put(key, value);
      e = json.next();
    }
  }

  /**
   * @param <V> the generic type of the value to parse.
   * @param value the value as {@link String}.
   * @param type the {@link GenericType} of the value to parse.
   * @return the given {@code value} parsed as the expected {@link GenericType}.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  protected <V> V convert(String value, GenericType<V> type) {

    Object result;
    Class<?> valueClass = type.getAssignmentClass();
    if ((type == null) || (valueClass == String.class) || (valueClass == CharSequence.class)) {
      result = value;
    } else if (Number.class.isAssignableFrom(valueClass)) {
      result = convertNumber(value, (Class) valueClass);
    } else if (valueClass.isEnum()) {
      result = convertEnum(value, (Class) valueClass);
    } else if ((valueClass == boolean.class) || (valueClass == Boolean.class)) {
      result = this.stringUtil.parseBoolean(value);
      if (result == null) {
        throw new ValueConvertException(value, valueClass);
      }
    } else {
      throw new IllegalArgumentException(type.toString());
    }
    return (V) result;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public void write(JsonGenerator json, String property, Object value) {

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
      write(json, property, (Iterable) value);
    } else if (value instanceof Map) {
      write(json, property, (Map) value);
    } else {
      String string = value.toString();
      write(json, property, string);
    }
  }

  @Override
  public void write(JsonGenerator json, String property, String value) {

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

  @Override
  public void write(JsonGenerator json, String property, boolean value) {

    if (property == null) {
      json.write(value);
    } else {
      json.write(property, value);
    }
  }

  @Override
  public void write(JsonGenerator json, String property, Number value) {

    if (value instanceof Long) {
      write(json, property, value.longValue());
    } else if ((value instanceof Integer) || (value instanceof Short) || (value instanceof Byte)) {
      write(json, property, value.intValue());
    } else if (value instanceof BigDecimal) {
      write(json, property, value);
    } else if (value instanceof BigInteger) {
      write(json, property, value);
    } else {
      if (value == null) {
        writeNull(json, property);
        return;
      }
      write(json, property, value.doubleValue());
    }
  }

  @Override
  public void write(JsonGenerator json, String property, long value) {

    if (property == null) {
      json.write(value);
    } else {
      json.write(property, value);
    }
  }

  @Override
  public void write(JsonGenerator json, String property, double value) {

    if (property == null) {
      json.write(value);
    } else {
      json.write(property, value);
    }
  }

  @Override
  public void write(JsonGenerator json, String property, BigDecimal value) {

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

  @Override
  public void write(JsonGenerator json, String property, BigInteger value) {

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

  @Override
  public void write(JsonGenerator json, String property, int value) {

    if (property == null) {
      json.write(value);
    } else {
      json.write(property, value);
    }
  }

  @Override
  public void writeNull(JsonGenerator json, String property) {

    if (property == null) {
      json.writeNull();
    }
  }

  @Override
  public void write(JsonGenerator json, String property, Iterable<?> value) {

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

  @Override
  public void write(JsonGenerator json, String property, Map<?, ?> value) {

    if (value == null) {
      writeNull(json, property);
      return;
    }
    if (property == null) {
      json.writeStartObject();
    } else {
      json.writeStartObject(property);
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

  @Override
  public void write(JsonGenerator json, String property, Object[] value) {

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

  @Override
  public void expectEvent(JsonParser json, Event event) {

    Event e = json.next();
    expectEvent(e, event);
  }

  @Override
  public void expectEvent(Event actual, Event expected) {

    if (actual != expected) {
      throw new ObjectMismatchException(actual, expected);
    }
  }

  @Override
  public <T> T read(JsonParser json, GenericType<T> type) {

    return read(json, type, json.next());
  }

  @Override
  public boolean readBoolean(Event e) {

    if (e == Event.VALUE_TRUE) {
      return true;
    } else if (e == Event.VALUE_FALSE) {
      return false;
    }
    throw new WrongValueTypeException(e, Boolean.class);
  }

  @Override
  public <N extends Number> N readNumber(JsonParser json, Class<N> type) {

    String value = json.getString();
    return convertNumber(value, type);
  }

  /**
   * @param <N> the generic type of the {@link Number} to parse.
   * @param value the numeric value as {@link String}.
   * @param type the {@link Class} reflecting the type of {@link Number} to parse.
   * @return the parsed value.
   */
  @SuppressWarnings("unchecked")
  protected <N extends Number> N convertNumber(String value, Class<N> type) {

    Number result;
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

  @Override
  public void skipValue(JsonParser json) {

    skipValue(json, json.next());
  }

  @Override
  public void skipValue(JsonParser json, Event e) {

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
        throw new IllegalStateException("Unhandled event: " + e);
    }
  }

}
