/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pool.base;

import net.sf.mmm.util.pool.api.CharArrayPool;

/**
 * This is a {@link AbstractNoPool dummy pool} for char-arrays.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class NoCharArrayPool extends AbstractNoPool<char[]> implements CharArrayPool {

  /** The singleton instance. */
  public static final NoCharArrayPool INSTANCE = new NoCharArrayPool();

  /** The default length of the managed char-arrays. */
  private static final int ARRAY_LENGTH = 2048;

  /**
   * The constructor.
   */
  private NoCharArrayPool() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public char[] borrow() {

    return new char[ARRAY_LENGTH];
  }

}
