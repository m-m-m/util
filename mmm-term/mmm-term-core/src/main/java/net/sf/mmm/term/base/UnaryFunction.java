/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.base;

import net.sf.mmm.term.api.OperatorPriority;
import net.sf.mmm.util.value.api.ValueException;

/**
 * This is the abstract base implementation of a unary
 * {@link net.sf.mmm.term.api.Function function}. This means that the function
 * takes exactly <code>1</code> argument.
 * 
 * @see net.sf.mmm.term.api.Function#calculate(net.sf.mmm.context.api.Context,
 *      net.sf.mmm.term.api.Term...)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class UnaryFunction extends BasicFunction {

  /**
   * The constructor.
   */
  public UnaryFunction() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public OperatorPriority getOperatorPriority() {

    return OperatorPriority.MAXIMUM;
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

    return 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public abstract Object calculate(Object argument) throws ValueException;

}
