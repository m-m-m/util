/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.api;

import java.io.InputStream;
import java.io.OutputStream;

import net.sf.mmm.value.api.ValueIOException;

/**
 * This is the interface for a BLOB (Binary Large OBject) value. This method
 * extends the {@link net.sf.mmm.content.value.api.Blob}interface with
 * write-methods.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface MutableBlob extends Blob {

  /**
   * This method gets write access to the data of this BLOB.
   * 
   * @see MutableBlob#writeData(InputStream, boolean)
   * 
   * @param append
   *        <code>true</code> if the data written to the stream should be
   *        appended to the BLOB, <code>false</code> if the BLOB should be
   *        overridden.
   * @return an output stream to write the BLOB data to.
   * @throws ValueIOException
   *         if the BLOB could not be opened for writing.
   */
  OutputStream getWriteAccess(boolean append) throws ValueIOException;

  /**
   * This method writes the data from the give input stream to this BLOB. The
   * given input stream will be closed at the end.
   * 
   * @param inputStream
   *        is an input stream containing the data to write to this BLOB.
   * @param append
   *        <code>true</code> if the data should be appended to the BLOB,
   *        <code>false</code> if the BLOB should be overridden.
   * @throws ValueIOException
   *         if the writing fails.
   */
  void writeData(InputStream inputStream, boolean append) throws ValueIOException;

}
