/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.lang.reflect.Method;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.WritableProperty;

/**
 * Operation on a {@link Bean} to get the plain {@link WritableProperty property} {@link WritableProperty#getValue()
 * value}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class BeanPrototypeOperationGet extends BeanPrototypeOperationOnProperty {

  /**
   * The constructor.
   *
   * @param prototype the {@link BeanAccessPrototype}.
   * @param method the {@link Method}.
   * @param property the {@link BeanPrototypeProperty}.
   */
  public BeanPrototypeOperationGet(BeanAccessPrototype<?> prototype, Method method,
      BeanPrototypeProperty property) {
    super(prototype, method, property);
  }

  @Override
  public boolean isPropertyRequired() {

    return false;
  }

  @Override
  protected Object doInvoke(WritableProperty<?> property, Object[] args) {

    Object value = null;
    if (property != null) {
      value = property.getValue();
    }
    if (value == null) {
      Class<?> returnType = getMethod().getReturnType();
      if (returnType.isPrimitive()) {
        if (returnType == boolean.class) {
          return Boolean.FALSE;
        } else if (returnType == long.class) {
          return Long.valueOf(0);
        } else if (returnType == int.class) {
          return Integer.valueOf(0);
        } else if (returnType == double.class) {
          return Double.valueOf(0);
        } else if (returnType == float.class) {
          return Float.valueOf(0);
        } else if (returnType == short.class) {
          return Short.valueOf((short) 0);
        } else if (returnType == byte.class) {
          return Byte.valueOf((byte) 0);
        } else if (returnType == char.class) {
          return Character.valueOf('\0');
        }
      }
    }
    return value;
  }

  @Override
  public BeanPrototypeOperation forPrototype(BeanAccessPrototype<?> prototype) {

    return new BeanPrototypeOperationGet(prototype, getMethod(),
        prototype.getPrototypeProperty(getProperty().getProperty().getName()));
  }

}
