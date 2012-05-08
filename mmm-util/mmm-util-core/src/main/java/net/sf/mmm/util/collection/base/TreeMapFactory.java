/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.SortedMap;
import java.util.TreeMap;

import net.sf.mmm.util.collection.api.SortedMapFactory;

/**
 * This is an implementation of the {@link net.sf.mmm.util.collection.api.SortedMapFactory} interface that
 * creates instances of {@link TreeMap}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class TreeMapFactory extends AbstractSortedMapFactory {

  /** The singleton instance. */
  public static final SortedMapFactory INSTANCE = new TreeMapFactory();

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("rawtypes")
  public Class<TreeMap> getMapImplementation() {

    return TreeMap.class;
  }

  /**
   * {@inheritDoc}
   */
  public <K, V> SortedMap<K, V> create() {

    return new TreeMap<K, V>();
  }

  /**
   * {@inheritDoc}
   */
  public <K, V> SortedMap<K, V> create(int capacity) {

    // capacity ignored...
    return new TreeMap<K, V>();
  }
}
