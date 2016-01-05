/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.IntegerProperty;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.number.ValidatorBuilderInteger;

/**
 * This is the implementation of {@link IntegerProperty}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class IntegerPropertyImpl extends NumberPropertyImpl implements IntegerProperty {

  /**
   * The constructor.
   */
  public IntegerPropertyImpl() {
    super();
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public IntegerPropertyImpl(String name, Bean bean) {
    super(name, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public IntegerPropertyImpl(String name, Bean bean, AbstractValidator<? super Number> validator) {
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
  public IntegerPropertyImpl copy(String newName, Bean newBean, AbstractValidator<? super Number> newValidator) {

    return new IntegerPropertyImpl(newName, newBean, newValidator);
  }

  @Override
  public ValidatorBuilderInteger<PropertyBuilder<IntegerPropertyImpl>> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderInteger<>(x));
  }

}
