/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.util;

import java.util.List;

import javafx.beans.value.WritableSetValue;
import javafx.collections.ObservableSet;
import net.sf.mmm.util.property.api.ReadableSetProperty;
import net.sf.mmm.util.property.api.WritableProperty;

/**
 * This is the interface for a {@link WritableProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link List}{@literal<E>}.
 *
 * @param <E> the generic type of the {@link ObservableSet#contains(Object) elements} of the {@link ObservableSet}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface WritableSetProperty<E>
    extends ReadableSetProperty<E>, WritableCollectionProperty<E, ObservableSet<E>>, WritableSetValue<E> {

  @Override
  default ObservableSet<E> get() {

    return ReadableSetProperty.super.get();
  }

  @Override
  default void set(ObservableSet<E> value) {

    setValue(value);
  }

}
