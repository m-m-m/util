/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.exception.api.IllegalCaseException;
import net.sf.mmm.util.exception.api.ReadOnlyException;
import net.sf.mmm.util.json.api.JsonUtil;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.lang.BooleanProperty;
import net.sf.mmm.util.property.api.lang.GenericProperty;
import net.sf.mmm.util.property.api.lang.StringProperty;
import net.sf.mmm.util.property.api.math.BigDecimalProperty;
import net.sf.mmm.util.property.api.util.ListProperty;
import net.sf.mmm.util.property.api.util.MapProperty;

/**
 * This is the abstract base implementation of {@link BeanAccess}.
 *
 * @param <BEAN> the generic type of the intercepted {@link #getBean() bean}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class BeanAccessBase<BEAN extends Bean>
    implements InvocationHandler, BeanAccess, Iterable<WritableProperty<?>> {

  private final Class<BEAN> beanClass;

  private final BEAN bean;

  private final BeanFactoryImpl beanFactory;

  /**
   * The constructor.
   *
   * @param beanClass - see {@link #getBeanClass()}.
   * @param beanFactory the owning {@link BeanFactoryImpl}.
   */
  public BeanAccessBase(Class<BEAN> beanClass, BeanFactoryImpl beanFactory) {
    this(beanClass, beanFactory, beanClass);
  }

  /**
   * The constructor.
   *
   * @param beanClass - see {@link #getBeanClass()}.
   * @param beanFactory the owning {@link BeanFactoryImpl}.
   * @param interfaces an array with optional {@link Bean} interfaces to be implemented by the dynamic proxy.
   */
  public BeanAccessBase(Class<BEAN> beanClass, BeanFactoryImpl beanFactory, Class<?>... interfaces) {
    super();
    this.beanClass = beanClass;
    this.bean = beanFactory.createProxy(this, interfaces);
    this.beanFactory = beanFactory;
  }

  @Override
  public Class<BEAN> getBeanClass() {

    return this.beanClass;
  }

  /**
   * @return the {@link Bean} proxy instance to {@link #invoke(Object, Method, Object[]) intercept}.
   */
  public BEAN getBean() {

    return this.bean;
  }

  /**
   * @return the {@link BeanAccessPrototype}.
   */
  protected abstract BeanAccessPrototype<BEAN> getPrototype();

  @Override
  public Iterable<WritableProperty<?>> getProperties() {

    return this;
  }

  /**
   * Gets the {@link WritableProperty} for the given {@code index}.
   *
   * @param prototypeProperty is the {@link BeanPrototypeProperty}.
   * @param create - {@code true} if the property is required and shall be created if it {@link #isDynamic() does not
   *        already exist}, {@code false} otherwise.
   * @return the requested {@link WritableProperty}. May be {@code null}.
   */
  protected abstract WritableProperty<?> getProperty(BeanPrototypeProperty prototypeProperty, boolean create);

  @Override
  public boolean isReadOnly() {

    return false;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    Object result = null;
    BeanPrototypeOperation operation = getPrototype().getOperation(method);
    if (operation != null) {
      result = operation.invoke(this, args);
    }
    return result;
  }

  /**
   * @return the {@link JsonUtil} to use.
   */
  protected final JsonUtil getJsonUtil() {

    return this.beanFactory.getJsonUtil();
  }

  @SuppressWarnings("unchecked")
  static <BEAN extends Bean> BeanAccessBase<BEAN> get(BEAN bean) {

    return (BeanAccessBase<BEAN>) bean.access();
  }

  @Override
  public void toJson(JsonGenerator json) {

    json.write(PROPERTY_TYPE, getSimpleName());
    for (WritableProperty<?> property : getProperties()) {
      property.toJson(json);
    }
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public void fromJson(JsonParser json) {

    if (isReadOnly()) {
      throw new ReadOnlyException(getBeanClass().getSimpleName());
    }
    JsonUtil jsonUtil = getJsonUtil();
    while (json.hasNext()) {
      Event e = json.next();
      if (e == Event.KEY_NAME) {
        String propertyName = json.getString();
        if (PROPERTY_TYPE.equals(propertyName)) {
          e = json.next();
          assert (e == Event.VALUE_STRING);
          assert (getSimpleName().equals(json.getString()));
        } else {
          WritableProperty<?> property = getProperty(propertyName);
          if (property == null) {
            if (isDynamic()) {
              e = json.next();
              switch (e) {
                case VALUE_STRING:
                  StringProperty stringProperty = createProperty(propertyName, StringProperty.class);
                  stringProperty.setValue(json.getString());
                  break;
                case VALUE_NUMBER:
                  BigDecimalProperty numberProperty = createProperty(propertyName, BigDecimalProperty.class);
                  numberProperty.setValue(json.getBigDecimal());
                  break;
                case VALUE_TRUE:
                case VALUE_FALSE:
                  BooleanProperty booleanProperty = createProperty(propertyName, BooleanProperty.class);
                  booleanProperty.set(e == Event.VALUE_TRUE);
                  break;
                case VALUE_NULL:
                  // ignore null values...
                  break;
                case START_ARRAY:
                  ListProperty<?> listProperty = createProperty(propertyName, ListProperty.class);
                  ObservableList list = listProperty.getOrCreateValue();
                  while (e != Event.END_ARRAY) {
                    Object item = jsonUtil.fromJson(json, GenericProperty.TYPE_DEFAULT, e);
                    list.add(item);
                  }
                  break;
                case START_OBJECT:
                  MapProperty<?, ?> mapProperty = createProperty(propertyName, MapProperty.class);
                  ObservableMap map = mapProperty.getOrCreateValue();
                  while (e != Event.END_OBJECT) {
                    jsonUtil.expectJsonEvent(e, Event.KEY_NAME);
                    String key = json.getString();
                    Object item = jsonUtil.fromJson(json, GenericProperty.TYPE_DEFAULT, json.next());
                    map.put(key, item);
                  }
                  break;
                default :
                  throw new IllegalCaseException(Event.class, e);
              }
            }
          } else {
            property.fromJson(json);
          }
        }
      }
    }
  }

}
