/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.property.api.WritableProperty;

/**
 * This is the abstract base implementation of {@link BeanAccess}.
 *
 * @param <BEAN> the generic type of the intercepted {@link #getBean() bean}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class BeanAccessBase<BEAN extends Bean>
    implements InvocationHandler, BeanAccess, Iterable<WritableProperty<?>> {

  private final Class<BEAN> beanClass;

  private final BEAN bean;

  /**
   * The constructor.
   *
   * @param beanClass - see {@link #getBeanClass()}.
   * @param beanFactory the owning {@link BeanFactoryImpl}.
   */
  public BeanAccessBase(Class<BEAN> beanClass, BeanFactoryImpl beanFactory) {
    this(beanClass, beanFactory, beanClass);
  }

  /**
   * The constructor.
   *
   * @param beanClass - see {@link #getBeanClass()}.
   * @param beanFactory the owning {@link BeanFactoryImpl}.
   * @param interfaces an array with optional {@link Bean} interfaces to be implemented by the dynamic proxy.
   */
  public BeanAccessBase(Class<BEAN> beanClass, BeanFactoryImpl beanFactory, Class<?>... interfaces) {
    super();
    this.beanClass = beanClass;
    this.bean = beanFactory.createProxy(this, interfaces);
  }

  @Override
  public Class<BEAN> getBeanClass() {

    return this.beanClass;
  }

  /**
   * @return the {@link Bean} proxy instance to {@link #invoke(Object, Method, Object[]) intercept}.
   */
  public BEAN getBean() {

    return this.bean;
  }

  /**
   * @return the {@link BeanAccessPrototype}.
   */
  protected abstract BeanAccessPrototype<BEAN> getPrototype();

  @Override
  public Iterable<WritableProperty<?>> getProperties() {

    return this;
  }

  /**
   * Gets the {@link WritableProperty} for the given <code>index</code>.
   *
   * @param prototypeProperty is the {@link BeanPrototypeProperty}.
   * @param create - <code>true</code> if the property is required and shall be created if it {@link #isDynamic() does
   *        not already exist}, <code>false</code> otherwise.
   * @return the requested {@link WritableProperty}. May be <code>null</code>.
   */
  protected abstract WritableProperty<?> getProperty(BeanPrototypeProperty prototypeProperty, boolean create);

  @Override
  public boolean isReadOnly() {

    return false;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    Object result = null;
    BeanPrototypeOperation operation = getPrototype().getOperation(method);
    if (operation != null) {
      result = operation.invoke(this, args);
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  static <BEAN extends Bean> BeanAccessBase<BEAN> get(BEAN bean) {

    return (BeanAccessBase<BEAN>) bean.access();
  }

}
