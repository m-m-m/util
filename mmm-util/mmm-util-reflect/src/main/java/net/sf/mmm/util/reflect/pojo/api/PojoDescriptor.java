/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.api;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorMode;

/**
 * This interface describes the {@link PojoPropertyDescriptor properties} of a
 * POJO. A POJO (plain old java object) in this manner is more or less any java
 * object.<br>
 * This interface is an alternative to {@link java.beans.BeanInfo}.
 * 
 * @param
 * <P>
 * is the templated type of the {@link #getPojoType() POJO}.
 * 
 * @see PojoPropertyDescriptor
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoDescriptor<P> {

  /**
   * This method gets the type reflecting the POJO represented by this
   * descriptor.
   * 
   * @return the type of the according POJO.
   */
  Class<P> getPojoType();

  /**
   * This method gets the {@link PojoPropertyDescriptor descriptor} for the
   * property identified by the given <code>propertyName</code>.
   * 
   * @param propertyName is the name of the requested property.
   * @return the descriptor for the property identified by the given
   *         <code>propertyName</code> or <code>null</code> if no such
   *         property exists for the according {@link #getPojoType() POJO}.
   */
  PojoPropertyDescriptor getPropertyDescriptor(String propertyName);

  /**
   * This method gets the {@link PojoPropertyDescriptor descriptor}s of all
   * properties of the according {@link #getPojoType() pojo}.
   * 
   * @return a collection with all
   *         {@link PojoPropertyDescriptor property descriptor}s
   */
  Collection<? extends PojoPropertyDescriptor> getPropertyDescriptors();

  /**
   * This method gets the {@link PojoPropertyAccessor accessor} for the property
   * with the given <code>propertyName</code> and for the given access
   * <code>mode</code>.
   * 
   * @param <ACCESSOR> is the type of the requested accessor.
   * @param propertyName is the {@link PojoPropertyDescriptor#getName() name} of
   *        the property.
   * @param mode is the {@link PojoPropertyAccessor#getMode() mode} of the
   *        requested accessor.
   * @return the requested accessor or <code>null</code> if NOT found (there
   *         is no property named <code>propertyName</code>, the property has
   *         no accessor for the given <code>mode</code>, etc.).
   */
  <ACCESSOR extends PojoPropertyAccessor> ACCESSOR getAccessor(String propertyName,
      PojoPropertyAccessorMode<ACCESSOR> mode);

  /**
   * This method gets the {@link #getPropertyDescriptor(String) property} with
   * the given <code>propertyName</code> from the given
   * <code>pojoInstance</code> using the
   * {@link net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArgMode#GET getter}.
   * 
   * @param pojoInstance is the {@link #getPojoType() POJO} instance where to
   *        access the property.
   * @param propertyName is the {@link PojoPropertyDescriptor#getName() name} of
   *        the property.
   * @return the value of the requested property. It will be an instance of the
   *         {@link PojoPropertyAccessor#getPropertyClass() type} of the
   *         according
   *         {@link net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArgMode#GET getter}.
   *         Depending on the POJO, the value may be <code>null</code>.
   * @throws PojoPropertyNotFoundException if the property with the given
   *         <code>propertyName</code> was NOT
   *         {@link #getPropertyDescriptor(String) found} or has no
   *         {@link net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArgMode#GET getter}.
   * @throws IllegalAccessException if you do NOT have permissions to access the
   *         underlying
   *         {@link PojoPropertyAccessor#getAccessibleObject() accessor}.
   * @throws InvocationTargetException if the POJO itself (typically the getter)
   *         throws an exception.
   */
  Object getProperty(P pojoInstance, String propertyName) throws PojoPropertyNotFoundException,
      IllegalAccessException, InvocationTargetException;

  /**
   * This method sets the given <code>value</code> for the
   * {@link #getPropertyDescriptor(String) property} with the given
   * <code>propertyName</code> from the given <code>pojoInstance</code>
   * using the
   * {@link net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorOneArgMode#SET setter}.
   * 
   * @param pojoInstance is the {@link #getPojoType() POJO} instance where to
   *        access the property.
   * @param propertyName is the {@link PojoPropertyDescriptor#getName() name} of
   *        the property.
   * @param value is the property value to set. Depending on the POJO the value
   *        may be <code>null</code>.
   * @return the result of the setter method. Will be <code>null</code> if the
   *         return type is <code>void</code> what should be the regular case.
   * @throws PojoPropertyNotFoundException if the property with the given
   *         <code>propertyName</code> was NOT
   *         {@link #getPropertyDescriptor(String) found} or has no
   *         {@link net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorOneArgMode#SET setter}.
   * @throws IllegalAccessException if you do NOT have permissions to access the
   *         underlying
   *         {@link PojoPropertyAccessor#getAccessibleObject() accessor}.
   * @throws InvocationTargetException if the POJO itself (typically the setter)
   *         throws an exception.
   */
  Object setProperty(P pojoInstance, String propertyName, Object value)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException;

  /**
   * This method gets the
   * {@link net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArgMode#SIZE size}
   * of the {@link #getPropertyDescriptor(String) property} with the given
   * <code>propertyName</code> from the given <code>pojoInstance</code>.
   * 
   * @param pojoInstance is the {@link #getPojoType() POJO} instance where to
   *        access the property.
   * @param propertyName is the {@link PojoPropertyDescriptor#getName() name} of
   *        the property.
   * @return the size of the requested property.
   * @throws PojoPropertyNotFoundException if the property with the given
   *         <code>propertyName</code> was NOT
   *         {@link #getPropertyDescriptor(String) found} or has no
   *         {@link net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArgMode#GET getter}.
   * @throws IllegalAccessException if you do NOT have permissions to access the
   *         underlying
   *         {@link PojoPropertyAccessor#getAccessibleObject() accessor}.
   * @throws InvocationTargetException if the POJO itself (typically the getter)
   *         throws an exception.
   */
  int getPropertySize(P pojoInstance, String propertyName) throws PojoPropertyNotFoundException,
      IllegalAccessException, InvocationTargetException;

  /**
   * This method adds the given <code>item</code> to the list-like
   * {@link #getPropertyDescriptor(String) property} with the given
   * <code>propertyName</code> from the given <code>pojoInstance</code>
   * using the
   * {@link net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorOneArgMode#ADD add}
   * {@link PojoPropertyDescriptor#getAccessor(net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorMode) accessor}.
   * 
   * @param pojoInstance is the {@link #getPojoType() POJO} instance where to
   *        access the property.
   * @param propertyName is the {@link PojoPropertyDescriptor#getName() name} of
   *        the property.
   * @param item is the item to add to the property.
   * @return the result of the add-method. Will be <code>null</code> if the
   *         return type is <code>void</code> what should be the regular case.
   * @throws PojoPropertyNotFoundException if the property with the given
   *         <code>propertyName</code> was NOT
   *         {@link #getPropertyDescriptor(String) found} or is NOT
   *         {@link net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorOneArgMode#ADD addable}.
   * @throws IllegalAccessException if you do NOT have permissions to access the
   *         underlying
   *         {@link PojoPropertyAccessor#getAccessibleObject() accessor}.
   * @throws InvocationTargetException if the POJO itself (the adder) throws an
   *         exception.
   */
  Object addPropertyItem(P pojoInstance, String propertyName, Object item)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException;

  /**
   * This method gets the item with the given <code>index</code> from the
   * list-like {@link #getPropertyDescriptor(String) property} with the given
   * <code>propertyName</code> of the given <code>pojoInstance</code> using
   * the
   * {@link net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorIndexedNonArgMode#GET_INDEXED indexed getter}
   * {@link PojoPropertyDescriptor#getAccessor(net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorMode) accessor}.
   * 
   * @param pojoInstance is the {@link #getPojoType() POJO} instance where to
   *        add the given property <code>item</code>.
   * @param propertyName is the {@link PojoPropertyDescriptor#getName() name} of
   *        the property.
   * @param index is the position of the requested item (see
   *        {@link java.util.List#get(int)}).
   * @return the result of the add-method. Will be <code>null</code> if the
   *         return type is <code>void</code> what should be the regular case.
   * @throws PojoPropertyNotFoundException if the property with the given
   *         <code>propertyName</code> was NOT
   *         {@link #getPropertyDescriptor(String) found} or is NOT
   *         {@link net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorOneArgMode#ADD addable}.
   * @throws IllegalAccessException if you do NOT have permissions to access the
   *         underlying
   *         {@link PojoPropertyAccessor#getAccessibleObject() accessor}.
   * @throws InvocationTargetException if the POJO itself (the adder) throws an
   *         exception.
   */
  Object getPropertyItem(P pojoInstance, String propertyName, int index)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException;

  /**
   * This method sets the given <code>item</code> at the given
   * <code>index</code> in the list-like
   * {@link #getPropertyDescriptor(String) property} with the given
   * <code>propertyName</code> of the given <code>pojoInstance</code> using
   * the
   * {@link net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorIndexedOneArgMode#SET_INDEXED indexed setter}
   * {@link PojoPropertyDescriptor#getAccessor(net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorMode) accessor}.
   * 
   * @param pojoInstance is the {@link #getPojoType() POJO} instance where to
   *        access the property.
   * @param propertyName is the {@link PojoPropertyDescriptor#getName() name} of
   *        the property.
   * @param index is the position of the item to set (see
   *        {@link java.util.List#set(int, Object)}).
   * @param item is the item to set at the given <code>index</code>.
   * @return the result of the add-method. Will be <code>null</code> if the
   *         return type is <code>void</code> what should be the regular case.
   * @throws PojoPropertyNotFoundException if the property with the given
   *         <code>propertyName</code> was NOT
   *         {@link #getPropertyDescriptor(String) found} or is NOT
   *         {@link net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorOneArgMode#ADD addable}.
   * @throws IllegalAccessException if you do NOT have permissions to access the
   *         underlying
   *         {@link PojoPropertyAccessor#getAccessibleObject() accessor}.
   * @throws InvocationTargetException if the POJO itself (the adder) throws an
   *         exception.
   */
  Object setPropertyItem(P pojoInstance, String propertyName, int index, Object item)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException;

}
