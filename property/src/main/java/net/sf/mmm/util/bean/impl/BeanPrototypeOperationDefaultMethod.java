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
 * @since 8.4.0
 */
public class BeanPrototypeOperationDefaultMethod extends BeanPrototypeOperation {

  private final MethodHandle methodHandle;

  /**
   * The constructor.
   *
   * @param prototype the {@link BeanAccessPrototype}.
   * @param method the {@link Method#isDefault() default} {@link Method}.
   */
  public BeanPrototypeOperationDefaultMethod(BeanAccessPrototype<?> prototype, Method method) {
    this(prototype, method, method);
  }

  /**
   * The constructor.
   *
   * @param prototype the {@link BeanAccessPrototype}.
   * @param method the {@link Method} to handle.
   * @param defaultMethod the {@link Method#isDefault() default} {@link Method} to delegate to.
   */
  public BeanPrototypeOperationDefaultMethod(BeanAccessPrototype<?> prototype, Method method,
      Method defaultMethod) {
    super(prototype, method);
    this.methodHandle = LookupHelper.INSTANCE.newMethodHandle(defaultMethod);
  }

  @Override
  public Object invoke(BeanAccessBase<?> access, Object[] args) throws Throwable {

    if (args == null) {
      return this.methodHandle.invoke(access.getBean());
    }
    Object[] array = new Object[args.length + 1];
    array[0] = access.getBean();
    System.arraycopy(args, 0, array, 1, args.length);
    return this.methodHandle.invokeWithArguments(array);
  }

}
