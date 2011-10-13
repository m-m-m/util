/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.datatype;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import net.sf.mmm.data.api.datatype.Blob;
import net.sf.mmm.data.api.datatype.MutableBlob;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;
import net.sf.mmm.util.io.api.StreamUtil;

/**
 * This is the abstract base implementation of the {@link MutableBlob}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractBlob implements MutableBlob {

  /** UID for serialization. */
  private static final long serialVersionUID = 5684175543293788598L;

  /** The {@link StreamUtil} instance. */
  private final StreamUtil streamUtil;

  /**
   * The constructor.
   * 
   * @param streamUtil is the {@link StreamUtil} instance.
   */
  public AbstractBlob(StreamUtil streamUtil) {

    super();
    this.streamUtil = streamUtil;
  }

  /**
   * {@inheritDoc}
   */
  public void writeData(InputStream inputStream, boolean append) {

    try {
      OutputStream outputStream = null;
      try {
        outputStream = getWriteAccess(append);
        // long bytesWritten =
        this.streamUtil.transfer(inputStream, outputStream, false);
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
      throw new RuntimeIoException(e, IoMode.COPY);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void streamData(OutputStream outStream) {

    InputStream inputStream = getReadAccess();
    try {
      // copy from input stream to ouput
      this.streamUtil.transfer(inputStream, outStream, true);
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.COPY);
    }
  }

  /**
   * {@inheritDoc}
   */
  public Blob getValue() {

    return this;
  }

  /**
   * {@inheritDoc}
   */
  public String getTitle() {

    return "<Blob>";
  }

  /**
   * NOTE: Override {@link #getTitle()} instead of this method.<br/>
   * 
   * {@inheritDoc}
   */
  @Override
  public final String toString() {

    return getTitle();
  }

}
