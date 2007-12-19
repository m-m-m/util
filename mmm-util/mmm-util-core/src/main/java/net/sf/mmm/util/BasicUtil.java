/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * This class is a collection of utility functions for general purpose
 * operations.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class BasicUtil {

  /** @see #getInstance() */
  private static BasicUtil instance;

  /**
   * The constructor.
   */
  public BasicUtil() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link BasicUtil}.<br>
   * This design is the best compromise between easy access (via this
   * indirection you have direct, static access to all offered functionality)
   * and IoC-style design which allows extension and customization.<br>
   * For IoC usage, simply ignore all static {@link #getInstance()} methods and
   * construct new instances via the container-framework of your choice (like
   * plexus, pico, springframework, etc.). To wire up the dependent components
   * everything is properly annotated using common-annotations (JSR-250). If
   * your container does NOT support this, you should consider using a better
   * one.
   * 
   * @return the singleton instance.
   */
  public static BasicUtil getInstance() {

    if (instance == null) {
      synchronized (BasicUtil.class) {
        if (instance == null) {
          instance = new BasicUtil();
        }
      }
    }
    return instance;
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
      Class<?> class1 = o1.getClass();
      if (class1.isArray()) {
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
          // primitive array
          Class<?> class2 = o2.getClass();
          if ((o2 != null) && (class2.isArray()) && (class1.equals(class2))) {
            int len1 = Array.getLength(o1);
            int len2 = Array.getLength(o2);
            if (len1 == len2) {
              if (o1 instanceof byte[]) {
                return Arrays.equals((byte[]) o1, (byte[]) o2);
              } else if (o1 instanceof int[]) {
                return Arrays.equals((int[]) o1, (int[]) o2);
              } else if (o1 instanceof long[]) {
                return Arrays.equals((long[]) o1, (long[]) o2);
              } else if (o1 instanceof boolean[]) {
                return Arrays.equals((boolean[]) o1, (boolean[]) o2);
              } else if (o1 instanceof double[]) {
                return Arrays.equals((double[]) o1, (double[]) o2);
              } else if (o1 instanceof float[]) {
                return Arrays.equals((float[]) o1, (float[]) o2);
              } else if (o1 instanceof short[]) {
                return Arrays.equals((short[]) o1, (short[]) o2);
              } else if (o1 instanceof char[]) {
                return Arrays.equals((char[]) o1, (char[]) o2);
              }
            }
          }
        }
      } else {
        return o1.equals(o2);
      }
    }
    return false;
  }

}
