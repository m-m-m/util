/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javafx.beans.value.ObservableMapValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import net.sf.mmm.util.property.api.ReadableProperty;

/**
 * This is the interface for a {@link ReadableProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link ObservableMap}{@literal<K, V>}.
 *
 * @param <K> the generic type of the {@link java.util.Map.Entry#getKey() keys}.
 * @param <V> the generic type of the {@link java.util.Map.Entry#getValue() values}.
 *
 * @author hohwille
 * @since 8.4.0
 */
public interface ReadableMapProperty<K, V>
    extends ReadableContainerProperty<ObservableMap<K, V>>, ObservableMapValue<K, V> {

  /**
   * @return the result of {@link #getValue()} but an empty {@link Map} instead of {@code null}.
   */
  @Override
  default ObservableMap<K, V> getValueNotNull() {

    ObservableMap<K, V> Map = getValue();
    if (Map == null) {
      Map = FXCollections.emptyObservableMap();
    }
    return Map;
  }

  @Override
  default ObservableMap<K, V> get() {

    return getValue();
  }

  @Override
  default int size() {

    return getValueNotNull().size();
  }

  @Override
  default boolean isEmpty() {

    return getValueNotNull().isEmpty();
  }

  @Override
  default boolean containsKey(Object obj) {

    return getValueNotNull().containsKey(obj);
  }

  @Override
  default boolean containsValue(Object obj) {

    return getValueNotNull().containsValue(obj);
  }

  @Override
  default V put(K key, V value) {

    return getValueNotNull().put(key, value);
  }

  @Override
  default V remove(Object obj) {

    return getValueNotNull().remove(obj);
  }

  @Override
  default void putAll(Map<? extends K, ? extends V> elements) {

    getValueNotNull().putAll(elements);
  }

  @Override
  default void clear() {

    getValueNotNull().clear();
  }

  @Override
  default Set<K> keySet() {

    return getValueNotNull().keySet();
  }

  @Override
  default Collection<V> values() {

    return getValueNotNull().values();
  }

  @Override
  default Set<Entry<K, V>> entrySet() {

    return getValueNotNull().entrySet();
  }

  @Override
  default V get(Object key) {

    return getValueNotNull().get(key);
  }

}
