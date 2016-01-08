/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.number;

import java.util.Collection;

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
public class ValidatorBuilderDouble<PARENT>
    extends NumberValidatorBuilder<Double, PARENT, ValidatorBuilderDouble<PARENT>> {

  /**
   * The constructor.
   *
   * @param parent the {@link #and() parent} builder.
   */
  public ValidatorBuilderDouble(PARENT parent) {
    super(parent);
  }

  /**
   * @see #size(Range)
   *
   * @param min the minimum {@link Collection#size() size} allowed.
   * @param max the maximum {@link Collection#size() size} allowed.
   * @return this build instance for fluent API calls.
   */
  public ValidatorBuilderDouble<PARENT> size(double min, double max) {

    return size(new Range<>(Double.valueOf(min), Double.valueOf(max)));
  }

}
