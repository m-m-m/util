/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

/**
 * This is an abstract interface for some object (typically some buffer) that
 * provides bytes. The main impact is to avoid redundant method declarations. It
 * should not be used directly.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.3
 */
public abstract interface ByteProvider {

  /**
   * This method gets the number of bytes available.
   * 
   * @return the bytes left.
   */
  int getBytesAvailable();

  /**
   * This method determines if this {@link ByteProvider} is empty. In other
   * words this method returns <code>{@link #getBytesAvailable()} == 0</code>.
   * 
   * @return <code>true</code> if empty, <code>false</code> otherwise.
   */
  boolean isEmpty();

}
