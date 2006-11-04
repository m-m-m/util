/* $Id$ */
package net.sf.mmm.configuration.base;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.term.api.FunctionIF;
import net.sf.mmm.term.api.ParseException;
import net.sf.mmm.term.api.TermIF;
import net.sf.mmm.term.base.Variable;
import net.sf.mmm.term.impl.Constant;
import net.sf.mmm.term.impl.Expression;
import net.sf.mmm.term.impl.function.FunctionConcat;

/**
 * This is a simple, hand-written parser for expressions allowed in
 * {@link net.sf.mmm.configuration.api.Configuration configuration}
 * {@link net.sf.mmm.configuration.api.Configuration#getValue() values}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ConfigurationExpressionParser {

  /** the string-concat function */
  private static final FunctionIF FUNCTION_CONCAT = new FunctionConcat();

  /** the first start char of a variable */
  private static final char VARIABLE_START_CAR1 = '$';

  /** the second start char of a variable */
  private static final char VARIABLE_START_CAR2 = '{';

  /** the start pattern of a variable */
  public static final String VARIABLE_START = "" + VARIABLE_START_CAR1 + VARIABLE_START_CAR2;

  /** the first end char of a variable */
  private static final char VARIABLE_END_CAR1 = '}';

  /** the end pattern of a variable */
  public static final String VARIABLE_END = "" + VARIABLE_END_CAR1;

  /**
   * The constructor.
   */
  private ConfigurationExpressionParser() {

    super();
  }

  /**
   * This method parses a simple string expression that allows substitution of
   * variables.
   * 
   * @param expression
   *        is the expression to parse as string.
   * @return the parsed expression.
   * @throws Exception
   *         if the parsing failed.
   */
  public static TermIF parse(String expression) throws Exception {

    StringBuffer buffer = new StringBuffer();
    int pos = 0;
    int len = expression.length();
    List<TermIF> arguments = new ArrayList<TermIF>();
    boolean inVariable = false;
    while (pos < len) {
      char c = expression.charAt(pos++);
      if (inVariable) {
        if (c == VARIABLE_END_CAR1) {
          inVariable = false;
          String variableName = buffer.toString();
          arguments.add(new Variable(variableName));
          buffer.setLength(0);
        } else {
          buffer.append(c);
        }
      } else {
        if (c == VARIABLE_START_CAR1) {
          if (buffer.length() > 0) {
            String constantString = buffer.toString();
            arguments.add(new Constant<String>(constantString));
            buffer.setLength(0);
          }
          if (pos < len) {
            c = expression.charAt(pos++);
            if (c == VARIABLE_START_CAR2) {
              inVariable = true;
            }
          }
          if (!inVariable) {
            // TODO i18n
            throw new ParseException(
                "Could not parse \"{0}\": character \"{1}\" must be followed by \"{2}\"!",
                expression, Character.valueOf(VARIABLE_START_CAR1), Character
                    .valueOf(VARIABLE_START_CAR2));
          }
        } else {
          buffer.append(c);
        }
      }
    }
    if (inVariable) {
      throw new ParseException("Could not parse \"{0}\": variable must be closed with \"{1}\"!",
          expression, VARIABLE_END);
    }
    if (buffer.length() > 0) {
      String constantString = buffer.toString();
      arguments.add(new Constant<String>(constantString));
    }
    return new Expression(FUNCTION_CONCAT, arguments.toArray(new TermIF[arguments.size()]));
  }

}
