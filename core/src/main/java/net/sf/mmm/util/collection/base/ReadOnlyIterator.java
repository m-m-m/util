/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.Iterator;

/**
 * This is an implementation of the {@link java.util.Iterator} interface that gives a read-only view on an existing
 * {@link java.util.Iterator} instance. <br>
 * The design of the JDK sucks: if only {@link java.util.Iterator} would extend {@link java.util.Enumeration} the world
 * could be so simple.
 *
 * @param <E> is the templated type of the elements to iterate.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ReadOnlyIterator<E> implements Iterator<E> {

  /** the actual iterator instance */
  private final Iterator<E> delegate;

  /**
   * The constructor.
   *
   * @param iterator is the {@link Iterator} to adapt.
   */
  public ReadOnlyIterator(Iterator<E> iterator) {

    super();
    this.delegate = iterator;
  }

  @Override
  public boolean hasNext() {

    return this.delegate.hasNext();
  }

  @Override
  public E next() {

    return this.delegate.next();
  }

  /**
   * This method will always throw an exception.
   *
   * @see java.util.Iterator#remove()
   *
   * @throws UnsupportedOperationException whenever this method is called.
   */
  @Override
  public void remove() throws UnsupportedOperationException {

    throw new UnsupportedOperationException();
  }

}
