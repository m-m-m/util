/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.NavigableMap;
import java.util.TreeMap;

import net.sf.mmm.util.collection.api.NavigableMapFactory;

/**
 * This is an implementation of the {@link net.sf.mmm.util.collection.api.NavigableMapFactory} interface that creates
 * instances of {@link TreeMap}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.7.0
 */
public class NavigableTreeMapFactory extends AbstractNavigableMapFactory {

  /** The singleton instance. */
  public static final NavigableMapFactory INSTANCE = new NavigableTreeMapFactory();

  @Override
  @SuppressWarnings("rawtypes")
  public Class<TreeMap> getMapImplementation() {

    return TreeMap.class;
  }

  @Override
  public <K, V> NavigableMap<K, V> create() {

    return new TreeMap<>();
  }

  @Override
  public <K, V> NavigableMap<K, V> create(int capacity) {

    // capacity ignored...
    return new TreeMap<>();
  }
}
