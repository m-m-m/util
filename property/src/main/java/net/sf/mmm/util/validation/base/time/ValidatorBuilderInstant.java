/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.time;

import java.time.Instant;

import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;

/**
 * The {@link net.sf.mmm.util.validation.base.ObjectValidatorBuilder builder} of
 * {@link net.sf.mmm.util.validation.base.AbstractValidator} for a {@link Instant}.
 *
 * @param <PARENT> the generic type of the {@link #and() parent builder}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public class ValidatorBuilderInstant<PARENT>
    extends ValidatorBuilderTime<Instant, PARENT, ValidatorBuilderInstant<PARENT>> {

  /**
   * The constructor.
   *
   * @param parent the {@link #and() parent} builder.
   */
  public ValidatorBuilderInstant(PARENT parent) {
    super(parent);
  }

  @Override
  protected Instant parse(String value) {

    return Instant.parse(value);
  }

  @Override
  public ValidatorBuilderInstant<PARENT> past() {

    return add(new ValidatorInstantPast());
  }

  @Override
  public ValidatorBuilderInstant<PARENT> future() {

    return add(new ValidatorInstantFuture());
  }

  @Override
  public ValidatorBuilderInstant<PARENT> after(Instant value) {

    return add(new ValidatorInstantAfter(value));
  }

  @Override
  public ValidatorBuilderInstant<PARENT> after(AttributeReadValue<Instant> valueSource) {

    return add(new ValidatorInstantAfter(valueSource));
  }

  @Override
  public ValidatorBuilderInstant<PARENT> before(Instant value) {

    return add(new ValidatorInstantBefore(value));
  }

  @Override
  public ValidatorBuilderInstant<PARENT> before(AttributeReadValue<Instant> valueSource) {

    return add(new ValidatorInstantBefore(valueSource));
  }

}
