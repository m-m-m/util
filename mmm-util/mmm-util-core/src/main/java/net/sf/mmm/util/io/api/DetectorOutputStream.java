/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

import java.io.OutputStream;

/**
 * This is the interface for a {@link DetectorStream} that wraps an {@link OutputStream}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public interface DetectorOutputStream extends DetectorStream {

  /**
   * This method gets the wrapped stream. After this stream is completely written and closed, the
   * {@link #getMetadata() metadata} can be retrieved. This requires that the entire data is written and no
   * existing data is appended via the original stream. This method is a simple getter - it will always return
   * the same stream object.<br>
   * 
   * @return the wrapper stream.
   */
  OutputStream getStream();

}
