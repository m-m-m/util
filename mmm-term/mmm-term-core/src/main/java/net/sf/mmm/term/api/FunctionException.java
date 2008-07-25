/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.api;

import net.sf.mmm.util.nls.api.NlsException;

/**
 * This exception represents an error that occured because of an invalid
 * implementation or usage of a {@link net.sf.mmm.term.api.Function function}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FunctionException extends NlsException {

  /** uid for serialization */
  private static final long serialVersionUID = -1874189448219258306L;

  /**
   * @see NlsException#NlsException(String, Object[])
   */
  public FunctionException(String internaitionalizedMessage, Object... arguments) {

    super(internaitionalizedMessage, arguments);
  }

  /**
   * @see NlsException#NlsException(Throwable, String, Object[])
   */
  public FunctionException(Throwable nested, String internaitionalizedMessage, Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }

}
