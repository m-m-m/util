/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.impl;

import java.io.IOException;
import java.util.Map;

import net.sf.mmm.util.io.api.spi.DetectorStreamBuffer;
import net.sf.mmm.util.io.api.spi.DetectorStreamProcessor;

/**
 * This is an implementation of {@link DetectorStreamProcessor} that counts the
 * number of bytes that are equal to the ASCII-char 'x'.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class DetectorStreamProcessorCountX implements DetectorStreamProcessor {

  /** metadata-key. */
  public static final String KEY_X_COUNT = "x-count";

  /** total byte count. */
  private int countTotal;

  /** x count. */
  private int countX;

  /**
   * The constructor.
   */
  public DetectorStreamProcessorCountX() {

    super();
    this.countTotal = 0;
    this.countX = 0;
  }

  /**
   * @return the countTotal
   */
  public int getCountTotal() {

    return this.countTotal;
  }

  /**
   * @return the countX
   */
  public int getCountX() {

    return this.countX;
  }

  /**
   * {@inheritDoc}
   */
  public void process(DetectorStreamBuffer buffer, Map<String, Object> metadata, boolean eos)
      throws IOException {

    while (buffer.hasNext()) {
      byte b = buffer.next();
      this.countTotal++;
      if (b == 'x') {
        this.countX++;
      }
    }
    if (eos) {
      metadata.put(KEY_X_COUNT, Integer.valueOf(this.countX));
    }
  }

}
