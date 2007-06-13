/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.term.api.Function;
import net.sf.mmm.term.api.TermParseException;
import net.sf.mmm.term.api.Term;
import net.sf.mmm.term.api.TermParser;
import net.sf.mmm.term.base.Variable;
import net.sf.mmm.term.impl.function.FunctionConcat;
import net.sf.mmm.util.StringParser;

/**
 * This is a simple parser for {@link Term}s that replace variables in plain
 * texts. <br>
 * Example:<br>
 * If you parse
 * 
 * <pre>"Hello ${name.first} ${name.last}!"</pre>
 * 
 * to the {@link Term} and then
 * {@link Term#evaluate(net.sf.mmm.context.api.Context) evaluate} it with a
 * context that maps <code>name.first</code> to <code>Peter</code> and
 * <code>name.last</code> to <code>Pepper</code> you will get
 * <code>"Hello Peter Pepper!"</code>.<br>
 * Hint:<br>
 * If you want to have something like <code>${foo}</code> in your result you
 * can simply define a variable in your context e.g. named <code>$</code> with
 * the value of <code>$</code> and then notate your expression term as:
 * 
 * <pre>"${$}{foo}"</pre>
 * 
 * @see Term
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SimpleTermParser implements TermParser {

  /** the string-concat function */
  private static final Function FUNCTION_CONCAT = new FunctionConcat();

  /** the first start char of a variable */
  private static final char VARIABLE_START_CHAR1 = Term.VARIABLE_START.charAt(0);

  /** the second start char of a variable */
  private static final char VARIABLE_START_CHAR2 = Term.VARIABLE_START.charAt(1);

  /** the first end char of a variable */
  private static final char VARIABLE_END_CHAR1 = Term.VARIABLE_END.charAt(0);

  /**
   * The constructor.
   */
  public SimpleTermParser() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Term parse(String expression) throws TermParseException {

    StringParser parser = new StringParser(expression);
    List<Term> arguments = new ArrayList<Term>();
    while (parser.hasNext()) {
      String plainText = parser.readUntil(VARIABLE_START_CHAR1, true);
      Variable variable = null;
      if (parser.hasNext()) {
        if (parser.expect(VARIABLE_START_CHAR2)) {
          String variableName = parser.readUntil(VARIABLE_END_CHAR1, false);
          if ((variableName == null) || (variableName.length() == 0)) {
            // TODO
            throw new TermParseException(expression);
          }
          variable = new Variable(variableName);
        } else {
          plainText = plainText + VARIABLE_START_CHAR1;
        }
      }
      if (plainText.length() > 0) {
        arguments.add(new Constant<String>(plainText));
      }
      if (variable != null) {
        arguments.add(variable);
      }
    }
    if (arguments.size() == 0) {
      // TODO
      throw new TermParseException(expression);
    } else if (arguments.size() == 1) {
      return arguments.get(0);
    } else {
      return new Expression(FUNCTION_CONCAT, arguments.toArray(new Term[arguments.size()]));
    }
  }

}
