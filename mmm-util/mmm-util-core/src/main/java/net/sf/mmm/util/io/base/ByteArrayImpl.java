/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.base;

/**
 * This class is similar to {@link java.nio.ByteBuffer} but a lot simpler.
 * 
 * @see java.nio.ByteBuffer#wrap(byte[], int, int)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.3
 */
public class ByteArrayImpl extends AbstractByteArray {

  /** @see #getBytes() */
  private final byte[] buffer;

  /** @see #getMinimumIndex() */
  private int minimumIndex;

  /** @see #getMaximumIndex() */
  private int maximumIndex;

  /**
   * The constructor.
   * 
   * @param capacity is the <code>length</code> of the internal
   *        {@link #getBytes() buffer}.
   */
  public ByteArrayImpl(int capacity) {

    this(new byte[capacity], 0, -1);
  }

  /**
   * The constructor.
   * 
   * @param buffer is the internal {@link #getBytes() buffer}.
   */
  public ByteArrayImpl(byte[] buffer) {

    this(buffer, 0, buffer.length - 1);
  }

  /**
   * The constructor.
   * 
   * @param buffer is the internal {@link #getBytes() buffer}.
   * @param startIndex is the {@link #getCurrentIndex() current index} as well
   *        as the {@link #getMinimumIndex() minimum index}.
   * @param maximumIndex is the {@link #getMaximumIndex() maximum index}.
   */
  public ByteArrayImpl(byte[] buffer, int startIndex, int maximumIndex) {

    super();
    this.buffer = buffer;
    this.minimumIndex = startIndex;
    this.maximumIndex = maximumIndex;
  }

  /**
   * {@inheritDoc}
   */
  public byte[] getBytes() {

    return this.buffer;
  }

  /**
   * {@inheritDoc}
   */
  public int getCurrentIndex() {

    return this.minimumIndex;
  }

  /**
   * {@inheritDoc}
   */
  public int getMinimumIndex() {

    return this.minimumIndex;
  }

  /**
   * {@inheritDoc}
   */
  public int getMaximumIndex() {

    return this.maximumIndex;
  }

  /**
   * This method sets the {@link #getMaximumIndex() maximumIndex}. This may be
   * useful if the buffer should be reused.<br>
   * <b>ATTENTION:</b><br>
   * Be very careful and only use this method if you know what you are doing!
   * 
   * @param maximumIndex is the {@link #getMaximumIndex() maximumIndex} to set.
   *        It has to be in the range from <code>0</code> (
   *        <code>{@link #getCurrentIndex() currentIndex} - 1</code>) to
   *        <code>{@link #getBytes()}.length</code>.
   */
  protected void setMaximumIndex(int maximumIndex) {

    this.maximumIndex = maximumIndex;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ByteArrayImpl createSubArray(int minimum, int maximum) {

    checkSubArray(minimum, maximum);
    return new ByteArrayImpl(this.buffer, minimum, maximum);
  }

}
