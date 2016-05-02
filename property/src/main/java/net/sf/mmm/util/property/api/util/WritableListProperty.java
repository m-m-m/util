/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.util;

import java.util.List;

import javafx.beans.value.WritableListValue;
import javafx.collections.ObservableList;
import net.sf.mmm.util.property.api.WritableProperty;

/**
 * This is the interface for a {@link WritableProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link List}{@literal<E>}.
 *
 * @param <E> the generic type of the {@link List#get(int) elements} of the {@link List}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface WritableListProperty<E>
    extends ReadableListProperty<E>, WritableCollectionProperty<E, ObservableList<E>>, WritableListValue<E> {

  @Override
  default ObservableList<E> get() {

    return ReadableListProperty.super.get();
  }

  @Override
  default void set(ObservableList<E> value) {

    setValue(value);
  }

}
