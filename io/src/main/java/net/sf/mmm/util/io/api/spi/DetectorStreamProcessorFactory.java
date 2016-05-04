/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api.spi;

/**
 * This is interface represents a factory of specific {@link DetectorStreamProcessor}s.
 *
 * @see net.sf.mmm.util.io.api.DetectorStream
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public interface DetectorStreamProcessorFactory {

  /**
   * This method determines the required number of bytes a {@link DetectorStreamProcessor} needs as lookahead in order
   * to make its decisions. The {@link net.sf.mmm.util.io.api.DetectorStream} will use the maximum required lookahead of
   * all registered {@link DetectorStreamProcessorFactory processor-factories}. <br>
   * <b>NOTE:</b><br>
   * To prevent waste of memory, you should NOT use a lookahead higher than necessary. However you should NOT worry
   * about values below {@code 1024}. <br>
   * As a stupid example we assume a {@link DetectorStreamProcessor} wants to figure out if some file contains the
   * ASCII-bytes '&lt;?xml' or '&lt;html'. Therefore he requires a lookahead of 5. If he consumes all data until a
   * '&lt;' is reached (@see {@link DetectorStreamBuffer#peek()}), he can check if 4 more bytes are available. In that
   * case he can consume all these bytes and make its decision. Otherwise he returns from
   * {@link DetectorStreamProcessor#process(DetectorStreamBuffer, java.util.Map, boolean)} and waits for the next call
   * that will guarantee the buffer is refilled with at least the specified lookahead. <br>
   *
   * @return the required lookahead in bytes.
   */
  int getLookaheadCount();

  /**
   * This method gets a {@link DetectorStreamProcessor} instance managed by this factory. Typically the factory will
   * create a new instance per {@link #createProcessor() request}. However if a {@link DetectorStreamProcessor} is
   * thread-safe the factory can always return the same instance.
   *
   * @return the requested {@link DetectorStreamProcessor} instance.
   */
  DetectorStreamProcessor createProcessor();

}
