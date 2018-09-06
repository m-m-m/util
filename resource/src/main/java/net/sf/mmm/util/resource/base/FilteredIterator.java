/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.sf.mmm.util.filter.api.Filter;

/**
 * @since 7.6.0
 */
class FilteredIterator<E> implements Iterator<E> {

  private final Iterator<? extends E> delegate;

  private final Filter<? super E> filter;

  private E next;

  private boolean done;

  /**
   * The constructor.
   *
   * @param delegate is the {@link Iterator} to adapt.
   * @param filter is the {@link Filter} that {@link Filter#accept(Object) filters} the iterated elements.
   */
  public FilteredIterator(Iterator<? extends E> delegate, Filter<? super E> filter) {

    super();
    this.delegate = delegate;
    this.filter = filter;
    this.next = findNext();
  }

  private E findNext() {

    E result = null;
    while (this.delegate.hasNext()) {
      E next = this.delegate.next();
      if (this.filter.accept(next)) {
        return next;
      }
    }
    return result;
  }

  @Override
  public boolean hasNext() {

    if (this.next != null) {
      return true;
    }
    if (this.done) {
      return false;
    }
    this.next = findNext();
    if (this.next == null) {
      this.done = true;
    }
    return (!this.done);
  }

  @Override
  public E next() {

    if (this.next == null) {
      throw new NoSuchElementException();
    } else {
      E result = this.next;
      this.next = null;
      return result;
    }
  }

  @Override
  public final void remove() throws UnsupportedOperationException {

    throw new UnsupportedOperationException();
  }

}
