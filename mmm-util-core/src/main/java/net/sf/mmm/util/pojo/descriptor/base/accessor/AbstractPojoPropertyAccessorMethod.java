/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base.accessor;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorDependencies;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.api.ReflectionUtil;

/**
 * This is the abstract implementation of the {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor}
 * interface used to access a {@link Method}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public abstract class AbstractPojoPropertyAccessorMethod extends AbstractPojoPropertyAccessorBase {

  /** @see #getMethod() */
  private final Method method;

  /** @see #getReturnType() */
  private final GenericType<?> returnType;

  /**
   * The constructor.
   *
   * @param propertyName is the {@link #getName() name} of the property.
   * @param propertyType is the {@link #getPropertyType() generic type} of the property.
   * @param descriptor is the descriptor this accessor is intended for.
   * @param dependencies are the {@link PojoDescriptorDependencies} to use.
   * @param method is the {@link #getMethod() method} to access.
   */
  public AbstractPojoPropertyAccessorMethod(String propertyName, Type propertyType, PojoDescriptor<?> descriptor,
      PojoDescriptorDependencies dependencies, Method method) {

    super(propertyName, propertyType, descriptor, dependencies);
    this.method = method;
    Type type = method.getGenericReturnType();
    // == or equals ???
    if (type == propertyType) {
      this.returnType = getPropertyType();
    } else {
      ReflectionUtil util = dependencies.getReflectionUtil();
      this.returnType = util.createGenericType(type, descriptor.getPojoType());
    }
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
  @Override
  public int getModifiers() {

    return this.method.getModifiers();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AccessibleObject getAccessibleObject() {

    return this.method;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getAccessibleObjectName() {

    return this.method.getName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getDeclaringClass() {

    return this.method.getDeclaringClass();
  }

  /**
   * {@inheritDoc}
   */
  @Override
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
  @Override
  public Class<?> getReturnClass() {

    if (getMode().isReading()) {
      return getPropertyClass();
    } else {
      return getReturnType().getRetrievalClass();
    }
  }

}
