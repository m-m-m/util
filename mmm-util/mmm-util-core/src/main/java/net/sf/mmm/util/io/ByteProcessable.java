/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io;

/**
 * This is the interface for an object providing data represented as sequence of
 * <code>byte</code>s - typically some sort of buffer.<br>
 * Following the idiom <em>separations of concerns</em> the idea is to allow
 * fast and easy {@link ByteProcessor#process(byte[], int, int) processing} of
 * the data without any dependency or knowledge of the internals of this
 * data-provider.<br>
 * The user of this API can implement a {@link ByteProcessor} (e.g. as anonymous
 * or inner class) with its custom logic. He is entirely independent from the
 * underlying implementation of this interface that may use one big byte-array
 * to store the provided data or have it sliced into multiple smaller
 * byte-arrays.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ByteProcessable {

  /**
   * This method processes the number of bytes given by <code>length</code>
   * (as far as available) using the given <code>processor</code>.
   * 
   * @param processor is the {@link ByteProcessor} called to process the bytes.
   *        It may be called multiple types if the data is sliced into multiple
   *        byte-arrays.
   * @param length is the desired number of bytes to process. The value has to
   *        be greater or equal to <code>0</code>. A value of <code>0</code>
   *        will have no effect. If you want to process all available data to
   *        the end of stream or buffer you may use {@link Long#MAX_VALUE}.
   * @return the number of bytes that have actually been processed. This will
   *         typically be equal to <code>length</code>. However if the end of
   *         the data has been reached, a smaller value is returned. The value
   *         will always be greater or equal to <code>0</code>.
   */
  long process(ByteProcessor processor, long length);

}
