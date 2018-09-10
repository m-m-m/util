/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.util.Comparator;

/**
 * This is an implementation of {@link Comparator} for instances of {@link Number}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public final class NumberComparator implements Comparator<Number> {

  private static final NumberComparator INSTANCE = new NumberComparator();

  private NumberComparator() {
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public int compare(Number o1, Number o2) {

    if (o1 == null) {
      if (o2 == null) {
        return 0;
      } else {
        return 1;
      }
    } else if (o2 == null) {
      return -1;
    }
    if ((o1 instanceof Comparable) && (o1.getClass() == o2.getClass())) {
      return ((Comparable) o1).compareTo(o2);
    }
    double d1 = o1.doubleValue();
    double d2 = o2.doubleValue();
    if (d1 == d2) {
      return 0;
    }
    if (d1 < d2) {
      return -1;
    }
    return 1;
  }

  /**
   * @return the singleton instance of {@link NumberComparator}.
   */
  public static NumberComparator getInstance() {

    return INSTANCE;
  }

}