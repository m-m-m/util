/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.time;

import java.time.LocalDateTime;

import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;

/**
 * The {@link net.sf.mmm.util.validation.base.ObjectValidatorBuilder builder} of
 * {@link net.sf.mmm.util.validation.base.AbstractValidator} for a {@link LocalDateTime}.
 *
 * @param <PARENT> the generic type of the {@link #and() parent builder}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public class ValidatorBuilderLocalDateTime<PARENT> extends ValidatorBuilderTime<LocalDateTime, PARENT, ValidatorBuilderLocalDateTime<PARENT>> {

  /**
   * The constructor.
   *
   * @param parent the {@link #and() parent} builder.
   */
  public ValidatorBuilderLocalDateTime(PARENT parent) {
    super(parent);
  }

  @Override
  protected LocalDateTime parse(String value) {

    return LocalDateTime.parse(value);
  }

  @Override
  public ValidatorBuilderLocalDateTime<PARENT> past() {

    return add(new ValidatorLocalDateTimePast());
  }

  @Override
  public ValidatorBuilderLocalDateTime<PARENT> future() {

    return add(new ValidatorLocalDateTimeFuture());
  }

  @Override
  public ValidatorBuilderLocalDateTime<PARENT> after(LocalDateTime value) {

    return add(new ValidatorLocalDateTimeAfter(value));
  }

  @Override
  public ValidatorBuilderLocalDateTime<PARENT> after(AttributeReadValue<LocalDateTime> valueSource) {

    return add(new ValidatorLocalDateTimeAfter(valueSource));
  }

  @Override
  public ValidatorBuilderLocalDateTime<PARENT> before(LocalDateTime value) {

    return add(new ValidatorLocalDateTimeBefore(value));
  }

  @Override
  public ValidatorBuilderLocalDateTime<PARENT> before(AttributeReadValue<LocalDateTime> valueSource) {

    return add(new ValidatorLocalDateTimeBefore(valueSource));
  }

}
