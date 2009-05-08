/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

/**
 * This is the interface for a buffer of bytes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ByteBuffer extends ByteIterator {

  /**
   * This method gets the number of bytes available in this buffer.
   * 
   * @return the bytes left in this buffer.
   */
  int getBytesAvailable();
}
