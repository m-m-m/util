/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.api;

import java.util.concurrent.ConcurrentMap;

/**
 * This is the interface for a {@link MapFactory} that {@link #create() creates}
 * instances of {@link ConcurrentMap}.
 * 
 * @see net.sf.mmm.util.collection.base.ConcurrentHashMapFactory#INSTANCE
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
@SuppressWarnings("unchecked")
public interface ConcurrentMapFactory extends MapFactory<ConcurrentMap> {

  /**
   * {@inheritDoc}
   */
  <K, V> ConcurrentMap<K, V> create();

  /**
   * {@inheritDoc}
   */
  <K, V> ConcurrentMap<K, V> create(int capacity);

}
