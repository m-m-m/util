/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import net.sf.mmm.util.collection.api.ConcurrentNavigableMapFactory;

/**
 * This is an implementation of {@link net.sf.mmm.util.collection.api.ConcurrentNavigableMapFactory} that creates
 * instances of {@link ConcurrentSkipListMap}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.7.0
 */
public class ConcurrentSkipListMapFactory extends AbstractConcurrentNavigableMapFactory {

  /** The singleton instance. */
  public static final ConcurrentNavigableMapFactory INSTANCE = new ConcurrentSkipListMapFactory();

  @Override
  @SuppressWarnings("rawtypes")
  public Class<ConcurrentSkipListMap> getMapImplementation() {

    return ConcurrentSkipListMap.class;
  }

  @Override
  public <K, V> ConcurrentNavigableMap<K, V> create() {

    return new ConcurrentSkipListMap<>();
  }

  @Override
  public <K, V> ConcurrentNavigableMap<K, V> create(int capacity) {

    // ignore capacity...
    return new ConcurrentSkipListMap<>();
  }
}
