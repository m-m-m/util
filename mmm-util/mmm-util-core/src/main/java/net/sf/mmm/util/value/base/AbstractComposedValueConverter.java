/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import java.lang.reflect.Type;

import javax.inject.Inject;

import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;
import net.sf.mmm.util.value.api.ComposedValueConverter;
import net.sf.mmm.util.value.api.ValueNotSetException;
import net.sf.mmm.util.value.api.WrongValueTypeException;

/**
 * This is the abstract base implementation of the
 * {@link ComposedValueConverter} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public abstract class AbstractComposedValueConverter extends AbstractGenericValueConverter<Object>
    implements ComposedValueConverter {

  /** @see #getReflectionUtil() */
  private ReflectionUtil reflectionUtil;

  /**
   * The constructor.
   */
  public AbstractComposedValueConverter() {

    super();
  }

  /**
   * This method gets the {@link ReflectionUtilImpl} instance to use.
   * 
   * @return the {@link ReflectionUtilImpl} to use.
   */
  public ReflectionUtil getReflectionUtil() {

    return this.reflectionUtil;
  }

  /**
   * @param reflectionUtil is the reflectionUtil to set
   */
  @Inject
  public void setReflectionUtil(ReflectionUtil reflectionUtil) {

    getInitializationState().requireNotInitilized();
    this.reflectionUtil = reflectionUtil;
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
  }

  /**
   * {@inheritDoc}
   */
  public final Class<Object> getSourceType() {

    return Object.class;
  }

  /**
   * {@inheritDoc}
   */
  public final Class<Object> getTargetType() {

    return Object.class;
  }

  /**
   * {@inheritDoc}
   */
  public final Object convert(Object value, Object valueSource, Class<? extends Object> targetClass) {

    return convert(value, valueSource, getReflectionUtil().createGenericType(targetClass));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  public <TARGET> TARGET convertValue(Object value, Object valueSource, Class<TARGET> targetClass)
      throws ValueNotSetException, WrongValueTypeException {

    if (value == null) {
      throw new ValueNotSetException(valueSource);
    }
    TARGET result = (TARGET) convert(value, valueSource, targetClass);
    if (result == null) {
      throw new WrongValueTypeException(value, valueSource, targetClass);
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public <TARGET> TARGET convertValue(Object value, Object valueSource, Class<TARGET> targetClass,
      Type targetType) throws ValueNotSetException, WrongValueTypeException {

    if (value == null) {
      throw new ValueNotSetException(valueSource);
    }
    GenericType<?> genericType = getReflectionUtil().createGenericType(targetType);
    TARGET result = (TARGET) convert(value, valueSource, genericType);
    if (result == null) {
      throw new WrongValueTypeException(value, valueSource, targetClass);
    }
    return result;
  }

}
