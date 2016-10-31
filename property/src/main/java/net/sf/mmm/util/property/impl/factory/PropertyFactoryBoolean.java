/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl.factory;

import javax.inject.Named;

import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.factory.PropertyFactory;
import net.sf.mmm.util.property.api.lang.BooleanProperty;
import net.sf.mmm.util.property.api.lang.ReadableBooleanProperty;
import net.sf.mmm.util.property.api.lang.WritableBooleanProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This is the implementation of {@link PropertyFactory} for {@link BooleanProperty}.
 *
 * @author hohwille
 * @since 8.4.0
 */
@Named
public class PropertyFactoryBoolean extends AbstractPropertyFactory<Boolean, BooleanProperty> {

  @Override
  public Class<? extends Boolean> getValueClass() {

    return Boolean.class;
  }

  @Override
  public Class<? extends ReadableProperty<Boolean>> getReadableInterface() {

    return ReadableBooleanProperty.class;
  }

  @Override
  public Class<? extends WritableProperty<Boolean>> getWritableInterface() {

    return WritableBooleanProperty.class;
  }

  @Override
  public Class<BooleanProperty> getImplementationClass() {

    return BooleanProperty.class;
  }

  @Override
  public BooleanProperty create(String name, GenericType<? extends Boolean> valueType, Object bean, AbstractValidator<? super Boolean> validator) {

    return new BooleanProperty(name, bean, validator);
  }

}
