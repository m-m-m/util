/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl.factory;

import java.time.Instant;

import javax.inject.Named;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.factory.PropertyFactory;
import net.sf.mmm.util.property.api.time.InstantProperty;
import net.sf.mmm.util.property.api.time.ReadableInstantProperty;
import net.sf.mmm.util.property.api.time.WritableInstantProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This is the implementation of {@link PropertyFactory} for {@link InstantProperty}.
 *
 * @author hohwille
 * @since 8.4.0
 */
@Named
public class PropertyFactoryInstant extends AbstractPropertyFactory<Instant, InstantProperty> {

  @Override
  public Class<Instant> getValueClass() {

    return Instant.class;
  }

  @Override
  public Class<? extends ReadableProperty<Instant>> getReadableInterface() {

    return ReadableInstantProperty.class;
  }

  @Override
  public Class<? extends WritableProperty<Instant>> getWritableInterface() {

    return WritableInstantProperty.class;
  }

  @Override
  public Class<InstantProperty> getImplementationClass() {

    return InstantProperty.class;
  }

  @Override
  public InstantProperty create(String name, GenericType<? extends Instant> valueType, Bean bean,
      AbstractValidator<? super Instant> validator) {

    return new InstantProperty(name, bean, validator);
  }

}
