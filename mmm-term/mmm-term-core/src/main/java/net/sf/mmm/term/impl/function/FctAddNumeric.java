/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.impl.function;

import net.sf.mmm.util.math.base.MathUtilImpl;

/**
 * This class partially implements a binary function that builds the sum of its
 * arguments. <br>
 * 
 * @see net.sf.mmm.term.impl.GenericFunction
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FctAddNumeric extends FctAdd {

  /** the <code>0</code> */
  private static final Integer ZERO = Integer.valueOf(0);

  /**
   * The constructor.
   */
  private FctAddNumeric() {

    super();
  }

  /**
   * The function implementation for the given signature.
   * 
   * @return the sum.
   */
  public static Integer add() {

    return ZERO;
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument is the argument.
   * @return the sum of the arguments.
   */
  public static Object add(Object argument) {

    return argument;
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1 is the first argument.
   * @param argument2 is the second argument.
   * @return the sum of both arguments.
   */
  public static Double add(Double argument1, Number argument2) {

    return Double.valueOf(argument1.doubleValue() + argument2.doubleValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1 is the first argument.
   * @param argument2 is the second argument.
   * @return the sum of both arguments.
   */
  public static Double add(Number argument1, Double argument2) {

    return Double.valueOf(argument1.doubleValue() + argument2.doubleValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1 is the first argument.
   * @param argument2 is the second argument.
   * @return the sum of both arguments.
   */
  public static Number add(Integer argument1, Integer argument2) {

    long sum = argument1.longValue() + argument2.longValue();
    int i = (int) sum;
    if (sum == i) {
      return Integer.valueOf(i);
    } else {
      return Long.valueOf(sum);
    }
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1 is the first argument.
   * @param argument2 is the second argument.
   * @return the sum of both arguments.
   */
  public static Long add(Long argument1, Integer argument2) {

    return Long.valueOf(argument1.longValue() + argument2.longValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1 is the first argument.
   * @param argument2 is the second argument.
   * @return the sum of both arguments.
   */
  public static Long add(Integer argument1, Long argument2) {

    return Long.valueOf(argument1.longValue() + argument2.longValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1 is the first argument.
   * @param argument2 is the second argument.
   * @return the sum of both arguments.
   */
  public static Long add(Long argument1, Long argument2) {

    return Long.valueOf(argument1.longValue() + argument2.longValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1 is the first argument.
   * @param argument2 is the second argument.
   * @return the sum of both arguments.
   */
  public static Float add(Float argument1, Float argument2) {

    return Float.valueOf(argument1.floatValue() + argument2.floatValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1 is the first argument.
   * @param argument2 is the second argument.
   * @return the sum of both arguments.
   */
  public static Float add(Float argument1, Integer argument2) {

    return Float.valueOf(argument1.floatValue() + argument2.floatValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1 is the first argument.
   * @param argument2 is the second argument.
   * @return the sum of both arguments.
   */
  public static Float add(Integer argument1, Float argument2) {

    return Float.valueOf(argument1.floatValue() + argument2.floatValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1 is the first argument.
   * @param argument2 is the second argument.
   * @return the sum of both arguments.
   */
  public static Float add(Float argument1, Long argument2) {

    return Float.valueOf(argument1.floatValue() + argument2.longValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1 is the first argument.
   * @param argument2 is the second argument.
   * @return the sum of both arguments.
   */
  public static Float add(Long argument1, Float argument2) {

    return Float.valueOf(argument1.longValue() + argument2.floatValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument1 is the first argument.
   * @param argument2 is the second argument.
   * @return the sum of both arguments.
   */
  public static Number add(Number argument1, Number argument2) {

    Double sum = Double.valueOf(argument1.doubleValue() + argument2.doubleValue());
    return MathUtilImpl.getInstance().toSimplestNumber(sum);
  }

}
