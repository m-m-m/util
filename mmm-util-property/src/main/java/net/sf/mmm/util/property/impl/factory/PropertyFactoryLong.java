/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl.factory;

import javax.inject.Named;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.factory.PropertyFactory;
import net.sf.mmm.util.property.api.lang.LongProperty;
import net.sf.mmm.util.property.api.lang.ReadableLongProperty;
import net.sf.mmm.util.property.api.lang.WritableLongProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This is the implementation of {@link PropertyFactory} for {@link LongProperty}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@Named
public class PropertyFactoryLong extends AbstractPropertyFactory<Number, LongProperty> {

  @Override
  public Class<Long> getValueClass() {

    return Long.class;
  }

  @Override
  public Class<? extends ReadableProperty<Number>> getReadableInterface() {

    return ReadableLongProperty.class;
  }

  @Override
  public Class<? extends WritableProperty<Number>> getWritableInterface() {

    return WritableLongProperty.class;
  }

  @Override
  public Class<LongProperty> getImplementationClass() {

    return LongProperty.class;
  }

  @Override
  public LongProperty create(String name, GenericType<? extends Number> valueType, Bean bean,
      AbstractValidator<? super Number> validator) {

    return new LongProperty(name, bean, validator);
  }

}
