/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.context.base;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.mmm.util.collection.api.MapFactory;
import net.sf.mmm.util.context.api.GenericContext;
import net.sf.mmm.util.context.api.MutableGenericContext;
import net.sf.mmm.util.value.api.ValueNotSetException;

/**
 * This class is the basic implementation of the {@link GenericContext}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public abstract class AbstractMutableGenericContext implements MutableGenericContext {

  /** maps variable names to {@link #getVariable(String) values} */
  private final Map<String, Object> variableMap;

  /** the parent context */
  private final GenericContext parent;

  /** the parent context */
  private final GenericContext immutableContext;

  /** @see #getMapFactory() */
  @SuppressWarnings("unchecked")
  private final MapFactory<? extends Map> mapFactory;

  /**
   * The constructor for a {@link #createChildContext() sub-context}.
   * 
   * @param mapFactory is used to create the map for storing variables.
   * @param parentContext is the context the created one will derive from.
   */
  @SuppressWarnings("unchecked")
  public AbstractMutableGenericContext(MapFactory<? extends Map> mapFactory,
      GenericContext parentContext) {

    super();
    this.parent = parentContext;
    this.mapFactory = mapFactory;
    this.variableMap = mapFactory.create();
    this.immutableContext = new ImmutableGenericContext(this);
  }

  /**
   * {@inheritDoc}
   */
  public Object getVariable(String variableName) {

    Object value = this.variableMap.get(variableName);
    if ((value == null) && (this.parent != null)) {
      value = this.parent.getVariable(variableName);
    }
    return value;
  }

  /**
   * {@inheritDoc}
   */
  public Object requireVariable(String variableName) throws ValueNotSetException {

    Object value = getVariable(variableName);
    if (value == null) {
      throw new ValueNotSetException(variableName);
    }
    return value;
  }

  /**
   * {@inheritDoc}
   */
  public <T> T requireVariable(String variableName, Class<T> type) throws ValueNotSetException {

    T value = getVariable(variableName, type);
    if (value == null) {
      throw new ValueNotSetException(variableName);
    }
    return value;
  }

  /**
   * {@inheritDoc}
   */
  public boolean hasVariable(String variableName) {

    if (this.variableMap.containsKey(variableName)) {
      return true;
    } else {
      if (this.parent == null) {
        return false;
      } else {
        return this.parent.hasVariable(variableName);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public Set<String> getVariableNames() {

    Set<String> result;
    if (this.parent == null) {
      result = new HashSet<String>();
    } else {
      result = this.parent.getVariableNames();
    }
    result.addAll(this.variableMap.keySet());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public Object setVariable(String variableName, Object value) {

    return this.variableMap.put(variableName, value);
  }

  /**
   * {@inheritDoc}
   */
  public Object removeVariable(String variableName) {

    return this.variableMap.remove(variableName);
  }

  /**
   * {@inheritDoc}
   */
  public Map<String, Object> toMap() {

    Map<String, Object> map;
    if (this.parent == null) {
      map = this.mapFactory.create();
    } else {
      map = this.parent.toMap();
    }
    map.putAll(this.variableMap);
    return map;
  }

  /**
   * {@inheritDoc}
   */
  public GenericContext getImmutableContext() {

    return this.immutableContext;
  }

  /**
   * This method gets the {@link MapFactory}.
   * 
   * @return the {@link MapFactory} to use.
   */
  @SuppressWarnings("unchecked")
  protected MapFactory<? extends Map> getMapFactory() {

    return this.mapFactory;
  }

}
