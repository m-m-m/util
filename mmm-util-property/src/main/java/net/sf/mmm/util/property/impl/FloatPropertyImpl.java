/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.FloatProperty;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.number.ValidatorBuilderFloat;

/**
 * This is the implementation of {@link FloatProperty}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class FloatPropertyImpl extends NumberPropertyImpl implements FloatProperty {

  /**
   * The constructor.
   */
  public FloatPropertyImpl() {
    super();
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public FloatPropertyImpl(String name, Bean bean) {
    super(name, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public FloatPropertyImpl(String name, Bean bean, AbstractValidator<? super Number> validator) {
    super(name, bean, validator);
  }

  @Override
  public void setValue(Number value) {

    if ((value == null) || (value instanceof Float)) {
      super.setValue(value);
    } else {
      super.setValue(Float.valueOf(value.floatValue()));
    }
  }

  @Override
  public FloatPropertyImpl copy(String newName, Bean newBean, AbstractValidator<? super Number> newValidator) {

    return new FloatPropertyImpl(newName, newBean, newValidator);
  }

  @Override
  public ValidatorBuilderFloat<PropertyBuilder<FloatPropertyImpl>> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderFloat<>(x));
  }

}
