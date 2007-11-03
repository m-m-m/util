/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.impl;

import net.sf.mmm.context.api.Context;
import net.sf.mmm.term.NlsBundleTermCore;
import net.sf.mmm.term.api.CalculationException;
import net.sf.mmm.term.api.Term;
import net.sf.mmm.term.base.AbstractVariable;
import net.sf.mmm.value.api.ValueException;

/**
 * This class represents a variable as term. The name of the variable is given
 * as expression.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ExpressionVariable extends AbstractVariable {

  /** uid for serialization */
  private static final long serialVersionUID = -6989674048385225206L;

  /**
   * the term that must resolve to a string that will be interpreted as name of
   * the variable.
   */
  private Term term;

  /**
   * The constructor.
   * 
   * @param variableNameTerm is an expression that must resolve to the name of
   *        the variable (as string value).
   * @throws CalculationException if the given term has a return type can not
   *         match {@link String}. It might be confusing that the caused
   *         exception is called this way but however.
   */
  public ExpressionVariable(Term variableNameTerm) throws CalculationException {

    super();
    this.term = variableNameTerm;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getVariableName(Context environment) throws ValueException {

    Object expressionResult = this.term.evaluate(environment);
    if (expressionResult == null) {
      throw new CalculationException(NlsBundleTermCore.ERR_EXPR_VAR_NULL, this);
    }
    return expressionResult.toString();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuffer sb = new StringBuffer();
    sb.append(VARIABLE_START);
    sb.append(EXPRESSION_START);
    sb.append(this.term.toString());
    sb.append(EXPRESSION_END);
    sb.append(VARIABLE_END);
    return sb.toString();
  }

}
