/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.context.impl;

import java.util.Map;

import net.sf.mmm.util.collection.api.MapFactory;
import net.sf.mmm.util.context.api.GenericContext;
import net.sf.mmm.util.context.api.MutableGenericContext;
import net.sf.mmm.util.context.base.AbstractMutableGenericContext;

/**
 * This is the implementation of the {@link MutableGenericContext} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class MutableGenericContextImpl extends AbstractMutableGenericContext {

  /**
   * The constructor for a root-context.
   * 
   * @param mapFactory is used to create the map for storing variables.
   */
  @SuppressWarnings("unchecked")
  public MutableGenericContextImpl(MapFactory<? extends Map> mapFactory) {

    this(mapFactory, null);
  }

  /**
   * The constructor for a {@link #createChildContext() sub-context}.
   * 
   * @param mapFactory is used to create the map for storing variables.
   * @param parentContext is the context the created one will derive from.
   */
  @SuppressWarnings("unchecked")
  public MutableGenericContextImpl(MapFactory<? extends Map> mapFactory,
      GenericContext parentContext) {

    super(mapFactory, parentContext);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public <T> T getVariable(String variableName, Class<T> type) {

    Object variable = getVariable(variableName);
    // TODO: potential conversion...
    return (T) variable;
  }

  /**
   * {@inheritDoc}
   */
  public MutableGenericContext createChildContext() {

    return new MutableGenericContextImpl(getMapFactory(), this);
  }

}
