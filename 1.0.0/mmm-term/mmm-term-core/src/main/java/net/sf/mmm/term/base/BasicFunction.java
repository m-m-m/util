/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.base;

import net.sf.mmm.context.api.Context;
import net.sf.mmm.term.api.CalculationException;
import net.sf.mmm.term.api.IllegalArgumentTypeException;
import net.sf.mmm.term.api.Term;
import net.sf.mmm.util.reflect.Arguments;
import net.sf.mmm.util.value.ValueException;
import net.sf.mmm.util.value.WrongValueTypeException;

/**
 * This is an abstract implementation of the
 * {@link net.sf.mmm.term.api.Function} interface. It makes the assumption that
 * if the function takes more than two arguments the calculation is performed by
 * iterating the calculation binary from the left to the right: <br>
 * Let <code>b</code> be a binary function that defines a calculation for two
 * given arguments. Now let there be the argument terms <code>t1,...,tn</code>
 * with <code>n</code> greater than two. The calculation of <code>b</code>
 * for that arguments is defined by the following equation: <br>
 * <code>b(t1,...,tn)=b(...b(b(t1,t2),t3),...,tn)</code>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class BasicFunction extends AbstractFunction {

  /**
   * The constructor.
   */
  public BasicFunction() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Object calculate(Context environment, Term... arguments) throws ValueException {

    validateArgumentCount(arguments.length);
    Object result;
    if (arguments.length == 0) {
      result = calculate();
    } else if (arguments.length == 1) {
      Object argument = arguments[0].evaluate(environment);
      try {
        result = calculate(argument);
      } catch (WrongValueTypeException e) {
        throw new IllegalArgumentTypeException(this, new Arguments(argument), e);
      }
    } else {
      result = calculateBinary(environment, arguments);
    }
    return result;
  }

  /**
   * This method is called if the argument count is valid and two or more
   * arguments are given. The implementation performs the calculation for more
   * than two arguments by iteratively performing a binary calculation with a
   * left-associative logic as described in the type comment of this class.
   * 
   * @see net.sf.mmm.term.api.Function#calculate(Context, Term[])
   * 
   * @param environment is a set of variables used to evaluate the (conditional)
   *        terms given as arguments.
   * @param arguments is an array containing all arguments for the function as
   *        terms. The terms may be lazy evaluated meaning that they only need
   *        to be evaluated as needed - e.g. <code>0*(...)</code> will be
   *        always <code>0</code>. The array MUST NOT be modified by this
   *        method!
   * @return the result the calculation.
   * @throws ValueException if an error occurs during calculation e.g. zero
   *         divide or incompatible types.
   */
  public Object calculateBinary(Context environment, Term... arguments) throws ValueException {

    Object result = arguments[0].evaluate(environment);
    for (int i = 1; i < arguments.length; i++) {
      Object argument2 = arguments[i].evaluate(environment);
      try {
        result = calculate(result, argument2);
      } catch (WrongValueTypeException e) {
        throw new IllegalArgumentTypeException(this, new Arguments(result, argument2), e);
      }
    }
    return result;
  }

  /**
   * This method performs the calculation of the two argument values.
   * 
   * @param argument1 is the first argument.
   * @param argument2 is the second argument.
   * @return the result of the calculation of this functions with the given two
   *         argument values.
   * @throws ValueException if an error occurs during calculation e.g. zero
   *         divide or incompatible types.
   */
  public Object calculate(Object argument1, Object argument2) throws ValueException {

    throw new CalculationException("Not implemented - internal error!");
  }

  /**
   * This method performs the actual calculation of the unary function.
   * 
   * @param argument is the input value for this unary function.
   * @return the result of this function applied to the given argument.
   * @throws ValueException if the given argument value is illegal for this
   *         function.
   */
  public Object calculate(Object argument) throws ValueException {

    throw new CalculationException("Not implemented - internal error!");
  }

  /**
   * This method performs the actual calculation of the non-arg function.
   * 
   * @return the result of this function with no arguments.
   * @throws ValueException if something went wrong.
   */
  public Object calculate() throws ValueException {

    throw new CalculationException("Not implemented - internal error!");
  }

}
