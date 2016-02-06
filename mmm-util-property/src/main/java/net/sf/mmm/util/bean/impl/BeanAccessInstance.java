/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.util.Iterator;
import java.util.Map;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.collection.base.ArrayIterator;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.impl.GenericProperty;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * The abstract base implementation of {@link BeanAccess} for a regular {@link Bean} instance.
 *
 * @param <BEAN> the generic type of the intercepted {@link #getBean() bean}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class BeanAccessInstance<BEAN extends Bean> extends BeanAccessBase<BEAN> {

  private final BeanAccessPrototype<BEAN> prototype;

  private WritableProperty<?>[] properties;

  /**
   * The constructor.
   *
   * @param beanClass - see {@link #getBeanClass()}.
   * @param name - see {@link #getName()}.
   * @param beanFactory the owning {@link BeanFactoryImpl}.
   * @param prototype the {@link BeanAccessPrototype}.
   */
  public BeanAccessInstance(Class<BEAN> beanClass, String name, BeanFactoryImpl beanFactory,
      BeanAccessPrototype<BEAN> prototype) {

    super(beanClass, name, beanFactory);
    this.prototype = prototype;
    this.properties = WritableProperty.NO_PROPERTIES;
  }

  @Override
  public BeanAccessPrototype<BEAN> getPrototype() {

    return this.prototype;
  }

  @Override
  public Iterator<WritableProperty<?>> iterator() {

    createProperties();
    return new ArrayIterator<>(this.properties);
  }

  @Override
  protected WritableProperty<?> getProperty(BeanPrototypeProperty prototypeProperty, boolean required) {

    int length = this.properties.length;
    int index = prototypeProperty.getIndex();
    if (index < length) {
      return this.properties[index];
    } else if (required) {
      createProperties();
      return this.properties[index];
    }
    return null;
  }

  @Override
  public <V> WritableProperty<V> createProperty(String name, GenericType<V> type) {

    return null;
  }

  void createProperties() {

    Map<String, BeanPrototypeProperty> name2PropertyMap = getPrototype().getName2PropertyMap();
    int size = name2PropertyMap.size();
    int length = this.properties.length;
    if (length == size) {
      return;
    }
    WritableProperty<?>[] newProperties = new WritableProperty<?>[size];
    System.arraycopy(this.properties, 0, newProperties, 0, length);
    for (BeanPrototypeProperty prototypeProperty : name2PropertyMap.values()) {
      int i = prototypeProperty.getIndex();
      if (i >= length) {
        newProperties[i] = createProperty(prototypeProperty);
      }
    }
    this.properties = newProperties;
  }

  /**
   * @param prototypeProperty the {@link BeanPrototypeProperty}.
   * @return a new {@link WritableProperty} instance
   *         {@link GenericProperty#copy(net.sf.mmm.util.validation.base.AbstractValidator) copied} from the given
   *         {@link BeanPrototypeProperty}.
   */
  protected abstract WritableProperty<?> createProperty(BeanPrototypeProperty prototypeProperty);

  @Override
  public boolean isDynamic() {

    return this.prototype.isDynamic();
  }

  @Override
  public boolean isPrototype() {

    return false;
  }

}