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
   * @param internaitionalizedMessage is a short description of the problem. It
   *        is used for internationalization and should be in English language.
   * @param arguments are the arguments filled into the
   *        <code>internaitionalizedMessage</code> after nationalization.
   */
  public NlsRuntimeException(String internaitionalizedMessage, Object... arguments) {

    super(NlsAccess.getFactory().create(internaitionalizedMessage, arguments));
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param internaitionalizedMessage is a short description of the problem. It
   *        is used for internationalization and should be in English language.
   * @param arguments are the arguments filled into the
   *        <code>internaitionalizedMessage</code> after nationalization.
   */
  public NlsRuntimeException(Throwable nested, String internaitionalizedMessage,
      Object... arguments) {

    super(nested, NlsAccess.getFactory().create(internaitionalizedMessage, arguments));
  }

}
