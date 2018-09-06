/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is an abstract base implementation of the {@link Iterator} interface.
 *
 * @param <E> is the generic type of the iterated elements.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
abstract class AbstractIterator<E> implements Iterator<E> {

  /** the next item or {@code null} if done */
  private E next;

  private boolean done;

  /**
   * The constructor.
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
   * @return the next element or {@code null} if done.
   */
  protected abstract E findNext();

  @Override
  public final boolean hasNext() {

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
  public final E next() {

    if (this.next == null) {
      throw new NoSuchElementException();
    } else {
      E result = this.next;
      this.next = null;
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
  @Override
  public final void remove() throws UnsupportedOperationException {

    throw new UnsupportedOperationException();
  }

}
