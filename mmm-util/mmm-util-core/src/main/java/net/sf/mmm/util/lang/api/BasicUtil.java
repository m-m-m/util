/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This is the interface for a collection of utility functions for very general
 * operations especially for dealing with Arrays.
 * 
 * @see net.sf.mmm.util.lang.base.BasicUtilImpl#getInstance()
 * @see java.util.Arrays
 * @see java.lang.reflect.Array
 * @see net.sf.mmm.util.reflect.api.ReflectionUtil
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public interface BasicUtil {

  /**
   * This method checks if two given objects are {@link Object#equals(Object)
   * equal} to each other. In advance to {@link Object#equals(Object)} the
   * objects may be <code>null</code>.
   * 
   * @see #isDeepEqual(Object, Object)
   * 
   * @param o1 the first object to compare. It may be <code>null</code>.
   * @param o2 the second object to compare. It may be <code>null</code>.
   * @return <code>true</code> if both objects are <code>null</code> or the
   *         first is NOT <code>null</code> and <code>o1.equals(o2)</code>,
   *         <code>false</code> otherwise.
   */
  boolean isEqual(Object o1, Object o2);

  /**
   * This method checks if two given arrays are structurally
   * {@link Object#equals(Object) equal} to each other. On arrays the
   * {@link Object#equals(Object) equals}-method only checks for identity. This
   * method checks that both arrays have the same <code>length</code> and if so
   * that the objects contained in the arrays are
   * {@link #isEqual(Object, Object) equal}. Additionally the given arrays may
   * be <code>null</code>.
   * 
   * @param array1 the first array to compare. It may be <code>null</code>.
   * @param array2 the second array to compare. It may be <code>null</code>.
   * @return <code>true</code> if both arrays are <code>null</code> or the first
   *         is NOT <code>null</code> and both arrays have the same
   *         <code>length</code> and {@link #isEqual(Object, Object) equal}
   *         content, <code>false</code> otherwise.
   */
  boolean isEqual(Object[] array1, Object[] array2);

  /**
   * This method checks if two given objects are {@link Object#equals(Object)
   * equal} to each other. In advance to {@link Object#equals(Object)} the
   * objects may be <code>null</code> and arrays are
   * {@link #isEqual(Object[], Object[]) compared structural} recursively.
   * 
   * @param o1 the first object to compare. It may be <code>null</code>.
   * @param o2 the second object to compare. It may be <code>null</code>.
   * @return <code>true</code> if both objects are <code>null</code> or the
   *         first is NOT <code>null</code> and <code>o1.equals(o2)</code>,
   *         <code>false</code> otherwise.
   */
  boolean isDeepEqual(Object o1, Object o2);

  /**
   * This method returns the index of the first element in the given
   * <code>array</code> that equals (see <code>checkEqual</code>) to the given
   * <code>value</code>.
   * 
   * @param value is the value to check. It may be <code>null</code>.
   * @param array is the array to check.
   * @param checkEqual - if <code>true</code> the <code>value</code> only needs
   *        to be {@link #isEqual(Object, Object) equal} to an element, if
   *        <code>false</code> it needs to be the same (<code>==</code>).
   * @return the according index or <code>-1</code> if <code>value</code> is NOT
   *         contained in <code>array</code>.
   */
  int findInArray(Object value, Object[] array, boolean checkEqual);

  /**
   * This method returns the index of the first element in the given
   * <code>array</code> that equals (see <code>checkEqual</code>) to the given
   * <code>value</code>.<br>
   * Unlike {@link #findInArray(Object, Object[], boolean)} this method also
   * works for {@link Class#isPrimitive() primitive} arrays (such as
   * <code>int[]</code>). In such case you need to supply the according
   * {@link net.sf.mmm.util.reflect.base.ReflectionUtilImpl#getNonPrimitiveType(Class)
   * object-type} for <code>value</code>.
   * 
   * @param value is the value to check. It may be <code>null</code>.
   * @param array is the array to check.
   * @param checkEqual - if <code>true</code> the <code>value</code> only needs
   *        to be {@link #isEqual(Object, Object) equal} to an element, if
   *        <code>false</code> it needs to be the same (<code>==</code>).
   * @return the according index or <code>-1</code> if <code>value</code> is NOT
   *         contained in <code>array</code>.
   */
  int findInArray(Object value, Object array, boolean checkEqual);

  /**
   * This method determines if the given <code>array</code> contains an element
   * that equals (see <code>checkEqual</code>) to the given <code>value</code>.
   * 
   * @see #findInArray(Object, Object[], boolean)
   * 
   * @param value is the value to check. It may be <code>null</code>.
   * @param array is the array to check.
   * @param checkEqual - if <code>true</code> the <code>value</code> only needs
   *        to be {@link #isEqual(Object, Object) equal} to an element, if
   *        <code>false</code> it needs to be the same (<code>==</code>).
   * @return <code>true</code> if the <code>array</code> contains
   *         <code>value</code>.
   */
  boolean isInArray(Object value, Object[] array, boolean checkEqual);

  /**
   * This method determines if the given <code>array</code> contains an element
   * that equals (see <code>checkEqual</code>) to the given <code>value</code>.
   * 
   * @see #findInArray(Object, Object, boolean)
   * 
   * @param value is the value to check. It may be <code>null</code>.
   * @param array is the array to check.
   * @param checkEqual - if <code>true</code> the <code>value</code> only needs
   *        to be {@link #isEqual(Object, Object) equal} to an element, if
   *        <code>false</code> it needs to be the same (<code>==</code>).
   * @return <code>true</code> if the <code>array</code> contains
   *         <code>value</code>.
   */
  boolean isInArray(Object value, Object array, boolean checkEqual);

  /**
   * This method compares the given {@link CharIterator} instances char by char.
   * 
   * @param charIterator1 is the first {@link CharIterator}.
   * @param charIterator2 is the second {@link CharIterator}.
   * @return <code>true</code> if both {@link CharIterator}s produced the same
   *         {@link CharIterator#next() next} chars until both ended at the same
   *         time.
   */
  boolean compare(CharIterator charIterator1, CharIterator charIterator2);

}
