/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.reflect.pojo.api.PojoDescriptor;
import net.sf.mmm.util.reflect.pojo.api.PojoPropertyDescriptor;
import net.sf.mmm.util.reflect.pojo.api.PojoPropertyNotFoundException;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorIndexedNonArgMode;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorIndexedOneArgMode;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorOneArgMode;

/**
 * This is the abstract base implementation of the {@link PojoDescriptor}
 * interface.
 * 
 * @param
 * <P>
 * is the templated type of the {@link #getPojoType() POJO}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoDescriptorImpl<P> implements PojoDescriptor<P> {

  /** @see #getPojoType() */
  private final Class<P> pojoType;

  /** @see #getPropertyDescriptor(String) */
  private final Map<String, PojoPropertyDescriptorImpl> propertyMap;

  /** @see #getPropertyDescriptor(String) */
  private final Collection<PojoPropertyDescriptorImpl> properties;

  /**
   * The constructor.
   * 
   * @param pojoClass is the {@link #getPojoType() pojo-class}.
   */
  public PojoDescriptorImpl(Class<P> pojoClass) {

    this.pojoType = pojoClass;
    // we do NOT want MapFactory here: no need for cache or to be thread-safe
    this.propertyMap = new HashMap<String, PojoPropertyDescriptorImpl>();
    this.properties = Collections.unmodifiableCollection(this.propertyMap.values());
  }

  /**
   * {@inheritDoc}
   */
  public Class<P> getPojoType() {

    return this.pojoType;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyDescriptorImpl getPropertyDescriptor(String propertyName) {

    return this.propertyMap.get(propertyName);
  }

  /**
   * This method gets the {@link PojoPropertyDescriptorImpl descriptor}s of all
   * properties of the according {@link #getPojoType() pojo}.
   * 
   * @return a collection with all
   *         {@link PojoPropertyDescriptorImpl property descriptor}s
   */
  public Collection<? extends PojoPropertyDescriptor> getPropertyDescriptors() {

    return this.properties;
  }

  /**
   * {@inheritDoc}
   */
  public <ACCESSOR extends PojoPropertyAccessor> ACCESSOR getAccessor(String propertyName,
      PojoPropertyAccessorMode<ACCESSOR> mode) {

    return getAccessor(propertyName, mode, false);
  }

  /**
   * This method gets the accessor for the given
   * <code>{@link PojoPropertyAccessor#getMode() mode}</code> from the
   * {@link #getPropertyDescriptor(String) descriptor} with the given
   * <code>{@link PojoPropertyDescriptor#getName() propertyName}</code>.
   * 
   * @see #getPropertyDescriptor(String)
   * @see PojoPropertyDescriptor#getAccessor(PojoPropertyAccessorMode)
   * 
   * @param <ACCESSOR> is the type of the requested accessor.
   * @param propertyName is the {@link PojoPropertyDescriptor#getName() name} of
   *        the property to access.
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
  public <ACCESSOR extends PojoPropertyAccessor> ACCESSOR getAccessor(String propertyName,
      PojoPropertyAccessorMode<ACCESSOR> mode, boolean required) {

    PojoPropertyDescriptorImpl descriptor = this.propertyMap.get(propertyName);
    if (descriptor == null) {
      if (required) {
        throw new PojoPropertyNotFoundException(this.pojoType, propertyName);
      } else {
        return null;
      }
    }
    ACCESSOR accessor = descriptor.getAccessor(mode);
    if ((accessor == null) && required) {
      throw new PojoPropertyNotFoundException(this.pojoType, propertyName, mode);
    }
    return accessor;
  }

  /**
   * This method gets the property-descriptor for the given
   * <code>propertyName</code>.
   * 
   * @param propertyName is the name of the requested property-descriptor.
   * @return the requested property-descriptor or <code>null</code> if NO
   *         property exists with the given <code>propertyName</code>.
   */
  protected PojoPropertyDescriptorImpl getOrCreatePropertyDescriptor(String propertyName) {

    PojoPropertyDescriptorImpl descriptor = this.propertyMap.get(propertyName);
    if (descriptor == null) {
      descriptor = new PojoPropertyDescriptorImpl(propertyName);
      this.propertyMap.put(propertyName, descriptor);
    }
    return descriptor;
  }

  /**
   * {@inheritDoc}
   */
  public Object getProperty(P pojoInstance, String propertyName)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException {

    return getAccessor(propertyName, PojoPropertyAccessorNonArgMode.GET, true).invoke(pojoInstance);
  }

  /**
   * {@inheritDoc}
   */
  public Object setProperty(P pojoInstance, String propertyName, Object value)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException {

    return getAccessor(propertyName, PojoPropertyAccessorOneArgMode.SET, true).invoke(pojoInstance,
        value);
  }

  /**
   * {@inheritDoc}
   */
  public int getPropertySize(P pojoInstance, String propertyName)
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
  public Object addPropertyItem(P pojoInstance, String propertyName, Object item)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException {

    return getAccessor(propertyName, PojoPropertyAccessorOneArgMode.ADD, true).invoke(pojoInstance,
        item);
  }

  /**
   * {@inheritDoc}
   */
  public Object getPropertyItem(P pojoInstance, String propertyName, int index)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException {

    return getAccessor(propertyName, PojoPropertyAccessorIndexedNonArgMode.GET_INDEXED).invoke(
        pojoInstance, index);
  }

  /**
   * {@inheritDoc}
   */
  public Object setPropertyItem(P pojoInstance, String propertyName, int index, Object item)
      throws PojoPropertyNotFoundException, IllegalAccessException, InvocationTargetException {

    return getAccessor(propertyName, PojoPropertyAccessorIndexedOneArgMode.SET_INDEXED).invoke(
        pojoInstance, index, item);
  }

}
