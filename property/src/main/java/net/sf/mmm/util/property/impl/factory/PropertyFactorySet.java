/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl.factory;

import javax.inject.Named;

import javafx.collections.ObservableSet;
import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.ReadableSetProperty;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.factory.PropertyFactory;
import net.sf.mmm.util.property.api.util.SetProperty;
import net.sf.mmm.util.property.api.util.WritableSetProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This is the implementation of {@link PropertyFactory} for {@link SetProperty}.
 *
 * @param <E> the generic type of the {@link ObservableSet#contains(Object) set elements}.
 *
 * @author hohwille
 * @since 8.4.0
 */
@Named
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PropertyFactorySet<E> extends AbstractPropertyFactory<ObservableSet<E>, SetProperty<E>> {

  @Override
  public Class<? extends ObservableSet<E>> getValueClass() {

    return (Class) ObservableSet.class;
  }

  @Override
  public Class<? extends ReadableProperty<ObservableSet<E>>> getReadableInterface() {

    return (Class) ReadableSetProperty.class;
  }

  @Override
  public Class<? extends WritableProperty<ObservableSet<E>>> getWritableInterface() {

    return (Class) WritableSetProperty.class;
  }

  @Override
  public Class<SetProperty<E>> getImplementationClass() {

    return (Class) SetProperty.class;
  }

  @Override
  public SetProperty<E> create(String name, GenericType<? extends ObservableSet<E>> valueType, Bean bean,
      AbstractValidator<? super ObservableSet<E>> validator) {

    return new SetProperty<>(name, valueType, bean, validator);
  }

}
