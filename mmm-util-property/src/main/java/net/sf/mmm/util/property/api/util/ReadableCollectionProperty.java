/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.api.util;

import java.util.Collection;
import java.util.Iterator;

import net.sf.mmm.util.property.api.ReadableProperty;

/**
 * This is the interface for a {@link ReadableProperty} of the {@link #getValue() value}-{@link #getType() type}
 * {@link Collection}.
 *
 * @param <E> the generic type of the {@link Collection#contains(Object) elements contained in the collection}.
 * @param <VALUE> the generic type of the {@link #getValue() value}.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface ReadableCollectionProperty<E, VALUE extends Collection<E>>
    extends ReadableContainerProperty<VALUE>, Collection<E> {

  @Override
  default int size() {

    return getValueNotNull().size();
  }

  @Override
  default boolean isEmpty() {

    return getValueNotNull().isEmpty();
  }

  @Override
  default boolean contains(Object o) {

    return getValueNotNull().contains(o);
  }

  @Override
  default boolean containsAll(Collection<?> c) {

    return getValueNotNull().containsAll(c);
  }

  @Override
  default boolean add(E e) {

    return getValueNotNull().add(e);
  }

  @Override
  default boolean addAll(Collection<? extends E> c) {

    return getValueNotNull().addAll(c);
  }

  @Override
  default boolean remove(Object o) {

    return getValueNotNull().remove(o);
  }

  @Override
  default boolean removeAll(Collection<?> c) {

    return getValueNotNull().removeAll(c);
  }

  @Override
  default void clear() {

    getValueNotNull().clear();
  }

  @Override
  default Iterator<E> iterator() {

    return getValueNotNull().iterator();
  }

  @Override
  default Object[] toArray() {

    return getValueNotNull().toArray();
  }

  @Override
  default <T> T[] toArray(T[] a) {

    return getValueNotNull().toArray(a);
  }

}
