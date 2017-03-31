/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.property.base;

import java.util.AbstractList;
import java.util.Collection;

import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * Immutable implementation of {@link ObservableList}.
 *
 * @param <E> the generic type of the {@link #get(int) elements} of this list.
 *
 * @author hohwille
 * @since 8.5.0
 */
public final class ImmutableObservableList<E> extends AbstractList<E> implements ObservableList<E> {

  private final E[] elements;

  /**
   * The constructor.
   *
   * @param elements the elements of this list.
   */
  @SafeVarargs
  public ImmutableObservableList(E... elements) {
    super();
    this.elements = elements;
  }

  @Override
  public void addListener(InvalidationListener listener) {

  }

  @Override
  public void removeListener(InvalidationListener listener) {

  }

  @Override
  public void addListener(ListChangeListener<? super E> listener) {

  }

  @Override
  public void removeListener(ListChangeListener<? super E> listener) {

  }

  @Override
  @SafeVarargs
  public final boolean addAll(E... items) {

    throw new UnsupportedOperationException();
  }

  @Override
  @SafeVarargs
  public final boolean setAll(E... items) {

    throw new UnsupportedOperationException();
  }

  @Override
  public final boolean setAll(Collection<? extends E> col) {

    throw new UnsupportedOperationException();
  }

  @Override
  @SafeVarargs
  public final boolean removeAll(E... items) {

    throw new UnsupportedOperationException();
  }

  @Override
  @SafeVarargs
  public final boolean retainAll(E... items) {

    throw new UnsupportedOperationException();
  }

  @Override
  public void remove(int from, int to) {

    throw new UnsupportedOperationException();
  }

  @Override
  public E get(int index) {

    if ((index < 0) || (index >= size())) {
      throw new IndexOutOfBoundsException();
    }
    return this.elements[index];
  }

  @Override
  public int size() {

    return this.elements.length;
  }

}
