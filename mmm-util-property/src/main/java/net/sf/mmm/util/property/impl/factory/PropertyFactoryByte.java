/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl.factory;

import javax.inject.Named;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.ByteProperty;
import net.sf.mmm.util.property.api.ReadableByteProperty;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.WritableByteProperty;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.factory.PropertyFactory;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This is the implementation of {@link PropertyFactory} for {@link ByteProperty}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@Named
public class PropertyFactoryByte extends AbstractPropertyFactory<Number, ByteProperty> {

  @Override
  public Class<? extends Number> getValueClass() {

    return Short.class;
  }

  @Override
  public Class<? extends ReadableProperty<Number>> getReadableInterface() {

    return ReadableByteProperty.class;
  }

  @Override
  public Class<? extends WritableProperty<Number>> getWritableInterface() {

    return WritableByteProperty.class;
  }

  @Override
  public Class<ByteProperty> getImplementationClass() {

    return ByteProperty.class;
  }

  @Override
  public ByteProperty create(String name, GenericType<Number> valueType, Bean bean,
      AbstractValidator<? super Number> validator) {

    return new ByteProperty(name, bean, validator);
  }

}