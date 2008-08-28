/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is an implementation of the {@link Iterator} interface that
 * {@link #next() iterates} a single element.
 * 
 * @param <E> is the templated type of the elements to iterate.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SingleElementIterator<E> implements Iterator<E> {

  /** The single element to iterate. */
  private E element;

  /**
   * The constructor.
   * 
   * @param element is the single element to iterate. If <code>null</code> the
   *        iterator will be entirely empty.
   */
  public SingleElementIterator(E element) {

    super();
    this.element = element;
  }

  /**
   * {@inheritDoc}
   */
  public boolean hasNext() {

    return (this.element != null);
  }

  /**
   * {@inheritDoc}
   */
  public E next() {

    if (this.element == null) {
      throw new NoSuchElementException();
    }
    E result = this.element;
    this.element = null;
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public void remove() {

    throw new UnsupportedOperationException();
  }

}
