/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import java.util.Collection;

import net.sf.mmm.util.lang.api.GenericBean;

/**
 * This is the interface for a collection of utility functions to deal with {@link Collection}s reflectively.
 *
 * @see ReflectionUtil
 * @see net.sf.mmm.util.reflect.base.CollectionReflectionUtilImpl
 * @see net.sf.mmm.util.reflect.api.GenericType#getComponentType()
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public interface CollectionReflectionUtil {

  /**
   * This method determines if the given {@code object} is an array or {@link java.util.List}.
   *
   * @param object is the object to check.
   * @return {@code true} if the given object is an array or {@link java.util.List}, {@code false} otherwise.
   */
  boolean isArrayOrList(Object object);

  /**
   * This method gets the size of the given {@code arrayMapOrCollection}. If {@code arrayMapOrCollection} is
   * an array, then its {@link java.lang.reflect.Array#getLength(Object) length} is returned. If it is a
   * {@link java.util.Map} or {@link Collection}, its {@link Collection#size() size} is returned.
   *
   * @param arrayMapOrCollection the array, {@link java.util.Map} or {@link Collection}.
   * @return the length or size of {@code arrayMapOrCollection}.
   * @throws IllegalArgumentException if the given {@code arrayMapOrCollection} is invalid ( {@code null} or
   *         neither array nor {@link java.util.Map} or {@link Collection}).
   */
  int getSize(Object arrayMapOrCollection) throws IllegalArgumentException;

  /**
   * This method gets the item at the given {@code index} from {@code arrayOrCollection}. <br>
   * It sets {@code ignoreIndexOverflow} to {@code true}.
   *
   * @see #get(Object, int, boolean)
   *
   * @param arrayOrList is the array or {@link java.util.List}.
   * @param index is the position of the requested item.
   * @return the item at the given {@code index}. May be {@code null} if the item itself is {@code null} or
   *         the index is greater than the {@link #getSize(Object) size} or {@code arrayOrCollection}.
   * @throws IllegalArgumentException if the given {@code arrayOrList} is invalid ({@code null} or neither
   *         array nor {@link java.util.List}).
   */
  Object get(Object arrayOrList, int index) throws IllegalArgumentException;

  /**
   * This method gets the item at the given {@code index} from {@code arrayOrCollection}.
   *
   * @see java.util.List#get(int)
   *
   * @param arrayOrList is the array or {@link java.util.List}.
   * @param index is the position of the requested item.
   * @param ignoreIndexOverflow - if {@code false} an the given {@code index} is greater or equal to the
   *        {@link #getSize(Object) size} of {@code arrayOrCollection} an {@link IndexOutOfBoundsException}
   *        will be thrown. Else if {@code true}, {@code null} is returned in this case.
   * @return the item at the given {@code index}. May be {@code null} if the item itself is {@code null} or
   *         the index is greater or equal to the {@link #getSize(Object) size} of {@code arrayOrCollection}.
   * @throws IllegalArgumentException if the given {@code arrayOrList} is invalid ({@code null} or neither
   *         array nor {@link java.util.List}).
   */
  Object get(Object arrayOrList, int index, boolean ignoreIndexOverflow) throws IllegalArgumentException;

  /**
   * This method sets the given {@code item} at the given {@code index} in {@code arrayOrCollection}. It uses
   * a default value for {@code maximumGrowth} and no {@code arrayReceiver} ( {@code null}).
   *
   * @see #set(Object, int, Object, GenericBean, int)
   *
   * @param arrayOrList is the array or {@link java.util.List}.
   * @param index is the position where to set the item.
   * @param item is the item to set.
   * @return the item at position {@code index} in {@code arrayOrList} that has been replaced by {@code item}.
   *         This can be {@code null}. Additional if the {@code arrayOrList} has been increased, {@code null}
   *         is returned.
   * @throws IllegalArgumentException if the given {@code arrayOrList} is invalid ({@code null} or neither
   *         array nor {@link java.util.List}).
   */
  Object set(Object arrayOrList, int index, Object item) throws IllegalArgumentException;

  /**
   * This method sets the given {@code item} at the given {@code index} in {@code arrayOrCollection}. If a
   * {@link java.util.List} is given that has a {@link java.util.List#size() size} less or equal to the given
   * {@code index}, the {@link java.util.List#size() size} of the {@link java.util.List} will be increased to
   * {@code index + 1} by {@link java.util.List#add(Object) adding} {@code null} values so the {@code item}
   * can be set. It uses a default value for {@code maximumGrowth}.
   *
   * @see java.util.List#set(int, Object)
   * @see #set(Object, int, Object, GenericBean, int)
   *
   * @param arrayOrList is the array or {@link java.util.List}.
   * @param index is the position where to set the item.
   * @param item is the item to set.
   * @param arrayReceiver is a {@link GenericBean} that allows to receive an
   *        {@link System#arraycopy(Object, int, Object, int, int) array-copy} of {@code arrayOrList} with an
   *        increased {@link java.lang.reflect.Array#getLength(Object) length}. It can be {@code null} to
   *        disable array-copying.
   * @return the item at position {@code index} in {@code arrayOrList} that has been replaced by {@code item}.
   *         This can be {@code null}. Additional if the {@code arrayOrList} has been increased, {@code null}
   *         is returned.
   * @throws IllegalArgumentException if the given {@code arrayOrList} is invalid ({@code null} or neither
   *         array nor {@link java.util.List}).
   */
  Object set(Object arrayOrList, int index, Object item, GenericBean<Object> arrayReceiver) throws IllegalArgumentException;

  /**
   * This method sets the given {@code item} at the given {@code index} in {@code arrayOrCollection}. If a
   * {@link java.util.List} is given that has a {@link java.util.List#size() size} less or equal to the given
   * {@code index}, the {@link java.util.List#size() size} of the {@link java.util.List} will be increased to
   * {@code index + 1} by {@link java.util.List#add(Object) adding} {@code null} values so the {@code item}
   * can be set. However the number of {@link java.util.List#add(Object) adds} is limited to
   * {@code maximumGrowth}.
   *
   * @see java.util.List#set(int, Object)
   *
   * @param arrayOrList is the array or {@link java.util.List}.
   * @param index is the position where to set the item.
   * @param item is the item to set.
   * @param maximumGrowth is the maximum number by which the {@link #getSize(Object) size} of
   *        {@code arrayOrList} will be increased (with {@code null} values) to reach {@code index + 1} so the
   *        {@code item} can be set. Set this value to {@code 0} to turn off this feature (and leave the
   *        {@link #getSize(Object) size} untouched). Please always specify a real maximum
   *        ({@literal <=65536}) and do NOT use {@link Integer#MAX_VALUE} since this might cause memory holes
   *        if something goes wrong. If {@code arrayOrList} is an array, increasing can only happen by
   *        creating a new array. To receive such new array, you need to supply an {@code arrayReceiver}.
   *        Otherwise (if {@code null}) this method behaves for arrays as if {@code maximumGrowth} was
   *        {@code 0}. If an array should be increased, a new array with the size of {@code index + 1} is
   *        created. The original items are {@link System#arraycopy(Object, int, Object, int, int) copied},
   *        the given {@code item} is set on the copy instead while the original array remains unchanged. Then
   *        the new array is {@link GenericBean#setValue(Object) set} to the {@code arrayReceiver}.
   * @param arrayReceiver is a {@link GenericBean} that allows to receive an
   *        {@link System#arraycopy(Object, int, Object, int, int) array-copy} of {@code arrayOrList} with an
   *        increased {@link java.lang.reflect.Array#getLength(Object) length}. It can be {@code null} to
   *        disable array-copying.
   * @return the item at position {@code index} in {@code arrayOrList} that has been replaced by {@code item}.
   *         This can be {@code null}. Additional if the {@code arrayOrList} has been increased, {@code null}
   *         is returned.
   * @throws IllegalArgumentException if the given {@code arrayOrList} is invalid ({@code null} or neither
   *         array nor {@link java.util.List}).
   */
  Object set(Object arrayOrList, int index, Object item, GenericBean<Object> arrayReceiver, int maximumGrowth) throws IllegalArgumentException;

  /**
   * This method adds the given {@code item} to the given {@code arrayOrCollection}.
   *
   * @param arrayOrCollection is the array or {@link Collection}.
   * @param item is the item to add.
   * @return the given {@code arrayOrCollection} if it was a {@link Collection}. Otherwise, in case of an
   *         array, a new array with a {@link java.lang.reflect.Array#getLength(Object) length} increased by
   *         {@code 1} and the elements of {@code arrayOrCollection} appended with the given {@code item} is
   *         returned.
   */
  Object add(Object arrayOrCollection, Object item);

  /**
   * This method removes the given {@code item} from the given {@code arrayOrCollection}.
   *
   * @param arrayOrCollection is the array or {@link Collection}.
   * @param item is the item to remove.
   * @return {@code null} if the given {@code item} was NOT contained in {@code arrayOrCollection}, the given
   *         {@code arrayOrCollection} if it was a {@link Collection} and the {@code item} has been removed.
   *         Otherwise, in case of an array, a new array with a
   *         {@link java.lang.reflect.Array#getLength(Object) length} decreased by {@code 1} and the elements
   *         of {@code arrayOrCollection} without the first occurrence of the given {@code item} is returned.
   */
  Object remove(Object arrayOrCollection, Object item);

  /**
   * This method converts the given {@link Collection} to an array of the given
   * {@link Class#getComponentType() componentType}. This method also allows to create primitive arrays. If
   * NOT required please prefer using {@link #toArrayTyped(Collection, Class)}.
   *
   * @param collection is the {@link Collection} to convert to an array.
   * @param componentType is the {@link Class#getComponentType() component type} of the requested array. If
   *        this type is {@link Class#isPrimitive() primitive}, the according collection-values with be
   *        unboxed.
   * @return the Array of the given {@link Class#getComponentType() componentType} and with the values of the
   *         given {@link Collection}.
   * @throws ClassCastException if the values of the {@link Collection} are NOT compatible with the given
   *         {@link Class#getComponentType() componentType}.
   */
  Object toArray(Collection<?> collection, Class<?> componentType) throws ClassCastException;

  /**
   * This method converts the given {@link Collection} to an array of the given
   * {@link Class#getComponentType() componentType}.
   *
   * @param <T> is the generic type of the {@link Class#getComponentType() componentType}.
   * @param collection is the {@link Collection} to convert to an array.
   * @param componentType is the {@link Class#getComponentType() component type} of the requested array.
   * @return the Array of the given {@link Class#getComponentType() componentType} and with the values of the
   *         given {@link Collection}.
   */
  <T> T[] toArrayTyped(Collection<T> collection, Class<T> componentType);

}
