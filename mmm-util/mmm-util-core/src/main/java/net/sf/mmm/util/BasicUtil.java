/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util;

/**
 * This class is a collection of utility functions for general purpose
 * operations.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class BasicUtil {

  /**
   * This is the singleton instance of this {@link BasicUtil}. Instead of
   * declaring the methods static, we declare this static instance what gives
   * the same way of access while still allowing a design for extension by
   * inheriting from this class.
   */
  public static final BasicUtil INSTANCE = new BasicUtil();

  /**
   * The constructor.
   */
  public BasicUtil() {

    super();
  }

  /**
   * This method checks if two given objects are
   * {@link Object#equals(Object) equal} to each other. In advance to
   * {@link Object#equals(Object)} the objects may be <code>null</code>.
   * 
   * @param o1 the first object to compare. It may be <code>null</code>.
   * @param o2 the second object to compare. It may be <code>null</code>.
   * @return <code>true</code> if both objects are <code>null</code> or the
   *         first is NOT <code>null</code> and <code>o1.equals(o2)</code>,
   *         <code>false</code> otherwise.
   */
  public boolean isEqual(Object o1, Object o2) {

    if (o1 == null) {
      return (o2 == null);
    } else {
      return o1.equals(o2);
    }
  }

  /**
   * This method checks if two given arrays are structurally
   * {@link Object#equals(Object) equal} to each other. On arrays the
   * {@link Object#equals(Object) equals}-method only checks for identity. This
   * method checks that both arrays have the same <code>length</code> and if
   * so that the objects contained in the arrays are
   * {@link #isEqual(Object, Object) equal}. Additionally the given arrays may
   * be <code>null</code>.
   * 
   * @param array1 the first array to compare. It may be <code>null</code>.
   * @param array2 the second array to compare. It may be <code>null</code>.
   * @return <code>true</code> if both arrays are <code>null</code> or the
   *         first is NOT <code>null</code> and both arrays have the same
   *         <code>length</code> and {@link #isEqual(Object, Object) equal}
   *         content, <code>false</code> otherwise.
   */
  public boolean isEqual(Object[] array1, Object[] array2) {

    if (array1 == array2) {
      return true;
    } else if ((array1 != null) && (array2 != null) && (array1.length == array2.length)) {
      for (int i = 0; i < array1.length; i++) {
        if (!isEqual(array1[i], array2[i])) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  /**
   * This method checks if two given objects are
   * {@link Object#equals(Object) equal} to each other. In advance to
   * {@link Object#equals(Object)} the objects may be <code>null</code> and
   * arrays are {@link #isEqual(Object[], Object[]) compared structural}
   * recursively.
   * 
   * @param o1 the first object to compare. It may be <code>null</code>.
   * @param o2 the second object to compare. It may be <code>null</code>.
   * @return <code>true</code> if both objects are <code>null</code> or the
   *         first is NOT <code>null</code> and <code>o1.equals(o2)</code>,
   *         <code>false</code> otherwise.
   */
  public boolean isDeepEqual(Object o1, Object o2) {

    if (o1 == o2) {
      return true;
    } else if (o1 != null) {
      if (o1 instanceof Object[]) {
        if ((o2 != null) && (o2 instanceof Object[])) {
          Object[] array1 = (Object[]) o1;
          Object[] array2 = (Object[]) o2;
          if (array1.length == array2.length) {
            for (int i = 0; i < array1.length; i++) {
              if (!isDeepEqual(array1[i], array2[i])) {
                return false;
              }
            }
            return true;
          }
        }
      } else {
        return o1.equals(o2);
      }
    }
    return false;
  }

}
