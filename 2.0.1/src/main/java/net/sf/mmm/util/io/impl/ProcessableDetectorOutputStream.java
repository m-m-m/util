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
import net.sf.mmm.util.io.base.ByteArrayImpl;

/**
 * This is the implementation of the {@link DetectorOutputStream}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
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

    super(mutableMetadata, provider.getByteArrayPool());
    this.wrapperOutputStream = new WrapperOutputStream(outputStream);
    initialize(provider, this.wrapperOutputStream);
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

    /** An array of buffered bytes or <code>null</code>. */
    private byte[] bytes;

    /** The number of bytes buffered in {@link #bytes}. */
    private int count;

    /**
     * The constructor.
     * 
     * @param outputStream is the {@link OutputStream} to adapt.
     */
    public WrapperOutputStream(OutputStream outputStream) {

      super();
      this.delegate = outputStream;
      this.bytes = null;
      this.count = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {

      flushBuffer();
      processInternal(null, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void flush() throws IOException {

      flushBuffer();
      this.delegate.flush();
    }

    /**
     * This method flushes the internal {@link #bytes buffer}.
     * 
     * @throws IOException if the underlying {@link OutputStream} caused such
     *         exception.
     */
    private void flushBuffer() throws IOException {

      if (this.count > 0) {
        processInternal(new PooledByteArray(this.bytes, 0, this.count - 1), false);
        this.count = 0;
        this.bytes = null;
      }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(byte[] b, int off, int len) throws IOException {

      flushBuffer();
      processInternal(new ByteArrayImpl(b, off, len - off - 1), false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(byte[] b) throws IOException {

      flushBuffer();
      processInternal(new ByteArrayImpl(b), false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(int b) throws IOException {

      if ((this.count > 0) && (this.count >= this.bytes.length)) {
        flushBuffer();
      }
      if (this.bytes == null) {
        this.bytes = getByteArrayPool().borrow();
      }
      this.bytes[this.count++] = (byte) b;
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
      buffer.skip();
    }

  }

}
