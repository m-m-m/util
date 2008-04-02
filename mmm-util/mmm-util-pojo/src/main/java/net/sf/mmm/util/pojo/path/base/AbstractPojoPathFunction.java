/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.base;

import net.sf.mmm.util.pojo.path.api.PojoPathContext;
import net.sf.mmm.util.pojo.path.api.PojoPathFunction;
import net.sf.mmm.util.pojo.path.api.PojoPathFunctionUnsupportedOperationException;

/**
 * This is the abstract base implementation of the {@link PojoPathFunction}
 * interface.
 * 
 * @param <ACTUAL> is the generic type of the actual
 *        {@link net.sf.mmm.util.pojo.api.Pojo} this function operates
 *        on.
 * @param <VALUE> is the generic type of the value this function traverses to,
 *        starting from the actual {@link net.sf.mmm.util.pojo.api.Pojo}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPojoPathFunction<ACTUAL, VALUE> implements
    PojoPathFunction<ACTUAL, VALUE> {

  /**
   * The constructor.
   */
  public AbstractPojoPathFunction() {

    super();
  }

  /**
   * This method gets a description of this function.
   * 
   * @param functionName is the name under which this function was invoked.
   * @return a string describing this function.
   */
  protected String getFunctionDescription(String functionName) {

    String className = getClass().getSimpleName();
    StringBuilder buffer = new StringBuilder(functionName.length() + className.length() + 3);
    buffer.append(functionName);
    buffer.append(" [");
    buffer.append(className);
    buffer.append("]");
    return buffer.toString();
  }

  /**
   * {@inheritDoc}
   */
  public VALUE create(ACTUAL actual, String functionName, PojoPathContext context) {

    throw new PojoPathFunctionUnsupportedOperationException("create",
        getFunctionDescription(functionName));
  }

  /**
   * {@inheritDoc}
   */
  public VALUE get(ACTUAL actual, String functionName, PojoPathContext context) {

    // actually get should always be supported!
    throw new PojoPathFunctionUnsupportedOperationException("get",
        getFunctionDescription(functionName));
  }

  /**
   * {@inheritDoc}
   */
  public VALUE set(ACTUAL actual, String functionName, VALUE value, PojoPathContext context) {

    throw new PojoPathFunctionUnsupportedOperationException("set",
        getFunctionDescription(functionName));
  }

}
