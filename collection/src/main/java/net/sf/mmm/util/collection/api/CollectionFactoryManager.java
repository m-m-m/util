/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.api;

import java.util.Collection;
import java.util.Map;

/**
 * This is the interface for a manager of {@link CollectionFactory} instances.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@SuppressWarnings("rawtypes")
public interface CollectionFactoryManager {

  /**
   * This method gets the {@link MapFactory} for the given {@code mapType}.
   *
   * @param <MAP> is the generic type of the {@link Map}.
   * @param mapType is the type of the {@link Map}. This should be the interface such as {@link Map}.class or
   *        {@link java.util.SortedMap}.class.
   * @return the {@link MapFactory} for the given {@code mapType}. The {@code mapType} has to be
   *         {@link Class#isAssignableFrom(Class) assignable from} {@link MapFactory#getMapInterface()} of the
   *         returned instance. Typically it will be equal.
   */
  <MAP extends Map> MapFactory getMapFactory(Class<MAP> mapType);

  /**
   * This method gets the {@link CollectionFactory} for the given {@code collectionType}.
   *
   * @param <COLLECTION> is the generic type of the {@link Collection}.
   * @param collectionType is the type of the {@link Collection}. This should be the {@link Collection}
   *        interface such as {@link java.util.List}{@code .class}.
   * @return the {@link CollectionFactory} for the given {@code collectionType}. The {@code collectionType}
   *         has to be {@link Class#isAssignableFrom(Class) assignable from}
   *         {@link CollectionFactory#getCollectionInterface()} of the returned instance. Typically it will be
   *         equal.
   */
  <COLLECTION extends Collection> CollectionFactory<COLLECTION> getCollectionFactory(Class<COLLECTION> collectionType);

  /**
   * This method creates a {@link Collection} implementing the given {@code type}.
   *
   * @param <C> is the generic type of the {@link Collection}.
   * @param type is the type of {@link Collection} to create. This is either an interface
   *        ({@link java.util.List}, {@link java.util.Set}, {@link java.util.Queue}, etc.) or a non-abstract
   *        implementation of a {@link Collection}.
   * @return the new instance of the given {@code type}.
   * @since 7.6.0
   */
  <C extends Collection> C create(Class<C> type);

  /**
   * This method creates a {@link Collection} implementing the given {@code type}.
   *
   * @since 1.7.0
   *
   * @param <C> is the generic type of the {@link Collection}.
   * @param type is the type of {@link Collection} to create. This is either an interface
   *        ({@link java.util.List}, {@link java.util.Set}, {@link java.util.Queue}, etc.) or a non-abstract
   *        implementation of a {@link Collection}.
   * @param capacity is the initial capacity of the {@link Collection}.
   * @return the new instance of the given {@code type}.
   * @since 7.6.0
   */
  <C extends Collection> C create(Class<C> type, int capacity);

  /**
   * This method creates a {@link Map} implementing the given {@code type}.
   *
   * @since 1.7.0
   *
   * @param <C> is the generic type of the {@link Map}.
   * @param type is the type of {@link Map} to create. This is either an interface ({@link Map},
   *        {@link java.util.concurrent.ConcurrentMap}, {@link java.util.NavigableMap}, etc.) or a
   *        non-abstract implementation of a {@link Map}.
   * @return the new instance of the given {@code type}.
   * @since 7.6.0
   */
  <C extends Map> C createMap(Class<C> type);

  /**
   * This method creates a {@link Map} implementing the given {@code type}.
   *
   * @since 1.7.0
   *
   * @param <C> is the generic type of the {@link Map}.
   * @param type is the type of {@link Map} to create. This is either an interface ({@link Map},
   *        {@link java.util.concurrent.ConcurrentMap}, {@link java.util.NavigableMap}, etc.) or a
   *        non-abstract implementation of a {@link Map}.
   * @param capacity is the initial capacity of the {@link Map}.
   * @return the new instance of the given {@code type}.
   * @since 7.6.0
   */
  <C extends Map> C createMap(Class<C> type, int capacity);
}
