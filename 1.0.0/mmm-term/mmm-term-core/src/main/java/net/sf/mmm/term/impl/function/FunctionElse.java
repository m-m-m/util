/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.impl.function;

import net.sf.mmm.term.api.CalculationException;
import net.sf.mmm.term.base.BasicFunction;

/**
 * This class represents the {@link net.sf.mmm.term.api.Function function}
 * <b>else</b> that returns the second argument if the first agrument is
 * <code>null</code> and the first argument in all other cases.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FunctionElse extends BasicFunction {

  /** the {@link #getName() name} of this function */
  public static final String NAME = "else";

  /** the {@link #getOperatorSymbol() "operator symbol"} of this function */
  public static final String SYMBOL = ":";

  /**
   * The constructor.
   */
  public FunctionElse() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {

    return NAME;
  }

  /**
   * {@inheritDoc}
   */
  public String getOperatorSymbol() {

    return SYMBOL;
  }

  /**
   * {@inheritDoc}
   */
  public int getMinimumArgumentCount() {

    return 2;
  }

  /**
   * {@inheritDoc}
   */
  public int getMaximumArgumentCount() {

    return Integer.MAX_VALUE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object calculate(Object argument1, Object argument2) throws CalculationException {

    if (argument1 == null) {
      return argument2;
    } else {
      return argument1;
    }
  }

}
