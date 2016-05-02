/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.util;

import javafx.beans.value.WritableMapValue;
import javafx.collections.ObservableMap;
import net.sf.mmm.util.property.api.WritableProperty;

/**
 * This is the interface for a {@link WritableProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link ObservableMap}{@literal<K, V>}.
 *
 * @param <K> the generic type of the {@link java.util.Map.Entry#getKey() keys}.
 * @param <V> the generic type of the {@link java.util.Map.Entry#getValue() values}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface WritableMapProperty<K, V>
    extends ReadableMapProperty<K, V>, WritableProperty<ObservableMap<K, V>>, WritableMapValue<K, V> {

  @Override
  default ObservableMap<K, V> get() {

    return ReadableMapProperty.super.get();
  }

  @Override
  default void set(ObservableMap<K, V> value) {

    setValue(value);
  }

}
