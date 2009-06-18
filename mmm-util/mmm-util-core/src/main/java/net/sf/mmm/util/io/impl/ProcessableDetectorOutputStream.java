/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import net.sf.mmm.util.io.api.ByteArray;
import net.sf.mmm.util.io.api.DetectorOutputStream;
import net.sf.mmm.util.io.api.spi.DetectorStreamBuffer;
import net.sf.mmm.util.io.api.spi.DetectorStreamProcessor;
import net.sf.mmm.util.io.base.AbstractDetectorStreamProvider;

/**
 * This is the implementation of the {@link DetectorOutputStream}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.3
 */
public class ProcessableDetectorOutputStream extends ProcessableDetectorStream implements
    DetectorOutputStream {

  /** @see WrapperOutputStream#write(int) */
  private static final byte[] SINGLE_BYTE_ARRAY = new byte[1];

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

    this(mutableMetadata, provider);
    this.wrapperOutputStream = new WrapperOutputStream(outputStream);
  }

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
  private ProcessableDetectorOutputStream(OutputStream outputStream,
      Map<String, Object> mutableMetadata, AbstractDetectorStreamProvider provider,
      WrapperOutputStream wrapperOutputStream) {

    super(mutableMetadata, provider, wrapperOutputStream);
    this.wrapperOutputStream = wrapperOutputStream;
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
  protected class WrapperOutputStream extends OutputStream implements DetectorStreamProcessor {

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

      processInternal(b, off, len, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(byte[] b) throws IOException {

      processInternal(b, 0, b.length, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(int b) throws IOException {

      SINGLE_BYTE_ARRAY[0] = (byte) b;
      write(SINGLE_BYTE_ARRAY);
    }

    /**
     * {@inheritDoc}
     */
    public void process(DetectorStreamBuffer buffer, Map<String, Object> metadata, boolean eos)
        throws IOException {

      int arrayCount = buffer.getByteArrayCount();
      for (int i = 0; i < arrayCount; i++) {
        ByteArray byteArray = buffer.getByteArray(i);
        this.delegate.write(byteArray.getBytes(), byteArray.getCurrentIndex(), byteArray
            .getBytesAvailable());
      }
    }

  }

}
