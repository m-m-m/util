/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.exception.api.ReadOnlyException;
import net.sf.mmm.util.property.api.GenericProperty;
import net.sf.mmm.util.property.impl.GenericPropertyImpl;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the implementation of {@link BeanAccess} for the {@link BeanFactory#getPrototype(Class) prototype} of a
 * {@link Bean}.
 *
 * @param <BEAN> the generic type of the {@link Bean}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class BeanAccessPrototype<BEAN extends Bean> extends BeanAccessBase {

  private final Map<String, BeanPrototypeProperty> name2PropertyMap;

  private final Map<Method, BeanPrototypeOperation> method2OperationMap;

  private final Class<BEAN> beanType;

  private final boolean dynamic;

  private final BeanFactoryImpl beanFactory;

  /**
   * The constructor.
   *
   * @param type - see {@link #getBeanType()}.
   * @param dynamic - see {@link #isDynamic()}.
   * @param beanFactory the owning {@link BeanFactoryImpl}.
   */
  public BeanAccessPrototype(Class<BEAN> type, boolean dynamic, BeanFactoryImpl beanFactory) {
    super();
    this.beanType = type;
    this.name2PropertyMap = new HashMap<>();
    this.method2OperationMap = new HashMap<>();
    this.dynamic = dynamic;
    this.beanFactory = beanFactory;
  }

  @Override
  protected BeanAccessPrototype<?> getPrototype() {

    return this;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public Iterator<GenericProperty<?>> iterator() {

    return (Iterator) this.name2PropertyMap.values().stream().map(x -> x.getProperty()).iterator();
  }

  @Override
  public <V> GenericProperty<V> createProperty(String name, GenericType<V> propertyType) {

    if (!this.dynamic) {
      throw new ReadOnlyException(this.beanType.getSimpleName(), "access.properties");
    }
    BeanPrototypeProperty prototypeProperty = this.name2PropertyMap.get(name);
    if (prototypeProperty != null) {
      throw new DuplicateObjectException(this, name, prototypeProperty);
    }
    GenericPropertyImpl<V> property = this.beanFactory.createProperty(name, propertyType, getBean());
    addProperty(property);
    return property;
  }

  @Override
  protected GenericProperty<?> getProperty(BeanPrototypeProperty prototypeProperty, boolean required) {

    return prototypeProperty.getProperty();
  }

  /**
   * @return the method2operationMap
   */
  protected Map<Method, BeanPrototypeOperation> getMethod2OperationMap() {

    return this.method2OperationMap;
  }

  /**
   * @return the name2propertyMap
   */
  protected Map<String, BeanPrototypeProperty> getName2PropertyMap() {

    return this.name2PropertyMap;
  }

  /**
   * @param property the {@link GenericPropertyImpl} to add.
   */
  protected void addProperty(GenericPropertyImpl<?> property) {

    BeanPrototypeProperty prototypeProperty = new BeanPrototypeProperty(property, this.name2PropertyMap.size());
    this.name2PropertyMap.put(property.getName(), prototypeProperty);
  }

  /**
   * @return the type
   */
  public Class<BEAN> getBeanType() {

    return this.beanType;
  }

  @Override
  public boolean isDynamic() {

    return this.dynamic;
  }

  @Override
  public boolean isPrototype() {

    return true;
  }

}