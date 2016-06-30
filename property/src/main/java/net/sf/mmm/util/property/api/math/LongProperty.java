/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.math;

import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.number.ValidatorBuilderLong;

/**
 * This is the implementation of {@link WritableLongProperty}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class LongProperty extends NumberProperty<Long> implements WritableLongProperty {

  private Long value;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public LongProperty(String name, Bean bean) {
    super(name, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public LongProperty(String name, Bean bean, AbstractValidator<? super Number> validator) {
    super(name, bean, validator);
  }

  @Override
  public void setValue(Number value) {

    if ((value == null) || (value instanceof Long)) {
      super.setValue(value);
    } else {
      super.setValue(Long.valueOf(value.longValue()));
    }
  }

  @Override
  protected Long doGetValue() {

    return this.value;
  }

  @Override
  protected void doSetNumber(Long newValue) {

    this.value = newValue;
  }

  @Override
  public ValidatorBuilderLong<PropertyBuilder<LongProperty>> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderLong<>(x));
  }

  @Override
  protected void toJson(JsonGenerator json, Number longValue) {

    json.write(getName(), longValue.longValue());
  }

  @Override
  public void fromJson(JsonParser json) {

    getJsonUtil().expectJsonEvent(json, Event.VALUE_NUMBER);
    set(json.getLong());
  }

}
