/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io;

import java.util.NoSuchElementException;

/**
 * This is the interface for an {@link java.util.Iterator} of <code>byte</code>-values.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ByteIterator {

  /**
   * This method determines if there is a {@link #getNext() next byte}
   * available.
   * 
   * @return <code>true</code> if there is a {@link #getNext() next byte}
   *         available, <code>false</code> otherwise (if the end of this
   *         buffer has been reached).
   */
  boolean hasNext();

  /**
   * This method gets the next byte to iterate.<br>
   * <b>ATTENTION:</b><br>
   * You should only call this method if {@link #hasNext()} returns
   * <code>true</code>.
   * 
   * @return the next byte.
   * @throws NoSuchElementException if there is no next byte
   *         {@link #hasNext() available}.
   */
  byte getNext() throws NoSuchElementException;

}
