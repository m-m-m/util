/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.api;

import net.sf.mmm.term.MainNlsResourceBundle;

/**
 * This is the exception thrown if a function was requested that ist NOT
 * available.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NoSuchFunctionException extends FunctionException {

  /** UID for serialization */
  private static final long serialVersionUID = -1205400349394700623L;

  /**
   * The constructor.
   * 
   * @param nameOrSymbol
   *        is the name or symbol of the requested function.
   */
  public NoSuchFunctionException(String nameOrSymbol) {

    super(MainNlsResourceBundle.ERR_FCT_NO_SUCH_NAME_OR_SYMBOL, nameOrSymbol);
  }
}
