/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl.factory;

import javax.inject.Named;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.GenericProperty;
import net.sf.mmm.util.property.api.LongProperty;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.factory.PropertyFactory;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This is the implementation of {@link PropertyFactory} for {@link LongProperty}.
 *
 * @param <V> is the generic type of the {@link GenericProperty#getValue() value}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@Named
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GenericPropertyFactory<V> extends AbstractPropertyFactory<V, GenericProperty<V>> {

  @Override
  public Class<? extends ReadableProperty<V>> getReadableInterface() {

    return (Class) ReadableProperty.class;
  }

  @Override
  public Class<? extends WritableProperty<V>> getWritableInterface() {

    return (Class) WritableProperty.class;
  }

  @Override
  public Class<GenericProperty<V>> getImplementationClass() {

    return (Class) GenericProperty.class;
  }

  @Override
  public GenericProperty<V> create(String name, GenericType<V> valueType, Bean bean,
      AbstractValidator<? super V> validator) {

    return new GenericProperty<>(name, valueType, bean, validator);
  }

}
