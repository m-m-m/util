/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

/**
 * Reduced variant of {@link java.lang.reflect.Array} that is GWT compatible.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public final class Array {

  /**
   * Constructor prohibited.
   */
  private Array() {

    super();
  }

  /**
   * @see java.lang.reflect.Array#getLength(Object)
   *
   * @param array is the supposed array.
   * @return the length of the given <code>array</code>.
   */
  public static int getLength(Object array) {

    if (array instanceof Object[]) {
      return ((Object[]) array).length;
    } else if (array instanceof byte[]) {
      return ((byte[]) array).length;
    } else if (array instanceof int[]) {
      return ((int[]) array).length;
    } else if (array instanceof long[]) {
      return ((long[]) array).length;
    } else if (array instanceof boolean[]) {
      return ((boolean[]) array).length;
    } else if (array instanceof double[]) {
      return ((double[]) array).length;
    } else if (array instanceof float[]) {
      return ((float[]) array).length;
    } else if (array instanceof short[]) {
      return ((short[]) array).length;
    } else if (array instanceof char[]) {
      return ((char[]) array).length;
    }
    assert (!array.getClass().isArray());
    throw new IllegalArgumentException(array.getClass().toString());
  }

  /**
   * @see java.lang.reflect.Array#get(Object, int)
   *
   * @param array is the supposed array.
   * @param index is the index in <code>array</code> to get the value for.
   * @return the value at <code>index</code> in the given <code>array</code>.
   */
  public static Object get(Object array, int index) {

    if (array instanceof Object[]) {
      return ((Object[]) array)[index];
    } else if (array instanceof byte[]) {
      return Byte.valueOf(((byte[]) array)[index]);
    } else if (array instanceof int[]) {
      return Integer.valueOf(((int[]) array)[index]);
    } else if (array instanceof long[]) {
      return Long.valueOf(((long[]) array)[index]);
    } else if (array instanceof boolean[]) {
      return Boolean.valueOf(((boolean[]) array)[index]);
    } else if (array instanceof double[]) {
      return Double.valueOf(((double[]) array)[index]);
    } else if (array instanceof float[]) {
      return Float.valueOf(((float[]) array)[index]);
    } else if (array instanceof short[]) {
      return Short.valueOf(((short[]) array)[index]);
    } else if (array instanceof char[]) {
      return Character.valueOf(((char[]) array)[index]);
    }
    assert (!array.getClass().isArray());
    throw new IllegalArgumentException(array.getClass().toString());
  }

  /**
   * @see java.lang.reflect.Array#set(Object, int, Object)
   *
   * @param array is the supposed array.
   * @param index is the index in <code>array</code> to get the value for.
   * @param value is the value to set at <code>index</code> in the given <code>array</code>.
   */
  public static void set(Object array, int index, Object value) {

    if (array instanceof Object[]) {
      ((Object[]) array)[index] = value;
    } else if (array instanceof byte[]) {
      ((byte[]) array)[index] = ((Byte) value).byteValue();
    } else if (array instanceof int[]) {
      ((int[]) array)[index] = ((Integer) value).intValue();
    } else if (array instanceof long[]) {
      ((long[]) array)[index] = ((Long) value).longValue();
    } else if (array instanceof boolean[]) {
      ((boolean[]) array)[index] = ((Boolean) value).booleanValue();
    } else if (array instanceof double[]) {
      ((double[]) array)[index] = ((Double) value).doubleValue();
    } else if (array instanceof float[]) {
      ((float[]) array)[index] = ((Float) value).floatValue();
    } else if (array instanceof short[]) {
      ((short[]) array)[index] = ((Byte) value).byteValue();
    } else if (array instanceof char[]) {
      ((char[]) array)[index] = ((Character) value).charValue();
    }
    assert (!array.getClass().isArray());
    throw new IllegalArgumentException(array.getClass().toString());
  }
}
