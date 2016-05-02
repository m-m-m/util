/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.sf.mmm.util.property.api.ReadableProperty;

/**
 * This is the interface for a {@link ReadableProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link ObservableList}{@literal<E>}.
 *
 * @param <E> the generic type of the {@link List#get(int) elements} of the {@link List}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface ReadableListProperty<E>
    extends ReadableCollectionProperty<E, ObservableList<E>>, ObservableListValue<E> {

  @Override
  default ObservableList<E> getValueNotNull() {

    ObservableList<E> list = getValue();
    if (list == null) {
      list = FXCollections.emptyObservableList();
    }
    return list;
  }

  @Override
  default boolean addAll(int index, Collection<? extends E> c) {

    return getValueNotNull().addAll(index, c);
  }

  @Override
  default boolean retainAll(Collection<?> c) {

    return getValueNotNull().retainAll(c);
  }

  @Override
  default E get(int index) {

    return getValueNotNull().get(index);
  }

  @Override
  default E set(int index, E element) {

    return getValueNotNull().set(index, element);
  }

  @Override
  default void add(int index, E element) {

    getValueNotNull().add(index, element);
  }

  @Override
  default E remove(int index) {

    return getValueNotNull().remove(index);
  }

  @Override
  default int indexOf(Object o) {

    return getValueNotNull().indexOf(o);
  }

  @Override
  default int lastIndexOf(Object o) {

    return getValueNotNull().lastIndexOf(o);
  }

  @Override
  default ListIterator<E> listIterator() {

    return getValueNotNull().listIterator();
  }

  @Override
  default ListIterator<E> listIterator(int index) {

    return getValueNotNull().listIterator(index);
  }

  @Override
  default List<E> subList(int fromIndex, int toIndex) {

    return getValueNotNull().subList(fromIndex, toIndex);
  }

  @Override
  @SuppressWarnings("unchecked")
  default boolean addAll(E... elements) {

    return getValueNotNull().addAll(elements);
  }

  @Override
  @SuppressWarnings("unchecked")
  default boolean setAll(E... elements) {

    return getValueNotNull().setAll(elements);
  }

  @Override
  default boolean setAll(Collection<? extends E> c) {

    return getValueNotNull().setAll(c);
  }

  @Override
  @SuppressWarnings("unchecked")
  default boolean removeAll(E... elements) {

    return getValueNotNull().removeAll(elements);
  }

  @Override
  @SuppressWarnings("unchecked")
  default boolean retainAll(E... elements) {

    return getValueNotNull().retainAll(elements);
  }

  @Override
  default void remove(int from, int to) {

    getValueNotNull().remove(from, to);
  }

  @Override
  default ObservableList<E> get() {

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
