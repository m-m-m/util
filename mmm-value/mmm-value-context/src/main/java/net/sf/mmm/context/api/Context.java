/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.context.api;

import java.util.Set;

import net.sf.mmm.value.api.GenericValue;
import net.sf.mmm.value.api.ValueNotSetException;

/**
 * This is the interface for a set of variables.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface Context {

  /**
   * This method gets the variable associated with the given
   * <code>variableName</code> as object. This method is similar to:
   * 
   * <pre>
   * {@link #getValue(String) getValue}(variableName).{@link GenericValue#getObject() getObject()}
   * </pre>
   * 
   * @param variableName is the name of the requested variable.
   * @return the value of the variable as
   *         {@link GenericValue#getObject() object} or <code>null</code> if
   *         the variable is defined but set to an
   *         {@link GenericValue#isEmpty() empty} value.
   * @throws ValueNotSetException if the requested value is not
   *         {@link #hasValue(String) available}. Use {@link #hasValue(String)}
   *         or {@link #getValue(String)} to prevent this.
   */
  Object getObject(String variableName) throws ValueNotSetException;

  /**
   * This method gets the variable associated with the given
   * <code>variableName</code> as value.
   * 
   * @param variableName is the name of the requested variable.
   * @return the value of the requested variable or an
   *         {@link net.sf.mmm.value.base.EmptyValue} if the variable is NOT
   *         defined.
   */
  GenericValue getValue(String variableName);

  /**
   * This method determines if the {@link #getValue(String) value} for the given
   * <code>variableName</code> exists.
   * 
   * @param variableName is the name of the value to check.
   * @return <code>true</code> if a value exists for the given
   *         <code>variableName</code>, <code>false</code> otherwise.
   */
  boolean hasValue(String variableName);

  /**
   * This method returns the names of all defined variables.<br>
   * Please note that this is an expensive operation so prefer
   * {@link #hasValue(String)} where possible.
   * 
   * @return a set of all variable names.
   */
  Set<String> getVariableNames();

  /**
   * This method creates a new context that inherits all variables from this
   * context (and its parent contexts). Variables that are set in the
   * child-context override inherited variables.
   * 
   * @return the new created sub-context.
   */
  MutableContext createChildContext();

}
