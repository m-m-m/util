/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.property.api.WritableProperty;

/**
 * The implementation of {@link BeanAccess} for a regular {@link #isReadOnly() read-only} {@link Bean} instance.
 *
 * @param <BEAN> the generic type of the {@link Bean}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class BeanAccessReadOnly<BEAN extends Bean> extends BeanAccessInstance<BEAN> {

  private final BeanAccessBase<BEAN> delegate;

  /**
   * The constructor.
   *
   * @param prototype the {@link BeanAccessPrototype}.
   * @param beanFactory the owning {@link BeanFactoryImpl}.
   * @param delegate the original intercepter instance to adapt.
   */
  public BeanAccessReadOnly(BeanFactoryImpl beanFactory, BeanAccessPrototype<BEAN> prototype,
      BeanAccessBase<BEAN> delegate) {
    super(beanFactory, prototype);
    this.delegate = delegate;
  }

  @Override
  protected WritableProperty<?> createProperty(BeanPrototypeProperty prototypeProperty) {

    WritableProperty<?> property = this.delegate.getProperty(prototypeProperty.getProperty().getName());
    return property.getReadOnly();
  }

  @Override
  public boolean isReadOnly() {

    return true;
  }

}
