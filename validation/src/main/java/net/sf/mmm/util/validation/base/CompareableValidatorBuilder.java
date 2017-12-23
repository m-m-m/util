/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import net.sf.mmm.util.validation.base.collection.ValidatorCollectionSize;
import net.sf.mmm.util.value.api.Range;

/**
 * The {@link ObjectValidatorBuilder builder} of {@link AbstractValidator} for {@link Comparable} values.
 *
 * @param <V> the generic type of the value to {@link AbstractValidator#validate(Object) validate}.
 * @param <PARENT> the generic type of the {@link #and() parent builder}.
 * @param <SELF> the generic type of this builder itself (this).
 *
 * @author hohwille
 * @since 7.1.0
 */
@SuppressWarnings("rawtypes")
public abstract class CompareableValidatorBuilder<V extends Comparable, PARENT, SELF extends CompareableValidatorBuilder<V, PARENT, SELF>>
    extends ObjectValidatorBuilder<V, PARENT, SELF> {

  /**
   * The constructor.
   *
   * @param parent the {@link #and() parent} builder.
   */
  public CompareableValidatorBuilder(PARENT parent) {

    super(parent);
  }

  /**
   * @see ValidatorCollectionSize
   *
   * @param range the {@link Range} to limit the value (or its size).
   * @return this build instance for fluent API calls.
   */
  public SELF range(Range<V> range) {

    return add(new ValidatorRange<>(range));
  }

}
