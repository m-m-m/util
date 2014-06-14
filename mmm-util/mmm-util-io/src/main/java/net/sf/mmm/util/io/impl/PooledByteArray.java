/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.impl;

import net.sf.mmm.util.exception.api.NlsIllegalStateException;
import net.sf.mmm.util.io.base.ByteArrayImpl;

/**
 * This is an implementation of {@link net.sf.mmm.util.io.api.ByteArray} that holds a pooled
 * {@link #getBytes() byte-array}.
 * 
 * @see net.sf.mmm.util.pool.api.ByteArrayPool
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PooledByteArray extends ByteArrayImpl {

  /** The parent that {@link #createSubArray(int, int) created} this array. */
  private final PooledByteArray parent;

  /** The number of {@link #createSubArray(int, int) children} created. */
  private int childCount;

  /** @see #release() */
  private boolean released;

  /**
   * The constructor.
   * 
   * @param buffer is the internal {@link #getBytes() buffer}.
   */
  public PooledByteArray(byte[] buffer) {

    this(buffer, 0, buffer.length - 1);
  }

  /**
   * The constructor.
   * 
   * @param buffer is the internal {@link #getBytes() buffer}.
   * @param startIndex is the {@link #getCurrentIndex() current index} as well as the
   *        {@link #getMinimumIndex() minimum index}.
   * @param maximumIndex is the {@link #getMaximumIndex() maximum index}.
   */
  public PooledByteArray(byte[] buffer, int startIndex, int maximumIndex) {

    this(buffer, startIndex, maximumIndex, null);
  }

  /**
   * The constructor.
   * 
   * @param buffer is the internal {@link #getBytes() buffer}.
   * @param startIndex is the {@link #getCurrentIndex() current index} as well as the
   *        {@link #getMinimumIndex() minimum index}.
   * @param maximumIndex is the {@link #getMaximumIndex() maximum index}.
   * @param parent is the parent that {@link #createSubArray(int, int) created} this array.
   */
  protected PooledByteArray(byte[] buffer, int startIndex, int maximumIndex, PooledByteArray parent) {

    super(buffer, startIndex, maximumIndex);
    this.parent = parent;
    this.childCount = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ByteArrayImpl createSubArray(int minimum, int maximum) {

    if ((this.parent != null) && (!this.parent.released)) {
      // avoid chains that could cause release leaks...
      return this.parent.createSubArray(minimum, maximum);
    }
    if (this.released) {
      throw new NlsIllegalStateException();
    }
    checkSubArray(minimum, maximum);
    this.childCount++;
    return new PooledByteArray(getBytes(), minimum, maximum, this);
  }

  /**
   * This method marks this array to be released.
   * 
   * @return <code>true</code> if this array can be released, <code>false</code> if there are references left
   *         that have to be released before.
   */
  public boolean release() {

    if (this.released) {
      // already (marked as) released...
      return false;
    }
    this.released = true;
    if (this.childCount == 0) {
      if (this.parent == null) {
        return true;
      } else {
        assert (this.parent.childCount > 0);
        this.parent.childCount--;
        if ((this.parent.childCount == 0) && (this.parent.released)) {
          return true;
        }
      }
    }
    return false;
  }
}
