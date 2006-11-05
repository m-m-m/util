/* $Id$ */
package net.sf.mmm.term.api;

import org.w3c.dom.Element;

import net.sf.mmm.term.api.Term;

/**
 * This is the interface for a service that provides expression handling.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ExpressionService {

  /**
   * This string indicates the start of a variable term as string for the
   * parser provided by this service. If <code>N</code> is a name then
   * <code>${N}</code> is an expression that evaluates to the value of the
   * variable. If <code>E</code> is an expression then <code>${(E)}</code>
   * is an expression.
   */
  String VARIABLE_START = "${";

  /**
   * This string indicates the end of a variable.
   * 
   * @see ExpressionService#VARIABLE_START
   */
  String VARIABLE_END = "}";

  /**
   * This method parses an expression given as string. Use this method to
   * create an Expression.
   * 
   * @param expression
   *        the expression to parse. TODO specifiy grammar.
   * @return the parsed expression
   * @throws ParseException
   *         if expression is invalid.
   */
  Term parse(String expression) throws ParseException;

  /**
   * This method parses an expression given as XML element. Use this method to
   * create an Expression.
   * 
   * @param expression
   *        the expression to parse. TODO specifiy grammar.
   * @return the parsed expression
   * @throws ParseException
   *         if expression is invalid.
   */
  Term parse(Element expression) throws ParseException;

}
