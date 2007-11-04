/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.context.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.mmm.context.api.Context;
import net.sf.mmm.context.api.MutableContext;
import net.sf.mmm.util.value.ValueNotSetException;
import net.sf.mmm.value.api.GenericValue;
import net.sf.mmm.value.base.EmptyValue;
import net.sf.mmm.value.impl.ObjectValue;
import net.sf.mmm.value.impl.StringValue;

/**
 * This class is the basic implementation of the {@link Context} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class MutableContextImpl implements MutableContext {

  /** maps variable names to {@link #getValue(String) values} */
  private final Map<String, GenericValue> variableTable;

  /** the parent context */
  private final Context parent;

  /** the {@link #getImmutableContext() "immutable context"} */
  private final Context immutableContext;

  /**
   * The constructor for a root-context.
   */
  public MutableContextImpl() {

    this(null);
  }

  /**
   * The constructor for a {@link #createChildContext() sub-context}.
   * 
   * @param parentContext is the context the created one will derive from.
   */
  public MutableContextImpl(Context parentContext) {

    super();
    this.parent = parentContext;
    this.variableTable = new HashMap<String, GenericValue>();
    this.immutableContext = new ContextProxy(this);
  }

  /**
   * @see net.sf.mmm.context.api.Context#getValue(java.lang.String)
   * 
   * 
   * @param variableName is the name of the requested context value.
   * @return the requested value or <code>null</code> if no such value exists.
   */
  private GenericValue get(String variableName) {

    GenericValue result = this.variableTable.get(variableName);
    if ((result == null) && (this.parent != null)) {
      result = this.parent.getValue(variableName);
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public GenericValue getValue(String variableName) {

    GenericValue result = get(variableName);
    if (result == null) {
      result = new EmptyValue(variableName);
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public Object getObject(String variableName) {

    GenericValue value = get(variableName);
    if (value == null) {
      throw new ValueNotSetException(variableName);
    }
    return value.getObject(null);
  }

  /**
   * {@inheritDoc}
   */
  public boolean hasValue(String variableName) {

    if (this.variableTable.containsKey(variableName)) {
      return true;
    } else {
      if (this.parent == null) {
        return false;
      } else {
        return this.parent.hasValue(variableName);
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
    result.addAll(this.variableTable.keySet());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public void setValue(String variableName, GenericValue value) {

    this.variableTable.put(variableName, value);
  }

  /**
   * {@inheritDoc}
   */
  public void setObject(String variableName, Object value) {

    if ((value != null) && (value instanceof String)) {
      setValue(variableName, new StringValue((String) value));
    } else {
      setValue(variableName, new ObjectValue(value));
    }
  }

  /**
   * {@inheritDoc}
   */
  public MutableContext createChildContext() {

    return new MutableContextImpl(this);
  }

  /**
   * {@inheritDoc}
   */
  public void unsetValue(String variableName) {

    this.variableTable.remove(variableName);
  }

  /**
   * {@inheritDoc}
   */
  public Context getImmutableContext() {

    return this.immutableContext;
  }

}
