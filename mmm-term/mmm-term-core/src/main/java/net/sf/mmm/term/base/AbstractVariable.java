/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.base;

import net.sf.mmm.context.api.Context;
import net.sf.mmm.value.api.ValueException;

/**
 * This is the abstract base implementation of a the
 * {@link net.sf.mmm.term.api.Term} interface that represents a variable.<br>
 * A variable {@link net.sf.mmm.term.api.Term#evaluate(Context) evaluates} to an
 * {@link net.sf.mmm.context.api.Context#getObject(String) "environment value"}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractVariable extends AbstractTerm {

  /**
   * The constructor.
   */
  public AbstractVariable() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public final Object evaluate(Context environment) throws ValueException {

    return environment.getObject(getVariableName(environment));
  }

  /**
   * This method gets the name of the variable.
   * 
   * @return the name of the variable.
   * @param environment
   *        is the environment given to evaluate this variable.
   * @throws ValueException
   *         if the variable name is determined dynamically via a calculation
   *         that failed.
   */
  public abstract String getVariableName(Context environment) throws ValueException;

}
