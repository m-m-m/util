/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.descriptor.impl.accessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorTwoArg;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorTwoArgMode;
import net.sf.mmm.util.reflect.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorMethod;

/**
 * This is the implementation of the {@link PojoPropertyAccessorTwoArg}
 * interface for accessing a {@link Method}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorTwoArgMethod extends AbstractPojoPropertyAccessorMethod implements
    PojoPropertyAccessorTwoArg {

  /** @see #getMode() */
  private final PojoPropertyAccessorTwoArgMode mode;

  /**
   * The constructor.
   * 
   * @param propertyName is the {@link #getName() name} of the property.
   * @param propertyType is the {@link #getPropertyType() generic type} of the
   *        property.
   * @param propertyClass is the {@link #getPropertyClass() raw type} of the
   *        property.
   * @param method is the {@link #getMethod() method} to access.
   * @param mode is the {@link #getMode() mode} of access.
   */
  public PojoPropertyAccessorTwoArgMethod(String propertyName, Type propertyType,
      Class<?> propertyClass, Method method, PojoPropertyAccessorTwoArgMode mode) {

    super(propertyName, propertyType, propertyClass, method);
    this.mode = mode;
  }

  /**
   * {@inheritDoc}
   */
  public Object invoke(Object pojoInstance, Object argument1, Object argument2)
      throws IllegalAccessException, InvocationTargetException {

    return getMethod().invoke(pojoInstance, argument1, argument2);
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorTwoArgMode getMode() {

    return this.mode;
  }

}
