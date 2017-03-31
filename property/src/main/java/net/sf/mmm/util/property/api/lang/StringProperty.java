/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.lang;

import java.util.Objects;
import java.util.function.Supplier;

import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import net.sf.mmm.util.property.api.AbstractRegularProperty;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.text.ValidatorBuilderString;

/**
 * This is the implementation of {@link WritableStringProperty}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class StringProperty extends AbstractRegularProperty<String> implements WritableStringProperty {

  private String value;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public StringProperty(String name, Object bean) {
    super(name, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public StringProperty(String name, Object bean, AbstractValidator<? super String> validator) {
    super(name, bean, validator);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param expression the {@link Supplier} {@link Supplier#get() providing} the actual {@link #getValue() value}.
   */
  public StringProperty(String name, Object bean, Supplier<String> expression) {
    super(name, bean, expression);
  }

  @Override
  protected String doGetValue() {

    return this.value;
  }

  @Override
  protected boolean doSetValue(String newValue) {

    if (Objects.equals(this.value, newValue)) {
      return false;
    }
    this.value = newValue;
    return true;
  }

  @Override
  public ValidatorBuilderString<PropertyBuilder<StringProperty>> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderString<>(x));
  }

  @Override
  protected void toJson(JsonGenerator json, String stringValue) {

    json.write(getName(), stringValue);
  }

  @Override
  public void fromJson(JsonParser json) {

    getJsonUtil().expectEvent(json, Event.VALUE_STRING);
    set(json.getString());
  }

}
