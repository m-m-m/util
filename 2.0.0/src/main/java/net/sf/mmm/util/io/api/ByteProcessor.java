/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

/**
 * This is a call back interface that allows efficient processing of
 * byte-buffers.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
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
   * @return the number of bytes that should be consumed. Typically you will
   *         simply return <code>length</code>. However you can also return a
   *         value less than length and greater or equal to zero, in order to
   *         stop processing at a specific position.
   */
  int process(byte[] buffer, int offset, int length);

}
