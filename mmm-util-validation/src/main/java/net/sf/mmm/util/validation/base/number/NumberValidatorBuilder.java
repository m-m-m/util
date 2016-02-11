/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.number;

import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.CompareableValidatorBuilder;
import net.sf.mmm.util.validation.base.ObjectValidatorBuilder;
import net.sf.mmm.util.validation.base.ValidatorRange;
import net.sf.mmm.util.value.api.NumberRange;

/**
 * The {@link ObjectValidatorBuilder builder} of {@link AbstractValidator} for {@link Number} values.
 *
 * @param <V> the generic type of the value to {@link AbstractValidator#validate(Object) validate}.
 * @param <PARENT> the generic type of the {@link #and() parent builder}.
 * @param <SELF> the generic type of this builder itself (this).
 *
 * @author hohwille
 * @since 7.1.0
 */
@SuppressWarnings("rawtypes")
public class NumberValidatorBuilder<V extends Number & Comparable, PARENT, SELF extends CompareableValidatorBuilder<V, PARENT, SELF>>
    extends CompareableValidatorBuilder<V, PARENT, SELF> {

  /**
   * The constructor.
   *
   * @param parent the {@link #and() parent} builder.
   */
  public NumberValidatorBuilder(PARENT parent) {
    super(parent);
  }

  @Override
  public SELF range(String min, String max) {

    Double dMin = null;
    if (min != null) {
      dMin = Double.valueOf(min);
    }
    Double dMax = null;
    if (max != null) {
      dMax = Double.valueOf(max);
    }
    add(new ValidatorRange<>(new NumberRange(dMin, dMax)));
    return self();
  }

}
