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

    return java.lang.reflect.Array.getLength(array);
  }

  /**
   * @see java.lang.reflect.Array#get(Object, int)
   * 
   * @param array is the supposed array.
   * @param index is the index in <code>array</code> to get the value for.
   * @return the value at <code>index</code> in the given <code>array</code>.
   */
  public static Object get(Object array, int index) {

    return java.lang.reflect.Array.get(array, index);
  }

  /**
   * @see java.lang.reflect.Array#set(Object, int, Object)
   * 
   * @param array is the supposed array.
   * @param index is the index in <code>array</code> to get the value for.
   * @param value is the value to set at <code>index</code> in the given <code>array</code>.
   */
  public static void set(Object array, int index, Object value) {

    java.lang.reflect.Array.set(array, index, value);
  }
}
