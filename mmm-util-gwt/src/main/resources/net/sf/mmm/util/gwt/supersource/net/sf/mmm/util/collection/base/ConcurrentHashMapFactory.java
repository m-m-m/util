/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.sf.mmm.util.collection.api.ConcurrentMapFactory;

/**
 * This is an implementation of the {@link net.sf.mmm.util.collection.api.ConcurrentMapFactory} interface that
 * for GWT. It simply creates instances of {@link HashMap}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class ConcurrentHashMapFactory extends AbstractConcurrentMapFactory {

  /** The singleton instance. */
  public static final ConcurrentMapFactory INSTANCE = new ConcurrentHashMapFactory();

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("rawtypes")
  public Class<HashMap> getMapImplementation() {

    return HashMap.class;
  }

  /**
   * {@inheritDoc}
   */
  public <K, V> Map<K, V> create() {

    return new HashMap<K, V>();
  }

  /**
   * {@inheritDoc}
   */
  public <K, V> Map<K, V> create(int capacity) {

    return new HashMap<K, V>(capacity);
  }
}
