/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

/**
 * This the base class for all runtime exceptions of the project.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class NlsRuntimeException extends AbstractNlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 6002426164465970398L;

  /**
   * The constructor.
   * 
   * @param internationalizedMessage is a short description of the problem. It
   *        is used for internationalization and should be in English language.
   * @param arguments are the arguments filled into the
   *        <code>internationalizedMessage</code> after nationalization.
   */
  public NlsRuntimeException(String internationalizedMessage, Object... arguments) {

    super(NlsAccess.getFactory().create(internationalizedMessage, arguments));
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param internationalizedMessage is a short description of the problem. It
   *        is used for internationalization and should be in English language.
   * @param arguments are the arguments filled into the
   *        <code>internationalizedMessage</code> after nationalization.
   */
  public NlsRuntimeException(Throwable nested, String internationalizedMessage, Object... arguments) {

    super(nested, NlsAccess.getFactory().create(internationalizedMessage, arguments));
  }

}
