/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import net.sf.mmm.util.pojo.api.PojoPropertyAccessMode;
import net.sf.mmm.util.pojo.base.AbstractPojoPropertyAccessor;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.util.pojo.api.PojoPropertyAccessor} interface using a
 * {@link Field} to access the property.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FieldPojoPropertyAccessor extends AbstractPojoPropertyAccessor {

  /** @see #getAccessibleObject() */
  private final Field field;

  /**
   * The constructor
   * 
   * @param propertyField
   *        is the {@link #getAccessibleObject() method} to access the property.
   * @param accessMode
   *        is the {@link #getAccessMode() mode} of this accessor.
   */
  public FieldPojoPropertyAccessor(Field propertyField, PojoPropertyAccessMode accessMode) {

    this(propertyField.getName(), accessMode, propertyField.getGenericType(), propertyField
        .getType(), propertyField);
  }

  /**
   * The constructor
   * 
   * @param propertyName
   *        is the {@link #getName() name} of the property.
   * @param accessMode
   *        is the {@link #getAccessMode() mode} of this accessor.
   * @param propertyType
   *        is the {@link #getPropertyType() generic type} of the property.
   * @param propertyClass
   *        is the {@link #getPropertyClass() raw type} of the property.
   * @param propertyField
   *        is the {@link #getAccessibleObject() method} to access the property.
   */
  public FieldPojoPropertyAccessor(String propertyName, PojoPropertyAccessMode accessMode,
      Type propertyType, Class<?> propertyClass, Field propertyField) {

    super(propertyName, accessMode, propertyType, propertyClass);
    this.field = propertyField;
  }

  /**
   * @see net.sf.mmm.util.pojo.base.AbstractPojoPropertyAccessor#get(java.lang.Object)
   */
  @Override
  public Object get(Object pojoInstance) throws IllegalAccessException, InvocationTargetException {

    return this.field.get(pojoInstance);
  }

  /**
   * @see net.sf.mmm.util.pojo.base.AbstractPojoPropertyAccessor#set(java.lang.Object,
   *      java.lang.Object)
   */
  @Override
  public void set(Object pojoInstance, Object value) throws IllegalAccessException,
      InvocationTargetException {

    this.field.set(pojoInstance, value);
  }

  /**
   * @see net.sf.mmm.util.pojo.api.PojoPropertyAccessor#getAccessibleObject()
   */
  public Field getAccessibleObject() {

    return this.field;
  }

}
