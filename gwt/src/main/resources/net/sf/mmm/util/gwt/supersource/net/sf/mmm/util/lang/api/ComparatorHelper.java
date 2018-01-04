/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * This class contains the GWT compatible implementation of {@link Comparator#eval(Object, Object)} for
 * {@link Comparable} arguments.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
class ComparatorHelper {

  /**
   * This method converts the given value to a more common type.
   *
   * @param object is the value to convert.
   * @param otherType the type of the value to compare that differs from the type
   * @return a simpler representation of {@code value} or the same {@code value} if on simpler type
   *         is known.
   */
  static Object convert(Object object, Class<?> otherType) {

    if (otherType == BigDecimal.class) {
      if (object instanceof BigInteger) {
        return new BigDecimal((BigInteger) object);
      } else if (object instanceof Number) {
        return BigDecimal.valueOf(((Number) object).doubleValue());
      }
    } else if (otherType == BigInteger.class) {
      if (!(object instanceof BigDecimal) && (object instanceof Number)) {
        return BigInteger.valueOf(((Number) object).longValue());
      }
    }
    return object;
  }

  /**
   * Logic for {@link Comparator#eval(Object, Object)} with {@link Comparable} arguments.
   *
   * @param comparator is the {@link Comparator}.
   * @param arg1 is the first argument.
   * @param arg2 is the second argument.
   * @return the result of the {@link Comparator} applied to the given arguments.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  static boolean evalComparable(Comparator comparator, Comparable arg1, Comparable arg2) {

    int delta;
    try {
      delta = signum(arg1.compareTo(arg2));
    } catch (ClassCastException e) {
      // delta = -arg2.compareTo(arg1);

      // incompatible comparables
      return ((arg1.equals(arg2)) == comparator.isTrueIfEquals());
    }
    return comparator.eval(delta);
  }

  private static int signum(int delta) {

    if (delta < 0) {
      return -1;
    } else if (delta > 0) {
      return 1;
    } else {
      return 0;
    }
  }
}
