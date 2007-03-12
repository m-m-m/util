/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.api;

import net.sf.mmm.nls.base.NlsException;
import net.sf.mmm.term.NlsBundleTermCore;

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
   * The constructor.
   * 
   * @param expression
   *        is the expression that could NOT be parsed.
   */
  public TermParseException(String expression) {

    super(NlsBundleTermCore.ERR_TERM_PARSE, expression);
  }

  /**
   * The constructor.
   * 
   * @param expression
   *        is the expression that could NOT be parsed.
   * @param nested
   *        is the {@link #getCause() cause} of this exception.
   */
  public TermParseException(String expression, Throwable nested) {

    super(nested, NlsBundleTermCore.ERR_TERM_PARSE, expression);
  }

}
