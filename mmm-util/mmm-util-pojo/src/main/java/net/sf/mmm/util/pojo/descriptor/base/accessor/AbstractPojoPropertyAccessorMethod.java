/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base.accessor;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * This is the abstract implementation of the
 * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor}
 * interface used to access a {@link Method}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPojoPropertyAccessorMethod extends AbstractPojoPropertyAccessor {

  /** @see #getMethod() */
  private final Method method;

  /**
   * The constructor.
   * 
   * @param propertyName is the {@link #getName() name} of the property.
   * @param propertyType is the {@link #getPropertyType() generic type} of the
   *        property.
   * @param propertyClass is the {@link #getPropertyClass() raw type} of the
   *        property.
   * @param method is the {@link #getMethod() method} to access.
   */
  public AbstractPojoPropertyAccessorMethod(String propertyName, Type propertyType,
      Class<?> propertyClass, Method method) {

    super(propertyName, propertyType, propertyClass);
    this.method = method;
  }

  /**
   * @see #getAccessibleObject()
   * 
   * @return the method to access.
   */
  protected Method getMethod() {

    return this.method;
  }

  /**
   * {@inheritDoc}
   */
  public int getModifiers() {

    return this.method.getModifiers();
  }

  /**
   * {@inheritDoc}
   */
  public AccessibleObject getAccessibleObject() {

    return this.method;
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
  public Type getReturnType() {

    return this.method.getGenericReturnType();
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getReturnClass() {

    return this.method.getReturnType();
  }

  /**
   * {@inheritDoc}
   */
  public Type[] getArgumentTypes() {

    return this.method.getGenericParameterTypes();
  }

}
