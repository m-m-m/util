/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.Map;

import net.sf.mmm.util.collection.api.MapFactory;

/**
 * This is a simple implementation of {@link AbstractClassHierarchyMap}.
 *
 * @param <E> is the generic type of the elements contained in this map.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1 (renamed in 4.0.0)
 */
public class SimpleClassHierarchyMap<E> extends AbstractClassHierarchyMap<E> {

  /**
   * The constructor.
   */
  public SimpleClassHierarchyMap() {

    super();
  }

  /**
   * The constructor.
   *
   * @param mapFactory is the factory used to create the internal {@link Map}.
   */
  @SuppressWarnings("rawtypes")
  public SimpleClassHierarchyMap(MapFactory<Map> mapFactory) {

    super(mapFactory);
  }

  @Override
  public E put(Class<?> type, E element) {

    return super.put(type, element);
  }

}
