/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedOneArgMode;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorDependencies;
import net.sf.mmm.util.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorMethod;
import net.sf.mmm.util.reflect.api.AccessFailedException;
import net.sf.mmm.util.reflect.api.InvocationFailedException;

/**
 * This is the implementation of the {@link PojoPropertyAccessorIndexedOneArg} interface for accessing a
 * {@link Method}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoPropertyAccessorIndexedOneArgMethod extends AbstractPojoPropertyAccessorMethod implements
    PojoPropertyAccessorIndexedOneArg {

  /** @see #getMode() */
  private final PojoPropertyAccessorIndexedOneArgMode mode;

  /**
   * {@code false} if index is first argument, {@code true} if second.
   */
  private final boolean inverted;

  /**
   * The constructor.
   * 
   * @param propertyName is the {@link #getName() name} of the property.
   * @param propertyType is the {@link #getPropertyType() generic type} of the property.
   * @param mode is the {@link #getMode() mode} of access.
   * @param descriptor is the descriptor this accessor is intended for.
   * @param dependencies are the {@link PojoDescriptorDependencies} to use.
   * @param method is the {@link #getMethod() method} to access.
   * @param inverted - {@code false} if the index is first {@code method}-argument,
   *        {@code true} if it is the second argument.
   */
  public PojoPropertyAccessorIndexedOneArgMethod(String propertyName, Type propertyType,
      PojoPropertyAccessorIndexedOneArgMode mode, PojoDescriptor<?> descriptor,
      PojoDescriptorDependencies dependencies, Method method, boolean inverted) {

    super(propertyName, propertyType, descriptor, dependencies, method);
    this.mode = mode;
    this.inverted = inverted;
  }

  @Override
  public Object invoke(Object pojoInstance, int index, Object item) {

    try {
      Integer i = Integer.valueOf(index);
      if (this.inverted) {
        return getMethod().invoke(pojoInstance, item, i);
      } else {
        return getMethod().invoke(pojoInstance, i, item);
      }
    } catch (IllegalAccessException e) {
      throw new AccessFailedException(e, getMethod());
    } catch (InvocationTargetException e) {
      throw new InvocationFailedException(e, getMethod(), pojoInstance);
    }
  }

  @Override
  public PojoPropertyAccessorIndexedOneArgMode getMode() {

    return this.mode;
  }

}
