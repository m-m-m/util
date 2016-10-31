/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.math;

import java.util.function.Supplier;

import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.number.ValidatorBuilderFloat;

/**
 * This is the implementation of {@link WritableFloatProperty}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class FloatProperty extends NumberProperty<Float> implements WritableFloatProperty {

  private Float value;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public FloatProperty(String name, Object bean) {
    super(name, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public FloatProperty(String name, Object bean, AbstractValidator<? super Number> validator) {
    super(name, bean, validator);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param expression the {@link Supplier} {@link Supplier#get() providing} the actual {@link #getValue() value}.
   */
  public FloatProperty(String name, Object bean, Supplier<Float> expression) {
    super(name, bean, expression);
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

  @Override
  protected void toJson(JsonGenerator json, Number floatValue) {

    json.write(getName(), floatValue.doubleValue());
  }

  @Override
  public void fromJson(JsonParser json) {

    getJsonUtil().expectEvent(json, Event.VALUE_NUMBER);
    set(Float.parseFloat(json.getString()));
  }

}
