/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import java.lang.reflect.Array;
import java.util.Arrays;

import javax.inject.Named;
import javax.inject.Singleton;

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
@Singleton
@Named
public class BasicUtilImpl implements BasicUtil {

  /** @see #getInstance() */
  private static BasicUtil instance;

  /**
   * The constructor.
   */
  public BasicUtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link BasicUtilImpl}.<br>
   * This design is the best compromise between easy access (via this indirection you have direct, static
   * access to all offered functionality) and IoC-style design which allows extension and customization.<br>
   * For IoC usage, simply ignore all static {@link #getInstance()} methods and construct new instances via
   * the container-framework of your choice (like plexus, pico, springframework, etc.). To wire up the
   * dependent components everything is properly annotated using common-annotations (JSR-250). If your
   * container does NOT support this, you should consider using a better one.
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

  /**
   * {@inheritDoc}
   */
  public boolean isEqual(Object o1, Object o2) {

    if (o1 == null) {
      return (o2 == null);
    } else {
      return o1.equals(o2);
    }
  }

  /**
   * {@inheritDoc}
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
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
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

  /**
   * {@inheritDoc}
   */
  public boolean isInArray(Object value, Object[] array, boolean checkEqual) {

    return (findInArray(value, array, checkEqual) >= 0);
  }

  /**
   * {@inheritDoc}
   */
  public boolean isInArray(Object value, Object array, boolean checkEqual) {

    return (findInArray(value, array, checkEqual) >= 0);
  }

  /**
   * {@inheritDoc}
   */
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
