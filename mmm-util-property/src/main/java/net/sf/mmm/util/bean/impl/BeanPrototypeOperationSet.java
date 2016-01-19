/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.lang.reflect.Method;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.WritableProperty;

/**
 * Operation on a {@link Bean} to {@link WritableProperty#setValue(Object) set} the {@link WritableProperty#getValue()
 * value} of a {@link WritableProperty property}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class BeanPrototypeOperationSet extends BeanPrototypeOperationOnProperty {

  /**
   * The constructor.
   *
   * @param prototype the {@link BeanAccessPrototype}.
   * @param method the {@link Method}.
   * @param property the {@link BeanPrototypeProperty}.
   */
  public BeanPrototypeOperationSet(BeanAccessPrototype<?> prototype, Method method,
      BeanPrototypeProperty property) {
    super(prototype, method, property);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  protected Object doInvoke(WritableProperty<?> property, Object[] args) {

    ((WritableProperty) property).setValue(args[0]);
    return null;
  }

  @Override
  public boolean isReadOnly() {

    return false;
  }

}
