/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is an implementation of the {@link java.util.Iterator} interface that
 * gives a read-only view on an existing {@link java.util.Iterator} instance.<br>
 * The design of the JDK sucks: if only {@link java.util.Iterator} would extend
 * {@link java.util.Enumeration} the world could be so simple.
 * 
 * @param <E>
 *        is the templated type of the elements to iterate.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractReadOnlyLookaheadIterator<E> implements Iterator<E> {

  /** the next item or <code>null</code> if done */
  private E next;

  /**
   * The constructor.
   * 
   * @see #findFirst()
   */
  public AbstractReadOnlyLookaheadIterator() {

    super();
  }

  /**
   * This method has to be called from the constructor of the implementing
   * class.
   */
  protected final void findFirst() {

    this.next = findNext();
  }

  /**
   * This method tries to find the {@link #next() next} element.
   * 
   * @return the next element or <code>null</code> if
   *         {@link #hasNext() done}.
   */
  protected abstract E findNext();

  /**
   * @see java.util.Iterator#hasNext()
   */
  public final boolean hasNext() {

    return (this.next != null);
  }

  /**
   * @see java.util.Iterator#next()
   */
  public final E next() {

    if (this.next == null) {
      throw new NoSuchElementException();
    } else {
      E result = this.next;
      this.next = findNext();
      return result;
    }
  }

  /**
   * This method will always throw an exception.
   * 
   * @see java.util.Iterator#remove()
   * 
   * @throws UnsupportedOperationException
   *         whenever this method is called.
   */
  public final void remove() {

    throw new UnsupportedOperationException();
  }

}
