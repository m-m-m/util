/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.time;

import java.time.LocalDate;

import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;

/**
 * The {@link net.sf.mmm.util.validation.base.ObjectValidatorBuilder builder} of
 * {@link net.sf.mmm.util.validation.base.AbstractValidator} for a {@link LocalDate}.
 *
 * @param <PARENT> the generic type of the {@link #and() parent builder}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public class ValidatorBuilderLocalDate<PARENT> extends ValidatorBuilderTime<LocalDate, PARENT, ValidatorBuilderLocalDate<PARENT>> {

  /**
   * The constructor.
   *
   * @param parent the {@link #and() parent} builder.
   */
  public ValidatorBuilderLocalDate(PARENT parent) {
    super(parent);
  }

  @Override
  protected LocalDate parse(String value) {

    return LocalDate.parse(value);
  }

  @Override
  public ValidatorBuilderLocalDate<PARENT> past() {

    return add(new ValidatorLocalDatePast());
  }

  @Override
  public ValidatorBuilderLocalDate<PARENT> future() {

    return add(new ValidatorLocalDateFuture());
  }

  @Override
  public ValidatorBuilderLocalDate<PARENT> after(LocalDate value) {

    return add(new ValidatorLocalDateAfter(value));
  }

  @Override
  public ValidatorBuilderLocalDate<PARENT> after(AttributeReadValue<LocalDate> valueSource) {

    return add(new ValidatorLocalDateAfter(valueSource));
  }

  @Override
  public ValidatorBuilderLocalDate<PARENT> before(LocalDate value) {

    return add(new ValidatorLocalDateBefore(value));
  }

  @Override
  public ValidatorBuilderLocalDate<PARENT> before(AttributeReadValue<LocalDate> valueSource) {

    return add(new ValidatorLocalDateBefore(valueSource));
  }

}
