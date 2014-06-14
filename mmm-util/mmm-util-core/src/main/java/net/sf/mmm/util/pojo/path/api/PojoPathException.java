/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.api;

import net.sf.mmm.util.exception.api.NlsRuntimeException;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This is the abstract base class for exceptions thrown by the {@link PojoPathNavigator}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public abstract class PojoPathException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 1514491167399845329L;

  /**
   * The constructor.
   *
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   * @since 3.0.0
   */
  public PojoPathException(NlsMessage message) {

    super(message);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   * @since 3.0.0
   */
  public PojoPathException(Throwable nested, NlsMessage message) {

    super(nested, message);
  }

}
