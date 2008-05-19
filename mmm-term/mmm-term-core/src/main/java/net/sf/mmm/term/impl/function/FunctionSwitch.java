/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.impl.function;

import net.sf.mmm.term.api.IllegalArgumentTypeException;
import net.sf.mmm.term.base.BasicFunction;
import net.sf.mmm.util.reflect.api.Arguments;
import net.sf.mmm.util.value.api.ValueException;

/**
 * This class implements the binary function <b>switch</b> that returns the
 * second argument if the first is <code>true</code>, else <code>null</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FunctionSwitch extends BasicFunction {

  /** the {@link #getName() name} of this function */
  public static final String NAME = "switch";

  /** the {@link #getOperatorSymbol() "operator symbol"} of this function */
  public static final String SYMBOL = "?";

  /**
   * The constructor.
   */
  public FunctionSwitch() {

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

    return 2;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object calculate(Object argument1, Object argument2) throws ValueException {

    try {
      Boolean test = (Boolean) argument1;
      if (test.booleanValue()) {
        return argument2;
      } else {
        // TODO: create fake NULL value in order to work with else!
        return null;
      }
    } catch (ClassCastException e) {
      throw new IllegalArgumentTypeException(this, new Arguments(argument1, argument2), e);
    }
  }

}
