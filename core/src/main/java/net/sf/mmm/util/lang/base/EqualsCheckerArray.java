/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import net.sf.mmm.util.lang.api.AbstractEqualsChecker;
import net.sf.mmm.util.lang.api.EqualsChecker;

/**
 * This is an implementation of {@link EqualsChecker} that recursively checks arrays based on a given
 * {@link EqualsChecker} delegate. So if two objects should be {@link #isEqual(Object, Object) checked for
 * equality} that are both arrays of the same kind, this implementation will check if they have the same
 * length and then recursively {@link #isEqual(Object, Object) check} the contained elements. For objects of
 * other types it will delegate to the {@link EqualsChecker} given at construction. This way you can simply
 * check if two arrays have the {@link net.sf.mmm.util.lang.api.EqualsCheckerIsSame same} or
 * {@link net.sf.mmm.util.lang.api.EqualsCheckerIsEqual equal} elements.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class EqualsCheckerArray extends AbstractEqualsChecker<Object> {

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /** The delegate to check regular objects. */
  private final EqualsChecker<Object> delegate;

  /**
   * The constructor.
   *
   * @param delegate is the {@link EqualsChecker} used to check objects other than arrays.
   */
  public EqualsCheckerArray(EqualsChecker<Object> delegate) {

    super();
    this.delegate = delegate;
  }

  @Override
  protected boolean isEqualNotNull(Object value1, Object value2) {

    Class<? extends Object> class1 = value1.getClass();
    if (class1.isArray()) {
      Class<? extends Object> class2 = value2.getClass();
      if (!class1.equals(class2)) {
        return false;
      }
      if (value1 instanceof Object[]) {
        if (value2 instanceof Object[]) {
          Object[] array1 = (Object[]) value1;
          Object[] array2 = (Object[]) value2;
          if (array1.length != array2.length) {
            return false;
          }
          for (int i = 0; i < array1.length; i++) {
            if (!isEqual(array1[i], array2[i])) {
              return false;
            }
          }
          return true;
        } else {
          return false;
        }
      }
      return isEqualPrimitiveArray(value1, value2);
    }
    return this.delegate.isEqual(value1, value2);
  }

  /**
   * This method is the part of {@link #isEqualNotNull(Object, Object)} for primitive arrays.
   *
   * @param value1 is the first value to check.
   * @param value2 is the first value to check.
   * @return {@code true} if the given values are considered as equal, {@code false} otherwise.
   */
  protected boolean isEqualPrimitiveArray(Object value1, Object value2) {

    if (value1 instanceof int[]) {
      if (value2 instanceof int[]) {
        int[] array1 = (int[]) value1;
        int[] array2 = (int[]) value2;
        if (array1.length != array2.length) {
          return false;
        }
        for (int i = 0; i < array1.length; i++) {
          if (array1[i] != array2[i]) {
            return false;
          }
        }
        return true;
      }
    } else if (value1 instanceof long[]) {
      if (value2 instanceof long[]) {
        long[] array1 = (long[]) value1;
        long[] array2 = (long[]) value2;
        if (array1.length != array2.length) {
          return false;
        }
        for (int i = 0; i < array1.length; i++) {
          if (array1[i] != array2[i]) {
            return false;
          }
        }
        return true;
      }
    } else if (value1 instanceof double[]) {
      if (value2 instanceof double[]) {
        double[] array1 = (double[]) value1;
        double[] array2 = (double[]) value2;
        if (array1.length != array2.length) {
          return false;
        }
        for (int i = 0; i < array1.length; i++) {
          if (array1[i] != array2[i]) {
            return false;
          }
        }
        return true;
      }
    } else if (value1 instanceof boolean[]) {
      if (value2 instanceof boolean[]) {
        boolean[] array1 = (boolean[]) value1;
        boolean[] array2 = (boolean[]) value2;
        if (array1.length != array2.length) {
          return false;
        }
        for (int i = 0; i < array1.length; i++) {
          if (array1[i] != array2[i]) {
            return false;
          }
        }
        return true;
      }
    } else if (value1 instanceof char[]) {
      if (value2 instanceof char[]) {
        char[] array1 = (char[]) value1;
        char[] array2 = (char[]) value2;
        if (array1.length != array2.length) {
          return false;
        }
        for (int i = 0; i < array1.length; i++) {
          if (array1[i] != array2[i]) {
            return false;
          }
        }
        return true;
      }
    } else if (value1 instanceof float[]) {
      if (value2 instanceof float[]) {
        float[] array1 = (float[]) value1;
        float[] array2 = (float[]) value2;
        if (array1.length != array2.length) {
          return false;
        }
        for (int i = 0; i < array1.length; i++) {
          if (array1[i] != array2[i]) {
            return false;
          }
        }
        return true;
      }
    } else if (value1 instanceof short[]) {
      if (value2 instanceof short[]) {
        short[] array1 = (short[]) value1;
        short[] array2 = (short[]) value2;
        if (array1.length != array2.length) {
          return false;
        }
        for (int i = 0; i < array1.length; i++) {
          if (array1[i] != array2[i]) {
            return false;
          }
        }
        return true;
      }
    } else if (value1 instanceof byte[]) {
      if (value2 instanceof byte[]) {
        byte[] array1 = (byte[]) value1;
        byte[] array2 = (byte[]) value2;
        if (array1.length != array2.length) {
          return false;
        }
        for (int i = 0; i < array1.length; i++) {
          if (array1[i] != array2[i]) {
            return false;
          }
        }
        return true;
      }
    }
    return false;
  }
}
