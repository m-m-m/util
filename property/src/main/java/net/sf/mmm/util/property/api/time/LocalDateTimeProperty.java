/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.time;

import java.time.LocalDateTime;
import java.util.Objects;

import net.sf.mmm.util.bean.api.Bean;
import net.sf.mmm.util.property.api.AbstractRegularProperty;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.time.ValidatorBuilderLocalDateTime;

/**
 * This is the implementation of {@link WritableLocalDateTimeProperty}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class LocalDateTimeProperty extends AbstractRegularProperty<LocalDateTime>
    implements WritableLocalDateTimeProperty {

  private LocalDateTime value;

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   */
  public LocalDateTimeProperty(String name, Bean bean) {
    super(name, bean);
  }

  /**
   * The constructor.
   *
   * @param name - see {@link #getName()}.
   * @param bean - see {@link #getBean()}.
   * @param validator - see {@link #validate()}.
   */
  public LocalDateTimeProperty(String name, Bean bean, AbstractValidator<? super LocalDateTime> validator) {
    super(name, bean, validator);
  }

  @Override
  protected LocalDateTime doGetValue() {

    return this.value;
  }

  @Override
  protected boolean doSetValue(LocalDateTime newValue) {

    if (Objects.equals(this.value, newValue)) {
      return false;
    }
    this.value = newValue;
    return true;
  }

  @Override
  public ValidatorBuilderLocalDateTime<PropertyBuilder<LocalDateTimeProperty>> withValdidator() {

    return withValdidator(x -> new ValidatorBuilderLocalDateTime<>(x));
  }

}
