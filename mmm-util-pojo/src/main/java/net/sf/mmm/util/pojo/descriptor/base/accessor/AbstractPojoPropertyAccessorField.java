/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base.accessor;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorDependencies;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the abstract implementation of the
 * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor} interface used to access a
 * {@link Field}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public abstract class AbstractPojoPropertyAccessorField extends AbstractPojoPropertyAccessorBase {

  /** @see #getField() */
  private final Field field;

  /**
   * The constructor.
   * 
   * @param descriptor is the descriptor this accessor is intended for.
   * @param dependencies are the {@link PojoDescriptorDependencies} to use.
   * @param field is the {@link #getField() field} to access.
   */
  public AbstractPojoPropertyAccessorField(PojoDescriptor<?> descriptor, PojoDescriptorDependencies dependencies,
      Field field) {

    this(field.getName(), field.getGenericType(), descriptor, dependencies, field);
  }

  /**
   * The constructor.
   * 
   * @param propertyName is the {@link #getName() name} of the property.
   * @param propertyType is the {@link #getPropertyType() generic type} of the property.
   * @param descriptor is the descriptor this accessor is intended for.
   * @param dependencies are the {@link PojoDescriptorDependencies} to use.
   * @param field is the {@link #getField() field} to access.
   */
  public AbstractPojoPropertyAccessorField(String propertyName, Type propertyType, PojoDescriptor<?> descriptor,
      PojoDescriptorDependencies dependencies, Field field) {

    super(propertyName, propertyType, descriptor, dependencies);
    this.field = field;
  }

  /**
   * @see #getAccessibleObject()
   * 
   * @return the field to access.
   */
  protected Field getField() {

    return this.field;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getModifiers() {

    return this.field.getModifiers();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AccessibleObject getAccessibleObject() {

    return this.field;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getAccessibleObjectName() {

    return this.field.getName();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getDeclaringClass() {

    return this.field.getDeclaringClass();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericType<?> getReturnType() {

    return getPropertyType();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getReturnClass() {

    return getPropertyClass();
  }

}
