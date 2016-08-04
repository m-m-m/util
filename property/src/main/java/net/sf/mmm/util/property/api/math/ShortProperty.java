/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.math;

import java.util.function.Supplier;

import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.number.ValidatorBuilderInteger;

/**
 * This is the implementation of {@link WritableShortProperty}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class ShortProperty extends NumberProperty<Short> implements WritableShortProperty {

  private Short value;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public ShortProperty(String name, Bean bean) {
    super(name, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public ShortProperty(String name, Bean bean, AbstractValidator<? super Number> validator) {
    super(name, bean, validator);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param expression the {@link Supplier} {@link Supplier#get() providing} the actual {@link #getValue() value}.
   */
  public ShortProperty(String name, Bean bean, Supplier<Short> expression) {
    super(name, bean, expression);
  }

  @Override
  public void setValue(Number value) {

    if ((value == null) || (value instanceof Short)) {
      super.setValue(value);
    } else {
      super.setValue(Short.valueOf(value.shortValue()));
    }
  }

  @Override
  protected Short doGetValue() {

    return this.value;
  }

  @Override
  protected void doSetNumber(Short newValue) {

    this.value = newValue;
  }

  @Override
  public ValidatorBuilderInteger<PropertyBuilder<ShortProperty>> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderInteger<>(x));
  }

  @Override
  protected void toJson(JsonGenerator json, Number shortValue) {

    json.write(getName(), shortValue.intValue());
  }

  @Override
  public void fromJson(JsonParser json) {

    getJsonUtil().expectEvent(json, Event.VALUE_NUMBER);
    set(Short.parseShort(json.getString()));
  }

}