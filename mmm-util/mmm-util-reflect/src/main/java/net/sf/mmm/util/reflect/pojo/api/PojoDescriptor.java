/* $Id$ */
package net.sf.mmm.util.reflect.pojo.api;

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
   * @param propertyName
   *        is the name of the requested property.
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
   * {@link PojoPropertyDescriptor#getReadAccess() getter}
   * {@link PojoPropertyAccessor#getMethod()}.
   * 
   * @param pojoInstance
   *        is the {@link #getPojoType() POJO} instance where to get the
   *        requested property value from.
   * @param propertyName
   *        is the {@link PojoPropertyDescriptor#getName() name} of the
   *        requested property.
   * @return the value of the requested property. It will be an instance of the
   *         {@link PojoPropertyAccessor#getRawType() type} of the according
   *         {@link PojoPropertyDescriptor#getReadAccess() getter}. Depending
   *         on the POJO, the value may be <code>null</code>.
   * @throws PojoPropertyNotFoundException
   *         if the property with the given <code>propertyName</code> was NOT
   *         {@link #getPropertyDescriptor(String) found} or is NOT
   *         {@link PojoPropertyDescriptor#getReadAccess() readable}.
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
   * using the {@link PojoPropertyDescriptor#getWriteAccess() setter}
   * {@link PojoPropertyAccessor#getMethod()}.
   * 
   * @param pojoInstance
   *        is the {@link #getPojoType() POJO} instance where to set the given
   *        property <code>value</code>.
   * @param propertyName
   *        is the {@link PojoPropertyDescriptor#getName() name} of the
   *        requested property.
   * @param value
   *        is the property value to set.
   * @throws PojoPropertyNotFoundException
   *         if the property with the given <code>propertyName</code> was NOT
   *         {@link #getPropertyDescriptor(String) found} or is NOT
   *         {@link PojoPropertyDescriptor#getWriteAccess() writeable}.
   * @throws IllegalAccessException
   *         if you do NOT have permissions the access the underlying setter
   *         method.
   * @throws InvocationTargetException
   *         if the POJO itself (the setter) throws an exception.
   */
  void setProperty(P pojoInstance, String propertyName, Object value)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException;

}
