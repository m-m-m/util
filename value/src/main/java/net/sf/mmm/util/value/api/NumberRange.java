/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import java.util.Comparator;
import java.util.Objects;

import net.sf.mmm.util.lang.base.NumberComparator;

/**
 * This class extends {@link Range} to allow mixed types of {@link Number} to be used.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class NumberRange extends Range<Number> {

  private static final long serialVersionUID = 1L;

  /**
   * The constructor.
   */
  public NumberRange() {
    super();
  }

  /**
   * The constructor.
   *
   * @param min - see {@link #getMin()}. To create an open range use the minimum value.
   * @param max - see {@link #getMax()}. To create an open range use the maximum value.
   */
  public NumberRange(Number min, Number max) {
    super(min, max);
  }

  @Override
  protected Comparator<? super Number> getComparator() {

    return NumberComparator.getInstance();
  }

  @Override
  public NumberRange withMin(Number minimum) {

    if (Objects.equals(getMin(), minimum)) {
      return this;
    }
    return new NumberRange(minimum, getMax());
  }

  @Override
  public NumberRange withMax(Number maximum) {

    if (Objects.equals(getMax(), maximum)) {
      return this;
    }
    return new NumberRange(getMin(), maximum);
  }

}
