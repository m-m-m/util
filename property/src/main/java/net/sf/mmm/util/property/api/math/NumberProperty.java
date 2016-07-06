/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.math;

import java.util.Objects;
import java.util.function.Supplier;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.AbstractRegularProperty;
import net.sf.mmm.util.validation.base.AbstractValidator;

/**
 * This is the implementation of {@link WritableNumberProperty}.
 *
 * @param <N> is the generic type of the internal numeric {@link #getValue() value} representation.
 *
 * @author hohwille
 * @since 8.0.0
 */
public abstract class NumberProperty<N extends Number> extends AbstractRegularProperty<Number>
    implements WritableNumberProperty<N> {

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public NumberProperty(String name, Bean bean) {
    super(name, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public NumberProperty(String name, Bean bean, AbstractValidator<? super Number> validator) {
    super(name, bean, validator);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param expression the {@link Supplier} {@link Supplier#get() providing} the actual {@link #getValue() value}.
   */
  public NumberProperty(String name, Bean bean, Supplier<? extends Number> expression) {
    super(name, bean, expression);
  }

  @SuppressWarnings("unchecked")
  @Override
  protected final boolean doSetValue(Number newValue) {

    if (Objects.equals(getValue(), newValue)) {
      return false;
    }
    doSetNumber((N) newValue);
    return true;
  }

  /**
   * @param newValue the new {@link #getValue() value} to set.
   */
  protected abstract void doSetNumber(N newValue);

}
