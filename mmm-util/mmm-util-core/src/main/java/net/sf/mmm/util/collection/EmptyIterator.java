/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is an implementation of the {@link Iterator} interface that is always
 * empty. It will never have any {@link #hasNext() next} element.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class EmptyIterator implements Iterator<Object> {

  /** @see #getInstance() */
  private static final EmptyIterator INSTANCE = new EmptyIterator();

  /**
   * The constructor.
   * 
   */
  public EmptyIterator() {

    super();
  }

  /**
   * This method gets the singleton instance of this empty iterator.
   * 
   * @param <E> is the type of the element to iterate.
   * @return the empty iterator instance.
   */
  @SuppressWarnings("unchecked")
  public static <E> Iterator<E> getInstance() {

    return (Iterator<E>) INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  public boolean hasNext() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  public Object next() {

    throw new NoSuchElementException();
  }

  /**
   * {@inheritDoc}
   */
  public void remove() {

    throw new IllegalStateException();
  }

}
