/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import java.util.Collection;
import java.util.Iterator;

import javafx.beans.value.ObservableSetValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

/**
 * This is the interface for a {@link ReadableProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link ObservableSet}{@literal<E>}.
 *
 * @param <E> the generic type of the {@link ObservableSet#contains(Object) elements} of the {@link ObservableSet}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface ReadableSetProperty<E>
    extends ReadableCollectionProperty<E, ObservableSet<E>>, ObservableSetValue<E> {

  @Override
  default ObservableSet<E> getValueNotNull() {

    ObservableSet<E> list = getValue();
    if (list == null) {
      list = FXCollections.emptyObservableSet();
    }
    return list;
  }

  @Override
  default boolean retainAll(Collection<?> c) {

    return getValueNotNull().retainAll(c);
  }

  @Override
  default ObservableSet<E> get() {

    return getValue();
  }

  @Override
  default Iterator<E> iterator() {

    return ReadableCollectionProperty.super.iterator();
  }

  @Override
  default int size() {

    return ReadableCollectionProperty.super.size();
  }

  @Override
  default boolean isEmpty() {

    return ReadableCollectionProperty.super.isEmpty();
  }

  @Override
  default boolean contains(Object o) {

    return ReadableCollectionProperty.super.contains(o);
  }

  @Override
  default boolean containsAll(Collection<?> c) {

    return ReadableCollectionProperty.super.containsAll(c);
  }

  @Override
  default boolean add(E e) {

    return ReadableCollectionProperty.super.add(e);
  }

  @Override
  default boolean addAll(Collection<? extends E> c) {

    return ReadableCollectionProperty.super.addAll(c);
  }

  @Override
  default boolean remove(Object o) {

    return ReadableCollectionProperty.super.remove(o);
  }

  @Override
  default boolean removeAll(Collection<?> c) {

    return ReadableCollectionProperty.super.removeAll(c);
  }

  @Override
  default void clear() {

    ReadableCollectionProperty.super.clear();
  }

  @Override
  default Object[] toArray() {

    return ReadableCollectionProperty.super.toArray();
  }

  @Override
  default <T> T[] toArray(T[] a) {

    return ReadableCollectionProperty.super.toArray(a);
  }

}
