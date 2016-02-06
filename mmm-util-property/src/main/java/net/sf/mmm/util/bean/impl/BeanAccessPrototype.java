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
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.base.AbstractProperty;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the implementation of {@link BeanAccess} for the {@link BeanFactory#createPrototype(Class) prototype} of a
 * {@link Bean}.
 *
 * @param <BEAN> the generic type of the {@link Bean}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class BeanAccessPrototype<BEAN extends Bean> extends BeanAccessBase<BEAN> {

  private final Map<String, BeanPrototypeProperty> name2PropertyMap;

  private final Map<Method, BeanPrototypeOperation> method2OperationMap;

  private final Class<BEAN> beanType;

  private final boolean dynamic;

  private final BeanFactoryImpl beanFactory;

  /**
   * The constructor.
   *
   * @param beanClass - see {@link #getBeanClass()}.
   * @param name - see {@link #getName()}.
   * @param beanFactory the owning {@link BeanFactoryImpl}.
   */
  public BeanAccessPrototype(Class<BEAN> beanClass, String name, BeanFactoryImpl beanFactory) {
    super(beanClass, name, beanFactory);
    this.beanType = beanClass;
    this.name2PropertyMap = new HashMap<>();
    this.method2OperationMap = new HashMap<>();
    this.dynamic = false;
    this.beanFactory = beanFactory;
  }

  /**
   * The constructor.
   *
   * @param master the {@link BeanAccessPrototype} to copy.
   * @param dynamic - see {@link #isDynamic()}.
   */
  public BeanAccessPrototype(BeanAccessPrototype<BEAN> master, boolean dynamic) {

    super(master.beanType, master.getName(), master.beanFactory);
    this.beanType = master.beanType;
    this.name2PropertyMap = new HashMap<>(master.name2PropertyMap.size());
    for (BeanPrototypeProperty prototypeProperty : master.name2PropertyMap.values()) {
      AbstractProperty<?> property = prototypeProperty.getProperty();
      BeanPrototypeProperty copy = new BeanPrototypeProperty(property.copy(getBean()),
          prototypeProperty.getIndex());
      this.name2PropertyMap.put(property.getName(), copy);
    }
    this.method2OperationMap = master.method2OperationMap;
    this.dynamic = dynamic;
    this.beanFactory = master.beanFactory;
  }

  @Override
  protected BeanAccessPrototype<BEAN> getPrototype() {

    return this;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public Iterator<WritableProperty<?>> iterator() {

    return (Iterator) this.name2PropertyMap.values().stream().map(x -> x.getProperty()).iterator();
  }

  @Override
  public <V> WritableProperty<V> createProperty(String name, GenericType<V> propertyType) {

    if (!this.dynamic) {
      throw new ReadOnlyException(this.beanType.getSimpleName(), "access.properties");
    }
    BeanPrototypeProperty prototypeProperty = this.name2PropertyMap.get(name);
    if (prototypeProperty != null) {
      throw new DuplicateObjectException(this, name, prototypeProperty);
    }
    AbstractProperty<V> property = this.beanFactory.createProperty(name, propertyType, getBean());
    addProperty(property);
    return property;
  }

  @Override
  protected WritableProperty<?> getProperty(BeanPrototypeProperty prototypeProperty, boolean required) {

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
   * @param property the {@link WritableProperty} to add.
   */
  protected void addProperty(AbstractProperty<?> property) {

    BeanPrototypeProperty prototypeProperty = new BeanPrototypeProperty(property, this.name2PropertyMap.size());
    this.name2PropertyMap.put(property.getName(), prototypeProperty);
  }

  /**
   * @return the type
   */
  @Override
  public Class<BEAN> getBeanClass() {

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