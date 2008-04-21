/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io;

/**
 * This is the interface for provider of data bytes (a <code>byte[]</code>-Buffer)
 * that is {@link ByteIterator iterable} and {@link ByteProcessable processable}.<br>
 * Please note that {@link #process(ByteProcessor, long) processed} bytes are
 * consumed (as if they were {@link #getNext() iterated}).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ProcessableByteArrayBuffer extends ByteIterator, ByteProcessable {

  /**
   * This method gets the number of bytes currently available in this buffer.
   * 
   * @return the bytes left in this buffer.
   */
  int getBytesAvailable();

}
