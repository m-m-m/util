/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.context.api;

/**
 * This is the interface for a {@link GenericContext context} that can be modified.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public interface MutableGenericContext extends GenericContext {

  /**
   * This method sets the variable specified by <code>variableName</code> to the given <code>value</code>. If
   * the variable is already set, it will be overridden.
   * 
   * @see #getVariable(String)
   * 
   * @param variableName is the name of the variable to set.
   * @param value is the value to assign to the variable.
   * @return the old value of the variable in this context (excluding inherited variables) or
   *         <code>null</code> if the variable was undefined or NOT overridden in this context.
   */
  Object setVariable(String variableName, Object value);

  /**
   * This method sets the variable given by <code>value</code>. If the variable is already set, it will be
   * overridden. This method will use the {@link Class#getName() classname} as
   * {@link #setVariable(String, Object) variable-name}.<br/>
   * <b>ATTENTION:</b><br>
   * Only use this method in combination with expressive types. E.g. types like {@link String} or
   * {@link Integer} are bad candidates while <code>MySpecificSingletonComponentInterface</code> might be a
   * good option.
   * 
   * @see #getVariable(String)
   * 
   * @see #getVariable(Class)
   * 
   * @param value is the value to assign to the variable.
   * @return the old value of the variable in this context (excluding inherited variables) or
   *         <code>null</code> if the variable was undefined or NOT overridden in this context.
   * @since 2.0.0
   */
  Object setVariable(Object value);

  /**
   * This method removes (unsets) the variable specified by <code>variableName</code>. This will only remove a
   * variable defined in this context. A variable inherited from a {@link GenericContext#createChildContext()
   * parent context} can not be removed here. <br>
   * If this method is called with the name of a variable not defined in this context it will have no effect.
   * You might want to {@link #setVariable(String, Object) set} a variable to <code>null</code> instead.
   * 
   * @param variableName is the name of the variable to unset.
   * @return the old value of the variable or <code>null</code> if the variable is NOT defined (in this
   *         context).
   */
  Object removeVariable(String variableName);

  /**
   * This method gets an immutable proxy of this context that can NOT be casted to
   * {@link MutableGenericContext}.
   * 
   * @return the immutable view on this context.
   */
  GenericContext getImmutableContext();

}
