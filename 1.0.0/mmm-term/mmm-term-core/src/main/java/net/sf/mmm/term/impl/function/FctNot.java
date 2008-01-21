/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.impl.function;

import net.sf.mmm.term.api.OperatorPriority;

/**
 * This class partially implements a unary function that performs a logical
 * (boolean) or the bitwise (integer) negation. <br>
 * 
 * @see net.sf.mmm.term.impl.GenericFunction
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FctNot {

  /**
   * the suggested
   * {@link net.sf.mmm.term.api.Function#getOperatorSymbol() "operator symbol"}
   */
  public static final String SYMBOL = "!";

  /**
   * the {@link net.sf.mmm.term.api.Function#getName() name} of this function
   */
  public static final String NAME = "not";

  /**
   * the {@link net.sf.mmm.term.api.Function#getOperatorPriority() priority} of
   * this function
   */
  public static final OperatorPriority PRIORITY = OperatorPriority.MAXIMUM;

  /**
   * The constructor.
   */
  private FctNot() {

    super();
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument is the argument of the function.
   * @return the bitwise negation of the given argument.
   */
  public static Integer not(Integer argument) {

    return Integer.valueOf(~argument.intValue());
  }

  /**
   * The function implementation for the given signature.
   * 
   * @param argument is the argument of the function.
   * @return the logical negation of the given argument.
   */
  public static Boolean not(Boolean argument) {

    return Boolean.valueOf(!argument.booleanValue());
  }

}
