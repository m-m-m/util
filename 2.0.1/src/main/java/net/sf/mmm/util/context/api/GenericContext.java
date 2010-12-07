/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.context.api;

import java.util.Map;
import java.util.Set;

import net.sf.mmm.util.value.api.ValueNotSetException;

/**
 * This is the interface for a context of variables. It allows to read all
 * variables as well as to {@link #createChildContext() create a child-context}
 * this is {@link MutableGenericContext mutable}.<br>
 * <b>Note:</b> Initially the name of this interface should be just
 * <code>Context</code>. However there are tons of common java projects shipped
 * with a type of this name so to avoid confusion a longer name was chosen.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public interface GenericContext {

  /**
   * This method gets the variable associated with the given
   * <code>variableName</code>.
   * 
   * @param variableName is the name of the requested variable.
   * @return the value of the variable.
   * @throws ValueNotSetException if the requested variable is NOT set.
   */
  Object requireVariable(String variableName) throws ValueNotSetException;

  /**
   * This method gets the variable associated with the given
   * <code>variableName</code>.
   * 
   * @param <T> the generic type of the variable.
   * @param variableName is the name of the requested variable.
   * @param type is the class reflecting the type of the variable.
   * @return the value of the variable.
   * @throws ValueNotSetException if the requested variable is NOT set.
   */
  <T> T requireVariable(String variableName, Class<T> type) throws ValueNotSetException;

  /**
   * This method gets the variable associated with the given <code>type</code>.
   * It will use the {@link Class#getName() classname} as
   * {@link #getVariable(String, Class) variable-name}.<br/>
   * <b>ATTENTION:</b><br>
   * Only use this method in combination with expressive types. E.g. types like
   * {@link String} or {@link Integer} are bad candidates while
   * <code>MySpecificSingletonComponentInterface</code> might be a good option.
   * 
   * @see MutableGenericContext#setVariable(String, Object)
   * 
   * @param <T> the generic type of the variable.
   * @param type is the class reflecting the type of the variable.
   * @return the value of the variable.
   * @throws ValueNotSetException if the requested variable is NOT set.
   * @since 2.0.0
   */
  <T> T requireVariable(Class<T> type) throws ValueNotSetException;

  /**
   * This method gets the variable associated with the given
   * <code>variableName</code>.
   * 
   * @param variableName is the name of the requested variable.
   * @return the value of the variable or <code>null</code> if the variable is
   *         NOT set.
   */
  Object getVariable(String variableName);

  /**
   * This method gets the variable associated with the given
   * <code>variableName</code> as the given <code>type</code>. If the
   * <code>type</code> does NOT match the actual type of the variable it may
   * automatically be converted as possible.
   * 
   * @param <T> the generic type of the variable.
   * @param variableName is the name of the requested variable.
   * @param type is the class reflecting the type of the variable.
   * @return the value of the variable or <code>null</code> if the variable is
   *         NOT set.
   */
  <T> T getVariable(String variableName, Class<T> type);

  /**
   * This method gets the variable associated with the given <code>type</code>.
   * It will use the {@link Class#getName() classname} as
   * {@link #getVariable(String, Class) variable-name}.<br/>
   * <b>ATTENTION:</b><br>
   * Only use this method in combination with expressive types. E.g. types like
   * {@link String} or {@link Integer} are bad candidates while
   * <code>MySpecificSingletonComponentInterface</code> might be a good option.
   * 
   * @see MutableGenericContext#setVariable(String, Object)
   * 
   * @param <T> the generic type of the variable.
   * @param type is the class reflecting the type of the variable.
   * @return the value of the variable or <code>null</code> if the variable is
   *         NOT set.
   * @since 2.0.0
   */
  <T> T getVariable(Class<T> type);

  /**
   * This method determines if the {@link #getVariable(String) variable} for the
   * given <code>variableName</code> exists.
   * 
   * @param variableName is the name of the requested variable.
   * @return <code>true</code> if a value exists for the given
   *         <code>variableName</code>, <code>false</code> otherwise.
   */
  boolean hasVariable(String variableName);

  /**
   * This method returns the names of all defined variables.<br>
   * <b>ATTENTION:</b><br>
   * Please note that this is an expensive operation so prefer
   * {@link #hasVariable(String)} where possible.
   * 
   * @return a set of all variable names.
   */
  Set<String> getVariableNames();

  /**
   * This method creates a new context that inherits all variables from this
   * context (and its parent contexts). Variables that are set in the
   * child-context override inherited variables. However changes to the
   * child-context do NOT modify this context.<br>
   * <b>ATTENTION:</b><br>
   * Typically the child-context will be a cheap-copy connected to this context.
   * So you should be aware of this when creating many child-contexts
   * recursively as their parent-contexts can NOT be collected by the
   * garbage-collector.
   * 
   * @return the new created sub-context.
   */
  MutableGenericContext createChildContext();

  /**
   * This method allows to create a {@link Map} representing the variables of
   * this context. This can be useful to pass this context to an external
   * component (e.g. a template-engine) that typically accepts a {@link Map}.<br>
   * <b>ATTENTION:</b><br>
   * This method will create a fresh map with all variables filled in. Therefore
   * it is NOT a cheap operation. Further the {@link Map} will NOT reflect
   * changes of this context and vice versa.
   * 
   * @return this context as map.
   */
  Map<String, Object> toMap();

}
