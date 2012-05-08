/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

import net.sf.mmm.util.NlsBundleUtilCore;
import net.sf.mmm.util.nls.api.NlsRuntimeException;

/**
 * A {@link StreamClosedException} is thrown if a stream is used that has already been
 * {@link java.io.Closeable#close() closed}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class StreamClosedException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 8196684815578319363L;

  /**
   * The constructor.
   */
  public StreamClosedException() {

    super(NlsBundleUtilCore.ERR_STREAM_CLOSED);
  }

}
