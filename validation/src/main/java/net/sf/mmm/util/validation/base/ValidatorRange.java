/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import net.sf.mmm.util.value.api.Range;

/**
 * This is a {@link net.sf.mmm.util.validation.api.ValueValidator} that validates that a given value is within a
 * specific {@link Range}.
 *
 * @param <V> is the generic type of the value to {@link #validate(Object) validate}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class ValidatorRange<V> extends AbstractValidatorRange<V, V> {

  /**
   * The constructor.
   *
   * @param range is the {@link Range} the value has to be within.
   */
  public ValidatorRange(Range<V> range) {

    super(range);
  }

  /**
   * The constructor.
   *
   * @param min is the {@link Range#getMin() minimum} value allowed.
   * @param max is the {@link Range#getMax() maximum} value allowed.
   */
  public ValidatorRange(V min, V max) {

    this(new Range<>(min, max));
  }

}
