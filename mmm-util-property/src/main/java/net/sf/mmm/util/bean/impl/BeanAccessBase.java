/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.property.api.GenericProperty;

/**
 * This is the abstract base implementation of {@link BeanAccess}.
 *
 * @author hohwille
 * @since 7.1.0
 */
abstract class BeanAccessBase implements InvocationHandler, BeanAccess, Iterable<GenericProperty<?>> {

  private Bean bean;

  /**
   * @return the {@link BeanAccessPrototype}.
   */
  protected abstract BeanAccessPrototype<?> getPrototype();

  @Override
  public Iterable<GenericProperty<?>> getProperties() {

    return this;
  }

  @Override
  public GenericProperty<?> getProperty(String name) {

    BeanPrototypeProperty prototypeProperty = getPrototype().getName2PropertyMap().get(name);
    if (prototypeProperty != null) {
      return getProperty(prototypeProperty, false);
    }
    return null;
  }

  /**
   * Gets the {@link GenericProperty} for the given <code>index</code>.
   *
   * @param prototypeProperty is the {@link BeanPrototypeProperty}.
   * @param required - <code>true</code> if the property is required and shall be created if it {@link #isDynamic() does
   *        not already exist}, <code>false</code> otherwise.
   * @return the requested {@link GenericProperty}. May be <code>null</code>.
   */
  protected abstract GenericProperty<?> getProperty(BeanPrototypeProperty prototypeProperty, boolean required);

  @Override
  public boolean isReadOnly() {

    return false;
  }

  /**
   * @return the bean
   */
  public Bean getBean() {

    return this.bean;
  }

  /**
   * @param bean is the bean to set
   */
  void setBean(Bean bean) {

    assert ((this.bean == null) || (this.bean == bean));
    this.bean = bean;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

    Object result = null;
    BeanPrototypeOperation operation = getPrototype().getMethod2OperationMap().get(method);
    if (operation != null) {
      result = operation.invoke(this, args);
    }
    return result;
  }

}
