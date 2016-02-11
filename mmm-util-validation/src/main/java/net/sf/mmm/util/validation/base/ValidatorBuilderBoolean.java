/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

/**
 * The {@link ObjectValidatorBuilder builder} of {@link AbstractValidator} for {@link String} values.
 *
 * @param <PARENT> the generic type of the {@link #and() parent builder}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class ValidatorBuilderBoolean<PARENT>
    extends ObjectValidatorBuilder<Boolean, PARENT, ValidatorBuilderBoolean<PARENT>> {

  /**
   * The constructor.
   *
   * @param parent the {@link #and() parent} builder.
   */
  public ValidatorBuilderBoolean(PARENT parent) {
    super(parent);
  }

  @Override
  public ValidatorBuilderBoolean<PARENT> range(String min, String max) {

    throw new UnsupportedOperationException("Min/max constraints not avilable for Boolean");
  }

}
