/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.exception.api.NlsRuntimeException;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * A {@link RuntimeIoException} is like an {@link java.io.IOException} but as a {@link RuntimeException}.
 * Besides it has native language support build in.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class RuntimeIoException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 5931918722203161328L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "IO";

  /**
   * The constructor.
   */
  public RuntimeIoException() {

    this((Throwable) null);
  }

  /**
   * The constructor.
   *
   * @param mode is the {@link IoMode}.
   */
  public RuntimeIoException(IoMode mode) {

    super(getMessage(mode));
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception. This should be an
   *        {@link java.io.IOException}. However it may also be an {@link java.io.IOError}.
   */
  public RuntimeIoException(Throwable nested) {

    super(nested, createBundle(NlsBundleUtilCoreRoot.class).errorIo());
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception. This should be an
   *        {@link java.io.IOException}. However it may also be an {@link java.io.IOError}.
   * @param mode is the {@link IoMode}.
   */
  public RuntimeIoException(Throwable nested, IoMode mode) {

    super(nested, getMessage(mode));
  }

  /**
   * The constructor.
   *
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   * @since 3.0.0
   */
  protected RuntimeIoException(NlsMessage message) {

    super(message);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   * @since 3.0.0
   */
  protected RuntimeIoException(Throwable nested, NlsMessage message) {

    super(nested, message);
  }

  /**
   * This method gets the {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() message}
   * according to the given <code>mode</code>.
   *
   * @param mode is the {@link IoMode} or <code>null</code> if unknown.
   * @return the message according to <code>mode</code>.
   */
  private static NlsMessage getMessage(IoMode mode) {

    NlsBundleUtilCoreRoot bundle = createBundle(NlsBundleUtilCoreRoot.class);
    switch (mode) {
      case READ:
        return bundle.errorIoRead();
      case WRITE:
        return bundle.errorIoWrite();
      case CLOSE:
        return bundle.errorIoClose();
      case FLUSH:
        return bundle.errorIoFlush();
      case COPY:
        return bundle.errorIoCopy();
      default :
        assert (false) : "IoMode is null!";
        return bundle.errorIo();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
