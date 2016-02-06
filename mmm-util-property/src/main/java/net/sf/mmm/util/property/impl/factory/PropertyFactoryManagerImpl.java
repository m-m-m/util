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

  private static PropertyFactoryManagerImpl INSTANCE;

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

  @SuppressWarnings("rawtypes")
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.type2factoryMap.isEmpty()) {
      registerFactory(new BooleanPropertyFactory());
      registerFactory(new DoublePropertyFactory());
      registerFactory(new FloatPropertyFactory());
      registerFactory(new IntegerPropertyFactory());
      registerFactory(new ListPropertyFactory());
      registerFactory(new LongPropertyFactory());
      registerFactory(new SetPropertyFactory());
      registerFactory(new StringPropertyFactory());
      registerFactory(new GenericPropertyFactory());
    }
  }

  @Override
  protected void doInitialized() {

    super.doInitialized();
    if (INSTANCE == null) {
      INSTANCE = this;
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