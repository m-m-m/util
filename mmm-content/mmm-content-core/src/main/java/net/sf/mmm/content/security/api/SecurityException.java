/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.security.api;

import net.sf.mmm.nls.base.NlsRuntimeException;

/**
 * This exception is used if a
 * {@link net.sf.mmm.content.api.ContentObject content-object} was accessed
 * violating a security constraint.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SecurityException extends NlsRuntimeException {

  /** uid for serialization */
  private static final long serialVersionUID = 3257285846510940725L;

  /**
   * The constructor.
   * 
   * @param internaitionalizedMessage is a short description of the problem. It
   *        is used for internationalization and should be in English language.
   * @param arguments are the arguments filled into the
   *        <code>internaitionalizedMessage</code> after nationalization.
   */
  public SecurityException(String internaitionalizedMessage, Object... arguments) {

    super(internaitionalizedMessage, arguments);
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
  public SecurityException(Throwable nested, String internaitionalizedMessage, Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }

}
