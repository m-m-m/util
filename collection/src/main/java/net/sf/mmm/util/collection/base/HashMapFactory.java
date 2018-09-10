/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.collection.api.MapFactory;

/**
 * This is an implementation of the {@link net.sf.mmm.util.collection.api.MapFactory} interface that creates instances
 * of {@link HashMap}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class HashMapFactory extends AbstractMapFactory {

  /** The singleton instance. */
  @SuppressWarnings("rawtypes")
  public static final MapFactory<Map> INSTANCE = new HashMapFactory();

  @Override
  @SuppressWarnings("rawtypes")
  public Class<HashMap> getMapImplementation() {

    return HashMap.class;
  }

  @Override
  public <K, V> Map<K, V> create() {

    return new HashMap<>();
  }

  @Override
  public <K, V> Map<K, V> create(int capacity) {

    return new HashMap<>(capacity);
  }
}
