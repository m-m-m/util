/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl.factory;

import javax.inject.Named;

import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.factory.PropertyFactory;
import net.sf.mmm.util.property.api.math.FloatProperty;
import net.sf.mmm.util.property.api.math.ReadableFloatProperty;
import net.sf.mmm.util.property.api.math.WritableFloatProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This is the implementation of {@link PropertyFactory} for {@link FloatProperty}.
 *
 * @author hohwille
 * @since 8.4.0
 */
@Named
public class PropertyFactoryFloat extends AbstractPropertyFactory<Number, FloatProperty> {

  @Override
  public Class<Float> getValueClass() {

    return Float.class;
  }

  @Override
  public Class<? extends ReadableProperty<Number>> getReadableInterface() {

    return ReadableFloatProperty.class;
  }

  @Override
  public Class<? extends WritableProperty<Number>> getWritableInterface() {

    return WritableFloatProperty.class;
  }

  @Override
  public Class<FloatProperty> getImplementationClass() {

    return FloatProperty.class;
  }

  @Override
  public FloatProperty create(String name, GenericType<? extends Number> valueType, Object bean, AbstractValidator<? super Number> validator) {

    return new FloatProperty(name, bean, validator);
  }

}
