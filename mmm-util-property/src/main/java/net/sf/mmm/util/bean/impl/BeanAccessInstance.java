/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.util.Iterator;
import java.util.Map;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.collection.base.ArrayIterator;
import net.sf.mmm.util.property.api.GenericProperty;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * The abstract base implementation of {@link BeanAccess} for a regular {@link Bean} instance.
 *
 * @author hohwille
 * @since 7.1.0
 */
abstract class BeanAccessInstance extends BeanAccessBase {

  private final BeanAccessPrototype<?> prototype;

  private GenericProperty<?>[] properties;

  /**
   * The constructor.
   *
   * @param prototype the {@link BeanAccessPrototype}.
   */
  public BeanAccessInstance(BeanAccessPrototype<?> prototype) {
    super();
    this.prototype = prototype;
    this.properties = GenericProperty.NO_PROPERTIES;
  }

  @Override
  public BeanAccessPrototype<?> getPrototype() {

    return this.prototype;
  }

  @Override
  public Iterator<GenericProperty<?>> iterator() {

    createProperties();
    return new ArrayIterator<>(this.properties);
  }

  @Override
  protected GenericProperty<?> getProperty(BeanPrototypeProperty prototypeProperty, boolean required) {

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
  public <V> GenericProperty<V> createProperty(String name, GenericType<V> type) {

    return null;
  }

  void createProperties() {

    Map<String, BeanPrototypeProperty> name2PropertyMap = getPrototype().getName2PropertyMap();
    int size = name2PropertyMap.size();
    int length = this.properties.length;
    if (length == size) {
      return;
    }
    GenericProperty<?>[] newProperties = new GenericProperty<?>[size];
    System.arraycopy(this.properties, 0, newProperties, 0, length);
    for (BeanPrototypeProperty prototypeProperty : name2PropertyMap.values()) {
      int i = prototypeProperty.getIndex();
      if (i >= length) {
        newProperties[i] = createProperty(prototypeProperty);
      }
    }
    this.properties = newProperties;
  }

  protected abstract GenericProperty<?> createProperty(BeanPrototypeProperty prototypeProperty);

  @Override
  public boolean isDynamic() {

    return this.prototype.isDynamic();
  }

  @Override
  public boolean isPrototype() {

    return false;
  }

}