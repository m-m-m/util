/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.impl;

import java.io.IOException;
import java.util.Map;

import net.sf.mmm.util.io.api.ByteArray;
import net.sf.mmm.util.io.api.spi.DetectorStreamBuffer;
import net.sf.mmm.util.io.api.spi.DetectorStreamProcessor;

/**
 * This is an implementation of {@link DetectorStreamProcessor} that replaces a sequence of two ASCII 'x'
 * bytes with one ASCII 'y'.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class DetectorStreamProcessorReplaceXx implements DetectorStreamProcessor {

  /** @see #process(DetectorStreamBuffer, Map, boolean) */
  private int xFound;

  /**
   * The constructor.
   */
  public DetectorStreamProcessorReplaceXx() {

    super();
    this.xFound = 0;
  }

  @Override
  public void process(DetectorStreamBuffer buffer, Map<String, Object> metadata, boolean eos) throws IOException {

    while (buffer.getByteArrayCount() > 0) {
      ByteArray currentByteArray = buffer.getByteArray(0);
      byte[] bytes = currentByteArray.getBytes();
      if (currentByteArray.getBytesAvailable() == 0) {
        break;
      }
      int current = currentByteArray.getCurrentIndex();
      int max = currentByteArray.getMaximumIndex();
      int pos = current;
      for (; pos <= max; pos++) {
        if (bytes[pos] == 'x') {
          this.xFound++;
          break;
        }
        this.xFound = 0;
      }
      buffer.skip(pos - current);
      if (this.xFound > 0) {
        buffer.remove(1);
        if (this.xFound > 1) {
          buffer.insert((byte) 'y');
          this.xFound = 0;
        }
      }
    }
  }
}
