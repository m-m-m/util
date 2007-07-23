/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.api;

import net.sf.mmm.nls.base.NlsRuntimeException;

/**
 * This is the abstract base exception of all errors about the content.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class ContentException extends NlsRuntimeException {

  /**
   * The constructor.<br>
   * 
   * @see NlsRuntimeException#NlsRuntimeException(String, Object[])
   * 
   * @param internaitionalizedMessage is a short description of the problem. It
   *        is used for internationalization and should be in English language.
   * @param arguments are the arguments filled into the
   *        <code>internaitionalizedMessage</code> after nationalization.
   */
  public ContentException(String internaitionalizedMessage, Object... arguments) {

    super(internaitionalizedMessage, arguments);
  }

  /**
   * The constructor.<br>
   * 
   * @see NlsRuntimeException#NlsRuntimeException(Throwable, String, Object[])
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param internaitionalizedMessage is a short description of the problem. It
   *        is used for internationalization and should be in English language.
   * @param arguments are the arguments filled into the
   *        <code>internaitionalizedMessage</code> after nationalization.
   */
  public ContentException(Throwable nested, String internaitionalizedMessage, Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }

}
