/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.number.ValidatorBuilderDouble;

/**
 * This is the implementation of {@link WritableDoubleProperty}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class DoubleProperty extends NumberProperty<Double> implements WritableDoubleProperty {

  private Double value;

  /**
   * The constructor.
   */
  public DoubleProperty() {
    super();
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public DoubleProperty(String name, Bean bean) {
    super(name, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public DoubleProperty(String name, Bean bean, AbstractValidator<? super Number> validator) {
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
  public DoubleProperty copy(String newName, Bean newBean, AbstractValidator<? super Number> newValidator) {

    return new DoubleProperty(newName, newBean, newValidator);
  }

  @Override
  public ValidatorBuilderDouble<PropertyBuilder<DoubleProperty>> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderDouble<>(x));
  }

}
