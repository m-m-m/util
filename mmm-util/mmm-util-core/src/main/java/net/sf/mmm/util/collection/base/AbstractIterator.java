/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is an abstract base implementation of the {@link Iterator} interface. It allows to implement an
 * read-only lookahead {@link Iterator} easier:<br>
 * Simply extend this class and implement {@link #findNext()}. From your constructor or initializer call
 * {@link #findFirst()}.<br>
 * <b>ATTENTION:</b><br>
 * Do NOT forget to call {@link #findFirst()} from your constructor or your iterator will always be empty.
 * 
 * @param <E> is the generic type of the {@link #next() iterated} elements.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractIterator<E> implements Iterator<E> {

  /** the next item or <code>null</code> if done */
  private E next;

  /**
   * The constructor.
   * 
   * @see #findFirst()
   */
  public AbstractIterator() {

    super();
  }

  /**
   * This method has to be called from the constructor of the implementing class.
   */
  protected final void findFirst() {

    this.next = findNext();
  }

  /**
   * This method tries to find the {@link #next() next} element.
   * 
   * @return the next element or <code>null</code> if {@link #hasNext() done}.
   */
  protected abstract E findNext();

  /**
   * {@inheritDoc}
   */
  public final boolean hasNext() {

    return (this.next != null);
  }

  /**
   * {@inheritDoc}
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
   * @throws UnsupportedOperationException whenever this method is called.
   */
  public final void remove() throws UnsupportedOperationException {

    throw new UnsupportedOperationException();
  }

}
