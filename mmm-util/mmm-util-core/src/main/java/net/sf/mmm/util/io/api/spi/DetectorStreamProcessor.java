/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api.spi;

import net.sf.mmm.util.io.api.DetectorStream;

/**
 * This is the interface for a processor that can be plugged into a
 * {@link DetectorStream}. Such {@link DetectorStream} holds a chain of
 * {@link DetectorStreamProcessor}s and routes the stream data through this
 * chain. At the end of this chain is the consumer of the data (the native
 * {@link java.io.OutputStream} or caller that wants to read from the wrapped
 * {@link java.io.InputStream}).<br>
 * An implementation of this interface can detect information (typically
 * metadata) from the streamed data. Additionally it is possible to manipulate
 * the streamed data by {@link DetectorStreamBuffer#remove(long) removing} data
 * from the stream and inserting new data into the stream.<br>
 * 
 * @see DetectorStream
 * @see DetectorStreamProcessorFactory
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface DetectorStreamProcessor {

  /**
   * This method performs the actual detection and optionally manipulates the
   * data. All this happens via the given <code>buffer</code> that provides
   * access to read buffered parts of the stream. As streams are typically
   * larger pieces of data, this method will be called repetitive in order to
   * process the entire stream.<br>
   * <b>ATTENTION:</b><br>
   * A legal implementation of this interface has to consume data from the given
   * <code>buffer</code> whenever it is invoked. To ensure your implementation
   * can always make an appropriate decision it may have to ensure a specific
   * {@link DetectorStreamProcessorFactory#getLookaheadCount() lookahead}.
   * 
   * @param buffer allows you to read parts of the streamed data as well as to
   *        manipulate the data.
   */
  void process(DetectorStreamBuffer buffer);

}
