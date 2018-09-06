/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.lang.api.GenericBean;
import net.sf.mmm.util.reflect.api.CollectionReflectionUtil;

/**
 * This is the implementation of the {@link CollectionReflectionUtil} interface.
 *
 * @see #getInstance()
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class CollectionReflectionUtilImpl extends AbstractLoggableComponent implements CollectionReflectionUtil {

  /**
   * The default value for the maximum growth of the {@link #getSize(Object) size} of an array or {@link List}
   * : {@value}
   */
  public static final int DEFAULT_MAXIMUM_LIST_GROWTH = 128;

  private static CollectionReflectionUtilImpl instance;

  private int maximumListGrowth;

  /**
   * The constructor.
   */
  public CollectionReflectionUtilImpl() {

    super();
    this.maximumListGrowth = DEFAULT_MAXIMUM_LIST_GROWTH;
  }

  /**
   * This method gets the singleton instance of this {@link CollectionReflectionUtilImpl}. <br>
   * <b>ATTENTION:</b><br>
   * Please prefer dependency-injection instead of using this method.
   *
   * @return the singleton instance.
   */
  public static CollectionReflectionUtilImpl getInstance() {

    if (instance == null) {
      synchronized (CollectionReflectionUtilImpl.class) {
        if (instance == null) {
          CollectionReflectionUtilImpl util = new CollectionReflectionUtilImpl();
          util.initialize();
          instance = util;
        }
      }
    }
    return instance;
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
  }

  /**
   * This method gets the maximum growth for arrays or {@link List}s.
   *
   * @see #set(Object, int, Object, GenericBean)
   * @see #setMaximumListGrowth(int)
   *
   * @return the maximumListGrowth
   */
  public int getMaximumListGrowth() {

    return this.maximumListGrowth;
  }

  /**
   * This method sets the {@link #getMaximumListGrowth() maximumListGrowth}.
   *
   * @param maximumListGrowth is the maximumListGrowth to set.
   */
  public void setMaximumListGrowth(int maximumListGrowth) {

    getInitializationState().requireNotInitilized();
    this.maximumListGrowth = maximumListGrowth;
  }

  @Override
  public boolean isArrayOrList(Object object) {

    Objects.requireNonNull(object, "object");
    Class<?> type = object.getClass();
    if (type.isArray()) {
      return true;
    } else if (List.class.isAssignableFrom(type)) {
      return true;
    }
    return false;
  }

  @Override
  public int getSize(Object arrayMapOrCollection) {

    Objects.requireNonNull(arrayMapOrCollection, "arrayMapOrCollection");
    Class<?> type = arrayMapOrCollection.getClass();
    if (type.isArray()) {
      return Array.getLength(arrayMapOrCollection);
    } else if (Collection.class.isAssignableFrom(type)) {
      return ((Collection<?>) arrayMapOrCollection).size();
    } else if (Map.class.isAssignableFrom(type)) {
      return ((Map<?, ?>) arrayMapOrCollection).size();
    } else {
      throw new IllegalArgumentException(arrayMapOrCollection.getClass().getName());
    }
  }

  @Override
  public Object get(Object arrayOrList, int index) {

    return get(arrayOrList, index, true);
  }

  @Override
  public Object get(Object arrayOrList, int index, boolean ignoreIndexOverflow) {

    Objects.requireNonNull(arrayOrList, "arrayOrList");
    Class<?> type = arrayOrList.getClass();
    if (type.isArray()) {
      if (ignoreIndexOverflow) {
        int length = Array.getLength(arrayOrList);
        if (index >= length) {
          return null;
        }
      }
      return Array.get(arrayOrList, index);
    } else if (List.class.isAssignableFrom(type)) {
      List<?> list = (List<?>) arrayOrList;
      if (ignoreIndexOverflow) {
        if (index >= list.size()) {
          return null;
        }
      }
      return list.get(index);
    } else {
      throw new IllegalArgumentException(arrayOrList.getClass().getName());
    }
  }

  @Override
  public Object set(Object arrayOrList, int index, Object item) {

    return set(arrayOrList, index, item, null, this.maximumListGrowth);
  }

  @Override
  public Object set(Object arrayOrList, int index, Object item, GenericBean<Object> arrayReceiver) {

    return set(arrayOrList, index, item, arrayReceiver, this.maximumListGrowth);
  }

  @Override
  @SuppressWarnings({ "rawtypes", "unchecked", "null" })
  public Object set(Object arrayOrList, int index, Object item, GenericBean<Object> arrayReceiver, int maximumGrowth) {

    Objects.requireNonNull(arrayOrList, "arrayOrList");
    int maxGrowth = maximumGrowth;
    Class<?> type = arrayOrList.getClass();
    List list = null;
    int size;
    if (type.isArray()) {
      size = Array.getLength(arrayOrList);
      if (arrayReceiver == null) {
        maxGrowth = 0;
      }
    } else if (List.class.isAssignableFrom(type)) {
      list = (List) arrayOrList;
      size = list.size();
    } else {
      throw new IllegalArgumentException(arrayOrList.getClass().getName());
    }
    int growth = index - size + 1;
    if (growth > maxGrowth) {
      throw new ContainerGrowthException(growth, maxGrowth);
    }
    if (type.isArray()) {
      if (growth > 0) {
        if (getLogger().isTraceEnabled()) {
          getLogger().trace("Increasing array size by {}", growth);
        }
        Object newArray = Array.newInstance(type.getComponentType(), index + 1);
        System.arraycopy(arrayOrList, 0, newArray, 0, size);
        Array.set(newArray, index, item);
        arrayReceiver.setValue(newArray);
        return null;
      } else {
        Object old = Array.get(arrayOrList, index);
        Array.set(arrayOrList, index, item);
        return old;
      }
    } else {
      // arrayOrList is list...
      // increase size of list
      if (growth > 0) {
        if (getLogger().isTraceEnabled()) {
          getLogger().trace("Increasing list size by {}", growth);
        }
        growth--;
        while (growth > 0) {
          list.add(null);
          growth--;
        }
        list.add(item);
        return null;
      } else {
        return list.set(index, item);
      }
    }
  }

  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public Object add(Object arrayOrCollection, Object item) {

    Objects.requireNonNull(arrayOrCollection, "arrayOrCollection");
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
      throw new IllegalArgumentException(arrayOrCollection.getClass().getName());
    }
    return arrayOrCollection;
  }

  @Override
  @SuppressWarnings("rawtypes")
  public Object remove(Object arrayOrCollection, Object item) {

    Objects.requireNonNull(arrayOrCollection, "arrayOrCollection");
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
      throw new IllegalArgumentException(arrayOrCollection.getClass().getName());
    }
  }

  @Override
  public Object toArray(Collection<?> collection, Class<?> componentType) throws ClassCastException {

    Objects.requireNonNull(componentType, "componentType");
    if (collection == null) {
      return null;
    }
    int length = collection.size();
    Object array = Array.newInstance(componentType, length);
    Iterator<?> iterator = collection.iterator();
    int i = 0;
    if (componentType.isPrimitive()) {
      while (iterator.hasNext()) {
        Array.set(array, i++, iterator.next());
      }
    } else {
      Object[] objectArray = (Object[]) array;
      while (iterator.hasNext()) {
        objectArray[i++] = iterator.next();
      }
    }
    return array;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T[] toArrayTyped(Collection<T> collection, Class<T> componentType) {

    return (T[]) toArray(collection, componentType);
  }

}
