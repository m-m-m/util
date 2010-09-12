/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This is an implementation of {@link net.sf.mmm.util.value.api.ValueValidator}
 * that {@link #validate(Number, Object) validates} if a {@link Number} is in a
 * specific range.
 * 
 * @see ValueOutOfRangeException#checkRange(Number, Number, Number, Object)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class ValueValidatorNumberRange extends AbstractValueValidator<Number> {

  /** @see #ValueValidatorNumberRange(Double, Double) */
  private final Double minimum;

  /** @see #ValueValidatorNumberRange(Double, Double) */
  private final Double maximum;

  /**
   * The constructor.
   * 
   * @param minimum is the minimum value that is {@link #validate(Number) valid}
   *        or <code>null</code> for no minimum.
   * @param maximum is the maximum value that is {@link #validate(Number) valid}
   *        or <code>null</code> for no maximum.
   */
  public ValueValidatorNumberRange(Double minimum, Double maximum) {

    super();
    this.minimum = minimum;
    this.maximum = maximum;
  }

  /**
   * {@inheritDoc}
   */
  public void validate(Number value, Object valueSource) throws RuntimeException {

    ValueOutOfRangeException.checkRange(value, this.minimum, this.maximum, valueSource);
  }

}
