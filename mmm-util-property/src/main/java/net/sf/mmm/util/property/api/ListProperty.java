/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the interface for a {@link GenericProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link List}{@literal<E>}.
 *
 * @param <E> the generic type of the {@link List#get(int) elements} of the {@link List}.
 *
 * @author hohwille
 * @since 7.1.0
 */
public interface ListProperty<E> extends CollectionProperty<E, ObservableList<E>>, ObservableListValue<E> {

  /**
   * @return an {@link ReadOnlyIntegerProperty} that represents the {@link #size()} property of the {@link #getValue()
   *         list}.
   */
  ReadOnlyIntegerProperty sizeProperty();

  /**
   * @return an {@link ReadOnlyBooleanProperty} that represents the {@link #isEmpty() empty} property of the
   *         {@link #getValue() list}.
   */
  ReadOnlyBooleanProperty emptyProperty();

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
  default int size() {

    return CollectionProperty.super.size();
  }

  @Override
  default boolean isEmpty() {

    return CollectionProperty.super.isEmpty();
  }

  @Override
  default boolean contains(Object o) {

    return CollectionProperty.super.contains(o);
  }

  @Override
  default boolean containsAll(Collection<?> c) {

    return CollectionProperty.super.containsAll(c);
  }

  @Override
  default Iterator<E> iterator() {

    return CollectionProperty.super.iterator();
  }

  @Override
  default boolean remove(Object o) {

    return CollectionProperty.super.remove(o);
  }

  @Override
  default boolean removeAll(Collection<?> c) {

    return CollectionProperty.super.removeAll(c);
  }

  @Override
  default boolean add(E e) {

    return CollectionProperty.super.add(e);
  }

  @Override
  default boolean addAll(Collection<? extends E> c) {

    return CollectionProperty.super.addAll(c);
  }

  @Override
  default void clear() {

    CollectionProperty.super.clear();
  }

  @Override
  default Object[] toArray() {

    return CollectionProperty.super.toArray();
  }

  @Override
  default <T> T[] toArray(T[] a) {

    return CollectionProperty.super.toArray(a);
  }

}
