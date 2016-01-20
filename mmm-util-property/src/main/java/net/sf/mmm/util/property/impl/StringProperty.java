/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl;

import java.util.Objects;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.WritableStringProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.impl.SimpleGenericTypeImpl;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.text.ValidatorBuilderString;

/**
 * This is the implementation of {@link WritableStringProperty}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class StringProperty extends AbstractRegularProperty<String> implements WritableStringProperty {

  private static final GenericType<String> TYPE = new SimpleGenericTypeImpl<>(String.class);

  private String value;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public StringProperty(String name, Bean bean) {
    super(name, TYPE, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public StringProperty(String name, Bean bean, AbstractValidator<? super String> validator) {
    super(name, TYPE, bean, validator);
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
  public StringProperty copy(String newName, Bean newBean, AbstractValidator<? super String> newValidator) {

    return new StringProperty(newName, newBean, newValidator);
  }

  @Override
  public ValidatorBuilderString<PropertyBuilder<StringProperty>> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderString<>(x));
  }

}
