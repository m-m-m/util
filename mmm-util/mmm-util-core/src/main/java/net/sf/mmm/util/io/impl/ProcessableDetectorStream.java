/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.sf.mmm.util.io.api.spi.DetectorStreamProcessor;
import net.sf.mmm.util.io.api.spi.DetectorStreamProcessorFactory;
import net.sf.mmm.util.io.base.AbstractDetectorStream;
import net.sf.mmm.util.io.base.AbstractDetectorStreamProvider;
import net.sf.mmm.util.pool.api.ByteArrayPool;

/**
 * This is the abstract base implementation of a
 * {@link net.sf.mmm.util.io.api.DetectorStream} specific for this
 * implementation.
 * 
 * @see ProcessableDetectorInputStream
 * @see ProcessableDetectorInputStream
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class ProcessableDetectorStream extends AbstractDetectorStream {

  /** @see #getByteArrayPool() */
  private final ByteArrayPool byteArrayPool;

  /** The first buffer of the chain. */
  private DetectorStreamBufferImpl firstBuffer;

  /**
   * The constructor.<br>
   * <b>ATTENTION:</b><br>
   * You have to call
   * {@link #initialize(AbstractDetectorStreamProvider, DetectorStreamProcessor)}
   * after <code>super</code>-call in subclass-constructor.
   * 
   * @param mutableMetadata is the initial {@link #getMutableMetadata() mutable
   *        metadata}.
   * @param byteArrayPool is used to pool byte[]-buffers.
   */
  public ProcessableDetectorStream(Map<String, Object> mutableMetadata, ByteArrayPool byteArrayPool) {

    super(mutableMetadata);
    this.byteArrayPool = byteArrayPool;
  }

  /**
   * This method initializes this class. It has to be called to complete the
   * construction.
   * 
   * @param provider is the
   *        {@link net.sf.mmm.util.io.api.DetectorStreamProvider} creating this
   *        instance.
   * @param lastProcessor is the last {@link DetectorStreamProcessor} of the
   *        chain (the data-receiver).
   */
  public void initialize(AbstractDetectorStreamProvider provider,
      DetectorStreamProcessor lastProcessor) {

    List<DetectorStreamProcessorFactory> factoryList = provider.getProcessorFactoryList();
    int factoryCount = factoryList.size();
    DetectorStreamBufferImpl buffer = new DetectorStreamBufferImpl(lastProcessor, null);
    for (int factoryIndex = factoryCount - 1; factoryIndex >= 0; factoryIndex--) {
      DetectorStreamProcessorFactory factory = factoryList.get(factoryIndex);
      DetectorStreamProcessor processor = factory.createProcessor();
      buffer = new DetectorStreamBufferImpl(processor, buffer);
    }
    this.firstBuffer = buffer;
  }

  /**
   * @see DetectorStreamProcessor#process(net.sf.mmm.util.io.api.spi.DetectorStreamBuffer,
   *      Map, boolean)
   * 
   * @param buffer is the next part of the streamed data.
   * @param offset is the start-index in <code>buffer</code>.
   * @param length is the number of bytes to append/process.
   * @param eos - <code>true</code> if the end of the stream has been reached
   *        and the given <code>buffer</code> has to be
   * @throws IOException in case of an Input/Output error. Should only be used
   *         internally.
   */
  public void processInternal(byte[] buffer, int offset, int length, boolean eos)
      throws IOException {

    this.firstBuffer.process(getMutableMetadata(), eos);
  }

  /**
   * This method gets a pool used to manage byte-arrays.
   * 
   * @return the {@link ByteArrayPool} instance.
   */
  protected ByteArrayPool getByteArrayPool() {

    return this.byteArrayPool;
  }

}
