/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.datatype.api;

import java.io.InputStream;
import java.io.OutputStream;

import net.sf.mmm.util.io.api.RuntimeIoException;

/**
 * This is the interface for a BLOB (Binary Large OBject) value. This method
 * extends the {@link net.sf.mmm.content.datatype.api.Blob}interface with
 * write-methods.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface MutableBlob extends Blob {

  /**
   * This method gets write access to the data of this BLOB.
   * 
   * @see MutableBlob#writeData(InputStream, boolean)
   * 
   * @param append <code>true</code> if the data written to the stream should be
   *        appended to the BLOB, <code>false</code> if the BLOB should be
   *        overridden.
   * @return an output stream to write the BLOB data to.
   * @throws RuntimeIoException if the BLOB could not be opened for writing.
   */
  OutputStream getWriteAccess(boolean append) throws RuntimeIoException;

  /**
   * This method writes the data from the give input stream to this BLOB. The
   * given input stream will be closed at the end.
   * 
   * @param inputStream is an input stream containing the data to write to this
   *        BLOB.
   * @param append <code>true</code> if the data should be appended to the BLOB,
   *        <code>false</code> if the BLOB should be overridden.
   * @throws RuntimeIoException if the writing fails.
   */
  void writeData(InputStream inputStream, boolean append) throws RuntimeIoException;

  /**
   * This method gets the revision counter of this {@link Blob}. It is initially
   * <code>0</code> when the {@link Blob} is logically created and gets
   * increased on each {@link #getWriteAccess(boolean) write access}.
   * 
   * @return the revision number.
   */
  int getRevision();

}
