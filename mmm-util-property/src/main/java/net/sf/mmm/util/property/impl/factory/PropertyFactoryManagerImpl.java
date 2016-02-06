/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.DuplicateObjectException;
import net.sf.mmm.util.exception.api.ObjectNotFoundException;
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

  private final Map<Class<?>, PropertyFactory<?, ?>> type2factoryMap;

  /**
   * The constructor.
   */
  public PropertyFactoryManagerImpl() {
    super();
    this.type2factoryMap = new HashMap<>();
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

    getInitializationState().requireNotInitilized();
    register(factory.getReadableInterface(), factory);
    register(factory.getWritableInterface(), factory);
    register(factory.getImplementationClass(), factory);
  }

  private void register(Class<?> type, PropertyFactory<?, ?> factory) {

    if (type == null) {
      return;
    }
    PropertyFactory<?, ?> old = this.type2factoryMap.put(type, factory);
    if (old != null) {
      throw new DuplicateObjectException(factory, type, old);
    }
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.type2factoryMap.isEmpty()) {
      registerDefaults();
    }
  }

  /**
   * {@link #registerFactory(PropertyFactory) Registers} the {@link PojoFactory factories} for the common default types.
   */
  @SuppressWarnings("rawtypes")
  protected void registerDefaults() {

    registerFactory(new StringPropertyFactory());
    registerFactory(new GenericPropertyFactory());
    registerFactory(new BooleanPropertyFactory());
    registerFactory(new DoublePropertyFactory());
    registerFactory(new FloatPropertyFactory());
    registerFactory(new IntegerPropertyFactory());
    registerFactory(new ListPropertyFactory());
    registerFactory(new LongPropertyFactory());
    registerFactory(new SetPropertyFactory());
    registerFactory(new MapPropertyFactory());
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
  public <V, PROPERTY extends ReadableProperty<V>> PropertyFactory<V, ? extends PROPERTY> getFactory(
      Class<PROPERTY> propertyType) {

    return (PropertyFactory) this.type2factoryMap.get(propertyType);
  }

  @Override
  public <V, PROPERTY extends ReadableProperty<V>> PROPERTY create(Class<PROPERTY> propertyType, String name,
      GenericType<V> valueType, Bean bean, AbstractValidator<? super V> validator) {

    PropertyFactory<V, ? extends PROPERTY> factory = getFactory(propertyType);
    if (factory == null) {
      throw new ObjectNotFoundException(PropertyFactory.class, propertyType);
    }
    return factory.create(name, valueType, bean, validator);
  }

}