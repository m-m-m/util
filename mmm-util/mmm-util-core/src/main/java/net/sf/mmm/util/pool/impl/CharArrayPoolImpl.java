/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pool.impl;

import net.sf.mmm.util.pool.api.CharArrayPool;
import net.sf.mmm.util.pool.base.AbstractPool;

/**
 * This is the default implementation of the
 * {@link net.sf.mmm.util.pool.api.Pool} interface for char-arrays.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CharArrayPoolImpl extends AbstractPool<char[]> implements CharArrayPool {

  /** The default array length. */
  public static final int DEFAULT_ARRAY_LENGTH = 2048;

  /** The actual length of the char-arrays. */
  private final int arrayLength;

  /**
   * The constructor.
   */
  public CharArrayPoolImpl() {

    // allocate at max 64 kB raw data...
    this(DEFAULT_CAPACITY, DEFAULT_ARRAY_LENGTH);
  }

  /**
   * The constructor.
   * 
   * @param capacity is the {@link #getCapacity() capacity} of the pool.
   * @param arrayLength is the length of each pooled byte-array.
   */
  public CharArrayPoolImpl(int capacity, int arrayLength) {

    super(capacity, true);
    this.arrayLength = arrayLength;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected char[] create() {

    return new char[this.arrayLength];
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean reset(char[] element) {

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
