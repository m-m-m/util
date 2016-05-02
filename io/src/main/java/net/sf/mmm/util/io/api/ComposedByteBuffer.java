/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

/**
 * This is the interface of a {@link ByteBuffer} that is internally composed out of multiple {@link ByteArray}
 * s. It allows to {@link #getByteArray(int) access} these {@link ByteArray}s for read-only lookahead
 * operations or more efficient processing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public interface ComposedByteBuffer extends ByteBuffer {

  /**
   * This method gets the {@link ByteArray} at the given <code>index</code>. The
   * {@link net.sf.mmm.util.io.api.spi.DetectorStreamBuffer} is composed out of {@link ByteArray}s. This
   * method allows efficient processing of bytes from <code>byte[]</code> rather then calling
   * {@link #hasNext()} and {@link #next()} and repetitive. Additionally this way allows full lookahead up to
   * the end of the buffer without consuming the data. <br>
   * <b>ATTENTION:</b><br>
   * Consuming operations such as {@link #next()} or {@link #skip(long)} will invalidate the returned
   * {@link ByteArray}. Please do NOT call these methods while working with {@link ByteArray}s.
   * 
   * @see #getByteArrayCount()
   * 
   * @param index is the index of the requested {@link ByteArray}. It has to be in the range from
   *        <code>0</code> to <code>{@link #getByteArrayCount()} - 1</code>. A value of <code>0</code>
   *        indicates the current {@link ByteArray} this buffer is currently pointing to. For that current
   *        buffer <code>{@link ByteArray#getBytes()}[{@link ByteArray#getCurrentIndex()}]</code> will have
   *        the same result as {@link #peek()}.
   * @return the requested {@link ByteArray}.
   */
  ByteArray getByteArray(int index);

  /**
   * This method gets the number of {@link ByteArray}s currently available via {@link #getByteArray(int)}. <br>
   * <b>ATTENTION:</b><br>
   * Consuming operations such as {@link #next()} or {@link #skip(long)} will invalidate the returned
   * {@link ByteArray}. Please do NOT call these methods while working with {@link ByteArray}s.
   * 
   * @return the number of currently available {@link ByteArray}s.
   */
  int getByteArrayCount();

  /**
   * This method fills the given <code>buffer</code> starting at <code>offset</code> with the {@link #next()
   * next} bytes from this {@link ComposedByteBuffer}. The bytes that are filled into the given
   * <code>buffer</code> will therefore be consumed. This method can be used to write data from this buffer to
   * an {@link java.io.OutputStream} or supply it to the consumer of an {@link java.io.InputStream}.
   * 
   * @param buffer is the buffer to fill.
   * @param offset is the index in the given <code>buffer</code> where to fill in the first byte. See
   *        {@link ByteArray#getCurrentIndex()}.
   * @param length is the expected number of bytes to fill into <code>buffer</code>. Has to be positive and
   *        less or equal to <code>buffer.length - offset</code>. However at most the number of
   *        {@link #getBytesAvailable() available bytes} can be filled even if <code>length</code> is greater.
   * @return the number of bytes that have actually been filled into the <code>buffer</code>. Will be positive
   *         and less or equal to the given <code>length</code>. Should be only less than
   *         <code>maxLength</code> if NOT enough {@link #getBytesAvailable() bytes are available}.
   */
  int fill(byte[] buffer, int offset, int length);

}
