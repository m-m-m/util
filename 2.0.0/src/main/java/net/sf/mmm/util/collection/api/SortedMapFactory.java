/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.api;

import java.util.SortedMap;

/**
 * This is the interface for a {@link MapFactory} that {@link #create() creates}
 * instances of {@link SortedMap}.
 * 
 * @see net.sf.mmm.util.collection.base.TreeMapFactory#INSTANCE
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@SuppressWarnings("rawtypes")
public interface SortedMapFactory extends MapFactory<SortedMap> {

  /**
   * {@inheritDoc}
   */
  <K, V> SortedMap<K, V> create();

  /**
   * {@inheritDoc}
   */
  <K, V> SortedMap<K, V> create(int capacity);

}
