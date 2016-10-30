/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.time;

import java.time.Instant;
import java.util.Objects;
import java.util.function.Supplier;

import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.AbstractRegularProperty;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.time.ValidatorBuilderInstant;

/**
 * This is the implementation of {@link WritableInstantProperty}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class InstantProperty extends AbstractRegularProperty<Instant> implements WritableInstantProperty {

  private Instant value;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public InstantProperty(String name, Bean bean) {
    super(name, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public InstantProperty(String name, Bean bean, AbstractValidator<? super Instant> validator) {
    super(name, bean, validator);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param expression the {@link Supplier} {@link Supplier#get() providing} the actual {@link #getValue() value}.
   */
  public InstantProperty(String name, Bean bean, Supplier<Instant> expression) {
    super(name, bean, expression);
  }

  @Override
  protected Instant doGetValue() {

    return this.value;
  }

  @Override
  protected boolean doSetValue(Instant newValue) {

    if (Objects.equals(this.value, newValue)) {
      return false;
    }
    this.value = newValue;
    return true;
  }

  @Override
  public ValidatorBuilderInstant<PropertyBuilder<InstantProperty>> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderInstant<>(x));
  }

  @Override
  protected void toJson(JsonGenerator json, Instant instantValue) {

    json.write(getName(), instantValue.toString());
  }

  @Override
  public void fromJson(JsonParser json) {

    getJsonUtil().expectEvent(json, Event.VALUE_STRING);
    setValue(Instant.parse(json.getString()));
  }

}
