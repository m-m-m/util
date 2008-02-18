/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.descriptor.base;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import net.sf.mmm.util.reflect.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.reflect.pojo.descriptor.api.PojoPropertyNotFoundException;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedNonArgMode;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedOneArgMode;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.reflect.pojo.descriptor.impl.PojoPropertyDescriptorImpl;

/**
 * This is the abstract base implementation of the {@link PojoDescriptor}
 * interface.
 * 
 * @param <POJO> is the templated type of the {@link #getPojoType() POJO}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPojoDescriptor<POJO> implements PojoDescriptor<POJO> {

  /** @see #getPojoType() */
  private final Class<POJO> pojoType;

  /**
   * The constructor.
   * 
   * @param pojoClass is the {@link #getPojoType() pojo-class}.
   */
  public AbstractPojoDescriptor(Class<POJO> pojoClass) {

    this.pojoType = pojoClass;
  }

  /**
   * {@inheritDoc}
   */
  public Class<POJO> getPojoType() {

    return this.pojoType;
  }

  /**
   * {@inheritDoc}
   */
  public abstract Collection<? extends AbstractPojoPropertyDescriptor> getPropertyDescriptors();

  /**
   * This method gets the accessor for the given
   * <code>{@link PojoPropertyAccessor#getMode() mode}</code> from the
   * {@link #getPropertyDescriptor(String) descriptor} with the given
   * <code>{@link net.sf.mmm.util.reflect.pojo.descriptor.api.PojoPropertyDescriptor#getName() propertyName}</code>.
   * 
   * @see #getPropertyDescriptor(String)
   * @see net.sf.mmm.util.reflect.pojo.descriptor.api.PojoPropertyDescriptor#getAccessor(PojoPropertyAccessorMode)
   * 
   * @param <ACCESSOR> is the type of the requested accessor.
   * @param propertyName is the
   *        {@link net.sf.mmm.util.reflect.pojo.descriptor.api.PojoPropertyDescriptor#getName() name}
   *        of the property to access.
   * @param mode is the {@link PojoPropertyAccessor#getMode() mode} of the
   *        requested accessor.
   * @param required - if <code>true</code> the accessor is required and an
   *        exception is thrown if NOT found.
   * @return the requested accessor or <code>null</code> if NOT found and
   *         <code>required</code> is <code>false</code>.
   * @throws PojoPropertyNotFoundException if <code>required</code> is
   *         <code>true</code> and no property named <code>propertyName</code>
   *         was found or no accessor exists for that property with the given
   *         <code>mode</code>.
   */
  public abstract <ACCESSOR extends PojoPropertyAccessor> ACCESSOR getAccessor(String propertyName,
      PojoPropertyAccessorMode<ACCESSOR> mode, boolean required)
      throws PojoPropertyNotFoundException;

  /**
   * This method gets the property-descriptor for the given
   * <code>propertyName</code>.
   * 
   * @param propertyName is the name of the requested property-descriptor.
   * @return the requested property-descriptor or <code>null</code> if NO
   *         property exists with the given <code>propertyName</code>.
   */
  public abstract PojoPropertyDescriptorImpl getOrCreatePropertyDescriptor(String propertyName);

  /**
   * {@inheritDoc}
   */
  public Object getProperty(POJO pojoInstance, String propertyName)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException {

    return getAccessor(propertyName, PojoPropertyAccessorNonArgMode.GET, true).invoke(pojoInstance);
  }

  /**
   * {@inheritDoc}
   */
  public Object setProperty(POJO pojoInstance, String propertyName, Object value)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException {

    return getAccessor(propertyName, PojoPropertyAccessorOneArgMode.SET, true).invoke(pojoInstance,
        value);
  }

  /**
   * {@inheritDoc}
   */
  public int getPropertySize(POJO pojoInstance, String propertyName)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException {

    Object result = getAccessor(propertyName, PojoPropertyAccessorNonArgMode.SIZE, true).invoke(
        pojoInstance);
    try {
      Number size = (Number) result;
      return size.intValue();
    } catch (ClassCastException e) {
      throw new IllegalStateException("Size of property '" + propertyName + "' in pojo '"
          + pojoInstance + "' is no number: " + result);
    }
  }

  /**
   * {@inheritDoc}
   */
  public Object addPropertyItem(POJO pojoInstance, String propertyName, Object item)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException {

    return getAccessor(propertyName, PojoPropertyAccessorOneArgMode.ADD, true).invoke(pojoInstance,
        item);
  }

  /**
   * {@inheritDoc}
   */
  public Object getPropertyItem(POJO pojoInstance, String propertyName, int index)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException {

    return getAccessor(propertyName, PojoPropertyAccessorIndexedNonArgMode.GET_INDEXED).invoke(
        pojoInstance, index);
  }

  /**
   * {@inheritDoc}
   */
  public Object setPropertyItem(POJO pojoInstance, String propertyName, int index, Object item)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException {

    return getAccessor(propertyName, PojoPropertyAccessorIndexedOneArgMode.SET_INDEXED).invoke(
        pojoInstance, index, item);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return "Descriptor for POJO " + this.pojoType;
  }

}
