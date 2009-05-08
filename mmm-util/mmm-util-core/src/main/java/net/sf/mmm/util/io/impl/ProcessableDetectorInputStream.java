/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import net.sf.mmm.util.io.api.DetectorInputStream;
import net.sf.mmm.util.io.api.DetectorOutputStream;

/**
 * This is the implementation of the {@link DetectorOutputStream}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ProcessableDetectorInputStream extends ProcessableDetectorStream implements
    DetectorInputStream {

  /** @see #getStream() */
  private final WrapperInputStream wrapperInputStream;

  /**
   * The constructor.
   * 
   * @param inputStream is the raw {@link InputStream} to {@link #getStream()
   *        warp}.
   */
  public ProcessableDetectorInputStream(InputStream inputStream) {

    super();
    this.wrapperInputStream = new WrapperInputStream(inputStream);
  }

  /**
   * The constructor.
   * 
   * @param inputStream is the raw {@link InputStream} to {@link #getStream()
   *        warp}.
   * @param mutableMetadata is the initial {@link #getMutableMetadata() mutable
   *        metadata}.
   */
  public ProcessableDetectorInputStream(InputStream inputStream, Map<String, Object> mutableMetadata) {

    super(mutableMetadata);
    this.wrapperInputStream = new WrapperInputStream(inputStream);
  }

  /**
   * {@inheritDoc}
   */
  public InputStream getStream() {

    return this.wrapperInputStream;
  }

  /**
   * This inner class is the actual wrapper stream.
   * 
   * @see ProcessableDetectorInputStream#getStream()
   */
  protected class WrapperInputStream extends InputStream {

    /** The delegate adapted by this wrapper. */
    private final InputStream delegate;

    /**
     * The constructor.
     * 
     * @param inputStream is the {@link InputStream} to adapt.
     */
    public WrapperInputStream(InputStream inputStream) {

      super();
      this.delegate = inputStream;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {

      this.delegate.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int read() throws IOException {

      // TODO Auto-generated method stub
      return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int read(byte[] buffer, int offset, int length) throws IOException {

      // fill();
      process(buffer, offset, length);
      return super.read(buffer, offset, length);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int read(byte[] b) throws IOException {

      // TODO Auto-generated method stub
      return super.read(b);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long skip(long n) throws IOException {

      // TODO Auto-generated method stub
      return super.skip(n);
    }

  }

}
