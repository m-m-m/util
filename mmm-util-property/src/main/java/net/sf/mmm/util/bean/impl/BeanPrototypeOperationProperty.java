/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.lang.reflect.Method;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.GenericProperty;

/**
 * Operation on a {@link Bean} the get the raw {@link GenericProperty property}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class BeanPrototypeOperationProperty extends BeanPrototypeOperationOnProperty {

  /**
   * The constructor.
   *
   * @param prototype the {@link BeanAccessPrototype}.
   * @param method the {@link Method}.
   * @param property the {@link BeanPrototypeProperty}.
   */
  public BeanPrototypeOperationProperty(BeanAccessPrototype<?> prototype, Method method,
      BeanPrototypeProperty property) {
    super(prototype, method, property);
  }

  @Override
  protected Object doInvoke(GenericProperty<?> property, Object[] args) {

    if (property == null) {
      return null;
    }
    return property;
  }

}
