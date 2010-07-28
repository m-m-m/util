/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.context.impl;

import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.collection.api.MapFactory;
import net.sf.mmm.util.collection.base.HashMapFactory;
import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.context.api.GenericContextFactory;
import net.sf.mmm.util.context.api.MutableGenericContext;

/**
 * This is the implementation of the {@link GenericContextFactory}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
@Singleton
@Named
public class GenericContextFactoryImpl extends AbstractLoggable implements GenericContextFactory {

  /**
   * The constructor.
   */
  public GenericContextFactoryImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public MutableGenericContext createContext() {

    return createContext(HashMapFactory.INSTANCE);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("rawtypes")
  public MutableGenericContext createContext(MapFactory<? extends Map> mapFactory) {

    return new MutableGenericContextImpl(mapFactory);
  }

}
