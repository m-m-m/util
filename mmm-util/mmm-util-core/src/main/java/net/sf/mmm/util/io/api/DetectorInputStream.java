/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

import java.io.InputStream;

/**
 * This is the interface for a {@link DetectorStream} that wraps an {@link InputStream}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public interface DetectorInputStream extends DetectorStream {

  /**
   * This method gets the wrapped stream. After this stream is read to the end, the {@link #getMetadata()
   * metadata} can be retrieved. This method is a simple getter - it will always return the same stream
   * object.<br>
   * This returned wrapper stream has the following limitations:
   * <ul>
   * <li>{@link java.io.InputStream#skip(long) skipping} is NOT permitted.</li>
   * <li>{@link java.io.InputStream#mark(int) mark} is NOT {@link java.io.InputStream#markSupported()
   * supported}.</li>
   * <li>You need to read/write your data completely (at least until the detection is {@link #isDone() done})
   * in order to get the complete {@link #getMetadata() metadata}.</li>
   * </ul>
   * 
   * @return the wrapper stream.
   */
  InputStream getStream();

}
