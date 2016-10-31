/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl.factory;

import javax.inject.Named;

import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.factory.PropertyFactory;
import net.sf.mmm.util.property.api.math.ReadableShortProperty;
import net.sf.mmm.util.property.api.math.ShortProperty;
import net.sf.mmm.util.property.api.math.WritableShortProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This is the implementation of {@link PropertyFactory} for {@link ShortProperty}.
 *
 * @author hohwille
 * @since 8.4.0
 */
@Named
public class PropertyFactoryShort extends AbstractPropertyFactory<Number, ShortProperty> {

  @Override
  public Class<Short> getValueClass() {

    return Short.class;
  }

  @Override
  public Class<? extends ReadableProperty<Number>> getReadableInterface() {

    return ReadableShortProperty.class;
  }

  @Override
  public Class<? extends WritableProperty<Number>> getWritableInterface() {

    return WritableShortProperty.class;
  }

  @Override
  public Class<ShortProperty> getImplementationClass() {

    return ShortProperty.class;
  }

  @Override
  public ShortProperty create(String name, GenericType<? extends Number> valueType, Object bean, AbstractValidator<? super Number> validator) {

    return new ShortProperty(name, bean, validator);
  }

}
