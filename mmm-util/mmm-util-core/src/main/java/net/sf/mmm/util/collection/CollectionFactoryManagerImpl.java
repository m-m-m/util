/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the default implementation of the {@link CollectionFactoryManager}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("unchecked")
public class CollectionFactoryManagerImpl implements CollectionFactoryManager {

  /** @see #getInstance() */
  private static CollectionFactoryManager instance;

  /** @see #getCollectionFactory(Class) */
  private final Map<Class<? extends Collection>, CollectionFactory> collectionFactoryMap;

  /** @see #getMapFactory(Class) */
  private final Map<Class<? extends Map>, MapFactory> mapFactoryMap;

  /**
   * The constructor.
   */
  public CollectionFactoryManagerImpl() {

    this.mapFactoryMap = new HashMap<Class<? extends Map>, MapFactory>();
    this.collectionFactoryMap = new HashMap<Class<? extends Collection>, CollectionFactory>();
    registerMapFactory(MapFactory.INSTANCE_HASH_MAP);
    registerCollectionFactory(ListFactory.INSTANCE_ARRAY_LIST, Collection.class);
    registerCollectionFactory(ListFactory.INSTANCE_ARRAY_LIST);
    registerCollectionFactory(SetFactory.INSTANCE_HASH_SET);
    registerCollectionFactory(SortedSetFactory.INSTANCE_TREE_SET);
    registerCollectionFactory(QueueFactory.INSTANCE_LINKED_LIST);
    registerCollectionFactory(BlockingQueueFactory.INSTANCE_LINKED_BLOCKING_QUEUE);
    // allow this class to work with java 5 as well...
    try {
      Class dequeFactoryClass = Class.forName("net.sf.mmm.util.collection.DequeFactory");
      CollectionFactory dequeFactory = (CollectionFactory) dequeFactoryClass.getField(
          "INSTANCE_LINKED_LIST").get(null);
      registerCollectionFactory(dequeFactory);
    } catch (Throwable e) {
      // Deque not available before java6, ignore...
    }
  }

  /**
   * This method gets the singleton instance of this
   * {@link CollectionFactoryManager} implementation.<br>
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
  public static CollectionFactoryManager getInstance() {

    if (instance == null) {
      synchronized (CollectionFactoryManagerImpl.class) {
        if (instance == null) {
          instance = new CollectionFactoryManagerImpl();
        }
      }
    }
    return instance;
  }

  /**
   * {@inheritDoc}
   */
  public <C extends Collection> CollectionFactory<C> getCollectionFactory(Class<C> collectionType) {

    return this.collectionFactoryMap.get(collectionType);
  }

  /**
   * {@inheritDoc}
   */
  public <MAP extends Map> MapFactory getMapFactory(Class<MAP> mapType) {

    return this.mapFactoryMap.get(mapType);
  }

  /**
   * This method registers the given <code>factory</code> using its
   * {@link MapFactory#getMapInterface() map-interface}.
   * 
   * @see #registerMapFactory(MapFactory, Class)
   * 
   * @param factory is the {@link MapFactory} to register.
   * @return the {@link MapFactory} that has been replaced with
   *         <code>factory</code> or <code>null</code> if none was replaced.
   */
  protected MapFactory registerMapFactory(MapFactory factory) {

    return registerMapFactory(factory, factory.getMapInterface());
  }

  /**
   * This method registers the given <code>factory</code> for the given
   * <code>mapInterface</code>.
   * 
   * @param <MAP> is the generic type of the <code>mapInterface</code>.
   * @param factory is the {@link MapFactory} to register.
   * @param mapInterface is the interface of the associated {@link Map}. It has
   *        to be {@link Class#isAssignableFrom(Class) assignable from} the
   *        {@link MapFactory#getMapInterface() map-interface} of the given
   *        <code>factory</code>.
   * @return the {@link MapFactory} that was registered for the given
   *         <code>mapInterface</code> before and has now been replaced with
   *         <code>factory</code> or <code>null</code> if none was replaced.
   */
  protected <MAP extends Map> MapFactory registerMapFactory(MapFactory<? extends MAP> factory,
      Class<MAP> mapInterface) {

    return this.mapFactoryMap.get(mapInterface);
  }

  /**
   * This method registers the given <code>factory</code> using its
   * {@link CollectionFactory#getCollectionInterface() collection-interface}.
   * 
   * @see #registerCollectionFactory(CollectionFactory, Class)
   * 
   * @param factory is the {@link CollectionFactory} to register.
   * @return the {@link CollectionFactory} that has been replaced with
   *         <code>factory</code> or <code>null</code> if none was replaced.
   */
  protected CollectionFactory registerCollectionFactory(CollectionFactory factory) {

    return registerCollectionFactory(factory, factory.getCollectionInterface());
  }

  /**
   * This method registers the given <code>factory</code> for the given
   * <code>collectionInterface</code>.
   * 
   * @param <COLLECTION> is the generic type of the
   *        <code>collectionInterface</code>.
   * @param factory is the {@link CollectionFactory} to register.
   * @param collectionInterface is the interface of the associated
   *        {@link Collection}. It has to be
   *        {@link Class#isAssignableFrom(Class) assignable from} the
   *        {@link CollectionFactory#getCollectionInterface() collection-interface}
   *        of the given <code>factory</code>.
   * @return the {@link CollectionFactory} that was registered for the given
   *         <code>collectionInterface</code> before and has now been replaced
   *         with <code>factory</code> or <code>null</code> if none was
   *         replaced.
   */
  protected <COLLECTION extends Collection> CollectionFactory registerCollectionFactory(
      CollectionFactory<? extends COLLECTION> factory, Class<COLLECTION> collectionInterface) {

    if (!collectionInterface.isAssignableFrom(factory.getCollectionInterface())) {
      throw new IllegalArgumentException();
    }
    return this.collectionFactoryMap.put(collectionInterface, factory);
  }

}
