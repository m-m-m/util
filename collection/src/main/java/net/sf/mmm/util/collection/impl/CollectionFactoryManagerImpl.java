/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
import net.sf.mmm.util.component.base.AbstractLoggableComponent;

/**
 * This is the default implementation of the {@link CollectionFactoryManager} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CollectionFactoryManagerImpl extends AbstractLoggableComponent implements CollectionFactoryManager {

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

    // ObservableList, ObservableSet and ObservableMap are only available since java 1.8,
    // allow this class to work with java 1.7 as well...
    // try {
    // } catch (Throwable e) {
    // // Deque not available before java6, ignore...
    // getLogger().info("Deque is NOT available before java 6 - support disabled: " + e.getClass().getName() +
    // ": "
    // + e.getMessage());
    // }
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
   * This method registers the given {@code factory} using its {@link MapFactory#getMapInterface()
   * map-interface}.
   *
   * @see #registerMapFactory(MapFactory, Class)
   *
   * @param factory is the {@link MapFactory} to register.
   * @return the {@link MapFactory} that has been replaced with {@code factory} or {@code null} if none was
   *         replaced.
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
   *        {@link Class#isAssignableFrom(Class) assignable from} the {@link MapFactory#getMapInterface()
   *        map-interface} of the given {@code factory}.
   * @return the {@link MapFactory} that was registered for the given {@code mapInterface} before and has now
   *         been replaced with {@code factory} or {@code null} if none was replaced.
   */
  protected <MAP extends Map> MapFactory registerMapFactory(MapFactory<? extends MAP> factory, Class<MAP> mapInterface) {

    return this.mapFactoryMap.put(mapInterface, factory);
  }

  /**
   * This method registers the given {@code factory} using its
   * {@link CollectionFactory#getCollectionInterface() collection-interface}.
   *
   * @see #registerCollectionFactory(CollectionFactory, Class)
   *
   * @param factory is the {@link CollectionFactory} to register.
   * @return the {@link CollectionFactory} that has been replaced with {@code factory} or {@code null} if none
   *         was replaced.
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
   *        {@link Class#isAssignableFrom(Class) assignable from} the
   *        {@link CollectionFactory#getCollectionInterface() collection-interface} of the given
   *        {@code factory}.
   * @return the {@link CollectionFactory} that was registered for the given {@code collectionInterface}
   *         before and has now been replaced with {@code factory} or {@code null} if none was replaced.
   */
  protected <COLLECTION extends Collection> CollectionFactory registerCollectionFactory(CollectionFactory<? extends COLLECTION> factory,
      Class<COLLECTION> collectionInterface) {

    if (!collectionInterface.isAssignableFrom(factory.getCollectionInterface())) {
      throw new IllegalArgumentException();
    }
    return this.collectionFactoryMap.put(collectionInterface, factory);
  }

}
