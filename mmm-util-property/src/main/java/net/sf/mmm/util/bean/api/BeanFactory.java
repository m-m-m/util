/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.api;

/**
 * This is the factory used to {@link #create(Class) create} instances of {@link Bean}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public interface BeanFactory {

  /**
   * @see #getPrototype(Class, boolean)
   *
   * @param <BEAN> the generic type of the {@link Bean}.
   * @param type the {@link Class} reflecting the {@link Bean}.
   * @return the prototype instance of the specified {@link Bean}.
   */
  default <BEAN extends Bean> BEAN getPrototype(Class<BEAN> type) {

    return getPrototype(type, false);
  }

  /**
   * Gets the prototype of the given {@link Bean}.
   *
   * @param <BEAN> the generic type of the {@link Bean}.
   * @param type the {@link Class} reflecting the {@link Bean}.
   * @param dynamic the {@link BeanAccess#isDynamic() dynamic flag} of the {@link Bean}.
   * @return the prototype instance of the specified {@link Bean}.
   */
  <BEAN extends Bean> BEAN getPrototype(Class<BEAN> type, boolean dynamic);

  /**
   * @see #create(Class)
   *
   * @param <BEAN> the generic type of the {@link Bean}.
   * @param prototype the {@link #getPrototype(Class, boolean) prototype} of the {@link Bean} to create.
   * @return the new {@link Bean} instance.
   */
  <BEAN extends Bean> BEAN create(BEAN prototype);

  /**
   * Creates a new simple instance of the specified {@link Bean}.
   *
   * @param <BEAN> the generic type of the {@link Bean}.
   * @param type the {@link Class} reflecting the {@link Bean}.
   * @return the new {@link Bean} instance.
   */
  default <BEAN extends Bean> BEAN create(Class<BEAN> type) {

    return create(getPrototype(type));
  }

  /**
   * @param <BEAN> the generic type of the {@link Bean}.
   * @param bean the bean to make {@link BeanAccess#isReadOnly() read-only}.
   * @return the {@link BeanAccess#isReadOnly() read-only} view on the given bean. Will be the given instance if already
   *         {@link BeanAccess#isReadOnly() read-only}.
   */
  <BEAN extends Bean> BEAN getReadOnlyBean(BEAN bean);

}
