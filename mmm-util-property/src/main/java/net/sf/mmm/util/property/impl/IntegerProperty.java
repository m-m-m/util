/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.WritableIntegerProperty;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.number.ValidatorBuilderInteger;

/**
 * This is the implementation of {@link WritableIntegerProperty}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class IntegerProperty extends NumberProperty<Integer> implements WritableIntegerProperty {

  private Integer value;

  /**
   * The constructor.
   */
  public IntegerProperty() {
    super();
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public IntegerProperty(String name, Bean bean) {
    super(name, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public IntegerProperty(String name, Bean bean, AbstractValidator<? super Number> validator) {
    super(name, bean, validator);
  }

  @Override
  public void setValue(Number value) {

    if ((value == null) || (value instanceof Integer)) {
      super.setValue(value);
    } else {
      super.setValue(Integer.valueOf(value.intValue()));
    }
  }

  @Override
  protected Integer doGetValue() {

    return this.value;
  }

  @Override
  protected void doSetNumber(Integer newValue) {

    this.value = newValue;
  }

  @Override
  public IntegerProperty copy(String newName, Bean newBean, AbstractValidator<? super Number> newValidator) {

    return new IntegerProperty(newName, newBean, newValidator);
  }

  @Override
  public ValidatorBuilderInteger<PropertyBuilder<IntegerProperty>> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderInteger<>(x));
  }

}
