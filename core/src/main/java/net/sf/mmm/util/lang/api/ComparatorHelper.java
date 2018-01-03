/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * This class contains the implementation of {@link CompareOperator#eval(Object, Object)} for
 * {@link Comparable} arguments. This allows the implementation to be replaced with a GWT compatible one.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
final class ComparatorHelper {

  /**
   * Construction prohibited.
   */
  private ComparatorHelper() {

    super();
  }

  /**
   * This method converts the given value to a more common type. E.g. instances of {@link Calendar} or
   * {@link XMLGregorianCalendar} will be converted to {@link java.util.Date}.
   *
   * @param object is the value to convert.
   * @param otherType the type of the value to compare that differs from the type
   * @return a simpler representation of {@code value} or the same {@code value} if on simpler type is known.
   */
  static Object convert(Object object, Class<?> otherType) {

    if (otherType == Date.class) {
      if (object instanceof Calendar) {
        return ((Calendar) object).getTime();
      }
      if (object instanceof XMLGregorianCalendar) {
        return ((XMLGregorianCalendar) object).toGregorianCalendar().getTime();
      }
    } else if (otherType == BigDecimal.class) {
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
   * Logic for {@link CompareOperator#eval(Object, Object)} with {@link Comparable} arguments.
   *
   * @param comparator is the {@link CompareOperator}.
   * @param arg1 is the first argument.
   * @param arg2 is the second argument.
   * @return the result of the {@link CompareOperator} applied to the given arguments.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  static boolean evalComparable(CompareOperator comparator, Comparable arg1, Comparable arg2) {

    Class<?> type1 = arg1.getClass();
    Class<?> type2 = arg2.getClass();
    int delta;
    if (type1.equals(type2) || type1.isAssignableFrom(type2)) {
      delta = signum(arg1.compareTo(arg2));
    } else if (type2.isAssignableFrom(type1)) {
      delta = -signum(arg2.compareTo(arg1));
    } else {
      // incompatible comparables
      return (arg1.equals(arg2) == comparator.isTrueIfEquals());
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
