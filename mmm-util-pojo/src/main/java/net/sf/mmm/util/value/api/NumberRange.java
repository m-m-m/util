/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import java.util.Comparator;

import net.sf.mmm.util.lang.base.NumberComparator;

/**
 * This class extends {@link Range} to allow mixed types of {@link Number} to be used.
 *
 * @author hohwille
 * @since 7.1.0
 */
public class NumberRange extends Range<Number> {

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

}
