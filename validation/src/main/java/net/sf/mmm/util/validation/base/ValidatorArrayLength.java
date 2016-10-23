/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.lang.reflect.Array;
import java.util.Map;

import net.sf.mmm.util.value.api.NumberRange;
import net.sf.mmm.util.value.api.Range;

/**
 * This is a {@link net.sf.mmm.util.validation.api.ValueValidator} {@link #validate(Object) validating} that the
 * {@link Array#getLength(Object) length} of a given array is within a {@link #ValidatorArrayLength(Range) predefined}
 * {@link Range}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class ValidatorArrayLength extends AbstractValidatorRange<Object, Number> {

  /**
   * The constructor.
   *
   * @param range is the {@link Range} the value has to be {@link Range#isContained(Object) contained in}.
   */
  public ValidatorArrayLength(Range<Number> range) {
    super(range);
  }

  /**
   *
   * The constructor.
   *
   * @param maxLength the {@link Range#getMax() maximum} {@link Map#size() size} allowed for the {@link Map} values.
   */
  public ValidatorArrayLength(int maxLength) {
    this(new NumberRange(null, Integer.valueOf(maxLength)));
  }

  @Override
  protected Integer convertValue(Object value) {

    return Integer.valueOf(Array.getLength(value));
  }

}
