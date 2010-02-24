/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

import net.sf.mmm.util.NlsBundleUtilCore;
import net.sf.mmm.util.nls.api.NlsRuntimeException;

/**
 * A {@link RuntimeIoException} is like an {@link java.io.IOException} but as a
 * {@link RuntimeException}. Besides it has native language support build in.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
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

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception. This
   *        should be an {@link java.io.IOException}. However it may also be an
   *        {@link java.io.IOError}.
   * @param mode is the {@link IoMode}.
   */
  public RuntimeIoException(Throwable nested, IoMode mode) {

    super(nested, getMessage(mode));
  }

  /**
   * This method gets the
   * {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage()
   * message} according to the given <code>mode</code>.
   * 
   * @param mode is the {@link IoMode} or <code>null</code> if unknown.
   * @return the message according to <code>mode</code>.
   */
  private static String getMessage(IoMode mode) {

    switch (mode) {
      case READ:
        return NlsBundleUtilCore.ERR_IO_READ;
      case WRITE:
        return NlsBundleUtilCore.ERR_IO_WRITE;
      case CLOSE:
        return NlsBundleUtilCore.ERR_IO_CLOSE;
      default :
        assert (false) : "IoMode is null!";
        return NlsBundleUtilCore.ERR_IO;
    }
  }

}
