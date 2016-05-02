/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.text;

import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ObjectValidatorBuilder;
import net.sf.mmm.util.validation.base.ValidatorRange;
import net.sf.mmm.util.value.api.Range;

/**
 * The {@link ObjectValidatorBuilder builder} of {@link AbstractValidator} for {@link String} values.
 *
 * @param <PARENT> the generic type of the {@link #and() parent builder}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class ValidatorBuilderString<PARENT>
    extends CharSequenceValidatorBuilder<String, PARENT, ValidatorBuilderString<PARENT>> {

  /**
   * The constructor.
   *
   * @param parent the {@link #and() parent} builder.
   */
  public ValidatorBuilderString(PARENT parent) {
    super(parent);
  }

  @Override
  public ValidatorBuilderString<PARENT> range(String min, String max) {

    add(new ValidatorRange<>(new Range<>(min, max)));
    return self();
  }
}
