/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.base;

import net.sf.mmm.util.lang.api.CharIterator;

/**
 * This is an abstract base implementation of the {@link CharIterator} interface. It allows to implement a lookahead
 * {@link CharIterator} easier:<br>
 * Simply extend this class and implement {@link #findNext()}. From your constructor or initializer call
 * {@link #findFirst()}. <br>
 * <b>ATTENTION:</b><br>
 * Do NOT forget to call {@link #findFirst()} from your constructor or your iterator will always be empty.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public abstract class AbstractCharIterator implements CharIterator {

  /** The next char to iterate. */
  private char next;

  /**
   * The constructor.
   */
  public AbstractCharIterator() {

    super();
    this.next = END_OF_ITERATOR;
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
   * @return the next element or {@code null} if {@link #hasNext() done}.
   */
  protected abstract char findNext();

  @Override
  public boolean hasNext() {

    return (this.next != END_OF_ITERATOR);
  }

  @Override
  public char next() {

    char c = this.next;
    if (c != END_OF_ITERATOR) {
      this.next = findNext();
    }
    return c;
  }
}
