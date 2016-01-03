/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Method;

import net.sf.mmm.util.bean.api.Bean;

/**
 * Operation for {@link Bean#equals(Object)}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class BeanPrototypeOperationDefault extends BeanPrototypeOperation {

  private final MethodHandle methodHandle;

  /**
   * The constructor.
   *
   * @param prototype the {@link BeanAccessPrototype}.
   * @param method the {@link Method#isDefault() default} {@link Method}.
   */
  public BeanPrototypeOperationDefault(BeanAccessPrototype<?> prototype, Method method) {
    super(prototype, method);
    this.methodHandle = LookupHelper.INSTANCE.newMethodHandle(method);
  }

  @Override
  public Object invoke(BeanAccessBase access, final Object[] args) throws Throwable {

    return this.methodHandle.invoke(args);
  }

}
