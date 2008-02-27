/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

/**
 * This is a simple class that allows to use any {@link #getDelegate() object}
 * as {@link java.util.Map#get(Object) hash-key}. It only
 * {@link #equals(Object) matches} the exact same instance regardless of the
 * {@link Object#equals(Object)} method of the
 * {@link #getDelegate() delegate-object}. Additionally it sill matches even if
 * the {@link #getDelegate() delegate-object} has been modified and therefore
 * changed its {@link Object#hashCode() hash-code}.
 * 
 * @param <T> is the templated type of the
 *        {@link #getDelegate() delegate-object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class HashKey<T> {

  /** @see #getDelegate() */
  private T delegate;

  /**
   * The constructor.
   * 
   * @param object is the {@link #getDelegate() delegate object}.
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

    return System.identityHashCode(this.delegate);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object other) {

    return (this.delegate == other);
  }

}
