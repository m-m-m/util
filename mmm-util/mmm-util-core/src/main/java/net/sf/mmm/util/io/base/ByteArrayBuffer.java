/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.base;

import net.sf.mmm.util.io.api.ByteBuffer;

/**
 * This is the interface for a byte[] buffer. It is similar to
 * {@link java.nio.ByteBuffer} but a lot simpler. However it allows to
 * {@link #setCurrentIndex(int) set the current index} so the internal
 * {@link #getBytes() buffer}-array can be consumed externally and proceeded
 * very fast.<br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ByteArrayBuffer extends ByteBuffer {

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
   * This method gets the current index in the {@link #getBytes() buffer}. It
   * will be in the range from <code>0</code> to
   * <code>{@link #getMaximumIndex() maximumIndex} + 1</code>. If this value is
   * less or equal to {@link #getMaximumIndex() maximumIndex}, then
   * <code>{@link #getBytes() buffer}[{@link #getCurrentIndex() currentIndex}]</code>
   * is the next available byte. If it is greater than
   * {@link #getMaximumIndex() maximumIndex}, the {@link #getBytes() buffer} is
   * consumed ({@link #hasNext() has} no next byte).
   * 
   * @return the currentIndex is the current buffer position.
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
   * This method sets the {@link #getCurrentIndex() currentIndex}. This can be
   * done if data from the {@link #getBytes() buffer} has been consumed
   * externally.<br>
   * <b>ATTENTION:</b><br>
   * Be very careful and only use this method if you know what you are doing!
   * 
   * @param currentIndex is the {@link #getCurrentIndex() currentIndex} to set.
   *        It has to be in the range from <code>0</code> to
   *        <code>{@link #getMaximumIndex() maximumIndex} + 1</code>. A value of
   *        <code>{@link #getMaximumIndex() maximumIndex} + 1</code> indicates that the
   *        buffer is consumed.
   */
  void setCurrentIndex(int currentIndex);

}
