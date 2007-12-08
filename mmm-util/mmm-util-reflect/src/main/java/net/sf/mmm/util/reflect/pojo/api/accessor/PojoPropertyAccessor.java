/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.api.accessor;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Type;

import net.sf.mmm.util.reflect.ReflectionUtil;
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
   * This method gets the type of the object returned when this accessor is
   * <code>invoked</code>.
   * 
   * @see java.lang.reflect.Method#getGenericReturnType()
   * @see java.lang.reflect.Field#getGenericType()
   * @see net.sf.mmm.util.reflect.ReflectionUtil#toClass(Type)
   * 
   * @return the return type.
   */
  Type getReturnType();

  /**
   * This method gets the types of the arguments required to <code>invoke</code>
   * this accessor.
   * 
   * @see java.lang.reflect.Method#getGenericParameterTypes()
   * @see java.lang.reflect.Field#getGenericType()
   * @see net.sf.mmm.util.reflect.ReflectionUtil#toClass(Type)
   * 
   * @return the argument types.
   */
  Type[] getArgumentTypes();

  /**
   * This method gets the type of this property.<br>
   * For a {@link PojoPropertyAccessorNonArgMode#GET getter} this will be the
   * {@link #getReturnType() return-type} while a
   * {@link PojoPropertyAccessorOneArgMode#SET setter} typically has
   * <code>void</code> as {@link #getReturnType() return-type} and this method
   * will return the type of its argument. For mapped or indexed getters/setters
   * this method will return the item type.
   * 
   * @see #getPropertyClass()
   * @see java.beans.PropertyDescriptor#getPropertyType()
   * @see net.sf.mmm.util.reflect.ReflectionUtil#toClass(Type)
   * 
   * @return the type of this property.
   */
  Type getPropertyType();

  /**
   * This method gets the {@link #getPropertyType() type} as raw class. It is a
   * convenience method for
   * <code>{@link ReflectionUtil#INSTANCE}.{@link ReflectionUtil#toClass(Type) toClass}(accessor.{@link #getPropertyType()})</code>
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

}
