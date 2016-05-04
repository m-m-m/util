/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorTwoArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorTwoArgMode;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorDependencies;
import net.sf.mmm.util.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorMethod;
import net.sf.mmm.util.reflect.api.AccessFailedException;
import net.sf.mmm.util.reflect.api.InvocationFailedException;

/**
 * This is the implementation of the {@link PojoPropertyAccessorTwoArg} interface for accessing a {@link Method}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoPropertyAccessorTwoArgMethod extends AbstractPojoPropertyAccessorMethod
    implements PojoPropertyAccessorTwoArg {

  private final PojoPropertyAccessorTwoArgMode mode;

  /**
   * The constructor.
   *
   * @param propertyName is the {@link #getName() name} of the property.
   * @param propertyType is the {@link #getPropertyType() generic type} of the property.
   * @param mode is the {@link #getMode() mode} of access.
   * @param descriptor is the descriptor this accessor is intended for.
   * @param dependencies are the {@link PojoDescriptorDependencies} to use.
   * @param method is the {@link #getMethod() method} to access.
   */
  public PojoPropertyAccessorTwoArgMethod(String propertyName, Type propertyType,
      PojoPropertyAccessorTwoArgMode mode, PojoDescriptor<?> descriptor, PojoDescriptorDependencies dependencies,
      Method method) {

    super(propertyName, propertyType, descriptor, dependencies, method);
    this.mode = mode;
  }

  @Override
  public Object invoke(Object pojoInstance, Object argument1, Object argument2) {

    try {
      return getMethod().invoke(pojoInstance, argument1, argument2);
    } catch (IllegalAccessException e) {
      throw new AccessFailedException(e, getMethod());
    } catch (InvocationTargetException e) {
      throw new InvocationFailedException(e, getMethod(), pojoInstance);
    }
  }

  @Override
  public PojoPropertyAccessorTwoArgMode getMode() {

    return this.mode;
  }

}
