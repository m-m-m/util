/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base.accessor;

import java.lang.reflect.Type;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorDependencies;
import net.sf.mmm.util.reflect.api.GenericType;
import net.sf.mmm.util.reflect.api.ReflectionUtil;

/**
 * This is the major base-implementation of the
 * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor} interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractPojoPropertyAccessorBase extends AbstractPojoPropertyAccessor {

  private  final String name;

  private  final GenericType<?> propertyType;

  /**
   * The constructor.
   *
   * @param propertyName is the {@link #getName() name} of the property.
   * @param propertyType is the {@link #getPropertyType() generic type} of the property.
   * @param descriptor is the descriptor this accessor is intended for.
   * @param dependencies are the {@link PojoDescriptorDependencies} to use.
   */
  public AbstractPojoPropertyAccessorBase(String propertyName, Type propertyType, PojoDescriptor<?> descriptor,
      PojoDescriptorDependencies dependencies) {

    super();
    this.name = propertyName;
    ReflectionUtil util = dependencies.getReflectionUtil();
    this.propertyType = util.createGenericType(propertyType, descriptor.getPojoType());
  }

  @Override
  public String getName() {

    return this.name;
  }

  @Override
  public GenericType<?> getPropertyType() {

    return this.propertyType;
  }

}
