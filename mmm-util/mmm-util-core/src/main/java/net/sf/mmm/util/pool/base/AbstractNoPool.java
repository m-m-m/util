/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pool.base;

import net.sf.mmm.util.pool.api.Pool;

/**
 * This is the abstract base implementation of a dummy {@link Pool} that is
 * actually NOT pooling at all. It always creates a fresh instance on
 * {@link #borrow()} and does nothing on {@link #release(Object)}.
 * 
 * @param <E> is the templated type of the elements in the pool.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractNoPool<E> implements Pool<E> {

  /**
   * The constructor.
   */
  public AbstractNoPool() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEmpty() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  public void release(E element) {

  }

}
