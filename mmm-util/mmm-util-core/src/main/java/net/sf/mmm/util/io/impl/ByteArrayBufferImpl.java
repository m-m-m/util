/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.impl;

import java.util.NoSuchElementException;

import net.sf.mmm.util.io.api.ByteArrayBuffer;
import net.sf.mmm.util.io.base.ByteArrayImpl;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

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

  /** @see #getCurrentIndex() */
  private int currentIndex;

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
    this.currentIndex = 0;
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
    this.currentIndex = currentIndex;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getCurrentIndex() {

    return this.currentIndex;
  }

  /**
   * {@inheritDoc}
   */
  public void setCurrentIndex(int currentIndex) {

    ValueOutOfRangeException.checkRange(Integer.valueOf(currentIndex), Integer
        .valueOf(getMinimumIndex()), Integer.valueOf(getMaximumIndex() + 1), "currentIndex");
    this.currentIndex = currentIndex;
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
  public byte next() throws NoSuchElementException {

    if (this.currentIndex > getMaximumIndex()) {
      throw new NoSuchElementException();
    }
    return getBytes()[this.currentIndex++];
  }

  /**
   * {@inheritDoc}
   */
  public byte peek() throws NoSuchElementException {

    if (this.currentIndex > getMaximumIndex()) {
      throw new NoSuchElementException();
    }
    return getBytes()[this.currentIndex];
  }

  /**
   * {@inheritDoc}
   */
  public boolean hasNext() {

    return (this.currentIndex <= getMaximumIndex());
  }

  /**
   * {@inheritDoc}
   */
  public long skip(long byteCount) {

    int bytesLeft = getMaximumIndex() - this.currentIndex + 1;
    int skip;
    if (bytesLeft > byteCount) {
      skip = (int) byteCount;
    } else {
      skip = bytesLeft;
    }
    this.currentIndex = this.currentIndex + skip;
    return skip;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ByteArrayImpl createSubArray(int minimum, int maximum) {

    checkSubArray(minimum, maximum);
    return new ByteArrayBufferImpl(getBytes(), minimum, maximum);
  }

}
