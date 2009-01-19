/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base.accessor;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorConfiguration;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.api.GenericType;

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

  /** @see #getReturnType() */
  private final GenericType<?> returnType;

  /**
   * The constructor.
   * 
   * @param propertyName is the {@link #getName() name} of the property.
   * @param propertyType is the {@link #getPropertyType() generic type} of the
   *        property.
   * @param descriptor is the descriptor this accessor is intended for.
   * @param configuration is the {@link PojoDescriptorConfiguration} to use.
   * @param method is the {@link #getMethod() method} to access.
   */
  public AbstractPojoPropertyAccessorMethod(String propertyName, Type propertyType,
      PojoDescriptor<?> descriptor, PojoDescriptorConfiguration configuration, Method method) {

    super(propertyName, propertyType, descriptor, configuration);
    this.method = method;
    Type type = method.getGenericReturnType();
    // == or equals ???
    if (type == propertyType) {
      this.returnType = getPropertyType();
    } else {
      ReflectionUtil util = configuration.getReflectionUtil();
      this.returnType = util.createGenericType(type, descriptor.getPojoType());
    }
    // if (mode.isReading()) {
    // this.returnType = getPropertyType();
    // } else {
    // ReflectionUtil util = configuration.getReflectionUtil();
    // this.returnType = util.createGenericType(method.getGenericReturnType(),
    // descriptor
    // .getPojoType());
    // }
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
  public GenericType<?> getReturnType() {

    if (getMode().isReading()) {
      return getPropertyType();
    } else {
      return this.returnType;
    }
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getReturnClass() {

    if (getMode().isReading()) {
      return getPropertyClass();
    } else {
      return getReturnType().getRetrievalClass();
    }
  }

}
