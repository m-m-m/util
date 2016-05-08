/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.util.Arrays;

import net.sf.mmm.util.lang.api.BasicUtil;
import net.sf.mmm.util.lang.api.CharIterator;

/**
 * This is the implementation of the {@link BasicUtil} interface.
 *
 * @see #getInstance()
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class BasicUtilImpl implements BasicUtil {

  private static BasicUtil instance;

  /**
   * The constructor.
   */
  public BasicUtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link BasicUtilImpl}. <br>
   * <b>ATTENTION:</b><br>
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
   *
   * @return the singleton instance.
   */
  public static BasicUtil getInstance() {

    if (instance == null) {
      synchronized (BasicUtilImpl.class) {
        if (instance == null) {
          instance = new BasicUtilImpl();
        }
      }
    }
    return instance;
  }

  @Override
  public boolean isEqual(Object o1, Object o2) {

    if (o1 == null) {
      return (o2 == null);
    } else {
      return o1.equals(o2);
    }
  }

  @Override
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

  @Override
  public boolean isDeepEqual(Object o1, Object o2) {

    if (o1 == o2) {
      return true;
    } else if (o1 != null) {
      if (o2 == null) {
        return false;
      }
      Class<?> class1 = o1.getClass();
      if (class1.isArray()) {
        if (o1 instanceof Object[]) {
          if (o2 instanceof Object[]) {
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
          if ((class2.isArray()) && (class1.equals(class2))) {
            if (o1 instanceof byte[]) {
              byte[] bytes1 = (byte[]) o1;
              byte[] bytes2 = (byte[]) o2;
              if (bytes1.length != bytes2.length) {
                return false;
              }
              return Arrays.equals(bytes1, bytes2);
            } else if (o1 instanceof int[]) {
              int[] ints1 = (int[]) o1;
              int[] ints2 = (int[]) o2;
              if (ints1.length != ints2.length) {
                return false;
              }
              return Arrays.equals(ints1, ints2);
            } else if (o1 instanceof long[]) {
              long[] longs1 = (long[]) o1;
              long[] longs2 = (long[]) o2;
              if (longs1.length != longs2.length) {
                return false;
              }
              return Arrays.equals(longs1, longs2);
            } else if (o1 instanceof boolean[]) {
              boolean[] booleans1 = (boolean[]) o1;
              boolean[] booleans2 = (boolean[]) o2;
              if (booleans1.length != booleans2.length) {
                return false;
              }
              return Arrays.equals(booleans1, booleans2);
            } else if (o1 instanceof double[]) {
              double[] doubles1 = (double[]) o1;
              double[] doubles2 = (double[]) o2;
              if (doubles1.length != doubles2.length) {
                return false;
              }
              return Arrays.equals(doubles1, doubles2);
            } else if (o1 instanceof float[]) {
              float[] floats1 = (float[]) o1;
              float[] floats2 = (float[]) o2;
              if (floats1.length != floats2.length) {
                return false;
              }
              return Arrays.equals(floats1, floats2);
            } else if (o1 instanceof short[]) {
              short[] shorts1 = (short[]) o1;
              short[] shorts2 = (short[]) o2;
              if (shorts1.length != shorts2.length) {
                return false;
              }
              return Arrays.equals(shorts1, shorts2);
            } else if (o1 instanceof char[]) {
              char[] chars1 = (char[]) o1;
              char[] chars2 = (char[]) o2;
              if (chars1.length != chars2.length) {
                return false;
              }
              return Arrays.equals(chars1, chars2);
            }
          }
        }
      } else {
        return o1.equals(o2);
      }
    }
    return false;
  }

  @Override
  public int findInArray(Object value, Object[] array, boolean checkEqual) {

    int length = array.length;
    for (int i = 0; i < length; i++) {
      Object current = array[i];
      if ((value == current) || (checkEqual && value.equals(current))) {
        return i;
      }
    }
    return -1;

  }

  @Override
  public int findInArray(Object value, Object array, boolean checkEqual) {

    if (array instanceof Object[]) {
      return findInArray(value, (Object[]) array, checkEqual);
    }
    int length = Array.getLength(array);
    for (int i = 0; i < length; i++) {
      Object current = Array.get(array, i);
      if ((value == current) || (checkEqual && value.equals(current))) {
        return i;
      }
    }
    return -1;

  }

  @Override
  public boolean isInArray(Object value, Object[] array, boolean checkEqual) {

    return (findInArray(value, array, checkEqual) >= 0);
  }

  @Override
  public boolean isInArray(Object value, Object array, boolean checkEqual) {

    return (findInArray(value, array, checkEqual) >= 0);
  }

  @Override
  public boolean compare(CharIterator charIterator1, CharIterator charIterator2) {

    char c1, c2;
    do {
      c1 = charIterator1.next();
      c2 = charIterator2.next();
      if (c1 != c2) {
        return false;
      }
    } while (c1 != CharIterator.END_OF_ITERATOR);
    return true;
  }

}
