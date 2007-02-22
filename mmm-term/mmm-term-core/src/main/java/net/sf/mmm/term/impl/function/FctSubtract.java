/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.impl.function;

import net.sf.mmm.term.api.OperatorPriority;

/**
 * This class partially implements a binary function that subtracts the
 * arguments. <br>
 * 
 * @see net.sf.mmm.term.impl.GenericFunction
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FctSubtract {

  /**
   * the suggested
   * {@link net.sf.mmm.term.api.Function#getOperatorSymbol() "operator symbol"}
   */
  public static final String SYMBOL = "-";

  /**
   * the {@link net.sf.mmm.term.api.Function#getName() name} of this function
   */
  public static final String NAME = "sub";

  /**
   * the {@link net.sf.mmm.term.api.Function#getOperatorPriority() priority} of
   * this function
   */
  public static final OperatorPriority PRIORITY = OperatorPriority.MEDIUM;

  /**
   * The constructor.
   */
  private FctSubtract() {

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
  public static Double sub(Double argument1, Number argument2) {

    return new Double(argument1.doubleValue() - argument2.doubleValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return the difference of both arguments.
   */
  public static Double sub(Number argument1, Double argument2) {

    return new Double(argument1.doubleValue() - argument2.doubleValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return the difference of both arguments.
   */
  public static Integer sub(Integer argument1, Integer argument2) {

    return new Integer(argument1.intValue() - argument2.intValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return the difference of both arguments.
   */
  public static Long sub(Long argument1, Integer argument2) {

    return new Long(argument1.longValue() - argument2.longValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return the difference of both arguments.
   */
  public static Long sub(Integer argument1, Long argument2) {

    return new Long(argument1.longValue() - argument2.longValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return the difference of both arguments.
   */
  public static Long sub(Long argument1, Long argument2) {

    return new Long(argument1.longValue() - argument2.longValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return the difference of both arguments.
   */
  public static Float sub(Float argument1, Float argument2) {

    return new Float(argument1.floatValue() - argument2.floatValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return the difference of both arguments.
   */
  public static Float sub(Float argument1, Integer argument2) {

    return new Float(argument1.floatValue() - argument2.floatValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return the difference of both arguments.
   */
  public static Float sub(Integer argument1, Float argument2) {

    return new Float(argument1.floatValue() - argument2.floatValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return the difference of both arguments.
   */
  public static Float sub(Float argument1, Long argument2) {

    return new Float(argument1.floatValue() - argument2.longValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1
   *        is the first argument.
   * @param argument2
   *        is the second argument.
   * @return the difference of both arguments.
   */
  public static Float sub(Long argument1, Float argument2) {

    return new Float(argument1.longValue() - argument2.floatValue());
  }

}
