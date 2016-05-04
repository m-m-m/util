/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.api;

import net.sf.mmm.util.exception.api.NlsRuntimeException;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * This exception is thrown if a something goes wrong about values. This can be an invalid "casting", a parse error,
 * etc.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class ValueException extends NlsRuntimeException {

  private static final long serialVersionUID = -8445209659250789499L;

  /**
   * The constructor for de-serialization in GWT.
   */
  protected ValueException() {

    super();
  }

  /**
   * The constructor.
   *
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   * @since 3.0.0
   */
  public ValueException(NlsMessage message) {

    super(message);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   * @since 3.0.0
   */
  public ValueException(Throwable nested, NlsMessage message) {

    super(nested, message);
  }

}
