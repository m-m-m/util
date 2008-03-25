/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.impl;

import net.sf.mmm.context.api.Context;
import net.sf.mmm.term.api.CalculationException;
import net.sf.mmm.term.api.Function;
import net.sf.mmm.term.api.Term;
import net.sf.mmm.term.base.AbstractTerm;
import net.sf.mmm.util.value.api.ValueException;

/**
 * This class represents an expression as a simple combination of a function and
 * some arguments. Each argument can be an expression again so any term of
 * functions, constants and variables can be build.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class Expression extends AbstractTerm {

  /** uid for serialization */
  private static final long serialVersionUID = -7391477832020859397L;

  /** the function of this expression */
  private final Function function;

  /** the arguments to the function */
  private final Term[] arguments;

  /**
   * The constructor.
   * 
   * @param theFunction is the function for this expression.
   * @param theArguments are the arguments applied to the function.
   * @throws ValueException if the given terms are invalid arguments for the
   *         given function.
   */
  public Expression(Function theFunction, Term... theArguments) throws ValueException {

    super();
    this.function = theFunction;
    this.arguments = theArguments;
    this.function.validateArgumentCount(this.arguments.length);
  }

  /**
   * @return the function of this expression.
   */
  public Function getFunction() {

    return this.function;
  }

  /**
   * {@inheritDoc}
   */
  public Object evaluate(Context environment) throws CalculationException, ValueException {

    return this.function.calculate(environment, this.arguments);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuffer result = new StringBuffer();
    String symbol = this.function.getOperatorSymbol();
    if (symbol == null) {
      result.append(this.function.getName());
      result.append(ARGUMENT_START);
      if (this.arguments.length > 0) {
        result.append(this.arguments[0]);
        for (int i = 1; i < this.arguments.length; i++) {
          result.append(ARGUMENT_SEPARATOR);
          result.append(this.arguments[i]);
        }
      }
      result.append(ARGUMENT_END);
    } else {
      result.append(EXPRESSION_START);
      if (this.arguments.length < 2) {
        result.append(symbol);
        if (this.arguments.length > 0) {
          result.append(this.arguments[0]);
        }
      } else {
        result.append(this.arguments[0]);
        for (int i = 1; i < this.arguments.length; i++) {
          result.append(symbol);
          result.append(this.arguments[i]);
        }
      }
      result.append(EXPRESSION_END);
    }
    return result.toString();
  }

}
