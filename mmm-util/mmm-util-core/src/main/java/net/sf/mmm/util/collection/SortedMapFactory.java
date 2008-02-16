/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * This is the interface for a {@link MapFactory} that {@link #create() creates}
 * instances of {@link SortedMap}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("unchecked")
public interface SortedMapFactory extends MapFactory<SortedMap> {

  /**
   * {@inheritDoc}
   */
  <K, V> SortedMap<K, V> create();

  /**
   * {@inheritDoc}
   */
  <K, V> SortedMap<K, V> create(int capacity);

  /**
   * 
   */
  SortedMapFactory INSTANCE_TREE_MAP = new AbstractSortedMapFactory() {

    /**
     * {@inheritDoc}
     */
    public Class<? extends SortedMap> getMapImplementation() {

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

  };

}
