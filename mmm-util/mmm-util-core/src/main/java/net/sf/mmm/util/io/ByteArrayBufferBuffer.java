/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io;

import java.util.NoSuchElementException;

/**
 * This class is a <code>byte</code>-Buffer that represents the concatenation
 * of multiple {@link ByteArrayBuffer}s. The resulting
 * {@link ByteArrayBufferBuffer} has its own state and does NOT modify a
 * contained {@link ByteArrayBuffer}.<br>
 * <b>NOTE:</b><br>
 * This class is NOT public visible, because further releases might break it's
 * compatibility. Feel free to review and give feedback on the mailing list if
 * you want to use it directly.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
class ByteArrayBufferBuffer implements ByteIterator {

  /** The actual buffers. */
  private final ByteArrayBuffer[] buffers;

  /** The index of the current buffer out of {@link #buffers}. */
  private int bufferIndex;

  /** The current position in <code>{@link #buffers}[{@link #bufferIndex}]</code>. */
  private int currentBufferIndex;

  /**
   * The value of
   * <code>{@link #buffers}[{@link #bufferIndex}].{@link ByteArrayBuffer#getMaximumIndex() getMaximumIndex()}</code>.
   */
  private int currentBufferMax;

  /**
   * The value of
   * <code>{@link #buffers}[{@link #bufferIndex}].{@link ByteArrayBuffer#getBytes() getBytes()}</code>.
   */
  private byte[] currentBufferBytes;

  /**
   * The constructor.
   * 
   * @param buffers are the buffers to concat.
   */
  public ByteArrayBufferBuffer(ByteArrayBuffer... buffers) {

    super();
    this.buffers = buffers;
    this.bufferIndex = 0;
    ByteArrayBuffer buffer = this.buffers[this.bufferIndex];
    this.currentBufferIndex = buffer.getCurrentIndex();
    this.currentBufferMax = buffer.getMaximumIndex();
    this.currentBufferBytes = buffer.getBytes();
  }

  /**
   * This method gets the current {@link ByteArrayBuffer}.
   * 
   * @return the current {@link ByteArrayBuffer}.
   */
  public ByteArrayBuffer getCurrentBuffer() {

    return this.buffers[this.bufferIndex];
  }

  /**
   * This method gets the current index in the
   * {@link #getCurrentBuffer() current buffer}.
   * 
   * @return the position in the {@link #getCurrentBuffer() current buffer}.
   */
  public int getCurrentBufferIndex() {

    return this.currentBufferIndex;
  }

  /**
   * This method sets the {@link #getCurrentBufferIndex() currentBufferIndex}.
   * If the given <code>currentBufferIndex</code> is greater than the
   * {@link ByteArrayBuffer#getMaximumIndex() maximum index} of the
   * {@link #getCurrentBuffer() current buffer}, this buffer will automatically
   * switch over to the {@link #nextBuffer() next buffer}.
   * 
   * @param currentBufferIndex is the currentBufferIndex to set. The value has
   *        to be greater or equal to
   *        <code>{@link #getCurrentBuffer()}.{@link ByteArrayBuffer#getCurrentIndex()}</code>
   */
  public void setCurrentBufferIndex(int currentBufferIndex) {

    this.currentBufferIndex = currentBufferIndex;
    if (this.currentBufferIndex > this.currentBufferMax) {
      nextBuffer();
    }
  }

  /**
   * This method switches the {@link #getCurrentBuffer() current buffer} to the
   * next available buffer. If this method is called when the last buffer has
   * already been reached, the {@link #getCurrentBufferIndex() index} will be
   * set to
   * <code>{@link #getCurrentBuffer()}.{@link ByteArrayBuffer#getMaximumIndex() getMaximumIndex()}+1</code>
   * so the end of this buffer is reached and {@link #hasNext()} will return
   * <code>false</code>.
   * 
   * @return <code>true</code> if there was a next buffer to switch to,
   *         <code>false</code> if the
   *         {@link #getCurrentBuffer() current buffer} is already the last one.
   */
  public boolean nextBuffer() {

    int bufferMax = this.buffers.length - 1;
    if (this.bufferIndex == bufferMax) {
      this.currentBufferIndex = this.currentBufferMax + 1;
      return false;
    }
    while (this.bufferIndex < bufferMax) {
      ByteArrayBuffer buffer = this.buffers[this.bufferIndex++];
      // to be formal correct, only if buffer is NOT empty...
      if (buffer.hasNext()) {
        this.currentBufferIndex = buffer.getCurrentIndex();
        this.currentBufferMax = buffer.getMaximumIndex();
        this.currentBufferBytes = buffer.getBytes();
        return true;
      }
    }
    return false;
  }

  /**
   * This method shifts all internal buffers so the given
   * <code>nextBuffer</code> can be appended.
   * 
   * @param tailBuffer is the {@link ByteArrayBuffer} to append.
   * @return <code>true</code> if bytes have been lost because the
   *         {@link #getCurrentBuffer() current buffer} was the first one and
   *         has been shifted out.
   */
  public boolean shiftBuffers(ByteArrayBuffer tailBuffer) {

    int i = 0;
    int max = this.buffers.length - 1;
    while (i < max) {
      this.buffers[i++] = this.buffers[i];
    }
    this.buffers[i] = tailBuffer;
    if (this.bufferIndex > 0) {
      this.bufferIndex--;
    } else {
      // bufferIndex == 0
      ByteArrayBuffer buffer = this.buffers[this.bufferIndex];
      // to be formal correct, if a buffer is empty...
      if (buffer.hasNext()) {
        this.currentBufferIndex = buffer.getCurrentIndex();
        this.currentBufferMax = buffer.getMaximumIndex();
        this.currentBufferBytes = buffer.getBytes();
      } else {
        nextBuffer();
      }
      return true;
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  public boolean hasNext() {

    if (this.currentBufferIndex <= this.currentBufferMax) {
      return true;
    }
    return nextBuffer();
  }

  /**
   * {@inheritDoc}
   */
  public byte getNext() throws NoSuchElementException {

    if (this.currentBufferIndex <= this.currentBufferMax) {
      byte result = this.currentBufferBytes[this.currentBufferIndex++];
      if (this.currentBufferIndex > this.currentBufferMax) {
        nextBuffer();
      }
      return result;
    } else {
      boolean hasNext = nextBuffer();
      if (hasNext) {
        return this.currentBufferBytes[this.currentBufferIndex++];
      } else {
        throw new NoSuchElementException();
      }
    }
  }

  /**
   * This method skips the number of bytes given by <code>byteCount</code>.
   * 
   * @param byteCount is the number of bytes to skip.
   * @return <code>true</code> if <code>byteCount</code> bytes have been
   *         skipped, <code>false</code> if the end of this buffer has been
   *         reached before the given <code>byteCount</code>.
   */
  public boolean skip(int byteCount) {

    int count = byteCount;
    while (count > 0) {
      int bytesLeft = this.currentBufferMax - this.currentBufferIndex + 1;
      if (bytesLeft > 0) {
        if (bytesLeft <= count) {
          this.currentBufferIndex = this.currentBufferIndex + count;
          return true;
        }
        count = count - bytesLeft;
      }
      boolean hasNext = nextBuffer();
      if (!hasNext) {
        return false;
      }
    }
    return true;
  }
}
