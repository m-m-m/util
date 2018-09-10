/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.mmm.util.collection.api.CollectionFactory;
import net.sf.mmm.util.collection.api.CollectionFactoryManager;
import net.sf.mmm.util.collection.api.MapFactory;
import net.sf.mmm.util.collection.base.ArrayListFactory;
import net.sf.mmm.util.collection.base.ConcurrentHashMapFactory;
import net.sf.mmm.util.collection.base.ConcurrentSkipListMapFactory;
import net.sf.mmm.util.collection.base.HashMapFactory;
import net.sf.mmm.util.collection.base.HashSetFactory;
import net.sf.mmm.util.collection.base.LinkedBlockingQueueFactory;
import net.sf.mmm.util.collection.base.LinkedListDequeFactory;
import net.sf.mmm.util.collection.base.LinkedListQueueFactory;
import net.sf.mmm.util.collection.base.NavigableTreeMapFactory;
import net.sf.mmm.util.collection.base.NavigableTreeSetFactory;
import net.sf.mmm.util.collection.base.TreeSetFactory;
import net.sf.mmm.util.component.base.AbstractComponent;

/**
 * This is the default implementation of the {@link CollectionFactoryManager} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CollectionFactoryManagerImpl extends AbstractComponent implements CollectionFactoryManager {

  private static final Logger LOG = LoggerFactory.getLogger(CollectionFactoryManagerImpl.class);

  private static final Class<?>[] CAPACITY_CONSTRUCTOR_ARGS = new Class<?>[] { int.class };

  private static CollectionFactoryManager instance;

  private final Map<Class<? extends Collection>, CollectionFactory> collectionFactoryMap;

  private final Map<Class<? extends Map>, MapFactory> mapFactoryMap;

  /**
   * The constructor.
   */
  public CollectionFactoryManagerImpl() {

    super();
    this.mapFactoryMap = new HashMap<>();
    this.collectionFactoryMap = new HashMap<>();
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    registerCollectionFactory(ArrayListFactory.INSTANCE, Collection.class);
    registerCollectionFactory(ArrayListFactory.INSTANCE);
    registerCollectionFactory(HashSetFactory.INSTANCE);
    registerCollectionFactory(TreeSetFactory.INSTANCE);
    registerCollectionFactory(LinkedListQueueFactory.INSTANCE);
    registerCollectionFactory(LinkedBlockingQueueFactory.INSTANCE);
    registerCollectionFactory(LinkedListDequeFactory.INSTANCE);
    registerCollectionFactory(NavigableTreeSetFactory.INSTANCE);

    registerMapFactory(HashMapFactory.INSTANCE);
    registerMapFactory(ConcurrentHashMapFactory.INSTANCE);
    registerMapFactory(NavigableTreeMapFactory.INSTANCE);
    registerMapFactory(ConcurrentSkipListMapFactory.INSTANCE);
  }

  /**
   * This method gets the singleton instance of this {@link CollectionFactoryManager} implementation. <br>
   * <b>ATTENTION:</b><br>
   * Please prefer dependency-injection instead of using this method.
   *
   * @return the singleton instance.
   */
  public static CollectionFactoryManager getInstance() {

    if (instance == null) {
      synchronized (CollectionFactoryManagerImpl.class) {
        if (instance == null) {
          CollectionFactoryManagerImpl manager = new CollectionFactoryManagerImpl();
          manager.initialize();
          instance = manager;
        }
      }
    }
    return instance;
  }

  @Override
  public <C extends Collection> CollectionFactory<C> getCollectionFactory(Class<C> collectionType) {

    return this.collectionFactoryMap.get(collectionType);
  }

  @Override
  public <MAP extends Map> MapFactory getMapFactory(Class<MAP> mapType) {

    return this.mapFactoryMap.get(mapType);
  }

  /**
   * This method registers the given {@code factory} using its {@link MapFactory#getMapInterface() map-interface}.
   *
   * @see #registerMapFactory(MapFactory, Class)
   *
   * @param factory is the {@link MapFactory} to register.
   * @return the {@link MapFactory} that has been replaced with {@code factory} or {@code null} if none was replaced.
   */
  protected MapFactory registerMapFactory(MapFactory factory) {

    return registerMapFactory(factory, factory.getMapInterface());
  }

  /**
   * This method registers the given {@code factory} for the given {@code mapInterface}.
   *
   * @param <MAP> is the generic type of the {@code mapInterface}.
   * @param factory is the {@link MapFactory} to register.
   * @param mapInterface is the interface of the associated {@link Map}. It has to be
   *        {@link Class#isAssignableFrom(Class) assignable from} the {@link MapFactory#getMapInterface() map-interface}
   *        of the given {@code factory}.
   * @return the {@link MapFactory} that was registered for the given {@code mapInterface} before and has now been
   *         replaced with {@code factory} or {@code null} if none was replaced.
   */
  protected <MAP extends Map> MapFactory registerMapFactory(MapFactory<? extends MAP> factory, Class<MAP> mapInterface) {

    return this.mapFactoryMap.put(mapInterface, factory);
  }

  /**
   * This method registers the given {@code factory} using its {@link CollectionFactory#getCollectionInterface()
   * collection-interface}.
   *
   * @see #registerCollectionFactory(CollectionFactory, Class)
   *
   * @param factory is the {@link CollectionFactory} to register.
   * @return the {@link CollectionFactory} that has been replaced with {@code factory} or {@code null} if none was
   *         replaced.
   */
  protected CollectionFactory registerCollectionFactory(CollectionFactory factory) {

    return registerCollectionFactory(factory, factory.getCollectionInterface());
  }

  /**
   * This method registers the given {@code factory} for the given {@code collectionInterface}.
   *
   * @param <COLLECTION> is the generic type of the {@code collectionInterface}.
   * @param factory is the {@link CollectionFactory} to register.
   * @param collectionInterface is the interface of the associated {@link Collection}. It has to be
   *        {@link Class#isAssignableFrom(Class) assignable from} the {@link CollectionFactory#getCollectionInterface()
   *        collection-interface} of the given {@code factory}.
   * @return the {@link CollectionFactory} that was registered for the given {@code collectionInterface} before and has
   *         now been replaced with {@code factory} or {@code null} if none was replaced.
   */
  protected <COLLECTION extends Collection> CollectionFactory registerCollectionFactory(CollectionFactory<? extends COLLECTION> factory,
      Class<COLLECTION> collectionInterface) {

    if (!collectionInterface.isAssignableFrom(factory.getCollectionInterface())) {
      throw new IllegalArgumentException();
    }
    return this.collectionFactoryMap.put(collectionInterface, factory);
  }

  @Override
  public <C extends Collection> C create(Class<C> type) {

    return create(type, null);
  }

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
  protected <C extends Collection<?>> C create(Class<C> type, Integer capacity) {

    if (type.isInterface()) {
      CollectionFactory<C> factory = getCollectionFactory(type);
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
          LOG.debug("Failed to create collection via capacitory constructor.", e);
        }
      }
      return type.newInstance();
    } catch (Exception e) {
      throw new IllegalStateException("Failed to create collection of type: " + type, e);
    }
  }

  /**
   * @param type {@link Class} reflecting the {@link Collection}.
   * @return to most specific {@link Collection} {@link Class#isInterface() interface}
   *         {@link Class#isAssignableFrom(Class) assignable from} the given {@code type}.
   */
  protected Class<? extends Collection> findCollectionInterface(Class<? extends Collection> type) {

    // actually a generic approach based on the registered CollectionFactory instances would be better to
    // support
    // additional collection types in a pluggable way (e.g. ObservableList or ObservableSet would already
    // require Java8
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

  @Override
  public <C extends Map> C createMap(Class<C> type) {

    return createMap(type, null);
  }

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
  protected <C extends Map<?, ?>> C createMap(Class<C> type, Integer capacity) {

    if (type.isInterface()) {
      MapFactory<C> factory = getMapFactory(type);
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
          LOG.debug("Failed to create map via capacitory constructor.", e);
        }
      }
      return type.newInstance();
    } catch (Exception e) {
      throw new IllegalStateException("Failed to create collection of type: " + type, e);
    }
  }

  /**
   * @param type {@link Class} reflecting the {@link Map}.
   * @return to most specific {@link Map} {@link Class#isInterface() interface} {@link Class#isAssignableFrom(Class)
   *         assignable from} the given {@code type}.
   */
  protected Class<? extends Map> findMapInterface(Class<? extends Map> type) {

    // actually a generic approach based on the registered CollectionFactory instances would be better to
    // support
    // additional collection types in a pluggable way (e.g. ObservableList or ObservableSet would already
    // require Java8
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

}
