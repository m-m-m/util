/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.property.base.AbstractProperty;

/**
 * The implementation of {@link BeanAccess} for a regular mutable {@link Bean} instance.
 *
 * @param <BEAN> the generic type of the intercepted {@link #getBean() bean}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class BeanAccessMutable<BEAN extends Bean> extends BeanAccessInstance<BEAN> {

  /**
   * The constructor.
   *
   * @param beanClass - see {@link #getBeanClass()}.
   * @param name - see {@link #getName()}.
   * @param beanFactory the owning {@link BeanFactoryImpl}.
   * @param prototype the {@link BeanAccessPrototype}.
   */
  public BeanAccessMutable(Class<BEAN> beanClass, String name, BeanFactoryImpl beanFactory,
      BeanAccessPrototype<BEAN> prototype) {

    super(beanClass, name, beanFactory, prototype);
  }

  @Override
  protected WritableProperty<?> createProperty(BeanPrototypeProperty prototypeProperty) {

    AbstractProperty<?> property = prototypeProperty.getProperty();
    return property.copy(getBean());
  }

}