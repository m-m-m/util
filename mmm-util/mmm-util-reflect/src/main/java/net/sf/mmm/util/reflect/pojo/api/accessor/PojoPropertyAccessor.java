/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.api.accessor;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Type;

import net.sf.mmm.util.reflect.pojo.api.PojoPropertyDescriptor;
import net.sf.mmm.util.reflect.pojo.api.attribute.PojoAttributeName;

/**
 * A {@link PojoPropertyAccessor} gives {@link #getAccessibleObject() access} to
 * a specific {@link #getName() property} of a POJO.<br>
 * 
 * @see PojoPropertyDescriptor
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract interface PojoPropertyAccessor extends PojoAttributeName {

  /**
   * This method gets the mode of this accessor.
   * 
   * @return the access mode.
   */
  PojoPropertyAccessorMode<? extends PojoPropertyAccessor> getMode();

  /**
   * This method gets the {@link AccessibleObject accessible object} used to
   * access (read/write) this property.
   * 
   * @see java.beans.PropertyDescriptor#getReadMethod()
   * @see java.lang.reflect.Method
   * @see java.lang.reflect.Constructor
   * @see java.lang.reflect.Field
   * 
   * @return the method to read this property or <code>null</code> if this
   *         property is NOT readable.
   */
  AccessibleObject getAccessibleObject();

  /**
   * This method gets the {@link java.lang.reflect.Modifier modifiers} of the
   * {@link #getAccessibleObject() accessible object}.
   * 
   * @see java.lang.reflect.Modifier
   * @see java.lang.reflect.Field#getModifiers()
   * @see java.lang.reflect.Method#getModifiers()
   * 
   * @return the modifiers of the field or method.
   */
  int getModifiers();

  /**
   * This method gets the required type to {@link #getAccessibleObject() access}
   * (read/write) this property.
   * 
   * @see #getPropertyClass()
   * @see java.beans.PropertyDescriptor#getPropertyType()
   * 
   * @return the required type to {@link #getAccessibleObject() access} this
   *         property or <code>null</code> if this property is NOT readable.
   */
  Type getPropertyType();

  /**
   * This method gets the {@link #getPropertyType() type} as raw class.
   * 
   * @return the raw type.
   */
  Class<?> getPropertyClass();

  /**
   * This method gets the class reflecting the type that declared this accessor.
   * It may be the pojo-class this accessor was created from but it may also be
   * a super-class where the {@link #getAccessibleObject() field or method} of
   * this accessor is inherited from and is NOT overridden.<br>
   * E.g. the read-accessor for the property "class" will have the
   * declaring-class {@link java.lang.Object}.
   * 
   * @return the class reflecting the type that declared this accessor.
   */
  Class<?> getDeclaringClass();

  /**
   * This method gets the generic component type of a list property.
   * 
   * @see Class#getComponentType()
   * @see java.lang.reflect.GenericArrayType#getGenericComponentType()
   * @see java.lang.reflect.ParameterizedType#getActualTypeArguments()
   * 
   * @return the component type of this property or <code>null</code> if the
   *         {@link #getPropertyType() type} is no {@link Class#isArray() array}
   *         or {@link java.util.Collection collection}.
   */
  Type getPropertyComponentType();

}
