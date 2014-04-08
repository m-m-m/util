/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;
import net.sf.mmm.util.value.api.ComposedValueConverter;
import net.sf.mmm.util.value.api.ValueConverter;
import net.sf.mmm.util.value.base.AbstractComposedValueConverterFactory;

/**
 * This is the implementation of {@link net.sf.mmm.util.value.api.ComposedValueConverterFactory}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 4.0.0
 */
@Named
public class ComposedValueConverterFactoryImpl extends AbstractComposedValueConverterFactory {

  /** @see #setConverters(List) */
  private List<ValueConverter<?, ?>> converters;

  /** @see #getReflectionUtil() */
  private ReflectionUtil reflectionUtil;

  /**
   * The constructor.
   */
  public ComposedValueConverterFactoryImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ComposedValueConverter createConverter(boolean addDefaultConverters, List<ValueConverter<?, ?>> converterList) {

    ComposedValueConverterImpl impl = new ComposedValueConverterImpl();
    List<ValueConverter<?, ?>> actualConverters = converterList;
    if (addDefaultConverters) {
      actualConverters = new ArrayList<>(converterList);
      for (ValueConverter<?, ?> converter : this.converters) {
        actualConverters.add(converter);
      }
    }
    impl.setReflectionUtil(this.reflectionUtil);
    impl.setConverters(actualConverters);
    impl.initialize();
    return impl;
  }

  /**
   * This method gets the {@link ReflectionUtil} instance to use.
   *
   * @return the {@link ReflectionUtil} to use.
   */
  public ReflectionUtil getReflectionUtil() {

    return this.reflectionUtil;
  }

  /**
   * @param reflectionUtil is the {@link ReflectionUtil} to {@link Inject}.
   */
  @Inject
  public void setReflectionUtil(ReflectionUtil reflectionUtil) {

    getInitializationState().requireNotInitilized();
    this.reflectionUtil = reflectionUtil;
  }

  /**
   * This method injects a {@link List} of {@link ValueConverter}s to use as default.
   *
   * @param converterList is the list of converters to register.
   */
  @Inject
  public void setConverters(List<ValueConverter<?, ?>> converterList) {

    getInitializationState().requireNotInitilized();
    this.converters = new ArrayList<>(converterList.size());
    for (ValueConverter<?, ?> converter : converterList) {
      if (!(converter instanceof ComposedValueConverter)) {
        this.converters.add(converter);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.reflectionUtil == null) {
      this.reflectionUtil = ReflectionUtilImpl.getInstance();
    }
    if (getDefaultConverter() == null) {
      setDefaultConverter(DefaultComposedValueConverter.getInstance());
    }
    if (this.converters == null) {
      ComposedValueConverter defaultConverter = getDefaultConverter();
      if (defaultConverter instanceof ComposedValueConverterImpl) {
        setConverters(((ComposedValueConverterImpl) defaultConverter).getConverters());
      } else {
        throw new ResourceMissingException("converters");
      }
    }
  }

}
