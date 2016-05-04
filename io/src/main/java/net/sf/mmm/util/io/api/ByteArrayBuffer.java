/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

/**
 * This is the interface for a byte[] buffer. It is similar to {@link java.nio.ByteBuffer} but a lot simpler. However it
 * allows to {@link #setCurrentIndex(int) set the current index} so the internal {@link #getBytes() buffer}-array can be
 * consumed externally and proceeded very fast. <br>
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public interface ByteArrayBuffer extends ByteArray, ByteBuffer {

  /**
   * This method sets the {@link #getCurrentIndex() currentIndex}. This can be useful e.g. if data from the
   * {@link #getBytes() buffer} has been consumed externally. <br>
   * <b>ATTENTION:</b><br>
   * Be very careful and only use this method if you know what you are doing!
   *
   * @param currentIndex is the {@link #getCurrentIndex() currentIndex} to set. It has to be in the range from
   *        {@link #getMinimumIndex() minimumIndex} to <code>{@link #getMaximumIndex() maximumIndex} + 1</code>. A value
   *        of <code>{@link #getMaximumIndex() maximumIndex} + 1</code> indicates that the buffer is consumed.
   */
  void setCurrentIndex(int currentIndex);

}
