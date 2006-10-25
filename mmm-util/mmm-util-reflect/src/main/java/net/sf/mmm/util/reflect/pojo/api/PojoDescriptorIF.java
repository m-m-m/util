/* $Id$ */
package net.sf.mmm.util.reflect.pojo.api;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 * This interface describes the {@link PojoPropertyDescriptorIF properties} of a
 * POJO. A POJO (plain old java object) in this manner is more or less any java
 * object.<br>
 * This interface is an alternative to {@link java.beans.BeanInfo}.
 * 
 * @param
 * <P>
 * is the templated type of the {@link #getPojoType() POJO}.
 * 
 * @see PojoPropertyDescriptorIF
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PojoDescriptorIF<P> {

  /**
   * This method gets the type reflecting the POJO represented by this
   * descriptor.
   * 
   * @return the type of the according POJO.
   */
  Class<P> getPojoType();

  /**
   * This method gets the {@link PojoPropertyDescriptorIF descriptor} for the
   * property identified by the given <code>propertyName</code>.
   * 
   * @param propertyName
   *        is the name of the requested property.
   * @return the descriptor for the property identified by the given
   *         <code>propertyName</code> or <code>null</code> if no such
   *         property exists for the according {@link #getPojoType() POJO}.
   */
  PojoPropertyDescriptorIF getPropertyDescriptor(String propertyName);

  /**
   * This method gets the {@link PojoPropertyDescriptorIF descriptor}s of all
   * properties of the according {@link #getPojoType() pojo}.
   * 
   * @return a collection with all
   *         {@link PojoPropertyDescriptorIF property descriptor}s
   */
  Collection<? extends PojoPropertyDescriptorIF> getPropertyDescriptors();

  /**
   * This method gets the {@link #getPropertyDescriptor(String) property} with
   * the given <code>propertyName</code> from the given
   * <code>pojoInstance</code> using the
   * {@link PojoPropertyDescriptorIF#getReadAccess() getter}
   * {@link PojoPropertyAccessorIF#getMethod()}.
   * 
   * @param pojoInstance
   *        is the {@link #getPojoType() POJO} instance where to get the
   *        requested property value from.
   * @param propertyName
   *        is the {@link PojoPropertyDescriptorIF#getName() name} of the
   *        requested property.
   * @return the value of the requested property. It will be an instance of the
   *         {@link PojoPropertyAccessorIF#getRawType() type} of the according
   *         {@link PojoPropertyDescriptorIF#getReadAccess() getter}. Depending
   *         on the POJO, the value may be <code>null</code>.
   * @throws PojoPropertyNotFoundException
   *         if the property with the given <code>propertyName</code> was NOT
   *         {@link #getPropertyDescriptor(String) found} or is NOT
   *         {@link PojoPropertyDescriptorIF#getReadAccess() readable}.
   * @throws IllegalAccessException
   *         if you do NOT have permissions the access the underlying getter
   *         method.
   * @throws InvocationTargetException
   *         if the POJO itself (the getter) throws an exception.
   */
  Object getProperty(P pojoInstance, String propertyName) throws PojoPropertyNotFoundException,
      IllegalAccessException, InvocationTargetException;

  /**
   * This method sets the given <code>value</code> for the
   * {@link #getPropertyDescriptor(String) property} with the given
   * <code>propertyName</code> from the given <code>pojoInstance</code>
   * using the {@link PojoPropertyDescriptorIF#getWriteAccess() setter}
   * {@link PojoPropertyAccessorIF#getMethod()}.
   * 
   * @param pojoInstance
   *        is the {@link #getPojoType() POJO} instance where to set the given
   *        property <code>value</code>.
   * @param propertyName
   *        is the {@link PojoPropertyDescriptorIF#getName() name} of the
   *        requested property.
   * @param value
   *        is the property value to set.
   * @throws PojoPropertyNotFoundException
   *         if the property with the given <code>propertyName</code> was NOT
   *         {@link #getPropertyDescriptor(String) found} or is NOT
   *         {@link PojoPropertyDescriptorIF#getWriteAccess() writeable}.
   * @throws IllegalAccessException
   *         if you do NOT have permissions the access the underlying setter
   *         method.
   * @throws InvocationTargetException
   *         if the POJO itself (the setter) throws an exception.
   */
  void setProperty(P pojoInstance, String propertyName, Object value)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException;

}
