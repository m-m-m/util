/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.api;

import java.util.NavigableMap;

/**
 * This is the interface for a {@link MapFactory} that {@link #create() creates} instances of {@link NavigableMap}.
 *
 * @see net.sf.mmm.util.collection.base.NavigableTreeMapFactory#INSTANCE
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.7.0
 */
@SuppressWarnings("rawtypes")
public interface NavigableMapFactory extends MapFactory<NavigableMap> {

  @Override
  <K, V> NavigableMap<K, V> create();

  @Override
  <K, V> NavigableMap<K, V> create(int capacity);

}
