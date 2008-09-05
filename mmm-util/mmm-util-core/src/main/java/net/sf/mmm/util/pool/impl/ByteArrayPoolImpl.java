/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pool.impl;

import net.sf.mmm.util.pool.api.ByteArrayPool;
import net.sf.mmm.util.pool.base.AbstractPool;

/**
 * This is the default implementation of the
 * {@link net.sf.mmm.util.pool.api.Pool} interface for byte-arrays.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ByteArrayPoolImpl extends AbstractPool<byte[]> implements ByteArrayPool {

  /** The default array length. */
  public static final int DEFAULT_ARRAY_LENGTH = 4096;

  /** The actual length of the byte-arrays. */
  private final int arrayLength;

  /**
   * The constructor.
   * 
   */
  public ByteArrayPoolImpl() {

    // allocate at max 64 kB raw data...
    this(DEFAULT_CAPACITY, DEFAULT_ARRAY_LENGTH);
  }

  /**
   * The constructor.
   * 
   * @param capacity is the {@link #getCapacity() capacity} of the pool.
   * @param arrayLength is the length of each pooled byte-array.
   */
  public ByteArrayPoolImpl(int capacity, int arrayLength) {

    super(capacity, true);
    this.arrayLength = arrayLength;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected byte[] create() {

    return new byte[this.arrayLength];
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean reset(byte[] element) {

    if (element.length != this.arrayLength) {
      return false;
    }
    // could be faster with System.arraycopy
    // for (int i = 0; i < element.length; i++) {
    // element[i] = 0;
    // }
    return super.reset(element);
  }

}
