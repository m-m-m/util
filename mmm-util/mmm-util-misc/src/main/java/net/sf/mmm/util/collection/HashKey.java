/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

/**
 * This is a simple class that allows to use any {@link #getDelegate() object}
 * as {@link java.util.Map#get(Object) hash-key}. It only
 * {@link #equals(Object) matches} the exact same instance regardless of the
 * {@link Object#equals(Object)} method of the
 * {@link #getDelegate() delegate object}.
 * 
 * @param <T>
 *        is the templated type of the {@link #getDelegate() delegate-object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class HashKey<T> {

  /** @see #getDelegate() */
  private T delegate;

  /**
   * The constructor.
   * 
   * @param object
   *        is the {@link #getDelegate() delegate object}.
   */
  public HashKey(T object) {

    super();
    this.delegate = object;
  }

  /**
   * This method gets the original object this hash-key delegates to.
   * 
   * @return the data object.
   */
  public T getDelegate() {

    return this.delegate;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    if (this.delegate == null) {
      return 0;
    }
    return this.delegate.hashCode();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    return (this.delegate == obj);
  }

}
