/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.DoubleProperty;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.number.ValidatorBuilderDouble;

/**
 * This is the implementation of {@link DoubleProperty}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class DoublePropertyImpl extends NumberPropertyImpl<Double> implements DoubleProperty {

  private Double value;

  /**
   * The constructor.
   */
  public DoublePropertyImpl() {
    super();
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public DoublePropertyImpl(String name, Bean bean) {
    super(name, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public DoublePropertyImpl(String name, Bean bean, AbstractValidator<? super Number> validator) {
    super(name, bean, validator);
  }

  @Override
  public void setValue(Number value) {

    if ((value == null) || (value instanceof Double)) {
      super.setValue(value);
    } else {
      super.setValue(Double.valueOf(value.doubleValue()));
    }
  }

  @Override
  protected Double doGetValue() {

    return this.value;
  }

  @Override
  protected void doSetNumber(Double newValue) {

    this.value = newValue;
  }

  @Override
  public DoublePropertyImpl copy(String newName, Bean newBean, AbstractValidator<? super Number> newValidator) {

    return new DoublePropertyImpl(newName, newBean, newValidator);
  }

  @Override
  public ValidatorBuilderDouble<PropertyBuilder<DoublePropertyImpl>> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderDouble<>(x));
  }

}
