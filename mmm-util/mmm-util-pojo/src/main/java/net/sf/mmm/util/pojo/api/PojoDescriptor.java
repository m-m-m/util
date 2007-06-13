/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.api;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

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
   * This method gets the {@link #getPropertyDescriptor(String) property} with
   * the given <code>propertyName</code> from the given
   * <code>pojoInstance</code> using the
   * {@link PojoPropertyAccessMode#READ read}
   * {@link PojoPropertyDescriptor#getAccessor(PojoPropertyAccessMode) accessor}
   * (e.g. getter).
   * 
   * @param pojoInstance is the {@link #getPojoType() POJO} instance where to
   *        get the requested property value from.
   * @param propertyName is the {@link PojoPropertyDescriptor#getName() name} of
   *        the property.
   * @return the value of the requested property. It will be an instance of the
   *         {@link PojoPropertyAccessor#getPropertyClass() type} of the
   *         according {@link PojoPropertyAccessMode#READ read}
   *         {@link PojoPropertyDescriptor#getAccessor(PojoPropertyAccessMode) accessor}.
   *         Depending on the POJO, the value may be <code>null</code>.
   * @throws PojoPropertyNotFoundException if the property with the given
   *         <code>propertyName</code> was NOT
   *         {@link #getPropertyDescriptor(String) found} or is NOT
   *         {@link PojoPropertyAccessMode#READ readable}.
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
   * using the {@link PojoPropertyAccessMode#WRITE write}
   * {@link PojoPropertyDescriptor#getAccessor(PojoPropertyAccessMode) accessor}
   * (e.g. setter).
   * 
   * @param pojoInstance is the {@link #getPojoType() POJO} instance where to
   *        set the given property <code>value</code>.
   * @param propertyName is the {@link PojoPropertyDescriptor#getName() name} of
   *        the property.
   * @param value is the property value to set. Depending on the POJO the value
   *        may be <code>null</code>.
   * @throws PojoPropertyNotFoundException if the property with the given
   *         <code>propertyName</code> was NOT
   *         {@link #getPropertyDescriptor(String) found} or is NOT
   *         {@link PojoPropertyAccessMode#WRITE writable}.
   * @throws IllegalAccessException if you do NOT have permissions to access the
   *         underlying
   *         {@link PojoPropertyAccessor#getAccessibleObject() accessor}.
   * @throws InvocationTargetException if the POJO itself (typically the setter)
   *         throws an exception.
   */
  void setProperty(P pojoInstance, String propertyName, Object value)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException;

  /**
   * This method adds the given <code>item</code> to the list-like
   * {@link #getPropertyDescriptor(String) property} with the given
   * <code>propertyName</code> from the given <code>pojoInstance</code>
   * using the {@link PojoPropertyAccessMode#ADD add}
   * {@link PojoPropertyDescriptor#getAccessor(PojoPropertyAccessMode) accessor}.
   * 
   * @param pojoInstance is the {@link #getPojoType() POJO} instance where to
   *        add the given property <code>item</code>.
   * @param propertyName is the {@link PojoPropertyDescriptor#getName() name} of
   *        the property.
   * @param item is the item to add to the property.
   * @throws PojoPropertyNotFoundException if the property with the given
   *         <code>propertyName</code> was NOT
   *         {@link #getPropertyDescriptor(String) found} or is NOT
   *         {@link PojoPropertyAccessMode#ADD addable}.
   * @throws IllegalAccessException if you do NOT have permissions to access the
   *         underlying
   *         {@link PojoPropertyAccessor#getAccessibleObject() accessor}.
   * @throws InvocationTargetException if the POJO itself (the adder) throws an
   *         exception.
   */
  void addPropertyItem(P pojoInstance, String propertyName, Object item)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException;

}
