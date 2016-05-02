/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.base.mapping;

import javax.inject.Inject;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanFactory;
import net.sf.mmm.util.bean.api.BeanPrototypeBuilder;
import net.sf.mmm.util.bean.api.EntityBean;
import net.sf.mmm.util.bean.impl.BeanFactoryImpl;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;

/**
 * The abstract base implementation of {@link net.sf.mmm.util.bean.api.mapping.PojoBeanMapper} or
 * {@link net.sf.mmm.util.bean.api.mapping.DocumentBeanMapper}.
 *
 * @param <P> the base type of the {@link net.sf.mmm.util.pojo.api.Pojo}s to convert (e.g. {@link Object} or
 *        {@link net.sf.mmm.util.bean.api.Entity}).
 * @param <B> the base type of the {@link Bean} to convert (e.g. {@link Bean} or {@link EntityBean}).
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class AbstractBeanMapper<P, B extends Bean> extends AbstractLoggableComponent {

  private BeanFactory beanFactory;

  private BeanPrototypeBuilder beanPrototypeBuilder;

  /**
   * The constructor.
   */
  public AbstractBeanMapper() {
    super();
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.beanFactory == null) {
      this.beanFactory = BeanFactoryImpl.getInstance();
    }
    if (this.beanPrototypeBuilder == null) {
      this.beanPrototypeBuilder = this.beanFactory.createPrototypeBuilder(true);
    }
  }

  /**
   * @param beanFactory is the {@link BeanFactory} to {@link Inject}.
   */
  @Inject
  public void setBeanFactory(BeanFactory beanFactory) {

    this.beanFactory = beanFactory;
  }

  /**
   * @return the {@link BeanFactory}.
   */
  protected BeanFactory getBeanFactory() {

    return this.beanFactory;
  }

  /**
   * @return the {@link BeanPrototypeBuilder}.
   */
  protected BeanPrototypeBuilder getBeanPrototypeBuilder() {

    return this.beanPrototypeBuilder;
  }

  /**
   * @param beanPrototypeBuilder is the {@link BeanPrototypeBuilder} to set.
   */
  public void setBeanPrototypeBuilder(BeanPrototypeBuilder beanPrototypeBuilder) {

    getInitializationState().requireNotInitilized();
    this.beanPrototypeBuilder = beanPrototypeBuilder;
  }

}
