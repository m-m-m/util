/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.base;

import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.exception.api.NlsUnsupportedOperationException;
import net.sf.mmm.util.pojo.path.api.PojoPathContext;
import net.sf.mmm.util.pojo.path.api.PojoPathFunction;
import net.sf.mmm.util.pojo.path.api.PojoPathNamedFunction;

/**
 * This is the abstract base implementation of the {@link PojoPathFunction} interface.
 *
 * @param <IN> is the generic {@link #getInputClass() input-type}.
 * @param <VALUE> is the generic {@link #getValueClass() value-type}
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public abstract class AbstractPojoPathFunction<IN, VALUE> extends AbstractLoggableComponent
    implements PojoPathFunction<IN, VALUE> {

  /**
   * The constructor.
   */
  public AbstractPojoPathFunction() {

    super();
  }

  /**
   * {@inheritDoc}
   *
   * This implementation always returns {@code true}. Override for indeterministic implementations.
   */
  @Override
  public boolean isDeterministic() {

    return true;
  }

  /**
   * This method gets a description of this function.
   *
   * @param functionName is the name under which this function was invoked.
   * @return a string describing this function.
   */
  protected String getFunctionDescription(String functionName) {

    String className = getClass().getSimpleName();
    StringBuilder buffer = new StringBuilder(functionName.length() + className.length() + 4);
    buffer.append(FUNCTION_NAME_PREFIX);
    buffer.append(functionName);
    buffer.append(" [");
    buffer.append(className);
    buffer.append("]");
    return buffer.toString();
  }

  @Override
  public VALUE create(IN actual, String functionName, PojoPathContext context) {

    throw new NlsUnsupportedOperationException("create", getFunctionDescription(functionName));
  }

  @Override
  public VALUE get(IN actual, String functionName, PojoPathContext context) {

    // actually get should always be supported!
    throw new NlsUnsupportedOperationException("get", getFunctionDescription(functionName));
  }

  @Override
  public VALUE set(IN actual, String functionName, VALUE value, PojoPathContext context) {

    throw new NlsUnsupportedOperationException("set", getFunctionDescription(functionName));
  }

  @SuppressWarnings("rawtypes")
  @Override
  public String toString() {

    String name;
    if (this instanceof PojoPathNamedFunction) {
      name = ((PojoPathNamedFunction) this).getName();
    } else {
      name = "<function>";
    }
    return getFunctionDescription(name);
  }

}
