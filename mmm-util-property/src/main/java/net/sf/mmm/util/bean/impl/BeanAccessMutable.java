/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.property.api.GenericProperty;
import net.sf.mmm.util.property.impl.GenericPropertyImpl;

/**
 * The implementation of {@link BeanAccess} for a regular mutable {@link Bean} instance.
 *
 * @param <BEAN> the generic type of the intercepted {@link #getBean() bean}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class BeanAccessMutable<BEAN extends Bean> extends BeanAccessInstance<BEAN> {

  /**
   * The constructor.
   *
   * @param beanType - see {@link #getBeanType()}.
   * @param beanFactory the owning {@link BeanFactoryImpl}.
   * @param prototype the {@link BeanAccessPrototype}.
   */
  public BeanAccessMutable(Class<BEAN> beanType, BeanFactoryImpl beanFactory,
      BeanAccessPrototype<BEAN> prototype) {

    super(beanType, beanFactory, prototype);
  }

  @Override
  protected GenericProperty<?> createProperty(BeanPrototypeProperty prototypeProperty) {

    GenericPropertyImpl<?> property = prototypeProperty.getProperty();
    return property.copy(getBean());
  }

}