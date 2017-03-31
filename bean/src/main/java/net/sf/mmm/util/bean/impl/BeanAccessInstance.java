/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.collection.base.ArrayIterator;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.lang.GenericProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * The abstract base implementation of {@link BeanAccess} for a regular {@link Bean} instance.
 *
 * @param <BEAN> the generic type of the intercepted {@link #getBean() bean}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public abstract class BeanAccessInstance<BEAN extends Bean> extends BeanAccessBase<BEAN> {

  private final BeanAccessPrototype<BEAN> prototype;

  private WritableProperty<?>[] properties;

  /**
   * The constructor.
   *
   * @param beanFactory the owning {@link BeanFactoryImpl}.
   * @param prototype the {@link BeanAccessPrototype}.
   */
  public BeanAccessInstance(BeanFactoryImpl beanFactory, BeanAccessPrototype<BEAN> prototype) {

    super(prototype.getBeanClass(), beanFactory, prototype.getInterfaces());
    this.prototype = prototype;
    this.properties = WritableProperty.NO_PROPERTIES;
  }

  @Override
  public BeanAccessPrototype<BEAN> getPrototype() {

    return this.prototype;
  }

  @Override
  public String getSimpleName() {

    return getPrototype().getSimpleName();
  }

  @Override
  public String getQualifiedName() {

    return getPrototype().getQualifiedName();
  }

  @Override
  public WritableProperty<?> getProperty(String propertyName) {

    BeanPrototypeProperty prototypeProperty = getPrototype().getPrototypeProperty(propertyName);
    if (prototypeProperty != null) {
      return getProperty(prototypeProperty, true);
    }
    return null;
  }

  @Override
  public Iterator<WritableProperty<?>> iterator() {

    createProperties();
    return new ArrayIterator<>(this.properties);
  }

  @Override
  public Iterable<String> getAliases() {

    return this.prototype.getAliases();
  }

  @Override
  public String getPropertyNameForAlias(String alias) {

    return this.prototype.getPropertyNameForAlias(alias);
  }

  @Override
  public Set<String> getPropertyNames() {

    return this.prototype.getPropertyNames();
  }

  @Override
  public Set<String> getDeclaredPropertyNames() {

    return this.prototype.getDeclaredPropertyNames();
  }

  @Override
  public String getPackageName() {

    return this.prototype.getPackageName();
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

  @SuppressWarnings("unchecked")
  @Override
  public <V> WritableProperty<V> createProperty(String name, GenericType<V> type) {

    if (isReadOnly()) {
      throw new UnsupportedOperationException();
    }
    getPrototype().createProperty(name, type);
    return (WritableProperty<V>) getProperty(name);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <V, PROPERTY extends WritableProperty<V>> PROPERTY createProperty(String name,
      GenericType<? extends V> valueType, Class<PROPERTY> propertyType) {

    requireWritable(name);
    getPrototype().createProperty(name, valueType, propertyType);
    return (PROPERTY) getProperty(name);
  }

  void createProperties() {

    Collection<BeanPrototypeProperty> prototypeProperties = this.prototype.getPrototypeProperties();
    int size = prototypeProperties.size();
    int length = this.properties.length;
    if (length == size) {
      return;
    }
    WritableProperty<?>[] newProperties = new WritableProperty<?>[size];
    System.arraycopy(this.properties, 0, newProperties, 0, length);
    for (BeanPrototypeProperty prototypeProperty : prototypeProperties) {
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
  public boolean isVirtual() {

    return this.prototype.isVirtual();
  }

  @Override
  public boolean isPrototype() {

    return false;
  }

  @Override
  public <V, PROPERTY extends WritableProperty<V>> void addPropertyValidator(WritableProperty<?> property,
      AbstractValidator<? super V> validator) {

    throw new UnsupportedOperationException("No prototype!");
  }

  @Override
  public <V, PROPERTY extends WritableProperty<V>> void addPropertyValidators(WritableProperty<?> property,
      Collection<AbstractValidator<? super V>> validators) {

    addPropertyValidator(property, null);
  }

}
