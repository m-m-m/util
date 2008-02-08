/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.mmm.util.collection.CollectionFactory;
import net.sf.mmm.util.collection.CollectionFactoryManager;
import net.sf.mmm.util.collection.CollectionFactoryManagerImpl;
import net.sf.mmm.util.component.AlreadyInitializedException;
import net.sf.mmm.util.nls.base.NlsIllegalArgumentException;

/**
 * This class is a collection of utility functions for reflectively dealing with
 * {@link Collections}.
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
  public static CollectionUtil instance;

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
   *        interface ({@link List}, {@link Set}, {@link Queue}, etc.) or a
   *        non-abstract implementation of a {@link Collection}.
   * @return the new instance of the given <code>type</code>.
   */
  @SuppressWarnings("unchecked")
  public <C extends Collection> C create(Class<C> type) {

    if (type.isInterface()) {
      CollectionFactory<C> factory = getCollectionFactoryManager().getFactory(type);
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
   * <code>maximumGrowth</code> of {@link #DEFAULT_MAXIMUM_LIST_GROWTH}.
   * 
   * @see #set(Object, int, Object, int)
   * 
   * @param arrayOrList is the array or {@link List}.
   * @param index is the position where to set the item.
   * @param item is the item to set.
   * @throws NlsIllegalArgumentException if the given <code>arrayOrList</code>
   *         is invalid (<code>null</code> or neither array nor {@link List}).
   */
  @SuppressWarnings("unchecked")
  public void set(Object arrayOrList, int index, Object item) throws NlsIllegalArgumentException {

    set(arrayOrList, index, item, DEFAULT_MAXIMUM_LIST_GROWTH);
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
   * @param maximumGrowth is the maximum number by which the
   *        {@link List#size() size} of a {@link List} will be increased to
   *        reach <code>index + 1</code> so the <code>item</code> can be
   *        set. Set this value to <code>0</code> to turn of this feature
   *        (leave {@link List#size() list-size} untouched). Please always
   *        specify a real maximum (<code>&lt;=65536</code>) and do NOT use
   *        {@link Integer#MAX_VALUE} since this might cause memory holes if
   *        something goes wrong.
   * @throws NlsIllegalArgumentException if the given <code>arrayOrList</code>
   *         is invalid (<code>null</code> or neither array nor {@link List}).
   */
  @SuppressWarnings("unchecked")
  public void set(Object arrayOrList, int index, Object item, int maximumGrowth)
      throws NlsIllegalArgumentException {

    if (arrayOrList != null) {
      Class<?> type = arrayOrList.getClass();
      if (type.isArray()) {
        Array.set(arrayOrList, index, item);
        return;
      } else if (List.class.isAssignableFrom(type)) {
        List list = (List) arrayOrList;
        int growth = index - list.size() + 1;
        if (growth > 0) {
          if (growth > maximumGrowth) {
            throw new NlsIllegalArgumentException("Can not increase size of list by " + growth
                + ", because limit is" + maximumGrowth);
          } else {
            growth--;
            while (growth > 0) {
              list.add(null);
              growth--;
            }
            list.add(item);
          }
        } else {
          list.set(index, item);
        }
        return;
      }
    }
    throw new NlsIllegalArgumentException(arrayOrList);
  }

}
