/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.api;

import org.w3c.dom.Element;

import net.sf.mmm.term.api.Term;

/**
 * This is the interface for a service that provides expression handling.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ExpressionService extends TermParser {

  /**
   * This method parses an expression given as XML element. Use this method to
   * create an Expression.
   * 
   * @param expression the expression to parse. TODO specify grammar.
   * @return the parsed expression
   * @throws TermParseException if expression is invalid.
   */
  Term parse(Element expression) throws TermParseException;

}
