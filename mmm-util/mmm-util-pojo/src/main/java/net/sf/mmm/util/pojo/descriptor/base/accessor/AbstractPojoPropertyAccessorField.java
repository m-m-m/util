/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base.accessor;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorConfiguration;
import net.sf.mmm.util.reflect.GenericType;

/**
 * This is the abstract implementation of the
 * {@link net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor}
 * interface used to access a {@link Field}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPojoPropertyAccessorField extends AbstractPojoPropertyAccessor {

  /** @see #getField() */
  private final Field field;

  /**
   * The constructor.
   * 
   * @param mode is the {@link #getMode() mode} of access.
   * @param descriptor is the descriptor this accessor is intended for.
   * @param configuration is the {@link PojoDescriptorConfiguration} to use.
   * @param field is the {@link #getField() field} to access.
   */
  public AbstractPojoPropertyAccessorField(PojoPropertyAccessorMode<?> mode,
      PojoDescriptor<?> descriptor, PojoDescriptorConfiguration configuration, Field field) {

    this(field.getName(), field.getGenericType(), mode, descriptor, configuration, field);
  }

  /**
   * The constructor.
   * 
   * @param propertyName is the {@link #getName() name} of the property.
   * @param propertyType is the {@link #getPropertyType() generic type} of the
   *        property.
   * @param mode is the {@link #getMode() mode} of access.
   * @param descriptor is the descriptor this accessor is intended for.
   * @param configuration is the {@link PojoDescriptorConfiguration} to use.
   * @param field is the {@link #getField() field} to access.
   */
  public AbstractPojoPropertyAccessorField(String propertyName, Type propertyType,
      PojoPropertyAccessorMode<?> mode, PojoDescriptor<?> descriptor,
      PojoDescriptorConfiguration configuration, Field field) {

    super(propertyName, propertyType, mode, descriptor, configuration);
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
  public int getModifiers() {

    return this.field.getModifiers();
  }

  /**
   * {@inheritDoc}
   */
  public AccessibleObject getAccessibleObject() {

    return this.field;
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getDeclaringClass() {

    return this.field.getDeclaringClass();
  }

  /**
   * {@inheritDoc}
   */
  public GenericType getReturnType() {

    return getPropertyType();
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getReturnClass() {

    return getPropertyClass();
  }

}
