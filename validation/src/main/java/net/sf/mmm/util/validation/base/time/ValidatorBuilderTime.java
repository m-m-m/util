/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.time;

import net.sf.mmm.util.lang.api.attribute.AttributeReadValue;
import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ObjectValidatorBuilder;
import net.sf.mmm.util.validation.base.ValidatorRange;

/**
 * This is the abstract base implementation of {@link ObjectValidatorBuilder} for date and time values.
 *
 * @param <V> the generic type of the value to {@link AbstractValidator#validate(Object) validate}.
 * @param <PARENT> the generic type of the {@link #and() parent builder}.
 * @param <SELF> the generic type of this builder itself (this).
 *
 * @author hohwille
 * @since 8.4.0
 */
public abstract class ValidatorBuilderTime<V, PARENT, SELF extends ValidatorBuilderTime<V, PARENT, SELF>> extends ObjectValidatorBuilder<V, PARENT, SELF> {

  /**
   * The constructor.
   *
   * @param parent the {@link #and() parent} builder.
   */
  public ValidatorBuilderTime(PARENT parent) {
    super(parent);
  }

  /**
   * @param value the value as {@link String}.
   * @return the parsed value.
   */
  protected abstract V parse(String value);

  @Override
  public SELF range(String min, String max) {

    V minimum = null;
    if (min != null) {
      minimum = parse(min);
    }
    V maximum = null;
    if (max != null) {
      maximum = parse(max);
    }
    return range(minimum, maximum);
  }

  /**
   * @param min the {@link net.sf.mmm.util.value.api.Range#getMinimumValue() lower bound}.
   * @param max the {@link net.sf.mmm.util.value.api.Range#getMaximumValue() upper bound}.
   * @return this build instance for fluent API calls.
   */
  public SELF range(V min, V max) {

    if ((min == null) && (max == null)) {
      return self();
    }
    return add(new ValidatorRange<>(min, max));
  }

  /**
   * {@link #add(AbstractValidator) Adds} a validator that checks that the date/time is in the past.
   *
   * @return this build instance for fluent API calls.
   */
  public abstract SELF past();

  /**
   * {@link #add(AbstractValidator) Adds} a validator that checks that the date/time is in the future.
   *
   * @return this build instance for fluent API calls.
   */
  public abstract SELF future();

  /**
   * {@link #add(AbstractValidator) Adds} a validator that checks that the date/time is after the given {@code value}.
   *
   * @param value the date/time to compare.
   * @return this build instance for fluent API calls.
   */
  public abstract SELF after(V value);

  /**
   * {@link #add(AbstractValidator) Adds} a validator that checks that the date/time is after the given {@code value}.
   *
   * @param valueSource the {@link AttributeReadValue source} of the date/time to compare.
   * @return this build instance for fluent API calls.
   */
  public abstract SELF after(AttributeReadValue<V> valueSource);

  /**
   * {@link #add(AbstractValidator) Adds} a validator that checks that the date/time is before the given {@code value}.
   *
   * @param value the date/time to compare.
   * @return this build instance for fluent API calls.
   */
  public abstract SELF before(V value);

  /**
   * {@link #add(AbstractValidator) Adds} a validator that checks that the date/time is before the given {@code value}.
   *
   * @param valueSource the {@link AttributeReadValue source} of the date/time to compare.
   * @return this build instance for fluent API calls.
   */
  public abstract SELF before(AttributeReadValue<V> valueSource);

}
