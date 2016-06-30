/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl.factory;

import javax.inject.Named;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.factory.PropertyFactory;
import net.sf.mmm.util.property.api.math.IntegerProperty;
import net.sf.mmm.util.property.api.math.ReadableIntegerProperty;
import net.sf.mmm.util.property.api.math.WritableIntegerProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This is the implementation of {@link PropertyFactory} for {@link IntegerProperty}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@Named
public class PropertyFactoryInteger extends AbstractPropertyFactory<Number, IntegerProperty> {

  @Override
  public Class<Integer> getValueClass() {

    return Integer.class;
  }

  @Override
  public Class<? extends ReadableProperty<Number>> getReadableInterface() {

    return ReadableIntegerProperty.class;
  }

  @Override
  public Class<? extends WritableProperty<Number>> getWritableInterface() {

    return WritableIntegerProperty.class;
  }

  @Override
  public Class<IntegerProperty> getImplementationClass() {

    return IntegerProperty.class;
  }

  @Override
  public IntegerProperty create(String name, GenericType<? extends Number> valueType, Bean bean,
      AbstractValidator<? super Number> validator) {

    return new IntegerProperty(name, bean, validator);
  }

}
