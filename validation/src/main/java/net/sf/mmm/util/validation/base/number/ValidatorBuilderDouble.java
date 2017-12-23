/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.number;

import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ObjectValidatorBuilder;
import net.sf.mmm.util.value.api.Range;

/**
 * The {@link ObjectValidatorBuilder builder} of {@link AbstractValidator} for {@link Double} values.
 *
 * @param <PARENT> the generic type of the {@link #and() parent builder}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class ValidatorBuilderDouble<PARENT> extends NumberValidatorBuilder<Double, PARENT, ValidatorBuilderDouble<PARENT>> {

  /**
   * The constructor.
   *
   * @param parent the {@link #and() parent} builder.
   */
  public ValidatorBuilderDouble(PARENT parent) {

    super(parent);
  }

  /**
   * @see #range(Range)
   *
   * @param min the minimum allowed value.
   * @param max the maximum allowed value.
   * @return this build instance for fluent API calls.
   */
  public ValidatorBuilderDouble<PARENT> range(double min, double max) {

    return range(new Range<>(Double.valueOf(min), Double.valueOf(max)));
  }

}
