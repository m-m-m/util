/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.number;

import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ObjectValidatorBuilder;
import net.sf.mmm.util.value.api.Range;

/**
 * The {@link ObjectValidatorBuilder builder} of {@link AbstractValidator} for {@link Long} values.
 *
 * @param <PARENT> the generic type of the {@link #and() parent builder}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class ValidatorBuilderLong<PARENT> extends NumberValidatorBuilder<Long, PARENT, ValidatorBuilderLong<PARENT>> {

  /**
   * The constructor.
   *
   * @param parent the {@link #and() parent} builder.
   */
  public ValidatorBuilderLong(PARENT parent) {

    super(parent);
  }

  /**
   * @see #range(Range)
   *
   * @param min the minimum allowed value.
   * @param max the maximum allowed value.
   * @return this build instance for fluent API calls.
   */
  public ValidatorBuilderLong<PARENT> range(long min, long max) {

    return range(new Range<>(Long.valueOf(min), Long.valueOf(max)));
  }

}
