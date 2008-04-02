/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedNonArgMode;
import net.sf.mmm.util.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorMethod;
import net.sf.mmm.util.reflect.AccessFailedException;
import net.sf.mmm.util.reflect.InvocationFailedException;

/**
 * This is the implementation of the {@link PojoPropertyAccessorIndexedNonArg}
 * interface for accessing a {@link Method}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorIndexedNonArgMethod extends AbstractPojoPropertyAccessorMethod
    implements PojoPropertyAccessorIndexedNonArg {

  /** @see #getMode() */
  private final PojoPropertyAccessorIndexedNonArgMode mode;

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
  public PojoPropertyAccessorIndexedNonArgMethod(String propertyName, Type propertyType,
      Class<?> propertyClass, Method method, PojoPropertyAccessorIndexedNonArgMode mode) {

    super(propertyName, propertyType, propertyClass, method);
    this.mode = mode;
  }

  /**
   * {@inheritDoc}
   */
  public Object invoke(Object pojoInstance, int index) {

    try {
      return getMethod().invoke(pojoInstance, Integer.valueOf(index));
    } catch (IllegalAccessException e) {
      throw new AccessFailedException(e, getMethod());
    } catch (InvocationTargetException e) {
      throw new InvocationFailedException(e, getMethod(), pojoInstance);
    }
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorIndexedNonArgMode getMode() {

    return this.mode;
  }

}
