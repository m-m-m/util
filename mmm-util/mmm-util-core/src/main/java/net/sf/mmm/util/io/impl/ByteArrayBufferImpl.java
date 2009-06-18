/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.impl;

import java.util.NoSuchElementException;

import net.sf.mmm.util.io.api.ByteArrayBuffer;
import net.sf.mmm.util.io.base.ByteArrayImpl;

/**
 * This class is similar to {@link java.nio.ByteBuffer} but a lot simpler.
 * However it allows to {@link #setCurrentIndex(int) set the current index} so
 * the internal {@link #getBytes() buffer}-array can be consumed externally and
 * proceeded very fast.<br>
 * <b>ATTENTION:</b><br>
 * This class is NOT intended to be exposed. It should only be used internally
 * by some class or component.<br>
 * 
 * @see java.nio.ByteBuffer#wrap(byte[], int, int)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class ByteArrayBufferImpl extends ByteArrayImpl implements ByteArrayBuffer {

  /**
   * The constructor.
   * 
   * @param capacity is the <code>length</code> of the internal
   *        {@link #getBytes() buffer}.
   */
  public ByteArrayBufferImpl(int capacity) {

    super(capacity);
  }

  /**
   * The constructor.
   * 
   * @param buffer is the internal {@link #getBytes() buffer}.
   */
  public ByteArrayBufferImpl(byte[] buffer) {

    super(buffer);
  }

  /**
   * The constructor.
   * 
   * @param buffer is the internal {@link #getBytes() buffer}.
   * @param currentIndex is the {@link #getCurrentIndex() current index}.
   * @param maximumIndex is the {@link #getMaximumIndex() maximum index}.
   */
  public ByteArrayBufferImpl(byte[] buffer, int currentIndex, int maximumIndex) {

    super(buffer, currentIndex, maximumIndex);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCurrentIndex(int currentIndex) {

    super.setCurrentIndex(currentIndex);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumIndex(int maximumIndex) {

    super.setMaximumIndex(maximumIndex);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte next() throws NoSuchElementException {

    return super.next();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte peek() throws NoSuchElementException {

    return super.peek();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasNext() {

    return super.hasNext();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long skip(long byteCount) {

    return super.skip(byteCount);
  }

}
