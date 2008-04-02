/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base.accessor;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

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
   * @param field is the {@link #getField() field} to access.
   */
  public AbstractPojoPropertyAccessorField(Field field) {

    this(field.getName(), field.getGenericType(), field.getType(), field);
  }

  /**
   * The constructor.
   * 
   * @param propertyName is the {@link #getName() name} of the property.
   * @param propertyType is the {@link #getPropertyType() generic type} of the
   *        property.
   * @param propertyClass is the {@link #getPropertyClass() raw type} of the
   *        property.
   * @param field is the {@link #getField() field} to access.
   */
  public AbstractPojoPropertyAccessorField(String propertyName, Type propertyType,
      Class<?> propertyClass, Field field) {

    super(propertyName, propertyType, propertyClass);
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
  public Type getReturnType() {

    return this.field.getGenericType();
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getReturnClass() {

    return this.field.getType();
  }

}
