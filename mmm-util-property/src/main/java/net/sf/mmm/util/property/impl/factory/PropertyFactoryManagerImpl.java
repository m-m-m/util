/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.pojo.api.PojoFactory;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.factory.PropertyFactory;
import net.sf.mmm.util.property.api.factory.PropertyFactoryManager;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This is the implementation of {@link PropertyFactoryManager}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@Named
public class PropertyFactoryManagerImpl extends AbstractLoggableComponent implements PropertyFactoryManager {

  private static PropertyFactoryManagerImpl instance;

  private final Map<Class<?>, PropertyFactory<?, ?>> propertyType2factoryMap;

  private final Map<Class<?>, PropertyFactory<?, ?>> valueType2factoryMap;

  /**
   * The constructor.
   */
  public PropertyFactoryManagerImpl() {
    super();
    this.propertyType2factoryMap = new HashMap<>();
    this.valueType2factoryMap = new HashMap<>();
  }

  /**
   * @param factories the {@link List} of {@link PropertyFactory} instances to {@link Inject}.
   */
  @Inject
  public void setFactories(List<PropertyFactory<?, ?>> factories) {

    for (PropertyFactory<?, ?> factory : factories) {
      registerFactory(factory);
    }
  }

  /**
   * @param factory the {@link PropertyFactory} to register.
   */
  public void registerFactory(PropertyFactory<?, ?> factory) {

    registerFactory(factory, false);
  }

  /**
   * @param factory the {@link PropertyFactory} to register.
   * @param allowOverride - {@code true} if the given {@link PropertyFactory} may override (replace) a previously
   *        {@link #registerFactory(PropertyFactory, boolean) registered} one.
   */
  protected void registerFactory(PropertyFactory<?, ?> factory, boolean allowOverride) {

    getInitializationState().requireNotInitilized();
    registerPropertyType(factory.getReadableInterface(), factory, allowOverride);
    registerPropertyType(factory.getWritableInterface(), factory, allowOverride);
    registerPropertyType(factory.getImplementationClass(), factory, allowOverride);
    registerValueType(factory.getValueClass(), factory, allowOverride);
  }

  private void registerValueType(Class<?> type, PropertyFactory<?, ?> factory, boolean allowOverride) {

    register(this.valueType2factoryMap, type, factory, allowOverride);
  }

  private void registerPropertyType(Class<?> type, PropertyFactory<?, ?> factory, boolean allowOverride) {

    register(this.propertyType2factoryMap, type, factory, allowOverride);
  }

  private static void register(Map<Class<?>, PropertyFactory<?, ?>> map, Class<?> type,
      PropertyFactory<?, ?> factory, boolean allowOverride) {

    if (type == null) {
      return;
    }
    PropertyFactory<?, ?> old = map.put(type, factory);
    if ((old != null) && !allowOverride) {
      throw new DuplicateObjectException(factory, type, old);
    }
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.propertyType2factoryMap.isEmpty()) {
      registerDefaults();
    }
  }

  /**
   * {@link #registerFactory(PropertyFactory) Registers} the {@link PojoFactory factories} for the common default types.
   */
  @SuppressWarnings("rawtypes")
  protected void registerDefaults() {

    registerFactory(new PropertyFactoryString());
    registerFactory(new PropertyFactoryGeneric());
    registerFactory(new PropertyFactoryBoolean());
    registerFactory(new PropertyFactoryDouble());
    registerFactory(new PropertyFactoryFloat());
    registerFactory(new PropertyFactoryInteger());
    registerFactory(new PropertyFactoryShort());
    registerFactory(new PropertyFactoryByte());
    registerFactory(new PropertyFactoryLong());
    registerFactory(new PropertyFactoryLocalDate());
    registerFactory(new PropertyFactoryLocalDateTime());
    registerFactory(new PropertyFactoryInstant());
    registerFactory(new PropertyFactoryList());
    registerFactory(new PropertyFactorySet());
    registerFactory(new PropertyFactoryMap());
  }

  /**
   * This method gets the singleton instance of this {@link PropertyFactoryManager}. <br>
   * <b>ATTENTION:</b><br>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
   *
   * @return the singleton instance.
   */
  public static PropertyFactoryManager getInstance() {

    if (instance == null) {
      synchronized (PropertyFactoryManagerImpl.class) {
        if (instance == null) {
          PropertyFactoryManagerImpl impl = new PropertyFactoryManagerImpl();
          impl.initialize();
        }
      }
    }
    return instance;
  }

  @Override
  protected void doInitialized() {

    super.doInitialized();
    if (instance == null) {
      instance = this;
    }
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public <V, PROPERTY extends ReadableProperty<V>> PropertyFactory<V, ? extends PROPERTY> getFactoryForPropertyType(
      Class<PROPERTY> propertyType) {

    return (PropertyFactory) this.propertyType2factoryMap.get(propertyType);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public <V> PropertyFactory<V, ? extends ReadableProperty<V>> getFactoryForValueType(Class<? extends V> valueType,
      boolean polymorphic) {

    PropertyFactory<?, ?> factory = this.valueType2factoryMap.get(valueType);
    if ((factory == null) && polymorphic) {
      for (Entry<Class<?>, PropertyFactory<?, ?>> entry : this.valueType2factoryMap.entrySet()) {
        if (entry.getKey().isAssignableFrom(valueType)) {
          factory = entry.getValue();
          break;
        }
      }
    }
    return (PropertyFactory) factory;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public <V, PROPERTY extends ReadableProperty<V>> PROPERTY create(Class<PROPERTY> propertyType,
      GenericType<V> valueType, boolean polymorphic, String name, Bean bean,
      AbstractValidator<? super V> validator) {

    Class<V> valueClass = null;
    if (valueType != null) {
      valueClass = valueType.getRetrievalClass();
    }
    PropertyFactory factory = getRequiredFactory(propertyType, valueClass, polymorphic);
    return (PROPERTY) factory.create(name, valueType, bean, validator);
  }

}