/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

import net.sf.mmm.util.NlsBundleUtilCore;
import net.sf.mmm.util.nls.api.NlsRuntimeException;

/**
 * A {@link BufferExceedException} is thrown if a buffer (typically array of
 * bytes or chars) is exceeded (e.g. offset or length are out of range).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.3
 */
public class BufferExceedException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -5375096243963460300L;

  /**
   * The constructor.
   * 
   * @param length is the given size that exceeds the buffer.
   * @param capacity is the available capacity of the buffer.
   */
  public BufferExceedException(int length, int capacity) {

    super(NlsBundleUtilCore.ERR_BUFFER_LENGTH_EXCEED, toMap(KEY_VALUE, Integer.valueOf(length),
        KEY_CAPACITY, Integer.valueOf(capacity)));
  }

}
