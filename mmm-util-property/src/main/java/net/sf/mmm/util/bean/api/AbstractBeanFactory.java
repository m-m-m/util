/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.api;

/**
 * This is the abstract base interface for {@link BeanFactory} and {@link BeanPrototypeBuilder}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract interface AbstractBeanFactory {

  /**
   * @param <BEAN> the generic type of the {@link Bean}.
   * @param bean the {@link Bean}.
   * @return the {@link BeanFactory#createPrototype(Class) prototype} of the given {@link Bean}.
   */
  <BEAN extends Bean> BEAN getPrototype(BEAN bean);

  /**
   * @see BeanFactory#create(Class)
   * @see BeanPrototypeBuilder#createPrototype(Class, String, Bean...)
   *
   * @param <BEAN> the generic type of the {@link Bean}.
   * @param prototype the {@link BeanFactory#createPrototype(Class) prototype} of the {@link Bean} to create.
   * @return the new {@link Bean} instance.
   */
  <BEAN extends Bean> BEAN create(BEAN prototype);

  /**
   * @param <BEAN> the generic type of the {@link Bean}.
   * @param bean the bean to make {@link BeanAccess#isReadOnly() read-only}.
   * @return the {@link BeanAccess#isReadOnly() read-only} view on the given bean. Will be the given instance if already
   *         {@link BeanAccess#isReadOnly() read-only}.
   */
  <BEAN extends Bean> BEAN getReadOnlyBean(BEAN bean);

  /**
   * Creates a copy of the given {@link Bean}.
   *
   * @param <BEAN> the generic type of the {@link Bean}.
   * @param bean the bean to make {@link BeanAccess#isReadOnly() read-only}.
   * @return the {@link BeanAccess#isReadOnly() read-only} view on the given bean. Will be the given instance if already
   *         {@link BeanAccess#isReadOnly() read-only}.
   */
  <BEAN extends Bean> BEAN copy(BEAN bean);

}
