/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl;

import java.util.Objects;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.WritableNumberProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This is the implementation of {@link WritableNumberProperty}.
 *
 * @param <V> the generic type of the actual {@link #getValue() value}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class NumberProperty<V extends Number> extends AbstractRegularPropertyImpl<Number>
    implements WritableNumberProperty {

  private static final GenericType<Number> TYPE = new SimpleGenericTypeImpl<>(Number.class);

  /**
   * The constructor.
   */
  public NumberProperty() {
    this(null, null);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public NumberProperty(String name, Bean bean) {
    super(name, TYPE, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public NumberProperty(String name, Bean bean, AbstractValidator<? super Number> validator) {
    super(name, TYPE, bean, validator);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected final boolean doSetValue(Number newValue) {

    if (Objects.equals(getValue(), newValue)) {
      return false;
    }
    doSetNumber((V) newValue);
    return true;
  }

  /**
   * @param newValue the new {@link #getValue() value} to set.
   */
  protected abstract void doSetNumber(V newValue);

}
