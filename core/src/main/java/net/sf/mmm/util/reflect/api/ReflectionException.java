/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import net.sf.mmm.util.exception.api.NlsRuntimeException;
import net.sf.mmm.util.nls.api.NlsMessage;

/**
 * A {@link ReflectionException} is thrown if an operation based on {@link java.lang.reflect reflection} failed.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public abstract class ReflectionException extends NlsRuntimeException {

  private static final long serialVersionUID = 6935836720426675909L;

  /** Key for the NLS message. */
  protected static final String KEY_ACCESSIBLE = "accessible";

  /**
   * The constructor.
   *
   * @param internationalizedMessage is a short description of the problem. It is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() internationalization} and should be
   *        in English language.
   */
  public ReflectionException(String internationalizedMessage) {

    super(null, internationalizedMessage);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param internationalizedMessage is a short description of the problem. It is used for
   *        {@link net.sf.mmm.util.nls.api.NlsMessage#getInternationalizedMessage() internationalization} and should be
   *        in English language.
   */
  public ReflectionException(Throwable nested, String internationalizedMessage) {

    super(nested, internationalizedMessage);
  }

  /**
   * The constructor.
   *
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   * @since 3.0.0
   */
  public ReflectionException(NlsMessage message) {

    super(message);
  }

  /**
   * The constructor.
   *
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param message the {@link #getNlsMessage() message} describing the problem briefly.
   * @since 3.0.0
   */
  public ReflectionException(Throwable nested, NlsMessage message) {

    super(nested, message);
  }

}
