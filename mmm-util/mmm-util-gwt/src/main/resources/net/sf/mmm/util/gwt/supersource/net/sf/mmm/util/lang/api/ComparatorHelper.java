/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This class contains the GWT compatible implementation of {@link Comparator#eval(Object, Object)} for
 * {@link Comparable} arguments.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
class ComparatorHelper {

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
      delta = arg1.compareTo(arg2);
    } catch (ClassCastException e) {
      // delta = -arg2.compareTo(arg1);

      // incompatible comparables
      return ((arg1.equals(arg2)) == comparator.isTrueIfEquals());
    }
    if (delta == 0) {
      // arg1 == arg2
      return comparator.isTrueIfEquals();
    } else if (delta < 0) {
      // arg1 < arg2
      return comparator.isTrueIfLess();
    } else {
      // arg1 > arg2
      return comparator.isTrueIfGreater();
    }
  }
}
