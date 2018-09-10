/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.resource.base;

import java.util.Iterator;

import net.sf.mmm.util.filter.api.Filter;

/**
 * @since 7.6.0
 */
class FilteredIterable<E> implements Iterable<E> {

  /** The actual {@link Iterable} instance to adapt. */
  private final Iterable<? extends E> delegate;

  private final Filter<? super E> filter;

  /**
   * The constructor.
   *
   * @param delegate is the {@link Iterable} to adapt.
   * @param filter is the {@link Filter} that {@link Filter#accept(Object) filters} the iterated elements.
   */
  public FilteredIterable(Iterable<? extends E> delegate, Filter<? super E> filter) {

    super();
    this.delegate = delegate;
    this.filter = filter;
  }

  @Override
  public Iterator<E> iterator() {

    return new FilteredIterator<>(this.delegate.iterator(), this.filter);
  }

}
