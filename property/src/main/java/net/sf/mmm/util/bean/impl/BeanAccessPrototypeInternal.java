/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanFactory;

/**
 * This is the implementation of {@link net.sf.mmm.util.bean.api.BeanAccess} for the internal master
 * {@link BeanFactory#createPrototype(Class) prototype} of a {@link Bean}.
 *
 * @param <BEAN> the generic type of the {@link Bean}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class BeanAccessPrototypeInternal<BEAN extends Bean> extends BeanAccessPrototype<BEAN> {

  /**
   * The constructor.
   *
   * @param beanClass - see {@link #getBeanClass()}.
   * @param qualifiedName - see {@link #getQualifiedName()}.
   * @param beanFactory the owning {@link BeanFactoryImpl}.
   */
  public BeanAccessPrototypeInternal(Class<BEAN> beanClass, String qualifiedName, BeanFactoryImpl beanFactory) {
    super(beanClass, qualifiedName, beanFactory);
  }

  @Override
  public boolean isDynamic() {

    return false;
  }

}
