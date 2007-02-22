/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.api;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

/**
 * A {@link PojoPropertyAccessor} gives {@link #getAccessibleObject() access} to
 * a specific {@link #getName() property} of a POJO.<br>
 * 
 * @see PojoPropertyDescriptor
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoPropertyAccessor {
  
  /**
   * This method gets {@link PojoPropertyDescriptor#getName() name} of the
   * according property.
   * 
   * @see PojoPropertyDescriptor#getName()
   * 
   * @return the property name.
   */
  String getName();

  /**
   * This method gets the mode of this accessor.
   * 
   * @return the access mode.
   */
  PojoPropertyAccessMode getAccessMode();
  
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
   * This method gets the generic component type of a list property.
   * 
   * @see Class#getComponentType()
   * @see GenericArrayType#getGenericComponentType()
   * @see ParameterizedType#getActualTypeArguments()
   * 
   * @return the component type of this property or <code>null</code> if the
   *         {@link #getPropertyType() type} is no {@link Class#isArray() array}
   *         or {@link Collection collection}.
   */
  Type getPropertyComponentType();

}
