/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.TransferQueue;

import javax.inject.Inject;

import javassist.Modifier;
import net.sf.mmm.util.collection.api.CollectionFactory;
import net.sf.mmm.util.collection.api.CollectionFactoryManager;
import net.sf.mmm.util.collection.api.MapFactory;
import net.sf.mmm.util.collection.impl.CollectionFactoryManagerImpl;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.exception.api.NlsNullPointerException;
import net.sf.mmm.util.lang.api.GenericBean;
import net.sf.mmm.util.reflect.api.AccessFailedException;
import net.sf.mmm.util.reflect.api.CollectionReflectionUtil;
import net.sf.mmm.util.reflect.api.InstantiationFailedException;

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
   * The default value for the maximum growth of the {@link #getSize(Object) size} of an array or {@link List} :
   * {@value}
   */
  public static final int DEFAULT_MAXIMUM_LIST_GROWTH = 128;

  private static final Class<?>[] CAPACITY_CONSTRUCTOR_ARGS = new Class<?>[] { int.class };

  private static CollectionReflectionUtilImpl instance;

  private CollectionFactoryManager collectionFactoryManager;

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
   * Please read {@link net.sf.mmm.util.component.api.Cdi#GET_INSTANCE} before using.
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
    if (this.collectionFactoryManager == null) {
      this.collectionFactoryManager = CollectionFactoryManagerImpl.getInstance();
    }
  }

  @Override
  public CollectionFactoryManager getCollectionFactoryManager() {

    return this.collectionFactoryManager;
  }

  /**
   * This method sets the {@link CollectionFactoryManager} instance to use.
   *
   * @param collectionFactoryManager is the {@link CollectionFactoryManager} instance.
   */
  @Inject
  public void setCollectionFactoryManager(CollectionFactoryManager collectionFactoryManager) {

    getInitializationState().requireNotInitilized();
    this.collectionFactoryManager = collectionFactoryManager;
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

  @SuppressWarnings("rawtypes")
  @Override
  public <C extends Collection> C create(Class<C> type) {

    return create(type, null);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public <C extends Collection> C create(Class<C> type, int capacity) {

    return create(type, Integer.valueOf(capacity));
  }

  /**
   * @see #create(Class, int)
   *
   * @param <C> is the generic type of the collection.
   * @param type is the type of collection to create. This is either an interface ({@link java.util.List},
   *        {@link java.util.Set}, {@link java.util.Queue}, etc.) or a non-abstract implementation of a
   *        {@link Collection}.
   * @param capacity is the initial capacity of the collection or {@code null} if unspecified.
   * @return the new instance of the given {@code type}.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected <C extends Collection<?>> C create(Class<C> type, Integer capacity) {

    if (type.isInterface()) {
      CollectionFactory<C> factory = getCollectionFactoryManager().getCollectionFactory(type);
      if (factory == null) {
        throw new UnknownCollectionInterfaceException(type);
      }
      if (capacity == null) {
        return factory.createGeneric();
      } else {
        return factory.createGeneric(capacity.intValue());
      }
    } else if (Modifier.isAbstract(type.getModifiers())) {
      Class<? extends Collection> collectionInterface = findCollectionInterface(type);
      if (collectionInterface != Collection.class) {
        return (C) create(collectionInterface, capacity);
      }
    }

    try {
      if (capacity != null) {
        try {
          Constructor<C> constructor = type.getConstructor(CAPACITY_CONSTRUCTOR_ARGS);
          return constructor.newInstance(capacity);
        } catch (Exception e) {
          getLogger().debug("Failed to create collection via capacitory constructor.", e);
        }
      }
      return type.newInstance();
    } catch (IllegalAccessException e) {
      throw new AccessFailedException(e, type);
    } catch (Exception e) {
      throw new InstantiationFailedException(e, type);
    }
  }

  /**
   * @param type {@link Class} reflecting the {@link Collection}.
   * @return to most specific {@link Collection} {@link Class#isInterface() interface}
   *         {@link Class#isAssignableFrom(Class) assignable from} the given {@code type}.
   */
  @SuppressWarnings("rawtypes")
  protected Class<? extends Collection> findCollectionInterface(Class<? extends Collection> type) {

    // actually a generic approach based on the registered CollectionFactory instances would be better to support
    // additional collection types in a pluggable way (e.g. ObservableList or ObservableSet would already require Java8
    // if hardcoded here).
    if (type.isInterface()) {
      return type;
    } else if (List.class.isAssignableFrom(type)) {
      return List.class;
    } else if (Queue.class.isAssignableFrom(type)) {
      if (BlockingDeque.class.isAssignableFrom(type)) {
        return BlockingDeque.class;
      } else if (Deque.class.isAssignableFrom(type)) {
        return Deque.class;
      } else if (BlockingQueue.class.isAssignableFrom(type)) {
        return BlockingQueue.class;
      } else if (TransferQueue.class.isAssignableFrom(type)) {
        return TransferQueue.class;
      } else {
        return Queue.class;
      }
    } else if (Set.class.isAssignableFrom(type)) {
      if (NavigableSet.class.isAssignableFrom(type)) {
        return NavigableSet.class;
      } else if (SortedSet.class.isAssignableFrom(type)) {
        return SortedSet.class;
      } else {
        return Set.class;
      }
    }
    return Collection.class;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public <C extends Map> C createMap(Class<C> type) {

    return createMap(type, null);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public <C extends Map> C createMap(Class<C> type, int capacity) {

    return createMap(type, Integer.valueOf(capacity));
  }

  /**
   * @see #create(Class, int)
   *
   * @param <C> is the generic type of the collection.
   * @param type is the type of collection to create. This is either an interface ({@link java.util.List},
   *        {@link java.util.Set}, {@link java.util.Queue}, etc.) or a non-abstract implementation of a
   *        {@link Collection}.
   * @param capacity is the initial capacity of the collection or {@code null} if unspecified.
   * @return the new instance of the given {@code type}.
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  protected <C extends Map<?, ?>> C createMap(Class<C> type, Integer capacity) {

    if (type.isInterface()) {
      MapFactory<C> factory = getCollectionFactoryManager().getMapFactory(type);
      if (factory == null) {
        throw new UnknownCollectionInterfaceException(type);
      }
      if (capacity == null) {
        return factory.createGeneric();
      } else {
        return factory.createGeneric(capacity.intValue());
      }
    } else if (Modifier.isAbstract(type.getModifiers())) {
      Class<? extends Map> collectionInterface = findMapInterface(type);
      return (C) createMap(collectionInterface, capacity);
    }

    try {
      if (capacity != null) {
        try {
          Constructor<C> constructor = type.getConstructor(CAPACITY_CONSTRUCTOR_ARGS);
          return constructor.newInstance(capacity);
        } catch (Exception e) {
          getLogger().debug("Failed to create map via capacitory constructor.", e);
        }
      }
      return type.newInstance();
    } catch (IllegalAccessException e) {
      throw new AccessFailedException(e, type);
    } catch (Exception e) {
      throw new InstantiationFailedException(e, type);
    }
  }

  /**
   * @param type {@link Class} reflecting the {@link Map}.
   * @return to most specific {@link Map} {@link Class#isInterface() interface} {@link Class#isAssignableFrom(Class)
   *         assignable from} the given {@code type}.
   */
  @SuppressWarnings("rawtypes")
  protected Class<? extends Map> findMapInterface(Class<? extends Map> type) {

    // actually a generic approach based on the registered CollectionFactory instances would be better to support
    // additional collection types in a pluggable way (e.g. ObservableList or ObservableSet would already require Java8
    // if hardcoded here).
    if (type.isInterface()) {
      return type;
    } else if (ConcurrentNavigableMap.class.isAssignableFrom(type)) {
      return ConcurrentNavigableMap.class;
    } else if (NavigableMap.class.isAssignableFrom(type)) {
      return NavigableMap.class;
    } else if (ConcurrentMap.class.isAssignableFrom(type)) {
      return ConcurrentMap.class;
    }
    return Map.class;
  }

  @Override
  public boolean isArrayOrList(Object object) {

    if (object == null) {
      throw new NlsIllegalArgumentException(null);
    }
    Class<?> type = object.getClass();
    if (type.isArray()) {
      return true;
    } else if (List.class.isAssignableFrom(type)) {
      return true;
    }
    return false;
  }

  @Override
  public int getSize(Object arrayMapOrCollection) throws NlsIllegalArgumentException {

    if (arrayMapOrCollection == null) {
      throw new NlsIllegalArgumentException(null);
    }
    Class<?> type = arrayMapOrCollection.getClass();
    if (type.isArray()) {
      return Array.getLength(arrayMapOrCollection);
    } else if (Collection.class.isAssignableFrom(type)) {
      return ((Collection<?>) arrayMapOrCollection).size();
    } else if (Map.class.isAssignableFrom(type)) {
      return ((Map<?, ?>) arrayMapOrCollection).size();
    } else {
      throw new NlsIllegalArgumentException(arrayMapOrCollection);
    }
  }

  @Override
  public Object get(Object arrayOrList, int index) throws NlsIllegalArgumentException {

    return get(arrayOrList, index, true);
  }

  @Override
  public Object get(Object arrayOrList, int index, boolean ignoreIndexOverflow)
      throws NlsIllegalArgumentException {

    if (arrayOrList == null) {
      throw new NlsNullPointerException("arrayOrList");
    }
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
      throw new NlsIllegalArgumentException(arrayOrList);
    }
  }

  @Override
  public Object set(Object arrayOrList, int index, Object item) throws NlsIllegalArgumentException {

    return set(arrayOrList, index, item, null, this.maximumListGrowth);
  }

  @Override
  public Object set(Object arrayOrList, int index, Object item, GenericBean<Object> arrayReceiver)
      throws NlsIllegalArgumentException {

    return set(arrayOrList, index, item, arrayReceiver, this.maximumListGrowth);
  }

  @Override
  @SuppressWarnings({ "rawtypes", "unchecked", "null" })
  public Object set(Object arrayOrList, int index, Object item, GenericBean<Object> arrayReceiver,
      int maximumGrowth) throws NlsIllegalArgumentException {

    if (arrayOrList == null) {
      throw new NlsNullPointerException("arrayOrList");
    }
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
      throw new NlsIllegalArgumentException(arrayOrList);
    }
    int growth = index - size + 1;
    if (growth > maxGrowth) {
      throw new ContainerGrowthException(growth, maxGrowth);
    }
    if (type.isArray()) {
      if (growth > 0) {
        if (getLogger().isTraceEnabled()) {
          getLogger().trace("Increasing array size by " + growth);
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
          getLogger().trace("Increasing list size by " + growth);
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

    if (arrayOrCollection == null) {
      throw new NlsIllegalArgumentException(null);
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

  @Override
  @SuppressWarnings("rawtypes")
  public Object remove(Object arrayOrCollection, Object item) {

    if (arrayOrCollection == null) {
      throw new NlsIllegalArgumentException(null);
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

  @Override
  public Object toArray(Collection<?> collection, Class<?> componentType) throws ClassCastException {

    if (componentType == null) {
      throw new NlsNullPointerException("componentType");
    }
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
