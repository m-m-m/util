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
public class CollectionFactoryManagerImpl implements CollectionFactoryManager {

  /** @see #getInstance() */
  private static CollectionFactoryManager instance;

  /** @see #getFactory(Class) */
  private Map<Class<? extends Collection>, CollectionFactory> factoryMap;

  /**
   * The constructor.
   */
  public CollectionFactoryManagerImpl() {

    this.factoryMap = new HashMap<Class<? extends Collection>, CollectionFactory>();
    registerFactory(ListFactory.INSTANCE_ARRAY_LIST, Collection.class);
    registerFactory(ListFactory.INSTANCE_ARRAY_LIST);
    registerFactory(SetFactory.INSTANCE_HASH_SET);
    registerFactory(SortedSetFactory.INSTANCE_TREE_SET);
    registerFactory(QueueFactory.INSTANCE_LINKED_LIST);
    registerFactory(BlockingQueueFactory.INSTANCE_LINKED_BLOCKING_QUEUE);
    // allow this class to work with java 5 as well...
    try {
      Class dequeFactoryClass = Class.forName("net.sf.mmm.util.collection.DequeFactory");
      CollectionFactory instance = (CollectionFactory) dequeFactoryClass.getField(
          "INSTANCE_LINKED_LIST").get(null);
      registerFactory(instance);
    } catch (Exception e) {
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
   * This method registers the given <code>factory</code> using its
   * {@link CollectionFactory#getCollectionInterface() collection-interface}.
   * 
   * @see #registerFactory(CollectionFactory, Class)
   * 
   * @param factory is the {@link CollectionFactory} to register.
   * @return the {@link CollectionFactory} that has been replaced with
   *         <code>factory</code> or <code>null</code> if none was replaced.
   */
  protected CollectionFactory registerFactory(CollectionFactory factory) {

    return registerFactory(factory, factory.getCollectionInterface());
  }

  /**
   * This method registers the given <code>factory</code> for the given
   * <code>collectionInterface</code>.
   * 
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
  protected CollectionFactory registerFactory(CollectionFactory factory,
      Class<? extends Collection> collectionInterface) {

    if (!collectionInterface.isAssignableFrom(factory.getCollectionInterface())) {
      throw new IllegalArgumentException();
    }
    return this.factoryMap.put(collectionInterface, factory);
  }

  /**
   * {@inheritDoc}
   */
  public <C extends Collection> CollectionFactory<C> getFactory(Class<C> collectionType) {

    return this.factoryMap.get(collectionType);
  }

}
