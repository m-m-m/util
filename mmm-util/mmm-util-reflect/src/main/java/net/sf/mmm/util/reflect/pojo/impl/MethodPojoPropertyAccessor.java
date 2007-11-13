/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import net.sf.mmm.util.reflect.ReflectionUtil;
import net.sf.mmm.util.reflect.pojo.api.PojoPropertyAccessMode;
import net.sf.mmm.util.reflect.pojo.base.AbstractPojoPropertyAccessor;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.util.reflect.pojo.api.PojoPropertyAccessor} interface using a
 * {@link Method} to access the property.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class MethodPojoPropertyAccessor extends AbstractPojoPropertyAccessor {

  /** @see #getAccessibleObject() */
  private final Method method;

  /**
   * The constructor.
   * 
   * @param propertyName is the {@link #getName() name} of the property.
   * @param accessMode is the {@link #getAccessMode() mode} of this accessor.
   * @param propertyMethod is the {@link #getAccessibleObject() method} to
   *        access the property.
   * @param propertyType is the {@link #getPropertyType() generic type} of the
   *        property.
   * @param propertyClass is the {@link #getPropertyClass() raw type} of the
   *        property.
   */
  public MethodPojoPropertyAccessor(String propertyName, PojoPropertyAccessMode accessMode,
      Type propertyType, Class<?> propertyClass, Method propertyMethod) {

    super(propertyName, accessMode, propertyType, propertyClass);
    this.method = propertyMethod;
  }

  /**
   * {@inheritDoc}
   */
  public Method getAccessibleObject() {

    return this.method;
  }

  /**
   * {@inheritDoc}
   */
  public Object get(Object pojoInstance) throws IllegalAccessException, InvocationTargetException {

    return this.method.invoke(pojoInstance, ReflectionUtil.NO_ARGUMENTS);
  }

  /**
   * {@inheritDoc}
   */
  public void set(Object pojoInstance, Object value) throws IllegalAccessException,
      InvocationTargetException {

    this.method.invoke(pojoInstance, value);
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getDeclaringClass() {
  
    return this.method.getDeclaringClass();
  }
  
  /**
   * {@inheritDoc}
   */
  public int getModifiers() {
  
    return this.method.getModifiers();
  }
  
}
