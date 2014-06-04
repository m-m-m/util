/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

/**
 * This is the interface for some object that holds a {@link #getBytes() byte-array} together with a range
 * where the user of this interface may {@link #getCurrentIndex() start} and {@link #getMaximumIndex() end}
 * reading in that array.<br>
 * A {@link ByteArray} is similar to {@link java.nio.ByteBuffer}. However it is a lot simpler, has an
 * interface as API and can permit modifying indices.<br>
 * <b>ATTENTION:</b><br>
 * Unfortunately there is no way in java to give a read-only view on a <code>byte[]</code> than can be
 * iterated as efficient. Unlike many APIs of the JDK this interface trusts the user NOT to modify the
 * underlying {@link #getBytes() byte-array} and therefore avoids
 * {@link System#arraycopy(Object, int, Object, int, int) arraycopy} overhead.
 * 
 * @see net.sf.mmm.util.io.base.ByteArrayImpl
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public interface ByteArray extends ByteProvider {

  /**
   * This method gets the underlying byte-array of this buffer. You are only permitted to read the content
   * from {@link #getMinimumIndex() minimumIndex} (typically {@link #getCurrentIndex() currentIndex} to
   * {@link #getMaximumIndex() maximumIndex}. Only the creator of this object may modify this array.
   * 
   * @see #getCurrentIndex()
   * @see #getMaximumIndex()
   * 
   * @return the buffer
   */
  byte[] getBytes();

  /**
   * This method gets the minimum index where to start reading in the {@link #getBytes() byte array}. It will
   * be in the range from <code>0</code> to <code>{@link #getMaximumIndex() maximumIndex} + 1</code>. This is
   * typically the same as the {@link #getCurrentIndex() current index}. However a
   * {@link net.sf.mmm.util.io.api.ByteArrayBuffer mutable variant} of a {@link ByteArray} may allow to modify
   * (increase) the {@link #getCurrentIndex() current-index}. The value returned by this method can NOT be
   * modified.
   * 
   * @return the minimum index.
   */
  int getMinimumIndex();

  /**
   * This method gets the offset in the {@link #getBytes() byte array}. It will be in the range from
   * <code>0</code> to <code>{@link #getMaximumIndex() maximumIndex} + 1</code>.
   * 
   * @return the offset in the {@link #getBytes() byte array}.
   */
  int getCurrentIndex();

  /**
   * This method gets the maximum index in the {@link #getBytes() buffer}. It will be in the range from
   * <code>-1</code> to <code>{@link #getBytes()}.length - 1</code>.<br>
   * A negative value (<code>-1</code>) indicates that the {@link #getBytes() buffer} does NOT contain data
   * (payload).
   * 
   * @return the maximumIndex
   */
  int getMaximumIndex();

  /**
   * This method gets the number of bytes available in this array. In other words this method returns
   * <code>{@link #getMaximumIndex()} - {@link #getCurrentIndex()} + 1</code>.
   * 
   * @return the bytes left in this array.
   */
  int getBytesAvailable();

  /**
   * This method creates a new {@link ByteArray} with the same {@link #getBytes() bytes} but the given
   * indices.<br>
   * <b>ATTENTION:</b><br>
   * If the implementation is immutable and the given <code>minimum</code> and <code>maximum</code> index are
   * both equal to the current indices of this {@link ByteArray} this method may return the instance itself (
   * <code>this</code>) rather than creating a new one.
   * 
   * @param minimum is the {@link #getMinimumIndex() minimumIndex} and the {@link #getCurrentIndex()
   *        currentIndex} for the new {@link ByteArray}. It has to be greater or equal to the
   *        {@link #getMinimumIndex() minimumIndex} of <code>this</code> {@link ByteArray}.
   * @param maximum is the {@link #getMaximumIndex() maximumIndex} for the new {@link ByteArray}.
   * @return a new {@link ByteArray} with the given indices.
   */
  ByteArray createSubArray(int minimum, int maximum);

}
