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

  /** The first buffer of the chain. */
  private final DetectorStreamBufferImpl firstBuffer;

  /**
   * The constructor.
   * 
   * @param mutableMetadata is the initial {@link #getMutableMetadata() mutable
   *        metadata}.
   * @param provider is the
   *        {@link net.sf.mmm.util.io.api.DetectorStreamProvider} creating this
   *        instance.
   */
  public ProcessableDetectorStream(Map<String, Object> mutableMetadata,
      AbstractDetectorStreamProvider provider, DetectorStreamProcessor lastProcessor) {

    super(mutableMetadata);
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
   * @param buffer
   * @param offset
   * @param length
   * @param eos
   * @throws IOException
   */
  public void processInternal(byte[] buffer, int offset, int length, boolean eos)
      throws IOException {

    this.firstBuffer.process(getMutableMetadata(), eos);
  }

}
