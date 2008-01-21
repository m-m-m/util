/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.impl.accessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import net.sf.mmm.util.reflect.ReflectionUtil;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.reflect.pojo.base.accessor.AbstractPojoPropertyAccessorMethod;

/**
 * This is the implementation of the {@link PojoPropertyAccessorNonArg}
 * interface for accessing a {@link Method}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorNonArgMethod extends AbstractPojoPropertyAccessorMethod implements
    PojoPropertyAccessorNonArg {

  /** @see #getMode() */
  private final PojoPropertyAccessorNonArgMode mode;

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
  public PojoPropertyAccessorNonArgMethod(String propertyName, Type propertyType,
      Class<?> propertyClass, Method method, PojoPropertyAccessorNonArgMode mode) {

    super(propertyName, propertyType, propertyClass, method);
    this.mode = mode;
  }

  /**
   * {@inheritDoc}
   */
  public Object invoke(Object pojoInstance) throws IllegalAccessException,
      InvocationTargetException {

    return getMethod().invoke(pojoInstance, ReflectionUtil.NO_ARGUMENTS);
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorNonArgMode getMode() {

    return this.mode;
  }

}
