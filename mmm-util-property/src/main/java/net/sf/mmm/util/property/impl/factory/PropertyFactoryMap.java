/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl.factory;

import javax.inject.Named;

import javafx.collections.ObservableMap;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.MapProperty;
import net.sf.mmm.util.property.api.ReadableMapProperty;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.SetProperty;
import net.sf.mmm.util.property.api.WritableMapProperty;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.factory.PropertyFactory;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This is the implementation of {@link PropertyFactory} for {@link SetProperty}.
 *
 * @param <K> the generic type of the {@link java.util.Map.Entry#getKey() keys}.
 * @param <V> the generic type of the {@link java.util.Map.Entry#getValue() values}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@Named
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PropertyFactoryMap<K, V> extends AbstractPropertyFactory<ObservableMap<K, V>, MapProperty<K, V>> {

  @Override
  public Class<? extends ObservableMap<K, V>> getValueClass() {

    return (Class) ObservableMap.class;
  }

  @Override
  public Class<? extends ReadableProperty<ObservableMap<K, V>>> getReadableInterface() {

    return (Class) ReadableMapProperty.class;
  }

  @Override
  public Class<? extends WritableProperty<ObservableMap<K, V>>> getWritableInterface() {

    return (Class) WritableMapProperty.class;
  }

  @Override
  public Class<MapProperty<K, V>> getImplementationClass() {

    return (Class) MapProperty.class;
  }

  @Override
  public MapProperty<K, V> create(String name, GenericType<? extends ObservableMap<K, V>> valueType, Bean bean,
      AbstractValidator<? super ObservableMap<K, V>> validator) {

    return new MapProperty<>(name, valueType, bean, validator);
  }

}
