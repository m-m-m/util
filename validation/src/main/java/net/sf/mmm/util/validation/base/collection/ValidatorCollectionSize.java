/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base.collection;

import java.util.Collection;

import net.sf.mmm.util.validation.base.AbstractValidatorRange;
import net.sf.mmm.util.value.api.NumberRange;
import net.sf.mmm.util.value.api.Range;

/**
 * This is a {@link net.sf.mmm.util.validation.api.ValueValidator} {@link #validate(Collection) validating} that the
 * {@link Collection#size() size} of a given {@link Collection} is within a {@link #ValidatorCollectionSize(Range)
 * predefined} {@link Range}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class ValidatorCollectionSize extends AbstractValidatorRange<Collection<?>, Number> {

  /**
   * The constructor.
   *
   * @param range is the {@link Range} the value has to be {@link Range#isContained(Object) contained in}.
   */
  public ValidatorCollectionSize(Range<Number> range) {
    super(range);
  }

  /**
   *
   * The constructor.
   *
   * @param maxLength the {@link Range#getMax() maximum} {@link Collection#size() size} allowed for the
   *        {@link Collection} values.
   */
  public ValidatorCollectionSize(int maxLength) {
    this(new NumberRange(null, Integer.valueOf(maxLength)));
  }

  @Override
  protected Integer convertValue(Collection<?> value) {

    return Integer.valueOf(value.size());
  }

}
