/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.lang.reflect.Method;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.bean.api.BeanAccess;
import net.sf.mmm.util.property.api.WritableProperty;

/**
 * Operation on a {@link Bean} {@link WritableProperty property}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class BeanPrototypeOperation {

  private final BeanAccessPrototype<?> prototype;

  private final Method method;

  /**
   * The constructor.
   *
   * @param prototype the {@link BeanAccessPrototype}.
   * @param method the {@link Method}.
   */
  public BeanPrototypeOperation(BeanAccessPrototype<?> prototype, Method method) {
    super();
    this.prototype = prototype;
    this.method = method;
  }

  /**
   * Invokes this operation of the given <code>access</code> using the given arguments.
   *
   * @param access the {@link BeanAccess}.
   * @param args the arguments of the reflective method invocation.
   * @return the result of the invocation.
   * @throws Throwable if something goes wrong.
   */
  public abstract Object invoke(BeanAccessBase<?> access, Object[] args) throws Throwable;

  /**
   * @return <code>true</code> if the {@link WritableProperty} is required for this operation, <code>false</code>
   *         otherwise.
   */
  public boolean isPropertyRequired() {

    return false;
  }

  /**
   * @return the prototype
   */
  public BeanAccessPrototype<?> getPrototype() {

    return this.prototype;
  }

  /**
   * @return the method
   */
  public Method getMethod() {

    return this.method;
  }

  /**
   * @param beanMethod the {@link BeanMethod}.
   * @param prototype the {@link BeanAccessPrototype}.
   * @return the according new {@link BeanPrototypeOperation}.
   */
  public static BeanPrototypeOperation create(BeanMethod beanMethod, BeanAccessPrototype<?> prototype) {

    BeanMethodType methodType = beanMethod.getMethodType();
    if (methodType != null) {
      String propertyName = beanMethod.getPropertyName();
      Method method = beanMethod.getMethod();
      BeanPrototypeProperty prototypeProperty = prototype.getPrototypeProperty(propertyName);
      switch (methodType) {
        case GET:
          return new BeanPrototypeOperationGet(prototype, method, prototypeProperty);
        case SET:
          return new BeanPrototypeOperationSet(prototype, method, prototypeProperty);
        case PROPERTY:
          return new BeanPrototypeOperationProperty(prototype, method, prototypeProperty);
        case ACCESS:
          return new BeanPrototypeOperationAccess(prototype, method);
        case EQUALS:
          return new BeanPrototypeOperationEquals(prototype, method);
        case HASH_CODE:
          return new BeanPrototypeOperationHashCode(prototype, method);
        case TO_STRING:
          return new BeanPrototypeOperationToString(prototype, method);
        case DEFAULT_METHOD:
        case CUSTOM_EQUALS:
        case CUSTOM_HASH_CODE:
          return new BeanPrototypeOperationDefaultMethod(prototype, method);
      }
    }
    return null;
  }

  /**
   * @return <code>true</code> if this is a read-only operation, <code>false</code> otherwise.
   */
  public boolean isReadOnly() {

    return true;
  }

  public BeanPrototypeOperation forPrototype(BeanAccessPrototype<?> prototype) {

    return this;
  }

}
