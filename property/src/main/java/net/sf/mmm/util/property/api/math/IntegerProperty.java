/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.math;

import java.util.function.Supplier;

import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.number.ValidatorBuilderInteger;

/**
 * This is the implementation of {@link WritableIntegerProperty}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class IntegerProperty extends NumberProperty<Integer> implements WritableIntegerProperty {

  private Integer value;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public IntegerProperty(String name, Object bean) {
    super(name, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public IntegerProperty(String name, Object bean, AbstractValidator<? super Number> validator) {
    super(name, bean, validator);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param expression the {@link Supplier} {@link Supplier#get() providing} the actual {@link #getValue() value}.
   */
  public IntegerProperty(String name, Object bean, Supplier<Integer> expression) {
    super(name, bean, expression);
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
  public ValidatorBuilderInteger<PropertyBuilder<IntegerProperty>> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderInteger<>(x));
  }

  @Override
  protected void toJson(JsonGenerator json, Number integerValue) {

    json.write(getName(), integerValue.intValue());
  }

  @Override
  public void fromJson(JsonParser json) {

    getJsonUtil().expectEvent(json, Event.VALUE_NUMBER);
    set(json.getInt());
  }

}
