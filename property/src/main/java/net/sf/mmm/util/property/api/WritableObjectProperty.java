/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import javafx.beans.value.WritableObjectValue;

/**
 * This is the interface for a {@link WritableProperty} with a non-primitive the {@link #getValue() value}-
 * {@link #getType() type}.
 *
 * @param <V> is the generic type of the {@link #getValue() value}.
 *
 * @author hohwille
 * @since 8.5.0
 */
public interface WritableObjectProperty<V> extends ReadableObjectProperty<V>, WritableObjectValue<V> {

  @Override
  default V get() {

    return ReadableObjectProperty.super.get();
  }

  @Override
  default void set(V value) {

    setValue(value);
  }

}
