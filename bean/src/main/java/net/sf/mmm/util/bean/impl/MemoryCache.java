/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.bean.impl;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;

/**
 * A simple cache implementation that is thread-safe and uses {@link WeakReference}s to avoid memory leaks.
 *
 * @author hohwille
 * @since 8.5.0
 */
class MemoryCache<K, V> {

  private final ReentrantLock lock;

  private final Map<K, WeakReference<V>> cache;

  /**
   * The constructor.
   */
  public MemoryCache() {
    super();
    this.lock = new ReentrantLock();
    this.cache = new HashMap<>(32);
  }

  public V get(K key, Supplier<V> factory) {

    this.lock.lock();
    try {
      WeakReference<V> weakReference = this.cache.computeIfAbsent(key, x -> new WeakReference<>(factory.get()));
      V value = weakReference.get();
      if (value == null) {
        value = factory.get();
        weakReference = new WeakReference<>(value);
        this.cache.put(key, weakReference);
      }
      return value;
    } finally {
      this.lock.unlock();
    }
  }

}
