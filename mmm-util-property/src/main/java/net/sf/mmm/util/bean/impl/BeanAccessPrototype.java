/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.exception.api.ReadOnlyException;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyNotFoundException;
import net.sf.mmm.util.property.api.AbstractProperty;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ComposedValidator;

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

  private static final Logger LOG = LoggerFactory.getLogger(BeanAccessPrototype.class);

  private final Map<String, BeanPrototypeProperty> name2PropertyMap;

  private final Map<Method, BeanPrototypeOperation> method2OperationMap;

  private final Map<String, String> aliasMap;

  private final Collection<String> aliases;

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
    this.name2PropertyMap = new HashMap<>();
    this.method2OperationMap = new HashMap<>();
    this.aliasMap = new HashMap<>();
    this.aliases = Collections.unmodifiableSet(this.aliasMap.keySet());
    this.dynamic = false;
    this.beanFactory = beanFactory;
  }

  /**
   * The constructor.
   *
   * @param master the {@link BeanAccessPrototype} to copy.
   * @param name - see {@link #getName()}.
   * @param dynamic - see {@link #isDynamic()}.
   */
  public BeanAccessPrototype(BeanAccessPrototype<BEAN> master, boolean dynamic, String name) {

    super(master.getBeanClass(), name, master.beanFactory);
    this.name2PropertyMap = new HashMap<>(master.name2PropertyMap.size());
    this.aliasMap = master.aliasMap;
    this.aliases = master.aliases;
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

  @SuppressWarnings("unchecked")
  @Override
  public <V, PROPERTY extends WritableProperty<V>> PROPERTY createProperty(String name, GenericType<V> valueType,
      Class<PROPERTY> propertyType) {

    if (!this.dynamic) {
      throw new ReadOnlyException(getBeanClass().getSimpleName(), "access.properties");
    }
    BeanPrototypeProperty prototypeProperty = this.name2PropertyMap.get(name);
    if (prototypeProperty != null) {
      throw new DuplicateObjectException(this, name, prototypeProperty);
    }
    AbstractProperty<?> property = this.beanFactory.createProperty(name, valueType, getBean(), propertyType);
    addProperty(property);
    return (PROPERTY) property;
  }

  @Override
  protected WritableProperty<?> getProperty(BeanPrototypeProperty prototypeProperty, boolean required) {

    return prototypeProperty.getProperty();
  }

  /**
   * @see #getOperation(Method)
   * @param operation the {@link BeanPrototypeOperation} to register.
   */
  protected void registerOperation(BeanPrototypeOperation operation) {

    this.method2OperationMap.put(operation.getMethod(), operation);
  }

  /**
   * @param method the {@link Method} to lookup.
   * @return the {@link BeanPrototypeOperation} for the given {@link Method} or {@code null} if not defined.
   */
  protected BeanPrototypeOperation getOperation(Method method) {

    return this.method2OperationMap.get(method);
  }

  /**
   * @see #getPropertyNameForAlias(String)
   *
   * @param alias the new {@link #getPropertyNameForAlias(String) alias} to register.
   * @param propertyName the {@link WritableProperty#getName() property name} to map to.
   */
  protected void registerAlias(String alias, String propertyName) {

    String old = this.aliasMap.put(alias, propertyName);
    if ((old != null) && !propertyName.equals(old)) {
      LOG.warn("Replaced alias {} to {} with {}.", alias, old, propertyName);
    }
  }

  @Override
  public WritableProperty<?> getProperty(String propertyName) {

    BeanPrototypeProperty prototypeProperty = getPrototypeProperty(propertyName);
    if (prototypeProperty != null) {
      return prototypeProperty.getProperty();
    }
    return null;
  }

  /**
   * @param name the {@link WritableProperty#getName() property name}.
   * @return the corresponding {@link BeanPrototypeProperty} or {@code null} if not defined.
   */
  protected BeanPrototypeProperty getPrototypeProperty(String name) {

    BeanPrototypeProperty result = this.name2PropertyMap.get(name);
    if (result == null) {
      String propertyName = this.aliasMap.get(name);
      if (propertyName != null) {
        result = this.name2PropertyMap.get(propertyName);
      }
    }
    return result;
  }

  /**
   * @return the current number of {@link #getProperties() properties} defined in this prototype.
   */
  protected int getPropertyCount() {

    return this.name2PropertyMap.size();
  }

  /**
   * @return a {@link Collection} with all {@link BeanPrototypeProperty properties} defined in this prototype.
   */
  protected Collection<BeanPrototypeProperty> getPrototypeProperties() {

    return this.name2PropertyMap.values();
  }

  /**
   * @param property the {@link WritableProperty} to add.
   */
  protected void addProperty(AbstractProperty<?> property) {

    BeanPrototypeProperty prototypeProperty = new BeanPrototypeProperty(property, this.name2PropertyMap.size());
    this.name2PropertyMap.put(property.getName(), prototypeProperty);
  }

  @Override
  public String getPropertyNameForAlias(String alias) {

    return this.aliasMap.get(alias);
  }

  @Override
  public Iterable<String> getAliases() {

    return this.aliases;
  }

  @Override
  public boolean isDynamic() {

    return this.dynamic;
  }

  @Override
  public boolean isPrototype() {

    return true;
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public <V, PROPERTY extends WritableProperty<V>> void addPropertyValidator(WritableProperty<?> property,
      AbstractValidator<? super V>... validators) {

    Objects.requireNonNull(property, "property");
    Objects.requireNonNull(validators, "validators");
    if (!this.dynamic) {
      throw new IllegalStateException("Bean not dynamic");
    }
    String propertyName = property.getName();
    BeanPrototypeProperty prototypeProperty = this.name2PropertyMap.get(propertyName);
    if (prototypeProperty == null) {
      throw new PojoPropertyNotFoundException(getBeanClass(), propertyName);
    }
    if (prototypeProperty.getProperty() != property) {
      throw new IllegalArgumentException("Given property '" + propertyName + "' is not owned by this bean!");
    }
    AbstractProperty<V> p = (AbstractProperty<V>) property;
    AbstractValidator<? super V> currentValidator = p.getValidator();
    Collection<AbstractValidator<? super V>> validatorsToAdd = new ArrayList<>(validators.length);
    for (int i = 0; i < validators.length; i++) {
      AbstractValidator<? super V> validator = validators[i];
      if (validator == null) {
        Objects.requireNonNull(validator, "validators[" + i + "]");
      }
      addValidator(currentValidator, validatorsToAdd, validator);
    }
    if (validatorsToAdd.isEmpty()) {
      return;
    }
    AbstractValidator<? super V> newValidator = ((AbstractValidator) currentValidator)
        .append(validatorsToAdd.toArray(new AbstractValidator[validatorsToAdd.size()]));
    AbstractProperty<V> newProperty = ((AbstractProperty<V>) property).copy(newValidator);
    prototypeProperty.setProperty(newProperty);
    LOG.info("Added validator to property {} of protoype for bean {}", propertyName, getBeanClass());
  }

  private static <V> void addValidator(AbstractValidator<? super V> currentValidator,
      Collection<AbstractValidator<? super V>> validatorsToAdd, AbstractValidator<? super V> validator) {

    if (!currentValidator.contains(validator)) {
      if (validator.getClass() == ComposedValidator.class) {
        ComposedValidator<? super V> composedValidator = (ComposedValidator<? super V>) validator;
        int count = composedValidator.getValidatorCount();
        for (int i = 0; i < count; i++) {
          addValidator(currentValidator, validatorsToAdd, composedValidator.getValidator(i));
        }
      } else {
        validatorsToAdd.add(validator);
      }
    }
  }

}