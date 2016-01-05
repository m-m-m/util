/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.text;

import java.util.regex.Pattern;

import net.sf.mmm.util.validation.base.AbstractValidator;
import net.sf.mmm.util.validation.base.ObjectValidatorBuilder;

/**
 * The abstract base for a {@link ObjectValidatorBuilder builder} of {@link AbstractValidator} for {@link CharSequence}
 * values.
 *
 * @param <V> the generic type of the value to {@link AbstractValidator#validate(Object) validate}.
 * @param <PARENT> the generic type of the {@link #and() parent builder}.
 * @param <SELF> the generic type of this builder itself (this).
 *
 * @author hohwille
 * @since 7.1.0
 */
public abstract class CharSequenceValidatorBuilder<V extends CharSequence, PARENT, SELF extends CharSequenceValidatorBuilder<V, PARENT, SELF>>
    extends ObjectValidatorBuilder<V, PARENT, SELF> {

  /**
   * The constructor.
   *
   * @param parent the {@link #and() parent} builder.
   */
  public CharSequenceValidatorBuilder(PARENT parent) {
    super(parent);
  }

  /**
   * @param pattern the regular expression {@link Pattern} to match.
   * @return this build instance for fluent API calls.
   */
  public SELF pattern(Pattern pattern) {

    return add(new ValidatorPattern(pattern));
  }

}
