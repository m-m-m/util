/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.context.base;

import java.util.Map;
import java.util.Set;

import net.sf.mmm.util.context.api.GenericContext;
import net.sf.mmm.util.context.api.MutableGenericContext;
import net.sf.mmm.util.value.api.ValueNotSetException;

/**
 * This is an abstract base implementation of the {@link net.sf.mmm.util.context.api.GenericContext} interface
 * that delegates to another instance.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractGenericContextProxy implements GenericContext {

  /**
   * The constructor.
   */
  public AbstractGenericContextProxy() {

    super();
  }

  /**
   * This method gets the delegate instance this proxy points to.
   * 
   * @return the real environment behind this proxy.
   */
  protected abstract GenericContext getContext();

  /**
   * {@inheritDoc}
   */
  public Object requireVariable(String variableName) throws ValueNotSetException {

    return getContext().requireVariable(variableName);
  }

  /**
   * {@inheritDoc}
   */
  public <T> T requireVariable(String variableName, Class<T> type) throws ValueNotSetException {

    return getContext().requireVariable(variableName, type);
  }

  /**
   * {@inheritDoc}
   */
  public <T> T requireVariable(Class<T> type) throws ValueNotSetException {

    return getContext().requireVariable(type);
  }

  /**
   * {@inheritDoc}
   */
  public Object getVariable(String variableName) {

    return getContext().getVariable(variableName);
  }

  /**
   * {@inheritDoc}
   */
  public <T> T getVariable(String variableName, Class<T> type) {

    return getContext().getVariable(variableName, type);
  }

  /**
   * {@inheritDoc}
   */
  public <T> T getVariable(Class<T> type) {

    return getContext().getVariable(type);
  }

  /**
   * {@inheritDoc}
   */
  public boolean hasVariable(String variableName) {

    return getContext().hasVariable(variableName);
  }

  /**
   * {@inheritDoc}
   */
  public Set<String> getVariableNames() {

    return getContext().getVariableNames();
  }

  /**
   * {@inheritDoc}
   */
  public MutableGenericContext createChildContext() {

    return getContext().createChildContext();
  }

  /**
   * {@inheritDoc}
   */
  public Map<String, Object> toMap() {

    return getContext().toMap();
  }

}
