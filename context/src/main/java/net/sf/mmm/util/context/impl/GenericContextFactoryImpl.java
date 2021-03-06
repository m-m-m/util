/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.context.impl;

import java.util.Map;

import javax.inject.Inject;

import net.sf.mmm.util.collection.api.MapFactory;
import net.sf.mmm.util.context.api.GenericContextFactory;
import net.sf.mmm.util.context.api.MutableGenericContext;
import net.sf.mmm.util.context.base.AbstractGenericContextFactory;
import net.sf.mmm.util.value.api.ComposedValueConverter;
import net.sf.mmm.util.value.api.GenericValueConverter;
import net.sf.mmm.util.value.impl.DefaultComposedValueConverter;

/**
 * This is the implementation of {@link GenericContextFactory}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class GenericContextFactoryImpl extends AbstractGenericContextFactory {

  private ComposedValueConverter composedValueConverter;

  /**
   * The constructor.
   */
  public GenericContextFactoryImpl() {

    super();
  }

  /**
   * @param composedValueConverter is the composedValueConverter to set
   */
  @Inject
  public void setComposedValueConverter(ComposedValueConverter composedValueConverter) {

    getInitializationState().requireNotInitilized();
    this.composedValueConverter = composedValueConverter;
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.composedValueConverter == null) {
      this.composedValueConverter = DefaultComposedValueConverter.getInstance();
    }
  }

  @Override
  @SuppressWarnings("rawtypes")
  public MutableGenericContext createContext(MapFactory<? extends Map> mapFactory) {

    return createContext(mapFactory, this.composedValueConverter);
  }

  @Override
  @SuppressWarnings("rawtypes")
  public MutableGenericContext createContext(MapFactory<? extends Map> mapFactory,
      GenericValueConverter<Object> valueConverter) {

    return new MutableGenericContextImpl(mapFactory, valueConverter);
  }
}
