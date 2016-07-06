/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.json.base;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.IllegalCaseException;
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
 * @since 8.0.0
 */
public class JsonUtilImpl extends AbstractLoggableComponent implements JsonUtil {

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
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
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
    if (valueClass == String.class) {
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
      result = readMap(json, (GenericType<Map<?, ?>>) type);
    } else {
      throw new IllegalArgumentException(type.toString());
    }
    return (T) result;
  }

  @Override
  public <E extends Enum<E>> E readEnum(JsonParser json, Class<E> enumType) throws IllegalCaseException {

    String value = json.getString();
    E[] constants = enumType.getEnumConstants();
    for (E e : constants) {
      if (e.name().equals(value)) {
        return e;
      }
    }
    String deCamlCased = this.stringUtil.fromCamlCase(value.toString(), '_');
    for (E e : constants) {
      if (e.name().equals(deCamlCased)) {
        return e;
      }
    }
    throw new IllegalCaseException(value.toString() + "@" + enumType.getName());
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
  public <M extends Map<?, ?>> M readMap(JsonParser json, GenericType<M> type) {

    // TODO Auto-generated method stub
    return null;
  }

}
