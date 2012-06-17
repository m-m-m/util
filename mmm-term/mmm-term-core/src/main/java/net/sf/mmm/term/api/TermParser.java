/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.api;

/**
 * This is the interface of a parser for {@link Term}s.
 * 
 * @see Term
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface TermParser {

  /**
   * This method parses the given <code>expression</code> that represents a
   * {@link Term} as string.
   * 
   * @param expression the expression to parse.
   * @return the parsed expression
   * @throws TermParseException if expression is invalid.
   */
  Term parse(String expression) throws TermParseException;

}
