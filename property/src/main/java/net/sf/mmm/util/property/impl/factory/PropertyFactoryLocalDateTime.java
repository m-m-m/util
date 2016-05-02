/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl.factory;

import java.time.LocalDateTime;

import javax.inject.Named;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.factory.PropertyFactory;
import net.sf.mmm.util.property.api.time.LocalDateTimeProperty;
import net.sf.mmm.util.property.api.time.ReadableLocalDateTimeProperty;
import net.sf.mmm.util.property.api.time.WritableLocalDateTimeProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This is the implementation of {@link PropertyFactory} for {@link LocalDateTimeProperty}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@Named
public class PropertyFactoryLocalDateTime extends AbstractPropertyFactory<LocalDateTime, LocalDateTimeProperty> {

  @Override
  public Class<LocalDateTime> getValueClass() {

    return LocalDateTime.class;
  }

  @Override
  public Class<? extends ReadableProperty<LocalDateTime>> getReadableInterface() {

    return ReadableLocalDateTimeProperty.class;
  }

  @Override
  public Class<? extends WritableProperty<LocalDateTime>> getWritableInterface() {

    return WritableLocalDateTimeProperty.class;
  }

  @Override
  public Class<LocalDateTimeProperty> getImplementationClass() {

    return LocalDateTimeProperty.class;
  }

  @Override
  public LocalDateTimeProperty create(String name, GenericType<? extends LocalDateTime> valueType, Bean bean,
      AbstractValidator<? super LocalDateTime> validator) {

    return new LocalDateTimeProperty(name, bean, validator);
  }

}
