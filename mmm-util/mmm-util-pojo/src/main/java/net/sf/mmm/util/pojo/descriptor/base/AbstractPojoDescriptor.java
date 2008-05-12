/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base;

import java.util.Collection;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedOneArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorTwoArgMode;
import net.sf.mmm.util.pojo.descriptor.impl.PojoPropertyDescriptorImpl;
import net.sf.mmm.util.reflect.GenericType;

/**
 * This is the abstract base implementation of the {@link PojoDescriptor}
 * interface.
 * 
 * @param <POJO> is the templated type of the {@link #getPojoClass() POJO}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPojoDescriptor<POJO> implements PojoDescriptor<POJO> {

  /** @see #getPojoClass() */
  private final Class<POJO> pojoClass;

  /** @see #getPojoType() */
  private final GenericType pojoType;

  /**
   * The constructor.
   * 
   * @param pojoClass is the {@link #getPojoClass() pojo-class}.
   * @param pojoType is the {@link #getPojoType() pojo-type}.
   */
  public AbstractPojoDescriptor(Class<POJO> pojoClass, GenericType pojoType) {

    this.pojoClass = pojoClass;
    this.pojoType = pojoType;
  }

  /**
   * {@inheritDoc}
   */
  public Class<POJO> getPojoClass() {

    return this.pojoClass;
  }

  /**
   * {@inheritDoc}
   */
  public GenericType getPojoType() {

    return this.pojoType;
  }

  /**
   * {@inheritDoc}
   */
  public abstract Collection<? extends AbstractPojoPropertyDescriptor> getPropertyDescriptors();

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
  public <ACCESSOR extends PojoPropertyAccessor> ACCESSOR getAccessor(String property,
      PojoPropertyAccessorMode<ACCESSOR> mode) {

    return getAccessor(property, mode, false);
  }

  /**
   * {@inheritDoc}
   */
  public Object getProperty(POJO pojoInstance, String property) {

    PojoProperty pojoProperty = new PojoProperty(property);
    if (pojoProperty.getIndex() != null) {
      return getAccessor(pojoProperty.getName(), PojoPropertyAccessorIndexedNonArgMode.GET_INDEXED,
          true).invoke(pojoInstance, pojoProperty.getIndex().intValue());
    } else if (pojoProperty.getKey() != null) {
      return getAccessor(pojoProperty.getName(), PojoPropertyAccessorOneArgMode.GET_MAPPED, true)
          .invoke(pojoInstance, pojoProperty.getKey());
    } else {
      return getAccessor(property, PojoPropertyAccessorNonArgMode.GET, true).invoke(pojoInstance);
    }
  }

  /**
   * {@inheritDoc}
   */
  public Object setProperty(POJO pojoInstance, String propertyName, Object value) {

    PojoProperty property = new PojoProperty(propertyName);
    if (property.getIndex() != null) {
      return getAccessor(property.getName(), PojoPropertyAccessorIndexedOneArgMode.SET_INDEXED,
          true).invoke(pojoInstance, property.getIndex().intValue(), value);
    } else if (property.getKey() != null) {
      return getAccessor(property.getName(), PojoPropertyAccessorTwoArgMode.SET_MAPPED, true)
          .invoke(pojoInstance, property.getKey(), value);
    } else {
      return getAccessor(propertyName, PojoPropertyAccessorOneArgMode.SET, true).invoke(
          pojoInstance, value);
    }
  }

  /**
   * {@inheritDoc}
   */
  public int getPropertySize(POJO pojoInstance, String propertyName) {

    Object result = getAccessor(propertyName, PojoPropertyAccessorNonArgMode.GET_SIZE, true)
        .invoke(pojoInstance);
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
  public Object addPropertyItem(POJO pojoInstance, String propertyName, Object item) {

    return getAccessor(propertyName, PojoPropertyAccessorOneArgMode.ADD, true).invoke(pojoInstance,
        item);
  }

  /**
   * {@inheritDoc}
   */
  public Boolean removePropertyItem(POJO pojoInstance, String propertyName, Object item) {

    Object result = getAccessor(propertyName, PojoPropertyAccessorOneArgMode.REMOVE, true).invoke(
        pojoInstance, item);
    if (result instanceof Boolean) {
      return (Boolean) result;
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public Object getPropertyItem(POJO pojoInstance, String propertyName, int index) {

    return getAccessor(propertyName, PojoPropertyAccessorIndexedNonArgMode.GET_INDEXED).invoke(
        pojoInstance, index);
  }

  /**
   * {@inheritDoc}
   */
  public Object setPropertyItem(POJO pojoInstance, String propertyName, int index, Object item) {

    return getAccessor(propertyName, PojoPropertyAccessorIndexedOneArgMode.SET_INDEXED).invoke(
        pojoInstance, index, item);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return "Descriptor for POJO " + this.pojoClass;
  }

}
