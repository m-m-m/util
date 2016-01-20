/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.impl;

import java.util.Objects;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.WritableProperty;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ObjectValidatorBuilder;
import net.sf.mmm.util.validation.base.ValidatorBuilderObject;

/**
 * This is the implementation of {@link WritableProperty}.
 *
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class GenericProperty<VALUE> extends AbstractRegularProperty<VALUE> {

  private VALUE value;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   */
  public GenericProperty(String name, GenericType<VALUE> type, Bean bean) {
    this(name, type, bean, null);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param type - see {@link #getType()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public GenericProperty(String name, GenericType<VALUE> type, Bean bean,
      AbstractValidator<? super VALUE> validator) {
    super(name, type, bean);
  }

  /**
   * Creates a new empty instance of this property with the given new parameters.
   *
   * @param newName the new {@link #getName() name}.
   * @param newBean the new {@link #getBean() bean}.
   * @param newValidator the new {@link #getValidator() validator}.
   * @return the new property instance.
   */
  @Override
  public GenericProperty<VALUE> copy(String newName, Bean newBean,
      AbstractValidator<? super VALUE> newValidator) {

    return new GenericProperty<>(newName, getType(), newBean, newValidator);
  }

  @Override
  protected VALUE doGetValue() {

    return this.value;
  }

  @Override
  protected boolean doSetValue(VALUE newValue) {

    if (Objects.equals(this.value, newValue)) {
      return false;
    }
    this.value = newValue;
    return true;
  }

  /**
   * @return a new {@link ObjectValidatorBuilder builder} for the validator of this property with a
   *         {@link ObjectValidatorBuilder#and() parent-builder} to create a {@link #copy(AbstractValidator)} of this
   *         property with the configured validator.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public ObjectValidatorBuilder<? extends VALUE, ? extends PropertyBuilder<? extends GenericProperty<? extends VALUE>>, ?> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderObject(x));
  }

}
