/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.time;

import java.time.LocalDate;
import java.util.Objects;
import java.util.function.Supplier;

import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.AbstractRegularProperty;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.time.ValidatorBuilderLocalDate;

/**
 * This is the implementation of {@link WritableLocalDateProperty}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class LocalDateProperty extends AbstractRegularProperty<LocalDate> implements WritableLocalDateProperty {

  private LocalDate value;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public LocalDateProperty(String name, Bean bean) {
    super(name, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public LocalDateProperty(String name, Bean bean, AbstractValidator<? super LocalDate> validator) {
    super(name, bean, validator);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param expression the {@link Supplier} {@link Supplier#get() providing} the actual {@link #getValue() value}.
   */
  public LocalDateProperty(String name, Bean bean, Supplier<LocalDate> expression) {
    super(name, bean, expression);
  }

  @Override
  protected LocalDate doGetValue() {

    return this.value;
  }

  @Override
  protected boolean doSetValue(LocalDate newValue) {

    if (Objects.equals(this.value, newValue)) {
      return false;
    }
    this.value = newValue;
    return true;
  }

  @Override
  public ValidatorBuilderLocalDate<PropertyBuilder<LocalDateProperty>> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderLocalDate<>(x));
  }

  @Override
  protected void toJson(JsonGenerator json, LocalDate instantValue) {

    json.write(getName(), instantValue.toString());
  }

  @Override
  public void fromJson(JsonParser json) {

    getJsonUtil().expectEvent(json, Event.VALUE_STRING);
    setValue(LocalDate.parse(json.getString()));
  }

}
