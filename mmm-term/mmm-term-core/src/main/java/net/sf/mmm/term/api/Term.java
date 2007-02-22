/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.api;

import net.sf.mmm.context.api.Context;
import net.sf.mmm.util.xml.api.XmlSerializable;
import net.sf.mmm.value.api.GenericValue;
import net.sf.mmm.value.api.ValueException;

/**
 * This is the interface for a mathematical term. A term is an object that can
 * be evaluated to a concrete value. It is either a constant, a variable or an
 * expression. An expression is a function in combination with a number of terms
 * treated as arguments to the function.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface Term extends XmlSerializable {

  /**
   * This string indicates the start of an expression term as string. If
   * <code>E</code> is an expression then also <code>(E)</code> is an
   * expression.
   */
  String EXPRESSION_START = "(";

  /**
   * This string indicates the end of an expression term.
   * 
   * @see #EXPRESSION_START
   */
  String EXPRESSION_END = ")";

  /**
   * This string indicates the start of a variable term as string. If
   * <code>N</code> is a name then <code>${N}</code> is an expression that
   * evaluates to the value of the variable. If <code>E</code> is an
   * expression then <code>${(E)}</code> is an expression, that evaluates to
   * the variable with the name of the result of <code>E</code>.
   */
  String VARIABLE_START = "${";

  /**
   * This string indicates the end of a variable.
   * 
   * @see #VARIABLE_START
   */
  String VARIABLE_END = "}";

  /**
   * This string indicates the start of an argument list for a function. If
   * <code>E1</code> ... <code>En</code> is a list of {@link Term terms},
   * and <code>f</code> is a {@link Function function} that
   * {@link Function#validateArgumentCount(int) takes} <code>n</code>
   * arguments, then also <code>f(E1,...,En)</code> is a legal
   * {@link Term term}.
   */
  String ARGUMENT_START = "(";

  /**
   * This string indicates the end of an argument list.
   * 
   * @see #ARGUMENT_START
   */
  String ARGUMENT_END = ")";

  /**
   * This string is used to separate the arguments of an argument list.
   * 
   * @see #ARGUMENT_START
   */
  String ARGUMENT_SEPARATOR = ",";

  /**
   * the tag name used to represent a
   * {@link net.sf.mmm.term.impl.Constant constant} as XML
   */
  String XML_TAG_CONSTANT = "constant";

  /**
   * the tag name used to represent a
   * {@link net.sf.mmm.term.base.AbstractVariable variable} as XML
   */
  String XML_TAG_VARIABLE = "variable";

  /** the attribute name used to represent the name of a variable in XML */
  String XML_ATR_VARIABLE_NAME = "name";

  /** the tag name used to represent an expression as XML */
  String XML_TAG_EXPRESSION = "expression";

  /**
   * the attribute name used to represent the name of an expression function
   * in XML
   */
  String XML_ATR_EXPRESSION_FKTNAME = "function";

  /**
   * This method evaluates the current term to a value. The term might contain
   * variables that are resolved through the given <code>environment</code>.
   * 
   * @param environment
   *        is the environment holding the variables used for evaluation.
   * @return the result value.
   * @throws CalculationException
   *         if a calculation fails during evaluation.
   * @throws ValueException
   *         if an argument (e.g. an
   *         {@link Context#getValue(String) "environment value"}) is
   *         {@link GenericValue#isEmpty() empty} or has the
   *         {@link net.sf.mmm.value.api.WrongValueTypeException "wrong type"}.
   */
  Object evaluate(Context environment) throws CalculationException, ValueException;

}
