/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.math;

import java.math.BigDecimal;
import java.util.function.Supplier;

import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.number.ValidatorBuilderBigDecimal;

/**
 * This is the implementation of {@link WritableDoubleProperty}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class BigDecimalProperty extends NumberProperty<BigDecimal> {

  /** @see #getType() */
  public static final GenericType<BigDecimal> TYPE = new SimpleGenericTypeImpl<>(BigDecimal.class);

  private BigDecimal value;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public BigDecimalProperty(String name, Bean bean) {
    super(name, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public BigDecimalProperty(String name, Bean bean, AbstractValidator<? super Number> validator) {
    super(name, bean, validator);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param expression the {@link Supplier} {@link Supplier#get() providing} the actual {@link #getValue() value}.
   */
  public BigDecimalProperty(String name, Bean bean, Supplier<BigDecimal> expression) {
    super(name, bean, expression);
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
  protected BigDecimal doGetValue() {

    return this.value;
  }

  @Override
  protected void doSetNumber(BigDecimal newValue) {

    this.value = newValue;
  }

  @Override
  public GenericType<? extends Number> getType() {

    return TYPE;
  }

  @Override
  public ValidatorBuilderBigDecimal<PropertyBuilder<BigDecimalProperty>> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderBigDecimal<>(x));
  }

  @Override
  protected void toJson(JsonGenerator json, Number number) {

    if (number instanceof BigDecimal) {
      json.write(getName(), (BigDecimal) number);
    } else {
      json.write(getName(), number.doubleValue());
    }
  }

  @Override
  public void fromJson(JsonParser json) {

    getJsonUtil().expectEvent(json, Event.VALUE_NUMBER);
    setValue(json.getBigDecimal());
  }

}
