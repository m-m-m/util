/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.Iterator;

import net.sf.mmm.util.filter.api.Filter;

/**
 * This is an implementation of an {@link Iterator} that adapts another
 * {@link Iterator} but only {@link #next() iterates} the elements that are
 * {@link Filter#accept(Object) accepted} by a given {@link Filter}.<br/>
 * <b>ATTENTION:</b><br>
 * According to the design of the {@link Iterator} interface, it is NOT possible
 * to implement the {@link #remove()} method properly here. The method
 * {@link #hasNext()} has to step forward in the adapted {@link Iterator} and a
 * call of {@link #remove()} would cause unintended and unpredictable results
 * after {@link #hasNext()} has been called. To prevent damage {@link #remove()}
 * will always throw an {@link UnsupportedOperationException}.
 * 
 * @param <E> is the generic type of the {@link #next() iterated} elements.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class FilteredIterator<E> extends AbstractIterator<E> {

  /** The actual iterator instance to adapt. */
  private final Iterator<E> delegate;

  /** @see #findNext() */
  private final Filter<E> filter;

  /**
   * The constructor.
   * 
   * @param delegate is the {@link Iterator} to adapt.
   * @param filter is the {@link Filter} that {@link Filter#accept(Object)
   *        filters} the iterated elements.
   */
  public FilteredIterator(Iterator<E> delegate, Filter<E> filter) {

    super();
    this.delegate = delegate;
    this.filter = filter;
    findFirst();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected E findNext() {

    E result = null;
    while (this.delegate.hasNext()) {
      E next = this.delegate.next();
      if (this.filter.accept(next)) {
        return next;
      }
    }
    return result;
  }

}
