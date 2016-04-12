/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl.factory;

import javax.inject.Named;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.EntityBean;
import net.sf.mmm.util.bean.api.link.Link;
import net.sf.mmm.util.property.api.ReadableProperty;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.api.factory.PropertyFactory;
import net.sf.mmm.util.property.api.lang.IntegerProperty;
import net.sf.mmm.util.property.api.link.LinkProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This is the implementation of {@link PropertyFactory} for {@link IntegerProperty}.
 *
 * @param <ID> the generic type of the {@link Link#getId() unique ID}.
 * @param <E> the generic type of the {@link Link#getTarget() linked} {@link EntityBean}.
 *
 * @author hohwille
 * @since 8.0.0
 */
@Named
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PropertyFactoryLink<ID, E extends EntityBean<ID>>
    extends AbstractPropertyFactory<Link<ID, E>, LinkProperty<ID, E>> {

  @Override
  public Class<Link<ID, E>> getValueClass() {

    return (Class) Link.class;
  }

  @Override
  public Class<? extends ReadableProperty<Link<ID, E>>> getReadableInterface() {

    return null;
  }

  @Override
  public Class<? extends WritableProperty<Link<ID, E>>> getWritableInterface() {

    return null;
  }

  @Override
  public Class<LinkProperty<ID, E>> getImplementationClass() {

    return (Class) LinkProperty.class;
  }

  @Override
  public LinkProperty<ID, E> create(String name, GenericType<? extends Link<ID, E>> valueType, Bean bean,
      AbstractValidator<? super Link<ID, E>> validator) {

    return new LinkProperty<>(name, (GenericType) valueType, bean, validator);
  }

}
