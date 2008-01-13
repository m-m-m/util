/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls;

import net.sf.mmm.util.nls.api.AbstractNlsRuntimeException;

/**
 * This the base class for all runtime exceptions of the project.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class NlsRuntimeException extends AbstractNlsRuntimeException {

  /**
   * The constructor.
   * 
   * @param internaitionalizedMessage is a short description of the problem. It
   *        is used for internationalization and should be in English language.
   * @param arguments are the arguments filled into the
   *        <code>internaitionalizedMessage</code> after nationalization.
   */
  public NlsRuntimeException(String internaitionalizedMessage, Object... arguments) {

    super(NlsMessageFactoryAccess.getInstance().create(internaitionalizedMessage, arguments));
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

    super(nested, NlsMessageFactoryAccess.getInstance().create(internaitionalizedMessage, arguments));
  }

}
