/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import net.sf.mmm.util.bean.api.CustomEquals;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.api.ReflectionUtil;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 7.1.0
 */
public class BeanPrototypeOperationCustomEquals extends BeanPrototypeOperationDefaultMethod {

  private final GenericType<?> argType;

  /**
   * The constructor.
   *
   * @param prototype the {@link BeanAccessPrototype}.
   * @param method the {@link Object#equals(Object)} {@link Method}.
   * @param defaultMethod the {@link Method#isDefault() default} {@link CustomEquals#isEqualTo(CustomEquals)}
   *        {@link Method} to delegate to.
   * @param reflectionUtil the {@link ReflectionUtil}.
   */
  public BeanPrototypeOperationCustomEquals(BeanAccessPrototype<?> prototype, Method method, Method defaultMethod,
      ReflectionUtil reflectionUtil) {
    super(prototype, method, defaultMethod);
    Type type = defaultMethod.getGenericParameterTypes()[0];
    this.argType = reflectionUtil.createGenericType(type, prototype.getBeanClass());
  }

  @Override
  public Object invoke(BeanAccessBase<?> access, Object[] args) throws Throwable {

    Object other = args[0];
    if (other == null) {
      return Boolean.FALSE;
    }
    if (other == access.getBean()) {
      return Boolean.TRUE;
    }
    if (!this.argType.getAssignmentClass().isInstance(other)) {
      return Boolean.FALSE;
    }
    return super.invoke(access, args);
  }

}
