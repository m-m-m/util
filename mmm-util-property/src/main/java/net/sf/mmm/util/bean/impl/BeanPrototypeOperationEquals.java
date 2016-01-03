/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.lang.reflect.Method;
import java.util.Objects;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.GenericProperty;

/**
 * Operation for {@link Bean#equals(Object)}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class BeanPrototypeOperationEquals extends BeanPrototypeOperation {

  /**
   * The constructor.
   *
   * @param prototype the {@link BeanAccessPrototype}.
   * @param method the {@link Method}.
   */
  public BeanPrototypeOperationEquals(BeanAccessPrototype<?> prototype, Method method) {
    super(prototype, method);
  }

  @Override
  public Object invoke(BeanAccessBase access, Object[] args) {

    Object other = args[0];
    if (other == null) {
      return Boolean.FALSE;
    }
    if (other == access.getBean()) {
      return Boolean.TRUE;
    }
    if (!(other instanceof Bean)) {
      return Boolean.FALSE;
    }
    Bean otherBean = (Bean) other;
    for (GenericProperty<?> property : access.getProperties()) {
      GenericProperty<?> otherProperty = otherBean.access().getProperty(property.getName());
      if (otherProperty == null) {
        return Boolean.FALSE;
      }
      if (!Objects.equals(property.getValue(), otherProperty.getValue())) {
        return Boolean.FALSE;
      }
    }
    return Boolean.TRUE;
  }

}
