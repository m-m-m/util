/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.impl.function;

import net.sf.mmm.term.NlsBundleTermCore;
import net.sf.mmm.term.api.CalculationException;
import net.sf.mmm.term.api.OperatorPriority;

/**
 * This class partially implements a binary function that multiplies the
 * arguments. <br>
 * E.g. <code>2*3</code> results to <code>6</code>.
 * 
 * @see net.sf.mmm.term.impl.GenericFunction
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FctMultiply {

  /**
   * the suggested
   * {@link net.sf.mmm.term.api.Function#getOperatorSymbol() "operator symbol"}
   */
  public static final String SYMBOL = "*";

  /**
   * the {@link net.sf.mmm.term.api.Function#getName() name} of this function
   */
  public static final String NAME = "mul";

  /**
   * the {@link net.sf.mmm.term.api.Function#getOperatorPriority() priority} of
   * this function
   */
  public static final OperatorPriority PRIORITY = OperatorPriority.HIGH;

  /** the maximum result length of a string multiplication */
  private static final int MAX_STRING_LENGTH = (2 << 15);

  /**
   * The constructor.
   */
  private FctMultiply() {

    super();
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return the sum of both arguments.
   */
  public static Double mul(Double argument1, Number argument2) {

    return new Double(argument1.doubleValue() * argument2.doubleValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return the sum of both arguments.
   */
  public static Double mul(Number argument1, Double argument2) {

    return new Double(argument1.doubleValue() * argument2.doubleValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return the sum of both arguments.
   */
  public static Integer mul(Integer argument1, Integer argument2) {

    return new Integer(argument1.intValue() * argument2.intValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return the sum of both arguments.
   */
  public static Long mul(Long argument1, Integer argument2) {

    return new Long(argument1.longValue() * argument2.longValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return the sum of both arguments.
   */
  public static Long mul(Integer argument1, Long argument2) {

    return new Long(argument1.longValue() * argument2.longValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return the sum of both arguments.
   */
  public static Long mul(Long argument1, Long argument2) {

    return new Long(argument1.longValue() * argument2.longValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return the sum of both arguments.
   */
  public static Float mul(Float argument1, Float argument2) {

    return new Float(argument1.floatValue() * argument2.floatValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return the sum of both arguments.
   */
  public static Float mul(Float argument1, Integer argument2) {

    return new Float(argument1.floatValue() * argument2.floatValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return the sum of both arguments.
   */
  public static Float mul(Integer argument1, Float argument2) {

    return new Float(argument1.floatValue() * argument2.floatValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return the sum of both arguments.
   */
  public static Float mul(Float argument1, Long argument2) {

    return new Float(argument1.floatValue() * argument2.longValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return the sum of both arguments.
   */
  public static Float mul(Long argument1, Float argument2) {

    return new Float(argument1.longValue() * argument2.floatValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return the string <code>argument1</code> repeated <code>argument2</code>
   *         times.
   * @throws CalculationException
   *         if <code>argument2</code> is negative or the result would exceed
   *         {@link FctMultiply#MAX_STRING_LENGTH}.
   */
  public static String mul(String argument1, Integer argument2) throws CalculationException {

    int count = argument2.intValue();
    if (count < 0) {
      throw new CalculationException(NlsBundleTermCore.ERR_FCT_MUL_STR_NEG);
    }
    long length = argument1.length() * count;
    if (length == 0) {
      return "";
    }
    // we need a limit here, howerver lets take 2^16=65536
    if (length > MAX_STRING_LENGTH) {
      throw new CalculationException(NlsBundleTermCore.ERR_FCT_MUL_STR_MAX, Integer
          .valueOf(MAX_STRING_LENGTH));
    }
    StringBuffer result = new StringBuffer((int) length);
    while (count > 0) {
      result.append(argument1);
      count--;
    }
    return result.toString();
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return the string <code>argument2</code> repeated <code>argument1</code>
   *         times.
   * @throws CalculationException
   *         if <code>argument1</code> is negative or the result would exceed
   *         {@link FctMultiply#MAX_STRING_LENGTH}.
   */
  public static String mul(Integer argument1, String argument2) throws CalculationException {

    return mul(argument2, argument1);
  }

}
