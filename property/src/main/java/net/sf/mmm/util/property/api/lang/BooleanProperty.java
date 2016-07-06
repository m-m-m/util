/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.lang;

import java.util.Objects;
import java.util.function.Supplier;

import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.exception.api.WrongValueTypeException;
import net.sf.mmm.util.property.api.AbstractRegularProperty;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ValidatorBuilderBoolean;

/**
 * This is the implementation of {@link BooleanProperty}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class BooleanProperty extends AbstractRegularProperty<Boolean> implements WritableBooleanProperty {

  private Boolean value;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public BooleanProperty(String name, Bean bean) {
    super(name, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public BooleanProperty(String name, Bean bean, AbstractValidator<? super Boolean> validator) {
    super(name, bean, validator);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param expression the {@link Supplier} {@link Supplier#get() providing} the actual {@link #getValue() value}.
   */
  public BooleanProperty(String name, Bean bean, Supplier<Boolean> expression) {
    super(name, bean, expression);
  }

  @Override
  protected Boolean doGetValue() {

    return this.value;
  }

  @Override
  protected boolean doSetValue(Boolean newValue) {

    if (Objects.equals(this.value, newValue)) {
      return false;
    }
    this.value = newValue;
    return true;
  }

  @Override
  public ValidatorBuilderBoolean<PropertyBuilder<BooleanProperty>> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderBoolean<>(x));
  }

  @Override
  protected void toJson(JsonGenerator json, Boolean booleanValue) {

    json.write(getName(), booleanValue.booleanValue());
  }

  @Override
  public void fromJson(JsonParser json) {

    Event e = json.next();
    if (e == Event.VALUE_TRUE) {
      set(true);
    } else if (e == Event.VALUE_FALSE) {
      set(false);
    } else {
      throw new WrongValueTypeException(e, Boolean.class);
    }
  }

}
