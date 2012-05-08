/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

import java.util.NoSuchElementException;

/**
 * This is the interface for an {@link java.util.Iterator} of <code>byte</code> -values.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public interface ByteIterator {

  /**
   * This method determines if there is a {@link #next() next byte} available.
   * 
   * @return <code>true</code> if there is a {@link #next() next byte} available, <code>false</code> otherwise
   *         (if the end of this buffer has been reached).
   */
  boolean hasNext();

  /**
   * This method gets the current byte in the iteration. After the call of this method this
   * {@link ByteIterator} points to the next byte in the iteration or to the end if there is no such byte
   * {@link #hasNext() available}.<br>
   * <b>ATTENTION:</b><br>
   * You should only call this method if {@link #hasNext()} returns <code>true</code>.
   * 
   * @see java.util.Iterator#next()
   * 
   * @return the current byte in the iteration.
   * @throws NoSuchElementException if there is no such byte {@link #hasNext() available}.
   */
  byte next() throws NoSuchElementException;

  /**
   * This method gets the current byte in the iteration. Unlike {@link #next()} this method does NOT modify
   * the state of this {@link ByteIterator}. Therefore the peeked byte does NOT get consumed and repetitive
   * calls will return the same value.<br>
   * <b>ATTENTION:</b><br>
   * You should only call this method if {@link #hasNext()} returns <code>true</code>.
   * 
   * @see #next()
   * 
   * @return the current byte in the iteration.
   * @throws NoSuchElementException if there is no such byte {@link #hasNext() available}.
   */
  byte peek() throws NoSuchElementException;

  /**
   * This method skips the number of bytes given by <code>byteCount</code>.
   * 
   * @see java.io.InputStream#skip(long)
   * 
   * @param byteCount is the expected number of bytes to skip.
   * @return the number of bytes that have actually been skipped. This will typically be equal to
   *         <code>byteCount</code>. However the value may be less if the end of this iterator has been
   *         reached before the according number of bytes have been skipped. The value will always be greater
   *         or equal to <code>0</code>.
   */
  long skip(long byteCount);

}
