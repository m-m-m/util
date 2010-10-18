/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

import java.util.Map;

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
   * The constructor.
   * 
   * @see #toMap(String, Object, String, Object)
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param internationalizedMessage is a short description of the problem. It
   *        is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage()
   *        internationalization} and should be in English language.
   * @param arguments are the
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getArgument(String)
   *        arguments} to be
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage()
   *        filled into <code>internationalizedMessage</code>}.
   */
  protected RuntimeIoException(Throwable nested, String internationalizedMessage,
      Map<String, Object> arguments) {

    super(nested, internationalizedMessage, arguments);
  }

  /**
   * The constructor.
   * 
   * @see #toMap(String, Object, String, Object)
   * 
   * @param internationalizedMessage is a short description of the problem. It
   *        is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage()
   *        internationalization} and should be in English language.
   * @param arguments are the
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getArgument(String)
   *        arguments} to be
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage()
   *        filled into <code>internationalizedMessage</code>}.
   */
  protected RuntimeIoException(String internationalizedMessage, Map<String, Object> arguments) {

    super(internationalizedMessage, arguments);
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
      case FLUSH:
        return NlsBundleUtilCore.ERR_IO_FLUSH;
      case COPY:
        return NlsBundleUtilCore.ERR_IO_COPY;
      default :
        assert (false) : "IoMode is null!";
        return NlsBundleUtilCore.ERR_IO;
    }
  }

}
