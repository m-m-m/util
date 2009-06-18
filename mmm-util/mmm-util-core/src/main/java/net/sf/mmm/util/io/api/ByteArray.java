/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

/**
 * This is the interface for some object that provides a {@link #getBytes()
 * byte-array} together with a range where the user of this interface may
 * {@link #getCurrentIndex() start} and {@link #getMaximumIndex() end} reading
 * in that array.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.3
 */
public interface ByteArray extends ByteProvider {

  /**
   * This method gets the underlying byte-array of this buffer. You are only
   * permitted to read the content from {@link #getCurrentIndex() currentIndex}
   * to {@link #getMaximumIndex() maximumIndex}. Only the creator of this object
   * may modify this array.
   * 
   * @see #getCurrentIndex()
   * @see #getMaximumIndex()
   * 
   * @return the buffer
   */
  byte[] getBytes();

  /**
   * This method gets the offset in the {@link #getBytes() byte array}. It will
   * be in the range from <code>0</code> to
   * <code>{@link #getMaximumIndex() maximumIndex} + 1</code>.
   * 
   * @return the offset in the {@link #getBytes() byte array}.
   */
  int getCurrentIndex();

  /**
   * This method gets the maximum index in the {@link #getBytes() buffer}. It
   * will be in the range from <code>-1</code> to
   * <code>{@link #getBytes()}.length - 1</code>.<br>
   * A negative value (<code>-1</code>) indicates that the {@link #getBytes()
   * buffer} does NOT contain data (payload).
   * 
   * @return the maximumIndex
   */
  int getMaximumIndex();

  /**
   * This method gets the number of bytes available in this array. In other
   * words this method returns
   * <code>{@link #getMaximumIndex()} - {@link #getCurrentIndex()} + 1</code>.
   * 
   * @return the bytes left in this array.
   */
  int getBytesAvailable();

}
