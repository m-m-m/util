/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.impl;

import java.util.ArrayList;
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

  private List<DetectorStreamBufferImpl> buffers;

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
      AbstractDetectorStreamProvider provider) {

    super(mutableMetadata);
    List<DetectorStreamProcessorFactory> factoryList = provider.getProcessorFactoryList();
    this.buffers = new ArrayList<DetectorStreamBufferImpl>(factoryList.size());
    DetectorStreamBufferImpl buffer = null;
    for (DetectorStreamProcessorFactory factory : factoryList) {
      DetectorStreamProcessor processor = factory.createProcessor();
      buffer = new DetectorStreamBufferImpl(processor, buffer);
      this.buffers.add(buffer);
    }
  }

  protected int process(byte[] buffer, int offset, int length) {

    return 0;
  }

}
