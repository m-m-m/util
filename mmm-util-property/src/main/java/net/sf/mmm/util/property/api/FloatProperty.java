/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.number.ValidatorBuilderFloat;

/**
 * This is the implementation of {@link WritableFloatProperty}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class FloatProperty extends NumberProperty<Float> implements WritableFloatProperty {

  private Float value;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public FloatProperty(String name, Bean bean) {
    super(name, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public FloatProperty(String name, Bean bean, AbstractValidator<? super Number> validator) {
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
  protected Float doGetValue() {

    return this.value;
  }

  @Override
  protected void doSetNumber(Float newValue) {

    this.value = newValue;
  }

  @Override
  public ValidatorBuilderFloat<PropertyBuilder<FloatProperty>> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderFloat<>(x));
  }

}
