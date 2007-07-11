/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.api;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

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

  /**
   * This method sets the according property of <code>pojoInstance</code> via
   * this accessor to the given <code>value</code>.<br>
   * <b>ATTENTION:</b><br>
   * This method only makes sense if the {@link #getAccessMode() mode} of this
   * accessor is writable (NOT {@link PojoPropertyAccessMode#READ}). If
   * possible use {@link PojoDescriptor#setProperty(Object, String, Object)} or
   * {@link PojoDescriptor#addPropertyItem(Object, String, Object)}.
   * 
   * @param pojoInstance is the instance of the POJO where to set the property.
   *        Has to be an instance of the
   *        {@link PojoDescriptor#getPojoType() type} from where this accessor
   *        was created for.
   * @param value is the value of the property to set.
   * @throws IllegalAccessException if you do NOT have permissions the access
   *         the underlying getter method.
   * @throws InvocationTargetException if the POJO itself (the getter) throws an
   *         exception.
   */
  void set(Object pojoInstance, Object value) throws IllegalAccessException,
      InvocationTargetException;

  /**
   * This method gets the according property of <code>pojoInstance</code> via
   * this accessor.<br>
   * <b>ATTENTION:</b><br>
   * This method only makes sense if the {@link #getAccessMode() mode} of this
   * accessor is readable ({@link PojoPropertyAccessMode#READ}). If possible
   * use {@link PojoDescriptor#getProperty(Object, String)}.
   * 
   * @param pojoInstance is the instance of the POJO where to get the property
   *        from. Has to be an instance of the
   *        {@link PojoDescriptor#getPojoType() type} from where this accessor
   *        was created for.
   * @return the value of the property.
   * @throws IllegalAccessException if you do NOT have permissions the access
   *         the underlying getter method.
   * @throws InvocationTargetException if the POJO itself (the getter) throws an
   *         exception.
   */
  Object get(Object pojoInstance) throws IllegalAccessException, InvocationTargetException;

}
