/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.impl;

import net.sf.mmm.util.io.base.ByteArrayImpl;

/**
 * This is an implementation of {@link net.sf.mmm.util.io.api.ByteArray} that
 * holds a pooled {@link #getBytes() byte-array}.
 * 
 * @see net.sf.mmm.util.pool.api.ByteArrayPool
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.3
 */
public class PooledByteArray extends ByteArrayImpl {

  /**
   * The constructor.
   * 
   * @param buffer is the internal {@link #getBytes() buffer}.
   */
  public PooledByteArray(byte[] buffer) {

    super(buffer);
  }

  /**
   * The constructor.
   * 
   * @param buffer is the internal {@link #getBytes() buffer}.
   * @param startIndex is the {@link #getCurrentIndex() current index} as well
   *        as the {@link #getMinimumIndex() minimum index}.
   * @param maximumIndex is the {@link #getMaximumIndex() maximum index}.
   */
  public PooledByteArray(byte[] buffer, int startIndex, int maximumIndex) {

    super(buffer, startIndex, maximumIndex);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ByteArrayImpl createSubArray(int minimum, int maximum) {

    checkSubArray(minimum, maximum);
    return new PooledByteArray(getBytes(), minimum, maximum);
  }

}
