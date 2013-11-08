/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package java.util.concurrent;

import java.util.Map;
import java.util.HashMap;

/**
 * Compatibility class for GWT. Nothing concurrent here as there is no real concurrency in web/JS.
 */
public class ConcurrentHashMap<K, V> extends HashMap<K, V> implements ConcurrentMap<K, V> {

  private static final int DEFAULT_INITIAL_CAPACITY = 16;

  private static final float DEFAULT_LOAD_FACTOR = 0.75f;

  private static final int DEFAULT_CONCURRENCY_LEVEL = 16;

  public ConcurrentHashMap() {
    this(DEFAULT_INITIAL_CAPACITY);
  }

  public ConcurrentHashMap(int initialCapacity) {
      this(initialCapacity, DEFAULT_LOAD_FACTOR);
  }

  public ConcurrentHashMap(int initialCapacity, float loadFactor) {
    this(initialCapacity, loadFactor, DEFAULT_CONCURRENCY_LEVEL);
  }

  public ConcurrentHashMap(int initialCapacity, float loadFactor, int concurrencyLevel) {
    super(initialCapacity, loadFactor);
  }

  public ConcurrentHashMap(Map<? extends K, ? extends V> m) {
      this(Math.max((int) (m.size() / DEFAULT_LOAD_FACTOR) + 1), DEFAULT_INITIAL_CAPACITY);
      putAll(m);
  }

  public V putIfAbsent(K key, V value) {
    if (!containsKey(key)) {
      return put(key, value);
    } else {
      return get(key);
    }
  }

  public boolean remove(Object key, Object value) {

    if (containsKey(key) && get(key).equals(value)) {
      remove(key);
      return true;
    }
    return false;
  }

  public boolean replace(K key, V oldValue, V newValue) {

    if (containsKey(key) && get(key).equals(oldValue)) {
      put(key, newValue);
      return true;
    }
    return false;
  }

  public V replace(K key, V value) {
    if (containsKey(key)) {
      return put(key, value);
    }
    return null;
  }

}