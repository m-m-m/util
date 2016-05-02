/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.time;

import java.time.LocalDate;
import java.util.Objects;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.AbstractRegularProperty;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.time.ValidatorBuilderLocalDate;

/**
 * This is the implementation of {@link WritableLocalDateProperty}.
 *
 * @author hohwille
 * @since 8.0.0
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

}
