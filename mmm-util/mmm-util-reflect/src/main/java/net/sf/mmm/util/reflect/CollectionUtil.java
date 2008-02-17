/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.mmm.util.collection.CollectionFactory;
import net.sf.mmm.util.collection.CollectionFactoryManager;
import net.sf.mmm.util.collection.CollectionFactoryManagerImpl;
import net.sf.mmm.util.component.AlreadyInitializedException;
import net.sf.mmm.util.nls.base.NlsIllegalArgumentException;

/**
 * This class is a collection of utility functions for reflectively dealing with
 * {@link Collection}s.
 * 
 * @see ReflectionUtil#getComponentType(java.lang.reflect.Type, boolean)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class CollectionUtil {

  /**
   * The default value for the maximum growth of the {@link List#size() size} of
   * a {@link List}: {@value}
   */
  protected static final int DEFAULT_MAXIMUM_LIST_GROWTH = 128;

  /** @see #getInstance() */
  private static CollectionUtil instance;

  /** @see #getCollectionFactoryManager() */
  private CollectionFactoryManager collectionFactoryManager;

  /**
   * The constructor.
   */
  protected CollectionUtil() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link CollectionUtil}.<br>
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
  public static CollectionUtil getInstance() {

    if (instance == null) {
      synchronized (CollectionUtil.class) {
        if (instance == null) {
          CollectionUtil util = new CollectionUtil();
          util.setCollectionFactoryManager(CollectionFactoryManagerImpl.getInstance());
          instance = util;
        }
      }
    }
    return instance;
  }

  /**
   * This method gets the {@link CollectionFactoryManager} instance used by this
   * util.
   * 
   * @return the {@link CollectionFactoryManager} instance.
   */
  public CollectionFactoryManager getCollectionFactoryManager() {

    return this.collectionFactoryManager;
  }

  /**
   * This method sets the {@link CollectionFactoryManager} instance to use.
   * 
   * @param collectionFactoryManager is the {@link CollectionFactoryManager}
   *        instance.
   */
  @Resource
  public void setCollectionFactoryManager(CollectionFactoryManager collectionFactoryManager) {

    if (this.collectionFactoryManager != null) {
      throw new AlreadyInitializedException();
    }
    this.collectionFactoryManager = collectionFactoryManager;
  }

  /**
   * This method creates a collection implementing the given collection
   * <code>type</code>.
   * 
   * @param <C> is the templated type of the collection.
   * @param type is the type of collection to create. This is either an
   *        interface ({@link java.util.List}, {@link java.util.Set},
   *        {@link java.util.Queue}, etc.) or a non-abstract implementation of
   *        a {@link Collection}.
   * @return the new instance of the given <code>type</code>.
   */
  @SuppressWarnings("unchecked")
  public <C extends Collection> C create(Class<C> type) {

    if (type.isInterface()) {
      CollectionFactory<C> factory = getCollectionFactoryManager().getCollectionFactory(type);
      if (factory == null) {
        throw new NlsIllegalArgumentException(
            NlsBundleUtilReflect.ERR_UNKNOWN_COLLECTION_INTERFACE, type);
      }
      return factory.createGeneric();
    }
    try {
      return type.newInstance();
    } catch (Exception e) {
      throw new InstantiationFailedException(e, type);
    }
  }

  /**
   * This method gets the size of the given <code>arrayMapOrCollection</code>.
   * If <code>arrayMapOrCollection</code> is an array, then its
   * {@link Array#getLength(Object) length} is returned. If it is a {@link Map}
   * or {@link Collection}, its {@link Collection#size() size} is returned.
   * 
   * @param arrayMapOrCollection the array, {@link Map} or {@link Collection}.
   * @return the length or size of <code>arrayMapOrCollection</code>.
   * @throws NlsIllegalArgumentException if the given
   *         <code>arrayMapOrCollection</code> is invalid (<code>null</code>
   *         or neither array nor {@link Map} or {@link Collection}).
   */
  public int getSize(Object arrayMapOrCollection) throws NlsIllegalArgumentException {

    if (arrayMapOrCollection != null) {
      Class<?> type = arrayMapOrCollection.getClass();
      if (type.isArray()) {
        return Array.getLength(arrayMapOrCollection);
      } else if (Collection.class.isAssignableFrom(type)) {
        return ((Collection<?>) arrayMapOrCollection).size();
      } else if (Map.class.isAssignableFrom(type)) {
        return ((Map<?, ?>) arrayMapOrCollection).size();
      }
    }
    throw new NlsIllegalArgumentException(arrayMapOrCollection);
  }

  /**
   * This method gets the item at the given <code>index</code> from
   * <code>arrayOrCollection</code>.
   * 
   * @see List#get(int)
   * 
   * @param arrayOrList is the array or {@link List}.
   * @param index is the position of the requested item.
   * @return the item at the given <code>index</code>.
   * @throws NlsIllegalArgumentException if the given <code>arrayOrList</code>
   *         is invalid (<code>null</code> or neither array nor {@link List}).
   */
  public Object get(Object arrayOrList, int index) throws NlsIllegalArgumentException {

    if (arrayOrList != null) {
      Class<?> type = arrayOrList.getClass();
      if (type.isArray()) {
        return Array.get(arrayOrList, index);
      } else if (List.class.isAssignableFrom(type)) {
        return ((List<?>) arrayOrList).get(index);
      }
    }
    throw new NlsIllegalArgumentException(arrayOrList);
  }

  /**
   * This method sets the given <code>item</code> at the given
   * <code>index</code> in <code>arrayOrCollection</code>. It uses a
   * <code>maximumListGrowth</code> of {@link #DEFAULT_MAXIMUM_LIST_GROWTH}
   * and <code>maximumArrayGrowth</code> of <code>0</code>.
   * 
   * @see #set(Object, int, Object, int, int)
   * 
   * @param arrayOrList is the array or {@link List}.
   * @param index is the position where to set the item.
   * @param item is the item to set.
   * @throws NlsIllegalArgumentException if the given <code>arrayOrList</code>
   *         is invalid (<code>null</code> or neither array nor {@link List}).
   */
  @SuppressWarnings("unchecked")
  public void set(Object arrayOrList, int index, Object item) throws NlsIllegalArgumentException {

    set(arrayOrList, index, item, DEFAULT_MAXIMUM_LIST_GROWTH, 0);
  }

  /**
   * This method sets the given <code>item</code> at the given
   * <code>index</code> in <code>arrayOrCollection</code>. If a
   * {@link List} is given that has a {@link List#size() size} less or equal to
   * the given <code>index</code>, the {@link List#size() size} of the
   * {@link List} will be increased to <code>index + 1</code> by
   * {@link List#add(Object) adding} <code>null</code> values so the
   * <code>item</code> can be set. However the number of
   * {@link List#add(Object) adds} is limited to <code>maximumGrowth</code>.
   * 
   * @see List#set(int, Object)
   * 
   * @param arrayOrList is the array or {@link List}.
   * @param index is the position where to set the item.
   * @param item is the item to set.
   * @param maximumListGrowth is the maximum number by which the
   *        {@link List#size() size} of a {@link List} will be increased to
   *        reach <code>index + 1</code> so the <code>item</code> can be
   *        set. Set this value to <code>0</code> to turn off this feature
   *        (and leave {@link List#size() list-size} untouched). Please always
   *        specify a real maximum (<code>&lt;=65536</code>) and do NOT use
   *        {@link Integer#MAX_VALUE} since this might cause memory holes if
   *        something goes wrong.
   * @param maximumArrayGrowth is the maximum number by which the
   *        {@link Array#getLength(Object) length} of an array will be
   *        "increased" if the <code>item</code> does NOT fit. In this case an
   *        new array will be created with the size of <code>index + 1</code>.
   *        The original items are
   *        {@link System#arraycopy(Object, int, Object, int, int) copied}, the
   *        given <code>item</code> is set on the copy instead and the copy is
   *        returned while the original array remains unchanged. Set this value
   *        to <code>0</code> to turn off this feature. Please always specify
   *        a real maximum (<code>&lt;=65536</code>) and do NOT use
   *        {@link Integer#MAX_VALUE} since this might cause memory holes if
   *        something goes wrong.
   * @return the given <code>arrayOrList</code> or an array-copy with
   *         increased size (as described for parameter
   *         <code>maximumArrayGrowth</code>).
   * @throws NlsIllegalArgumentException if the given <code>arrayOrList</code>
   *         is invalid (<code>null</code> or neither array nor {@link List}).
   */
  @SuppressWarnings("unchecked")
  public Object set(Object arrayOrList, int index, Object item, int maximumListGrowth,
      int maximumArrayGrowth) throws NlsIllegalArgumentException {

    if (arrayOrList == null) {
      throw new NlsIllegalArgumentException(arrayOrList);
    }
    int maximumGrowth;
    Class<?> type = arrayOrList.getClass();
    List list = null;
    int size;
    if (type.isArray()) {
      size = Array.getLength(arrayOrList);
      maximumGrowth = maximumArrayGrowth;
    } else if (List.class.isAssignableFrom(type)) {
      list = (List) arrayOrList;
      size = list.size();
      maximumGrowth = maximumListGrowth;
    } else {
      throw new NlsIllegalArgumentException(arrayOrList);
    }
    int growth = index - size + 1;
    if (growth > maximumGrowth) {
      throw new NlsIllegalArgumentException(NlsBundleUtilReflect.ERR_INCREASE_EXCEEDS_MAX_GROWTH,
          Integer.valueOf(growth), Integer.valueOf(maximumGrowth));
    }
    if (type.isArray()) {
      if (growth > 0) {
        Object newArray = Array.newInstance(type.getComponentType(), index + 1);
        System.arraycopy(arrayOrList, 0, newArray, 0, size);
        Array.set(newArray, index, item);
        return newArray;
      } else {
        Array.set(arrayOrList, index, item);
      }
    } else {
      // arrayOrList is list...
      // increase size of list
      if (growth > 0) {
        growth--;
        while (growth > 0) {
          list.add(null);
          growth--;
        }
        list.add(item);
      } else {
        list.set(index, item);
      }
    }
    return arrayOrList;
  }

  /**
   * This method adds the given <code>item</code> to the given
   * <code>arrayOrCollection</code>.
   * 
   * @param arrayOrCollection is the array or {@link Collection}.
   * @param item is the item to add.
   * @return the given <code>arrayOrCollection</code> if it was a
   *         {@link Collection}. Otherwise, in case of an array, a new array
   *         with a {@link Array#getLength(Object) length} increased by
   *         <code>1</code> and the elements of <code>arrayOrCollection</code>
   *         appended with the given <code>item</code> is returned.
   */
  @SuppressWarnings("unchecked")
  public Object add(Object arrayOrCollection, Object item) {

    if (arrayOrCollection == null) {
      throw new NlsIllegalArgumentException(arrayOrCollection);
    }
    Class<?> type = arrayOrCollection.getClass();
    if (type.isArray()) {
      int size = Array.getLength(arrayOrCollection);
      Object newArray = Array.newInstance(type.getComponentType(), size + 1);
      System.arraycopy(arrayOrCollection, 0, newArray, 0, size);
      Array.set(newArray, size, item);
      return newArray;
    } else if (Collection.class.isAssignableFrom(type)) {
      Collection collection = (Collection) arrayOrCollection;
      collection.add(item);
    } else {
      throw new NlsIllegalArgumentException(arrayOrCollection);
    }
    return arrayOrCollection;
  }

  /**
   * This method removes the given <code>item</code> from the given
   * <code>arrayOrCollection</code>.
   * 
   * @param arrayOrCollection is the array or {@link Collection}.
   * @param item is the item to remove.
   * @return <code>null</code> if the given <code>item</code> was NOT
   *         contained in <code>arrayOrCollection</code>, the given
   *         <code>arrayOrCollection</code> if it was a {@link Collection} and
   *         the <code>item</code> has been removed. Otherwise, in case of an
   *         array, a new array with a {@link Array#getLength(Object) length}
   *         decreased by <code>1</code> and the elements of
   *         <code>arrayOrCollection</code> without the first occurrence of
   *         the given <code>item</code> is returned.
   */
  @SuppressWarnings("unchecked")
  public Object remove(Object arrayOrCollection, Object item) {

    if (arrayOrCollection == null) {
      throw new NlsIllegalArgumentException(arrayOrCollection);
    }
    Class<?> type = arrayOrCollection.getClass();
    if (type.isArray()) {
      int size = Array.getLength(arrayOrCollection);
      for (int index = 0; index < size; index++) {
        Object currentItem = Array.get(arrayOrCollection, index);
        if ((item == currentItem) || ((item != null) && (item.equals(currentItem)))) {
          // found
          Object newArray = Array.newInstance(type.getComponentType(), size - 1);
          System.arraycopy(arrayOrCollection, 0, newArray, 0, index);
          System.arraycopy(arrayOrCollection, index + 1, newArray, index, size - index - 1);
          return newArray;
        }
      }
      return null;
    } else if (Collection.class.isAssignableFrom(type)) {
      Collection collection = (Collection) arrayOrCollection;
      boolean removed = collection.remove(item);
      if (removed) {
        return arrayOrCollection;
      } else {
        return null;
      }
    } else {
      throw new NlsIllegalArgumentException(arrayOrCollection);
    }
  }
}
