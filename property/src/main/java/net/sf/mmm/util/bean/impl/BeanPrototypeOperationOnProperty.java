/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.lang.reflect.Method;
import java.util.Objects;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.WritableProperty;

/**
 * Operation on a {@link Bean} {@link WritableProperty property}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public abstract class BeanPrototypeOperationOnProperty extends BeanPrototypeOperation {

  private final BeanPrototypeProperty prototypeProperty;

  /**
   * The constructor.
   *
   * @param prototype the {@link BeanAccessPrototype}.
   * @param method the {@link Method}.
   * @param property the {@link BeanPrototypeProperty}.
   */
  public BeanPrototypeOperationOnProperty(BeanAccessPrototype<?> prototype, Method method,
      BeanPrototypeProperty property) {
    super(prototype, method);
    if (property == null) {
      Objects.requireNonNull(property, "property");
    }
    this.prototypeProperty = property;
  }

  /**
   * @return the {@link BeanPrototypeProperty}.
   */
  public BeanPrototypeProperty getProperty() {

    return this.prototypeProperty;
  }

  /**
   * @return {@code true} if the {@link WritableProperty} is required for this operation, {@code false} otherwise.
   */
  @Override
  public boolean isPropertyRequired() {

    return true;
  }

  @Override
  public Object invoke(BeanAccessBase<?> instance, Object[] args) {

    return doInvoke(instance.getProperty(this.prototypeProperty, isPropertyRequired()), args);
  }

  /**
   * @see #invoke(BeanAccessBase, Object[])
   *
   * @param property the {@link WritableProperty}.
   * @param args the given method arguments.
   * @return the result of the invocation.
   */
  protected abstract Object doInvoke(WritableProperty<?> property, Object[] args);

  @Override
  public abstract BeanPrototypeOperation forPrototype(BeanAccessPrototype<?> prototype);

}
