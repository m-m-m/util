/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;

import net.sf.mmm.util.exception.api.NlsIllegalArgumentException;
import net.sf.mmm.util.io.api.BufferExceedException;
import net.sf.mmm.util.io.api.ByteArray;
import net.sf.mmm.util.io.api.ByteArrayBuffer;
import net.sf.mmm.util.io.api.ByteProcessor;
import net.sf.mmm.util.io.api.ComposedByteBuffer;
import net.sf.mmm.util.io.api.ProcessableByteArrayBuffer;
import net.sf.mmm.util.io.base.ByteArrayImpl;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This is the abstract base implementation of the {@link ProcessableByteArrayBuffer} interface for a
 * <code>byte[]</code>-Buffer that represents the concatenation of multiple {@link ByteArrayBufferImpl}s. It
 * has its own state (read-pointer) and does NOT modify a contained {@link ByteArrayBufferImpl buffer} when
 * reading. If one of the underlying {@link ByteArrayBufferImpl buffers} has been read to the
 * {@link ByteArrayBufferImpl#getMaximumIndex() end} this class steps to the next one in a rotating way until
 * the last buffer has been reached, that contains data that has NOT been read before. Further this class
 * allows to be {@link #fill(InputStream) (re)filled}.<br>
 * <b>NOTE:</b><br>
 * This class is NOT public visible, because further releases might break it's compatibility. Feel free to
 * review and give feedback on the mailing list if you want to use it directly.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public abstract class AbstractByteArrayBufferBuffer implements ProcessableByteArrayBuffer, ComposedByteBuffer {

  /** The actual buffers. */
  private final ByteArrayBufferImpl[] buffers;

  /** The index of the current buffer out of {@link #buffers}. */
  private int buffersIndex;

  /** The index of the last buffer out of {@link #buffers}. */
  private int buffersEndIndex;

  /** The number of buffers that have been stepped through. */
  private int bufferStepCount;

  /**
   * The current position in <code>{@link #buffers}[{@link #buffersIndex}]</code>.
   */
  private int currentBufferIndex;

  /**
   * The value of
   * <code>{@link #buffers}[{@link #buffersIndex}].{@link ByteArrayBufferImpl#getMaximumIndex() getMaximumIndex()}</code>
   * .
   */
  private int currentBufferMax;

  /**
   * The value of
   * <code>{@link #buffers}[{@link #buffersIndex}].{@link ByteArrayBufferImpl#getBytes() getBytes()}</code> .
   */
  private byte[] currentBufferBytes;

  /**
   * The constructor.
   * 
   * @param buffers are the buffers to concat.
   */
  public AbstractByteArrayBufferBuffer(ByteArrayBufferImpl... buffers) {

    super();
    this.buffers = buffers;
    this.buffersIndex = 0;
    this.buffersEndIndex = 0;
    this.bufferStepCount = 0;
    ByteArrayBuffer buffer = this.buffers[this.buffersIndex];
    this.currentBufferIndex = buffer.getCurrentIndex();
    this.currentBufferMax = buffer.getMaximumIndex();
    this.currentBufferBytes = buffer.getBytes();
    while (buffer.hasNext()) {
      this.buffersEndIndex++;
      if (this.buffersEndIndex < this.buffers.length) {
        buffer = this.buffers[this.buffersIndex];
      } else {
        this.buffersEndIndex--;
        break;
      }
    }
  }

  /**
   * The constructor used to copy from the given <code>template</code>.
   * 
   * @param template is the buffer to copy.
   */
  protected AbstractByteArrayBufferBuffer(AbstractByteArrayBufferBuffer template) {

    super();
    this.buffers = template.buffers;
    this.buffersEndIndex = template.buffersEndIndex;
    this.buffersIndex = template.buffersIndex;
    this.bufferStepCount = template.bufferStepCount;
    this.currentBufferBytes = template.currentBufferBytes;
    this.currentBufferIndex = template.currentBufferIndex;
    this.currentBufferMax = template.currentBufferMax;
  }

  /**
   * This method gets the current {@link ByteArrayBufferImpl}.
   * 
   * @return the current {@link ByteArrayBufferImpl}.
   */
  protected ByteArrayBuffer getCurrentBuffer() {

    return this.buffers[this.buffersIndex];
  }

  /**
   * This method gets the current index in the {@link #getCurrentBuffer() current buffer}.
   * 
   * @return the position in the {@link #getCurrentBuffer() current buffer}.
   */
  protected int getCurrentBufferIndex() {

    return this.currentBufferIndex;
  }

  /**
   * This method switches the {@link #getCurrentBuffer() current buffer} to the next available buffer. If this
   * method is called when the last buffer has already been reached, the {@link #getCurrentBufferIndex()
   * index} will be set to
   * <code>{@link #getCurrentBuffer()}.{@link ByteArrayBufferImpl#getMaximumIndex() getMaximumIndex()}+1</code>
   * so the end of this buffer is reached and {@link #hasNext()} will return <code>false</code>.
   * 
   * @return <code>true</code> if there was a next buffer to switch to, <code>false</code> if the
   *         {@link #getCurrentBuffer() current buffer} is already the last one.
   */
  protected boolean nextBuffer() {

    if (this.buffersIndex == this.buffersEndIndex) {
      return false;
    }
    this.bufferStepCount++;
    this.buffersIndex++;
    if (this.buffersIndex >= this.buffers.length) {
      this.buffersIndex = 0;
    }
    ByteArrayBuffer buffer = this.buffers[this.buffersIndex];
    this.currentBufferBytes = buffer.getBytes();
    this.currentBufferIndex = buffer.getCurrentIndex();
    this.currentBufferMax = buffer.getMaximumIndex();
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getBytesAvailable() {

    int count = this.currentBufferMax - this.currentBufferIndex + 1;
    int i = this.buffersIndex;
    while (i != this.buffersEndIndex) {
      i++;
      if (i >= this.buffers.length) {
        i = 0;
      }
      ByteArrayBuffer buffer = this.buffers[i];
      count = count + buffer.getBytesAvailable();
    }
    return count;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean hasNext() {

    if (this.currentBufferIndex <= this.currentBufferMax) {
      return true;
    }
    return nextBuffer();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte next() throws NoSuchElementException {

    if (this.currentBufferIndex <= this.currentBufferMax) {
      byte result = this.currentBufferBytes[this.currentBufferIndex++];
      if (this.currentBufferIndex > this.currentBufferMax) {
        nextBuffer();
      }
      return result;
    } else {
      throw new NoSuchElementException();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public byte peek() throws NoSuchElementException {

    if (this.currentBufferIndex <= this.currentBufferMax) {
      return this.currentBufferBytes[this.currentBufferIndex];
    } else {
      throw new NoSuchElementException();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long skip(long byteCount) {

    return process(null, byteCount);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long process(ByteProcessor processor, long byteCount) {

    if (byteCount < 0) {
      throw new NlsIllegalArgumentException(Long.valueOf(byteCount));
    }
    long count = byteCount;
    while (count > 0) {
      int bytesLeft = this.currentBufferMax - this.currentBufferIndex + 1;
      int len;
      if (bytesLeft > count) {
        len = (int) count;
      } else {
        len = bytesLeft;
      }
      int consumed = len;
      if (processor != null) {
        consumed = processor.process(this.currentBufferBytes, this.currentBufferIndex, len);
        if ((consumed < 0) || (consumed > len)) {
          throw new ValueOutOfRangeException(Integer.valueOf(consumed), Integer.valueOf(0), Integer.valueOf(len),
              processor);
        }
      }
      this.currentBufferIndex = this.currentBufferIndex + consumed;
      count = count - consumed;
      if ((count == 0) || (consumed < len)) {
        break;
      }
      if (this.currentBufferIndex <= this.currentBufferMax) {
        break;
      }
      boolean hasNext = nextBuffer();
      if (!hasNext) {
        break;
      }
    }
    return byteCount - count;
  }

  /**
   * This method synchronizes the buffer with the given <code>master</code>.
   * 
   * @param master is the buffer this buffer was created from.
   */
  protected void sync(AbstractByteArrayBufferBuffer master) {

    this.buffersEndIndex = master.buffersEndIndex;
    int bufferDist = master.bufferStepCount - this.bufferStepCount;
    if (bufferDist > 0) {
      this.buffersIndex = master.buffersIndex;
      this.bufferStepCount = master.bufferStepCount;
      ByteArrayBuffer buffer = this.buffers[this.buffersIndex];
      this.currentBufferBytes = buffer.getBytes();
      this.currentBufferIndex = buffer.getCurrentIndex();
      this.currentBufferMax = buffer.getMaximumIndex();
    }
  }

  /**
   * This method fills this buffer using the given <code>inputStream</code>. If the buffer is already filled,
   * this method will have no effect and return <code>false</code>.
   * 
   * @param inputStream is the {@link InputStream} providing the data to fill this buffer with.
   * @throws IOException if caused by the <code>inputStream</code> whilst reading.
   * @return <code>true</code> if the end of the stream was encountered while (re)filling this buffer,
   *         <code>false</code> otherwise.
   */
  protected boolean fill(InputStream inputStream) throws IOException {

    int nextEnd = this.buffersEndIndex + 1;
    if (nextEnd >= this.buffers.length) {
      nextEnd = 0;
    }
    boolean todo = true;
    while (todo) {
      ByteArrayBufferImpl buffer = this.buffers[nextEnd];
      if (nextEnd == this.buffersIndex) {
        if (buffer.hasNext()) {
          break;
        } else {
          // the buffer was initially completely empty...
          nextBuffer();
          todo = false;
        }
      }
      if (nextEnd != this.buffersIndex) {
        int bytes = inputStream.read(buffer.getBytes());
        if (bytes == 0) {
          // strange API but however give it another try...
          bytes = inputStream.read(buffer.getBytes());
        }
        if (bytes > 0) {
          buffer.setCurrentIndex(0);
          buffer.setMaximumIndex(bytes - 1);
        } else {
          assert (bytes == -1);
          return true;
        }
      }
      this.buffersEndIndex = nextEnd;
      nextEnd++;
      if (nextEnd >= this.buffers.length) {
        nextEnd = 0;
      }
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int fill(byte[] buffer, int offset, int length) {

    if (offset >= buffer.length) {
      throw new BufferExceedException(offset, buffer.length);
    }
    if ((length + offset) > buffer.length) {
      throw new BufferExceedException(length, buffer.length - offset);
    }
    if (!hasNext()) {
      // buffer is empty...
      return 0;
    }
    int bufferIndex = offset;
    int bytesLeft = length;

    while (bytesLeft > 0) {
      int count = this.currentBufferMax - this.currentBufferIndex + 1;
      if (count > bytesLeft) {
        count = bytesLeft;
        bytesLeft = 0;
      } else {
        bytesLeft = bytesLeft - count;
      }
      System.arraycopy(this.currentBufferBytes, this.currentBufferIndex, buffer, bufferIndex, count);
      bufferIndex = bufferIndex + count;
      this.currentBufferIndex = this.currentBufferIndex + count;
      if (this.currentBufferIndex > this.currentBufferMax) {
        boolean bufferLeft = nextBuffer();
        if (!bufferLeft) {
          break;
        }
      }
    }
    return (length - bytesLeft);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ByteArray getByteArray(int index) {

    assert (index >= 0);
    assert (index < getByteArrayCount());
    if (index == 0) {
      // TODO: use inner class as view instead of new object?
      return new ByteArrayImpl(this.currentBufferBytes, this.currentBufferIndex, this.currentBufferMax);
    } else {
      int newIndex = this.buffersIndex + index;
      if (newIndex > this.buffers.length) {
        if (this.buffersIndex <= this.buffersEndIndex) {
          newIndex = newIndex - this.buffers.length;
        } else {
          throw new NlsIllegalArgumentException(Integer.valueOf(index));
        }
      }
      if (newIndex > this.buffersEndIndex) {
        throw new NlsIllegalArgumentException(Integer.valueOf(index));
      }
      return this.buffers[newIndex];
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getByteArrayCount() {

    if (this.buffersIndex <= this.buffersEndIndex) {
      return this.buffersEndIndex - this.buffersIndex + 1;
    } else {
      return (this.buffers.length - this.buffersIndex) + this.buffersEndIndex;
    }
  }

}
