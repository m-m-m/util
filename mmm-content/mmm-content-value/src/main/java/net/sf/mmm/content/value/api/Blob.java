/* $Id$ */
package net.sf.mmm.content.value.api;

import java.io.InputStream;
import java.io.OutputStream;

import net.sf.mmm.value.api.ValueIOException;

/**
 * This is the interface for a BLOB (Binary Large OBject) value. The interface
 * only declares read methods.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface Blob {

  /**
   * The {@link net.sf.mmm.value.api.ValueManager#getName() name} of this
   * value type.
   */
  String VALUE_NAME = "Blob";

  /**
   * This method gets the current size of this file.
   * 
   * @return the file size.
   */
  long getSize();

  /**
   * This method gets the MD5 checksum of this BLOB.
   * 
   * @todo return type? String / BigInt
   * 
   * @return the MD5 checksum.
   */
  String getChecksum();

  /**
   * This method gets the mimetype of this file.
   * 
   * @return the files mimetype.
   */
  String getMimeType();

  /**
   * This method gets read access to the data of this BLOB.
   * 
   * @see MutableBlob#streamData(OutputStream)
   * 
   * @return the BLOB data as input stream.
   * @throws ValueIOException
   *         if the BLOB could not be opened for reading.
   */
  InputStream getReadAccess() throws ValueIOException;

  /**
   * This method writes the data of this BLOB to the given output stream. The
   * given output stream will NOT be closed.
   * 
   * @param outStream
   *        is the stream where to write the data to.
   * @throws ValueIOException
   *         if the streaming fails.
   */
  void streamData(OutputStream outStream) throws ValueIOException;

}
