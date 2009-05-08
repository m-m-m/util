/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.impl;

import java.util.Map;

import net.sf.mmm.util.io.base.AbstractDetectorStream;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class ProcessableDetectorStream extends AbstractDetectorStream {

  /**
   * The constructor.
   */
  public ProcessableDetectorStream() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param mutableMetadata is the initial {@link #getMutableMetadata() mutable
   *        metadata}.
   */
  public ProcessableDetectorStream(Map<String, Object> mutableMetadata) {

    super(mutableMetadata);
  }

  protected int process(byte[] buffer, int offset, int length) {

    return 0;
  }

}
