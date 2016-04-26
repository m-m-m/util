/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
public abstract class BeanAccessPrototype<BEAN extends Bean> extends BeanAccessBase<BEAN> {

  private static final Logger LOG = LoggerFactory.getLogger(BeanAccessPrototype.class);

  private final String simpleName;

  private final String qualifiedName;

  private final String packageName;

  private final Map<String, BeanPrototypeProperty> name2PropertyMap;

  private final Map<Method, BeanPrototypeOperation> method2OperationMap;

  private final Map<String, String> aliasMap;

  private final Collection<String> aliases;

  private final Set<String> propertyNames;

  private final Set<String> declaredPropertyNamesMutable;

  private final Set<String> declaredPropertyNames;

  private final BeanFactoryImpl beanFactory;

  private final Class<?>[] interfaces;

  /**
   * The constructor.
   *
   * @param beanClass - see {@link #getBeanClass()}.
   * @param qualifiedName - see {@link #getQualifiedName()}.
   * @param beanFactory the owning {@link BeanFactoryImpl}.
   */
  protected BeanAccessPrototype(Class<BEAN> beanClass, String qualifiedName, BeanFactoryImpl beanFactory) {
    this(beanClass, qualifiedName, beanFactory, null, false, beanClass);
  }

  /**
   * The constructor.
   *
   * @param master the {@link BeanAccessPrototype} to copy.
   * @param qualifiedName - see {@link #getQualifiedName()}.
   * @param dynamic - see {@link #isDynamic()}.
   * @param interfaces - the interfaces to be implemented by the {@link #getBean() dynamic proxy}.
   */
  protected BeanAccessPrototype(BeanAccessPrototype<BEAN> master, String qualifiedName, boolean dynamic,
      Class<?>... interfaces) {

    this(master.getBeanClass(), qualifiedName, master.beanFactory, master, dynamic, interfaces);
  }

  private BeanAccessPrototype(Class<BEAN> beanClass, String qualifiedName, BeanFactoryImpl beanFactory,
      BeanAccessPrototype<BEAN> master, boolean dynamic, Class<?>... interfaces) {

    super(beanClass, beanFactory);

    this.qualifiedName = qualifiedName;
    int lastDot = qualifiedName.lastIndexOf('.');
    if (lastDot < 0) {
      this.simpleName = qualifiedName;
      this.packageName = "";
    } else {
      this.simpleName = qualifiedName.substring(lastDot + 1);
      this.packageName = qualifiedName.substring(0, lastDot);
    }
    if (master == null) {
      this.name2PropertyMap = new HashMap<>();
      this.aliasMap = new HashMap<>();
      this.aliases = Collections.unmodifiableSet(this.aliasMap.keySet());
      this.method2OperationMap = new HashMap<>();
      this.declaredPropertyNamesMutable = new HashSet<>();
    } else {
      if (dynamic) {
        this.name2PropertyMap = new ConcurrentHashMap<>(master.name2PropertyMap.size());
      } else {
        this.name2PropertyMap = new HashMap<>(master.name2PropertyMap.size());
      }
      this.declaredPropertyNamesMutable = new HashSet<>(master.declaredPropertyNamesMutable);
      this.aliasMap = master.aliasMap;
      this.aliases = master.aliases;
      // copy properties from master to new prototype
      for (BeanPrototypeProperty prototypeProperty : master.name2PropertyMap.values()) {
        AbstractProperty<?> property = prototypeProperty.getProperty();
        BeanPrototypeProperty copy = new BeanPrototypeProperty(property.copy(getBean()),
            prototypeProperty.getIndex());
        this.name2PropertyMap.put(property.getName(), copy);
      }
      this.method2OperationMap = new HashMap<>(master.method2OperationMap.size());
      for (BeanPrototypeOperation prototypeOperation : master.method2OperationMap.values()) {
        BeanPrototypeOperation copy = prototypeOperation.forPrototype(this);
        this.method2OperationMap.put(copy.getMethod(), copy);
      }
    }
    this.propertyNames = Collections.unmodifiableSet(this.name2PropertyMap.keySet());
    this.declaredPropertyNames = Collections.unmodifiableSet(this.declaredPropertyNamesMutable);
    this.beanFactory = beanFactory;
    this.interfaces = interfaces;
  }

  /**
   * @return a new {@link Map} instance such as {@link HashMap} or {@link ConcurrentHashMap} for the properties.
   */
  protected Map<String, BeanPrototypeProperty> createPropertyMap() {

    return new HashMap<>();
  }

  @Override
  protected BeanAccessPrototype<BEAN> getPrototype() {

    return this;
  }

  Class<?>[] getInterfaces() {

    return this.interfaces;
  }

  @Override
  public String getSimpleName() {

    return this.simpleName;
  }

  @Override
  public String getQualifiedName() {

    return this.qualifiedName;
  }

  @Override
  public String getPackageName() {

    return this.packageName;
  }

  @Override
  public Set<String> getPropertyNames() {

    return this.propertyNames;
  }

  @Override
  public Set<String> getDeclaredPropertyNames() {

    return this.declaredPropertyNames;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public Iterator<WritableProperty<?>> iterator() {

    return (Iterator) this.name2PropertyMap.values().stream().map(x -> x.getProperty()).iterator();
  }

  @SuppressWarnings("unchecked")
  @Override
  public <V, PROPERTY extends WritableProperty<V>> PROPERTY createProperty(String name,
      GenericType<? extends V> valueType, Class<PROPERTY> propertyType) {

    if (!isDynamic()) {
      throw new ReadOnlyException(getBeanClass().getSimpleName(), "access.properties");
    }
    AbstractProperty<?> property = this.beanFactory.createProperty(name, valueType, getBean(), propertyType);
    addProperty(property, true);
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
   * @param declared - {@code true} in case of {@link #getDeclaredPropertyNames() declared property}, {@code false}
   *        otherwise (inherited).
   */
  protected void addProperty(AbstractProperty<?> property, boolean declared) {

    addPropertyInternal(property, declared);
  }

  /**
   * Internal implementation of {@link #addProperty(AbstractProperty, boolean)} (e.g. recursive and without locking).
   *
   * @param property the {@link WritableProperty} to add.
   * @param declared - {@code true} in case of {@link #getDeclaredPropertyNames() declared property}, {@code false}
   *        otherwise (inherited).
   */
  protected void addPropertyInternal(AbstractProperty<?> property, boolean declared) {

    String name = property.getName();
    this.name2PropertyMap.compute(name, (k, v) -> {
      if (v != null) {
        throw new DuplicateObjectException(property, name, v);
      }
      return new BeanPrototypeProperty(property, this.name2PropertyMap.size());
    });
    char first = name.charAt(0);
    char lower = Character.toLowerCase(first);
    if (lower != first) {
      this.aliasMap.put(lower + name.substring(1), name);
    }

    if (declared) {
      this.declaredPropertyNamesMutable.add(name);
    }
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
  public boolean isPrototype() {

    return true;
  }

  @Override
  public boolean isVirtual() {

    return false;
  }

  @Override
  public <V, PROPERTY extends WritableProperty<V>> void addPropertyValidator(WritableProperty<?> property,
      AbstractValidator<? super V> validator) {

    addPropertyValidators(property, Arrays.asList(validator));
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public <V, PROPERTY extends WritableProperty<V>> void addPropertyValidators(WritableProperty<?> property,
      Collection<AbstractValidator<? super V>> validators) {

    Objects.requireNonNull(property, "property");
    Objects.requireNonNull(validators, "validators");
    if (!isDynamic()) {
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
    Collection<AbstractValidator<? super V>> validatorsToAdd = new ArrayList<>(validators.size());
    int i = 0;
    for (AbstractValidator<? super V> validator : validators) {
      if (validator == null) {
        Objects.requireNonNull(validator, "validators[" + i + "]");
      }
      addValidator(currentValidator, validatorsToAdd, validator);
      i++;
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

  @SuppressWarnings("unchecked")
  static <BEAN extends Bean> BeanAccessPrototype<BEAN> get(BEAN bean) {

    return ((BeanAccessBase<BEAN>) bean.access()).getPrototype();
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
