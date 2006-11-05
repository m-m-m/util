/* $Id$ */
package net.sf.mmm.term.api;

import net.sf.mmm.context.api.Context;
import net.sf.mmm.value.api.ValueException;

/**
 * This is the interface for a function. A function can perform a
 * {@link #calculate(Context, Term[]) calculation} on a list of
 * {@link net.sf.mmm.term.api.Term terms} and returns the result value.<br>
 * A function can be used via its {@link #getName() name} in prefix notation
 * (e.g. "add(5,3)"). If the {@link #getOperatorSymbol() "operator symbol"} is
 * NOT <code>null</code>, the function can also be used this way in infix
 * notation (e.g. "5+3").<br>
 * If you want to implement your own function please consider to extend the
 * class {@link net.sf.mmm.term.base.AbstractFunction} instead of directly
 * implementing this interface.<br>
 * Your implementation should declare a <code>public static final String</code>
 * field named <code>NAME</code> that contains the name of the function as
 * returned by {@link Function#getName()}. And another string constant named
 * <code>SYMBOL</code> should contain the
 * {@link Function#getOperatorSymbol() "operator symbol"}.
 * 
 * @see net.sf.mmm.term.impl.GenericFunction
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface Function {

  /**
   * A string build of the only legal characters for operator symbols. These
   * characters must be forbidden in names.
   */
  String LEGAL_OPERATOR_CHARS = "+-*/<>=&|!?:\\%#.~";

  /**
   * This method gets the unique name of the function. That name should be an
   * short english term describing the meaning of the function. The name can be
   * used in an expression string to call the function. E.g. if a function is
   * named <code>add</code> the expression <code>add(1, 2)</code> would
   * produce the result of that function with the constant arguments 1 and 2
   * applied. <br>
   * An implemenation of this method should only contain the single statement
   * <code>return NAME;</code>. Where <code>NAME</code> is defined in the
   * implementing class as a <code>public static final String</code> field
   * that is applies to the following contract:
   * <ul>
   * <li>the length is greater or equal to 2</li>
   * <li>it contains only characters out of ['a'-'z'|'A'-'Z']</li>
   * <li>it does NOT equal to <code>true</code></li>
   * <li>it does NOT equal to <code>false</code></li>
   * <li>it does NOT equal to <code>null</code></li>
   * </ul>
   * 
   * @return the name of the function.
   */
  String getName();

  /**
   * This method gets the operator symbol of the function or <code>null</code>
   * if the function can not be used as operator. Please consider to define the
   * proper {@link Function#getOperatorPriority() priority} if you enable the
   * operator usage.<br>
   * The operator can be used for unary function usage (e.g. "!true") or
   * multi-argument usage (e.g. "3+5+4"). Non-arg usage is NOT possible via the
   * operator.
   * 
   * @return the unique operator symbol or <code>null</code> if the function
   *         is no operator. The operator symbol MUST NOT be "" and consist only
   *         legal charactes as described by {@link #LEGAL_OPERATOR_CHARS}.
   *         Further the operator symbol should explain the meaning of the
   *         function as much as possible.
   */
  String getOperatorSymbol();

  /**
   * This method get the number of minimum number of arguments required by this
   * function for calculation. The value must be greater or equal than zero and
   * less or equal than the maximum argument count returned by the method
   * {@link Function#getMaximumArgumentCount()}.
   * 
   * @return the minimum argument count.
   */
  int getMinimumArgumentCount();

  /**
   * This method gets the maximum number of arguments required by this function
   * for calculation. The value must be greater or equal than the minimum
   * argument count returned by the method
   * {@link Function#getMinimumArgumentCount()}. If the number of arguments is
   * not limited, return <code>Integer.MAX_VALUE</code>.
   * 
   * @return the maximum argument count.
   */
  int getMaximumArgumentCount();

  /**
   * This method validates that the given number of arguments is legal for this
   * function.
   * 
   * @param count
   *        is the number of arguments to validate.
   * @throws CalculationException
   *         if <code>count</code> is less than
   *         {@link Function#getMinimumArgumentCount()} or greater than
   *         {@link Function#getMaximumArgumentCount()} or any number within
   *         this range is illegal for this function.
   */
  void validateArgumentCount(int count) throws CalculationException;

  /**
   * This method gets the priority of this function as
   * {@link #getOperatorSymbol() opeator}. E.g. let there be the two operators
   * <b>+</b> and <b>*</b>. Further an expression is given by the string
   * <code>1+2*3</code>. Now if the priority of <b>*</b> is higher than the
   * priority of <b>+</b> the expression will be parsed as <code>1+(2*3)</code>.
   * Else (if the priority of <b>*</b> is less or equal to <b>+</b>) the
   * expression will be parsed as <code>(1+2)*3</code>.<br>
   * If the function is used as unary operator (single-argument e.g. "+3" or
   * "!true") the priority is NOT determined by this method and is always
   * {@link OperatorPriority#MAXIMUM}. The method will be ignored if the
   * {@link Function#getOperatorSymbol() "operator symbol"} is <code>null</code>.
   * 
   * @return the priority of the function as operator.
   */
  OperatorPriority getOperatorPriority();

  /**
   * This method performs a calculation of the function with the given
   * arguments.
   * 
   * @param environment
   *        is a set of variables used to evaluate the (conditional) terms given
   *        as arguments.
   * @param arguments
   *        is an array containing all arguments for the function as terms. The
   *        terms may be lazy evaluated meaning that they only need to be
   *        evaluated as needed - e.g. <code>0*(...)</code> will be always
   *        <code>0</code>. The given varargs array MUST NOT be modified by
   *        the implementation of this method!
   * @return the result the calculation.
   * @throws CalculationException
   *         if an error occurs during calculation e.g. zero divide or
   *         incompatible types.
   * @throws ValueException
   *         if an argument (e.g. an
   *         {@link Context#getValue(String) "environment value"}) is
   *         {@link net.sf.mmm.value.api.GenericValue#hasValue() undefined} or
   *         has the
   *         {@link net.sf.mmm.value.api.WrongValueTypeException "wrong type"}.
   */
  Object calculate(Context environment, Term... arguments) throws ValueException;

  /**
   * This method should return the result of {@link Function#getName()}. If
   * this function has an operator symbol that should be appended in brackets.
   * E.g. "add[+]".
   * 
   * @see java.lang.Object#toString()
   */
  String toString();

}
