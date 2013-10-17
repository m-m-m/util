/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.context.impl;

import java.util.Map;

import net.sf.mmm.util.collection.api.MapFactory;
import net.sf.mmm.util.context.api.GenericContext;
import net.sf.mmm.util.context.api.MutableGenericContext;
import net.sf.mmm.util.context.base.AbstractMutableGenericContext;
import net.sf.mmm.util.lang.api.CompilerWarnings;
import net.sf.mmm.util.value.api.GenericValueConverter;
import net.sf.mmm.util.value.api.WrongValueTypeException;

/**
 * This is the implementation of the {@link MutableGenericContext} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class MutableGenericContextImpl extends AbstractMutableGenericContext {

  /** @see #getVariable(String, Class) */
  private final GenericValueConverter<Object> valueConverter;

  /**
   * The constructor for a root-context.
   * 
   * @param mapFactory is used to create the map for storing variables.
   */
  @SuppressWarnings(CompilerWarnings.RAWTYPES)
  public MutableGenericContextImpl(MapFactory<? extends Map> mapFactory) {

    this(mapFactory, null, null);
  }

  /**
   * The constructor for a {@link #createChildContext() sub-context}.
   * 
   * @param mapFactory is used to create the map for storing variables.
   * @param valueConverter is the {@link GenericValueConverter} used to convert variables that are
   *        {@link #getVariable(String, Class) requested} as a different type.
   */
  @SuppressWarnings(CompilerWarnings.RAWTYPES)
  public MutableGenericContextImpl(MapFactory<? extends Map> mapFactory, GenericValueConverter<Object> valueConverter) {

    this(mapFactory, null, valueConverter);
  }

  /**
   * The constructor for a {@link #createChildContext() sub-context}.
   * 
   * @param mapFactory is used to create the map for storing variables.
   * @param parentContext is the context the created one will derive from.
   */
  @SuppressWarnings(CompilerWarnings.RAWTYPES)
  public MutableGenericContextImpl(MapFactory<? extends Map> mapFactory, GenericContext parentContext) {

    this(mapFactory, parentContext, null);
  }

  /**
   * The constructor for a {@link #createChildContext() sub-context}.
   * 
   * @param mapFactory is used to create the map for storing variables.
   * @param parentContext is the context the created one will derive from.
   * @param valueConverter is the {@link GenericValueConverter} used to convert variables that are
   *        {@link #getVariable(String, Class) requested} as a different type.
   */
  @SuppressWarnings(CompilerWarnings.RAWTYPES)
  public MutableGenericContextImpl(MapFactory<? extends Map> mapFactory, GenericContext parentContext,
      GenericValueConverter<Object> valueConverter) {

    super(mapFactory, parentContext);
    this.valueConverter = valueConverter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings(CompilerWarnings.UNCHECKED)
  public <T> T getVariable(String variableName, Class<T> type) {

    Object variable = getVariable(variableName);
    if ((variable == null) || type.isInstance(variable)) {
      return (T) variable;
    }
    if (this.valueConverter != null) {
      return this.valueConverter.convertValue(variable, variableName, type);
    }
    throw new WrongValueTypeException(variable, variableName, type);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public MutableGenericContext createChildContext() {

    return new MutableGenericContextImpl(getMapFactory(), this);
  }

}
