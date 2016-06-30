/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.lang;

import java.util.Objects;

import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.AbstractRegularProperty;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.text.ValidatorBuilderString;

/**
 * This is the implementation of {@link WritableStringProperty}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class StringProperty extends AbstractRegularProperty<String> implements WritableStringProperty {

  private String value;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public StringProperty(String name, Bean bean) {
    super(name, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public StringProperty(String name, Bean bean, AbstractValidator<? super String> validator) {
    super(name, bean, validator);
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

    getJsonUtil().expectJsonEvent(json, Event.VALUE_STRING);
    set(json.getString());
  }

}
