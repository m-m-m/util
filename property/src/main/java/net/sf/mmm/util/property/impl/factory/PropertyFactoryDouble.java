/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl.factory;

import javax.inject.Named;

import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.factory.PropertyFactory;
import net.sf.mmm.util.property.api.math.DoubleProperty;
import net.sf.mmm.util.property.api.math.ReadableDoubleProperty;
import net.sf.mmm.util.property.api.math.WritableDoubleProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This is the implementation of {@link PropertyFactory} for {@link DoubleProperty}.
 *
 * @author hohwille
 * @since 8.5.0
 */
@Named
public class PropertyFactoryDouble extends AbstractPropertyFactory<Number, DoubleProperty> {

  @Override
  public Class<Double> getValueClass() {

    return Double.class;
  }

  @Override
  public Class<? extends ReadableProperty<Number>> getReadableInterface() {

    return ReadableDoubleProperty.class;
  }

  @Override
  public Class<? extends WritableProperty<Number>> getWritableInterface() {

    return WritableDoubleProperty.class;
  }

  @Override
  public Class<DoubleProperty> getImplementationClass() {

    return DoubleProperty.class;
  }

  @Override
  public DoubleProperty create(String name, GenericType<? extends Number> valueType, Object bean, AbstractValidator<? super Number> validator) {

    return new DoubleProperty(name, bean, validator);
  }

}
