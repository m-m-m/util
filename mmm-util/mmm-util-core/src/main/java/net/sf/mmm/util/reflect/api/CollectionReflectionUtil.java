/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import java.util.Collection;

import net.sf.mmm.util.collection.api.CollectionFactoryManager;
import net.sf.mmm.util.component.base.ComponentSpecification;
import net.sf.mmm.util.lang.api.GenericBean;
import net.sf.mmm.util.nls.api.NlsIllegalArgumentException;

/**
 * This is the interface for a collection of utility functions to deal with {@link Collection}s reflectively.
 * 
 * @see net.sf.mmm.util.reflect.base.CollectionReflectionUtilImpl
 * @see net.sf.mmm.util.reflect.api.GenericType#getComponentType()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
@ComponentSpecification
public interface CollectionReflectionUtil {

  /**
   * This method gets the {@link CollectionFactoryManager} instance used by this util.
   * 
   * @return the {@link CollectionFactoryManager} instance.
   */
  CollectionFactoryManager getCollectionFactoryManager();

  /**
   * This method creates a collection implementing the given collection <code>type</code>.
   * 
   * @param <C> is the generic type of the collection.
   * @param type is the type of collection to create. This is either an interface ({@link java.util.List},
   *        {@link java.util.Set}, {@link java.util.Queue}, etc.) or a non-abstract implementation of a
   *        {@link Collection}.
   * @return the new instance of the given <code>type</code>.
   */
  @SuppressWarnings("rawtypes")
  <C extends Collection> C create(Class<C> type);

  /**
   * This method determines if the given <code>object</code> is an array or {@link java.util.List}.
   * 
   * @param object is the object to check.
   * @return <code>true</code> if the given object is an array or {@link java.util.List}, <code>false</code>
   *         otherwise.
   */
  boolean isArrayOrList(Object object);

  /**
   * This method gets the size of the given <code>arrayMapOrCollection</code>. If
   * <code>arrayMapOrCollection</code> is an array, then its {@link java.lang.reflect.Array#getLength(Object)
   * length} is returned. If it is a {@link java.util.Map} or {@link Collection}, its
   * {@link Collection#size() size} is returned.
   * 
   * @param arrayMapOrCollection the array, {@link java.util.Map} or {@link Collection}.
   * @return the length or size of <code>arrayMapOrCollection</code>.
   * @throws NlsIllegalArgumentException if the given <code>arrayMapOrCollection</code> is invalid (
   *         <code>null</code> or neither array nor {@link java.util.Map} or {@link Collection}).
   */
  int getSize(Object arrayMapOrCollection) throws NlsIllegalArgumentException;

  /**
   * This method gets the item at the given <code>index</code> from <code>arrayOrCollection</code>.<br>
   * It sets <code>ignoreIndexOverflow</code> to <code>true</code>.
   * 
   * @see #get(Object, int, boolean)
   * 
   * @param arrayOrList is the array or {@link java.util.List}.
   * @param index is the position of the requested item.
   * @return the item at the given <code>index</code>. May be <code>null</code> if the item itself is
   *         <code>null</code> or the index is greater than the {@link #getSize(Object) size} or
   *         <code>arrayOrCollection</code>.
   * @throws NlsIllegalArgumentException if the given <code>arrayOrList</code> is invalid (<code>null</code>
   *         or neither array nor {@link java.util.List}).
   */
  Object get(Object arrayOrList, int index) throws NlsIllegalArgumentException;

  /**
   * This method gets the item at the given <code>index</code> from <code>arrayOrCollection</code>.
   * 
   * @see java.util.List#get(int)
   * 
   * @param arrayOrList is the array or {@link java.util.List}.
   * @param index is the position of the requested item.
   * @param ignoreIndexOverflow - if <code>false</code> an the given <code>index</code> is greater or equal to
   *        the {@link #getSize(Object) size} of <code>arrayOrCollection</code> an
   *        {@link IndexOutOfBoundsException} will be thrown. Else if <code>true</code>, <code>null</code> is
   *        returned in this case.
   * @return the item at the given <code>index</code>. May be <code>null</code> if the item itself is
   *         <code>null</code> or the index is greater or equal to the {@link #getSize(Object) size} of
   *         <code>arrayOrCollection</code>.
   * @throws NlsIllegalArgumentException if the given <code>arrayOrList</code> is invalid (<code>null</code>
   *         or neither array nor {@link java.util.List}).
   */
  Object get(Object arrayOrList, int index, boolean ignoreIndexOverflow) throws NlsIllegalArgumentException;

  /**
   * This method sets the given <code>item</code> at the given <code>index</code> in
   * <code>arrayOrCollection</code>. It uses a default value for <code>maximumGrowth</code> and no
   * <code>arrayReceiver</code> ( <code>null</code>).
   * 
   * @see #set(Object, int, Object, GenericBean, int)
   * 
   * @param arrayOrList is the array or {@link java.util.List}.
   * @param index is the position where to set the item.
   * @param item is the item to set.
   * @return the item at position <code>index</code> in <code>arrayOrList</code> that has been replaced by
   *         <code>item</code>. This can be <code>null</code>. Additional if the <code>arrayOrList</code> has
   *         been increased, <code>null</code> is returned.
   * @throws NlsIllegalArgumentException if the given <code>arrayOrList</code> is invalid (<code>null</code>
   *         or neither array nor {@link java.util.List}).
   */
  Object set(Object arrayOrList, int index, Object item) throws NlsIllegalArgumentException;

  /**
   * This method sets the given <code>item</code> at the given <code>index</code> in
   * <code>arrayOrCollection</code>. If a {@link java.util.List} is given that has a
   * {@link java.util.List#size() size} less or equal to the given <code>index</code>, the
   * {@link java.util.List#size() size} of the {@link java.util.List} will be increased to
   * <code>index + 1</code> by {@link java.util.List#add(Object) adding} <code>null</code> values so the
   * <code>item</code> can be set. It uses a default value for <code>maximumGrowth</code>.
   * 
   * @see java.util.List#set(int, Object)
   * @see #set(Object, int, Object, GenericBean, int)
   * 
   * @param arrayOrList is the array or {@link java.util.List}.
   * @param index is the position where to set the item.
   * @param item is the item to set.
   * @param arrayReceiver is a {@link GenericBean} that allows to receive an
   *        {@link System#arraycopy(Object, int, Object, int, int) array-copy} of <code>arrayOrList</code>
   *        with an increased {@link java.lang.reflect.Array#getLength(Object) length}. It can be
   *        <code>null</code> to disable array-copying.
   * @return the item at position <code>index</code> in <code>arrayOrList</code> that has been replaced by
   *         <code>item</code>. This can be <code>null</code>. Additional if the <code>arrayOrList</code> has
   *         been increased, <code>null</code> is returned.
   * @throws NlsIllegalArgumentException if the given <code>arrayOrList</code> is invalid (<code>null</code>
   *         or neither array nor {@link java.util.List}).
   */
  Object set(Object arrayOrList, int index, Object item, GenericBean<Object> arrayReceiver)
      throws NlsIllegalArgumentException;

  /**
   * This method sets the given <code>item</code> at the given <code>index</code> in
   * <code>arrayOrCollection</code>. If a {@link java.util.List} is given that has a
   * {@link java.util.List#size() size} less or equal to the given <code>index</code>, the
   * {@link java.util.List#size() size} of the {@link java.util.List} will be increased to
   * <code>index + 1</code> by {@link java.util.List#add(Object) adding} <code>null</code> values so the
   * <code>item</code> can be set. However the number of {@link java.util.List#add(Object) adds} is limited to
   * <code>maximumGrowth</code>.
   * 
   * @see java.util.List#set(int, Object)
   * 
   * @param arrayOrList is the array or {@link java.util.List}.
   * @param index is the position where to set the item.
   * @param item is the item to set.
   * @param maximumGrowth is the maximum number by which the {@link #getSize(Object) size} of
   *        <code>arrayOrList</code> will be increased (with <code>null</code> values) to reach
   *        <code>index + 1</code> so the <code>item</code> can be set. Set this value to <code>0</code> to
   *        turn off this feature (and leave the {@link #getSize(Object) size} untouched). Please always
   *        specify a real maximum (<code>&lt;=65536</code>) and do NOT use {@link Integer#MAX_VALUE} since
   *        this might cause memory holes if something goes wrong. If <code>arrayOrList</code> is an array,
   *        increasing can only happen by creating a new array. To receive such new array, you need to supply
   *        an <code>arrayReceiver</code>. Otherwise (if <code>null</code>) this method behaves for arrays as
   *        if <code>maximumGrowth</code> was <code>0</code>. If an array should be increased, a new array
   *        with the size of <code>index + 1</code> is created. The original items are
   *        {@link System#arraycopy(Object, int, Object, int, int) copied}, the given <code>item</code> is set
   *        on the copy instead while the original array remains unchanged. Then the new array is
   *        {@link GenericBean#setValue(Object) set} to the <code>arrayReceiver</code>.
   * @param arrayReceiver is a {@link GenericBean} that allows to receive an
   *        {@link System#arraycopy(Object, int, Object, int, int) array-copy} of <code>arrayOrList</code>
   *        with an increased {@link java.lang.reflect.Array#getLength(Object) length}. It can be
   *        <code>null</code> to disable array-copying.
   * @return the item at position <code>index</code> in <code>arrayOrList</code> that has been replaced by
   *         <code>item</code>. This can be <code>null</code>. Additional if the <code>arrayOrList</code> has
   *         been increased, <code>null</code> is returned.
   * @throws NlsIllegalArgumentException if the given <code>arrayOrList</code> is invalid (<code>null</code>
   *         or neither array nor {@link java.util.List}).
   */
  Object set(Object arrayOrList, int index, Object item, GenericBean<Object> arrayReceiver, int maximumGrowth)
      throws NlsIllegalArgumentException;

  /**
   * This method adds the given <code>item</code> to the given <code>arrayOrCollection</code>.
   * 
   * @param arrayOrCollection is the array or {@link Collection}.
   * @param item is the item to add.
   * @return the given <code>arrayOrCollection</code> if it was a {@link Collection}. Otherwise, in case of an
   *         array, a new array with a {@link java.lang.reflect.Array#getLength(Object) length} increased by
   *         <code>1</code> and the elements of <code>arrayOrCollection</code> appended with the given
   *         <code>item</code> is returned.
   */
  Object add(Object arrayOrCollection, Object item);

  /**
   * This method removes the given <code>item</code> from the given <code>arrayOrCollection</code>.
   * 
   * @param arrayOrCollection is the array or {@link Collection}.
   * @param item is the item to remove.
   * @return <code>null</code> if the given <code>item</code> was NOT contained in
   *         <code>arrayOrCollection</code>, the given <code>arrayOrCollection</code> if it was a
   *         {@link Collection} and the <code>item</code> has been removed. Otherwise, in case of an array, a
   *         new array with a {@link java.lang.reflect.Array#getLength(Object) length} decreased by
   *         <code>1</code> and the elements of <code>arrayOrCollection</code> without the first occurrence of
   *         the given <code>item</code> is returned.
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
