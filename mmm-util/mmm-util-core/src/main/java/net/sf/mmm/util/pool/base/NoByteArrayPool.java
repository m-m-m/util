/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pool.base;

/**
 * This is a {@link AbstractNoPool dummy pool} for byte-arrays.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class NoByteArrayPool extends AbstractNoPool<byte[]> {

  /** The singleton instance. */
  public static final NoByteArrayPool INSTANCE = new NoByteArrayPool();

  /** The default length of the managed byte-arrays. */
  private static final int ARRAY_LENGTH = 4096;

  /**
   * The constructor.
   */
  private NoByteArrayPool() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public byte[] borrow() {

    return new byte[ARRAY_LENGTH];
  }

}
