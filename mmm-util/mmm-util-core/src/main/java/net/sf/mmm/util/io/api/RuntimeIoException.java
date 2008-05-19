/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

import net.sf.mmm.util.nls.NlsRuntimeException;
import net.sf.mmm.util.nls.base.NlsBundleUtilCore;

/**
 * A {@link RuntimeIoException} is like an {@link java.io.IOException} but as a
 * {@link RuntimeException}. Besides it has native language support build in.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class RuntimeIoException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 5931918722203161328L;

  /**
   * The constructor.
   */
  public RuntimeIoException() {

    super(NlsBundleUtilCore.ERR_IO);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception. This
   *        should be an {@link java.io.IOException}. However it may also be an
   *        {@link java.io.IOError}.
   */
  public RuntimeIoException(Throwable nested) {

    super(nested, NlsBundleUtilCore.ERR_IO);
  }

}
