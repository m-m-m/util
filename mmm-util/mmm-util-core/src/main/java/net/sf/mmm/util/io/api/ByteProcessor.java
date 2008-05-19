/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

/**
 * This is a call back interface that allows efficient processing of
 * byte-buffers.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ByteProcessor {

  /**
   * This method is called to process the number of <code>length</code> bytes
   * from the given <code>buffer</code> starting from the given
   * <code>offset</code>.<br>
   * <b>ATTENTION:</b><br>
   * An implementation of this interface should only read bytes from the given
   * <code>buffer</code>. It is NOT permitted to modify the given
   * <code>buffer</code> unless this is explicitly specified by the calling
   * object (typically an implementation of {@link ByteProcessable}).
   * 
   * @param buffer contains the bytes to process.
   * @param offset is the index where to start in the <code>buffer</code>.
   * @param length is the number of bytes to proceed.
   */
  void process(byte[] buffer, int offset, int length);

}
