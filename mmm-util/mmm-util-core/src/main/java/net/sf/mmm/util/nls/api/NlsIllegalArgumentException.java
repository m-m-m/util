/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * A {@link NlsIllegalArgumentException} is analog to an
 * {@link IllegalArgumentException} but with true native language support.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class NlsIllegalArgumentException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -1537683835966488723L;

  /**
   * The constructor.
   * 
   * @see NlsRuntimeException#NlsRuntimeException(String, Object[])
   * 
   * @param argument is the argument that is illegal. May be <code>null</code>.
   */
  public NlsIllegalArgumentException(Object argument) {

    super(NlsBundleUtilCore.ERR_ILLEGAL_ARGUMENT, argument);
  }

  /**
   * The constructor.
   * 
   * @see NlsRuntimeException#NlsRuntimeException(String, Object[])
   * 
   * @param argument is the argument that is illegal. May be <code>null</code>.
   * @param nested is the {@link #getCause() cause} of this exception.
   */
  public NlsIllegalArgumentException(Object argument, Throwable nested) {

    super(nested, NlsBundleUtilCore.ERR_ILLEGAL_ARGUMENT, argument);
  }

  /**
   * The constructor.
   * 
   * @see NlsRuntimeException#NlsRuntimeException(String, Object[])
   * 
   * @param internaitionalizedMessage is a short description of the problem. It
   *        is used for internationalization and should be in English language.
   * @param arguments are the arguments filled into the
   *        <code>internaitionalizedMessage</code> after nationalization.
   */
  public NlsIllegalArgumentException(String internaitionalizedMessage, Object... arguments) {

    super(internaitionalizedMessage, arguments);
  }

  /**
   * The constructor.
   * 
   * @see NlsRuntimeException#NlsRuntimeException(Throwable, String, Object[])
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param internaitionalizedMessage is a short description of the problem. It
   *        is used for internationalization and should be in English language.
   * @param arguments are the arguments filled into the
   *        <code>internaitionalizedMessage</code> after nationalization.
   */
  public NlsIllegalArgumentException(Throwable nested, String internaitionalizedMessage,
      Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }

}
