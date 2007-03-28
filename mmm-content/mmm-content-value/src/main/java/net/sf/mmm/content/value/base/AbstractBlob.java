/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.sf.mmm.content.value.api.MutableBlob;
import net.sf.mmm.value.api.ValueIOException;

/**
 * This is the abstract base implementation of the Blob interface.
 * 
 * @todo Transaction support
 * @todo mimetype detection (wrapper stream)
 * @todo checksum support (sha-2 + filelength, wrapper stream)
 * @todo locking support
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractBlob implements MutableBlob {

  /** the size of the BLOB */
  private int size;

  /** the mimetype of the BLOB */
  private String mimetype;

  /**
   * The constructor.
   */
  public AbstractBlob() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public void writeData(InputStream inputStream, boolean append) throws ValueIOException {

    try {
      OutputStream outputStream = null;
      try {
        outputStream = getWriteAccess(append);
        // wrap input stream with mimetype detecting and size counting
        // stream implementation

        // copy data from input stream to output stream using utility class
        // that returns the number of bytes copied
        int bytesWritten = 0; // get from wrapper stream...
        // TODO:
        
        if (append) {
          this.size += bytesWritten;
        } else {
          this.size = bytesWritten;
          this.mimetype = ""; // get from wrapper stream...
        }
      } finally {
        try {
          inputStream.close();
        } finally {
          if (outputStream != null) {
            outputStream.close();            
          }
        }
      }
    } catch (IOException e) {
      // TODO Tx ? - better use Aspect
      throw new ValueIOException(e.getMessage(), e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void streamData(OutputStream outStream) throws ValueIOException {

    InputStream inputStream = getReadAccess();
    try {
      // copy from input stream to ouput

      inputStream.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * {@inheritDoc}
   */
  public long getSize() {

    return this.size;
  }

  /**
   * {@inheritDoc}
   */
  public String getMimeType() {

    return this.mimetype;
  }

}
