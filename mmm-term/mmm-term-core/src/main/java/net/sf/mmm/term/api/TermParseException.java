/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.api;

import net.sf.mmm.nls.base.NlsException;

/**
 * This exception is thrown if an error occurred while parsing a
 * {@link net.sf.mmm.term.api.Term term} ({@link net.sf.mmm.term.impl.Expression expression}).
 * This exception means that the expression is malformed and can not be parsed.
 * <br>
 * E.g. the expression "1*(" is illegal would produce such an exception.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class TermParseException extends NlsException {

  /** UID for serialization */
  private static final long serialVersionUID = 8299920898179225587L;

  /**
   * @see NlsException#NlsException(String, Object[])
   */
  public TermParseException(String internaitionalizedMessage, Object... arguments) {

    super(internaitionalizedMessage, arguments);
  }

  /**
   * @see NlsException#NlsException(Throwable, String, Object[])
   */
  public TermParseException(Throwable nested, String internaitionalizedMessage, Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }

}
