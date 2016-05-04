/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api.spi;

import java.io.IOException;
import java.util.Map;

/**
 * This is the interface for a processor that can be plugged into a {@link net.sf.mmm.util.io.api.DetectorStream}. Such
 * {@link net.sf.mmm.util.io.api.DetectorStream} holds a chain of {@link DetectorStreamProcessor}s and routes the stream
 * data through this chain. <br>
 * At the head of this chain is the producer of the data (the native {@link java.io.InputStream} or the caller that
 * wants to write to the {@link net.sf.mmm.util.io.api.DetectorStreamProvider#wrapOutputStream(java.io.OutputStream)
 * wrapped} {@link java.io.OutputStream}). The end of the chain is the consumer of the data (the native
 * {@link java.io.OutputStream} or caller that wants to read from the
 * {@link net.sf.mmm.util.io.api.DetectorStreamProvider#wrapInputStream(java.io.InputStream) wrapped}
 * {@link java.io.InputStream}). <br>
 * An implementation of this interface can detect information (typically metadata) from the streamed data. Additionally
 * it is possible to manipulate the streamed data by {@link DetectorStreamBuffer#remove(long) removing} data from the
 * stream and/or {@link DetectorStreamBuffer#insert(net.sf.mmm.util.io.api.ByteArray) inserting} new data into the
 * stream. <br>
 *
 * @see net.sf.mmm.util.io.api.DetectorStream
 * @see DetectorStreamProcessorFactory
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public interface DetectorStreamProcessor {

  /**
   * This method performs the actual detection and optionally manipulates the data. All this happens via the given
   * {@code buffer} that provides access to read buffered parts of the stream. As streams are typically larger pieces of
   * data, this method will be called repetitive in order to process the entire stream. <br>
   * <b>ATTENTION:</b><br>
   * A legal implementation of this interface has to consume data from the given {@code buffer} whenever it is invoked.
   * To ensure your implementation can always make an appropriate decision it may have to ensure a specific
   * {@link DetectorStreamProcessorFactory#getLookaheadCount() lookahead}.
   *
   * @param buffer allows you to read parts of the streamed data as well as to manipulate the data.
   * @param metadata is the {@link Map} with the meta-data.
   * @param eos - {@code true} if the end of the stream has been reached and the given {@code buffer} has to be
   * @throws IOException in case of an Input/Output error. Should only be used internally.
   */
  void process(DetectorStreamBuffer buffer, Map<String, Object> metadata, boolean eos) throws IOException;

}
