/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection.base;

import java.io.Serializable;

import net.sf.mmm.util.lang.api.EqualsChecker;
import net.sf.mmm.util.lang.api.HashCodeFunction;

/**
 * This is an abstract base class that allows to use any {@link #getDelegate() object} as
 * {@link java.util.Map#get(Object) hash-key} with external custom implementations of {@link #equals(Object)
 * equals} and {@link Object#hashCode() hashCode}.
 * 
 * @param <T> is the generic type of the {@link #getDelegate() delegate-object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
public abstract class AbstractHashKey<T> implements Serializable {

  /** UID for serialization. */
  private static final long serialVersionUID = -2858598306180975505L;

  /** @see #getDelegate() */
  private T delegate;

  /** @see #hashCode() */
  private int hashCode;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected AbstractHashKey() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param object is the {@link #getDelegate() delegate object}.
   */
  public AbstractHashKey(T object) {

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
   * @return the {@link EqualsChecker} to use by {@link #equals(Object)}.
   */
  protected abstract EqualsChecker<T> getEqualsChecker();

  /**
   * @return the {@link HashCodeFunction} to use by {@link #hashCode()}.
   */
  protected abstract HashCodeFunction<T> getHashCodeFunction();

  @Override
  public int hashCode() {

    if (this.hashCode == 0) {
      int hash = getHashCodeFunction().hashCode(this.delegate);
      if (hash == 0) {
        hash = 1;
      }
      this.hashCode = hash;
    }
    return this.hashCode;
  }

  @Override
  public boolean equals(Object other) {

    if (other == this) {
      return true;
    }
    if (other == null) {
      return false;
    }
    if (!getClass().equals(other.getClass())) {
      return false;
    }
    try {
      @SuppressWarnings("unchecked")
      AbstractHashKey<T> otherKey = (AbstractHashKey<T>) other;
      return getEqualsChecker().isEqual(this.delegate, otherKey.delegate);
    } catch (ClassCastException e) {
      // Will never happen under sane conditions, but to get sure we better return false in such case (we
      // cannot test this before using instanceof due to generic erasure limitations)...
      return false;
    }
  }

  @Override
  public String toString() {

    if (this.delegate == null) {
      return "<null>";
    }
    return this.delegate.toString();
  }

}
