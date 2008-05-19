/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.impl.function;

import net.sf.mmm.term.api.CalculationException;
import net.sf.mmm.term.api.IllegalArgumentTypeException;
import net.sf.mmm.term.base.BasicFunction;
import net.sf.mmm.util.reflect.api.Arguments;
import net.sf.mmm.util.value.api.ValueException;

/**
 * This class represents the {@link net.sf.mmm.term.api.Function function}
 * <b>concat</b> that concats all given arguments.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FunctionConcat extends BasicFunction {

  /** the {@link #getName() name} of this function */
  public static final String NAME = "concat";

  /** the {@link #getOperatorSymbol() "operator symbol"} of this function */
  public static final String SYMBOL = null;

  /**
   * The constructor.
   */
  public FunctionConcat() {

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

    return 1;
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
  public Object calculate(Object argument) throws ValueException {

    return argument;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String calculate(Object argument1, Object argument2) throws CalculationException {

    if ((argument1 != null) && (argument1.getClass() == String.class)) {
      return ((String) argument1) + argument2;
    }
    throw new IllegalArgumentTypeException(this, new Arguments(argument1, argument2));
  }

}
