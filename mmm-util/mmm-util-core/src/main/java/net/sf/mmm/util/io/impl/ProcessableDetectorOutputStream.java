/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import net.sf.mmm.util.io.api.DetectorOutputStream;
import net.sf.mmm.util.io.base.AbstractDetectorStreamProvider;

/**
 * This is the implementation of the {@link DetectorOutputStream}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ProcessableDetectorOutputStream extends ProcessableDetectorStream implements
    DetectorOutputStream {

  /** @see #getStream() */
  private final WrapperOutputStream wrapperOutputStream;

  /**
   * The constructor.
   * 
   * @param outputStream is the raw {@link OutputStream} to {@link #getStream()
   *        warp}.
   * @param mutableMetadata is the initial {@link #getMutableMetadata() mutable
   *        metadata}.
   * @param provider is the
   *        {@link net.sf.mmm.util.io.api.DetectorStreamProvider} creating this
   *        instance.
   */
  public ProcessableDetectorOutputStream(OutputStream outputStream,
      Map<String, Object> mutableMetadata, AbstractDetectorStreamProvider provider) {

    super(mutableMetadata, provider);
    this.wrapperOutputStream = new WrapperOutputStream(outputStream);
  }

  /**
   * {@inheritDoc}
   */
  public OutputStream getStream() {

    return this.wrapperOutputStream;
  }

  /**
   * This inner class is the actual wrapper stream.
   * 
   * @see ProcessableDetectorOutputStream#getStream()
   */
  protected class WrapperOutputStream extends OutputStream {

    /** The delegate adapted by this wrapper. */
    private final OutputStream delegate;

    /**
     * The constructor.
     * 
     * @param outputStream is the {@link OutputStream} to adapt.
     */
    public WrapperOutputStream(OutputStream outputStream) {

      super();
      this.delegate = outputStream;
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
    public void flush() throws IOException {

      // TODO: flush internal buffer!!! Might not work!!!
      this.delegate.flush();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(byte[] b, int off, int len) throws IOException {

      process(b, off, len);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(byte[] b) throws IOException {

      // TODO Auto-generated method stub
      super.write(b);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(int b) throws IOException {

    // TODO Auto-generated method stub

    }

  }

}
