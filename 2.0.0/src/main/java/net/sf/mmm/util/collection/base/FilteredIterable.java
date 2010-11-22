/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.Iterator;

import net.sf.mmm.util.filter.api.Filter;

/**
 * This is an implementation of an {@link Iterator} that adapts another
 * {@link Iterator} but only {@link #iterator() iterates} the elements that are
 * {@link Filter#accept(Object) accepted} by a given {@link Filter}.<br/>
 * <b>ATTENTION:</b><br>
 * This implementation is using {@link FilteredIterator}. Please read according
 * javadoc first.
 * 
 * @param <E> is the generic type of the {@link #iterator() iterated} elements.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class FilteredIterable<E> implements Iterable<E> {

  /** The actual {@link Iterable} instance to adapt. */
  private final Iterable<E> delegate;

  /** @see #iterator() */
  private final Filter<E> filter;

  /**
   * The constructor.
   * 
   * @param delegate is the {@link Iterable} to adapt.
   * @param filter is the {@link Filter} that {@link Filter#accept(Object)
   *        filters} the iterated elements.
   */
  public FilteredIterable(Iterable<E> delegate, Filter<E> filter) {

    super();
    this.delegate = delegate;
    this.filter = filter;
  }

  /**
   * {@inheritDoc}
   */
  public Iterator<E> iterator() {

    return new FilteredIterator<E>(this.delegate.iterator(), this.filter);
  }

}
