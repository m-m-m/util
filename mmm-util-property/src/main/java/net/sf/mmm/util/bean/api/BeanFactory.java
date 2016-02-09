/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.api;

import net.sf.mmm.util.property.api.WritableProperty;

/**
 * This is the factory used to {@link #create(Class) create} instances of {@link Bean}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface BeanFactory {

  /**
   * @see #createPrototype(Class, boolean)
   *
   * @param <BEAN> the generic type of the {@link Bean}.
   * @param type the {@link Class} reflecting the {@link Bean}.
   * @return the prototype instance of the specified {@link Bean}.
   */
  default <BEAN extends Bean> BEAN createPrototype(Class<BEAN> type) {

    return createPrototype(type, false, null);
  }

  /**
   * Creates a prototype of the given {@link Bean}. A prototype is used as template to {@link #create(Bean) create}
   * regular {@link Bean}s. Such beans will inherit the defaults from the prototype what are the
   * {@link BeanAccess#getProperties() available properties} as well as their default {@link WritableProperty#getValue()
   * value}.
   *
   * @param <BEAN> the generic type of the {@link Bean}.
   * @param type the {@link Class} reflecting the {@link Bean}.
   * @param dynamic the {@link BeanAccess#isDynamic() dynamic flag} of the {@link Bean}.
   * @return the prototype instance of the specified {@link Bean}.
   */
  default <BEAN extends Bean> BEAN createPrototype(Class<BEAN> type, boolean dynamic) {

    return createPrototype(type, dynamic, null);
  }

  /**
   * Creates a prototype of the given {@link Bean}. A prototype is used as template to {@link #create(Bean) create}
   * regular {@link Bean}s. Such beans will inherit the defaults from the prototype what are the
   * {@link BeanAccess#getProperties() available properties} as well as their default {@link WritableProperty#getValue()
   * value}.
   *
   * @param <BEAN> the generic type of the {@link Bean}.
   * @param type the {@link Class} reflecting the {@link Bean}.
   * @param dynamic the {@link BeanAccess#isDynamic() dynamic flag} of the {@link Bean}.
   * @param name the explicit {@link BeanAccess#getName() name} of the {@link Bean}.
   * @return the prototype instance of the specified {@link Bean}.
   */
  <BEAN extends Bean> BEAN createPrototype(Class<BEAN> type, boolean dynamic, String name);

  /**
   * @param <BEAN> the generic type of the {@link Bean}.
   * @param bean the {@link Bean}.
   * @return the {@link #createPrototype(Class, boolean) prototype} of the given {@link Bean}.
   */
  <BEAN extends Bean> BEAN getPrototype(BEAN bean);

  /**
   * @see #create(Class)
   *
   * @param <BEAN> the generic type of the {@link Bean}.
   * @param prototype the {@link #createPrototype(Class, boolean) prototype} of the {@link Bean} to create.
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

    return create(createPrototype(type));
  }

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
