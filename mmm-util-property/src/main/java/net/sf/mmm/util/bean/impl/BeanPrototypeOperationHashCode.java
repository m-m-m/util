/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.lang.reflect.Method;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.GenericProperty;

/**
 * Operation for {@link Bean#hashCode()}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class BeanPrototypeOperationHashCode extends BeanPrototypeOperation {

  /**
   * The constructor.
   *
   * @param prototype the {@link BeanAccessPrototype}.
   * @param method the {@link Method}.
   */
  public BeanPrototypeOperationHashCode(BeanAccessPrototype<?> prototype, Method method) {
    super(prototype, method);
  }

  @Override
  public Object invoke(BeanAccessBase<?> access, Object[] args) {

    int hashCode = 1;

    for (GenericProperty<?> property : access.getProperties()) {
      Object value = property.getValue();
      int hash = 0;
      if (value != null) {
        hash = value.hashCode();
      }
      hashCode = 31 * hashCode + hash;
    }
    return Integer.valueOf(hashCode);
  }

}
