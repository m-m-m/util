/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.reflect.pojo.api.PojoPropertyDescriptor;
import net.sf.mmm.util.reflect.pojo.api.PojoPropertyNotFoundException;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.reflect.pojo.base.AbstractPojoDescriptor;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.util.reflect.pojo.api.PojoDescriptor} interface.
 * 
 * @param <POJO> is the templated type of the {@link #getPojoType() POJO}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoDescriptorImpl<POJO> extends AbstractPojoDescriptor<POJO> {

  /** @see #getPropertyDescriptor(String) */
  private final Map<String, PojoPropertyDescriptorImpl> propertyMap;

  /** @see #getPropertyDescriptor(String) */
  private final Collection<PojoPropertyDescriptorImpl> properties;

  /**
   * The constructor.
   * 
   * @param pojoClass is the {@link #getPojoType() pojo-class}.
   */
  public PojoDescriptorImpl(Class<POJO> pojoClass) {

    super(pojoClass);
    // we do NOT want MapFactory here: no need for cache or to be thread-safe
    this.propertyMap = new HashMap<String, PojoPropertyDescriptorImpl>();
    this.properties = Collections.unmodifiableCollection(this.propertyMap.values());
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
   * {@inheritDoc}
   */
  @Override
  public <ACCESSOR extends PojoPropertyAccessor> ACCESSOR getAccessor(String propertyName,
      PojoPropertyAccessorMode<ACCESSOR> mode, boolean required) {

    PojoPropertyDescriptorImpl descriptor = this.propertyMap.get(propertyName);
    if (descriptor == null) {
      if (required) {
        throw new PojoPropertyNotFoundException(getPojoType(), propertyName);
      } else {
        return null;
      }
    }
    ACCESSOR accessor = descriptor.getAccessor(mode);
    if ((accessor == null) && required) {
      throw new PojoPropertyNotFoundException(getPojoType(), propertyName, mode);
    }
    return accessor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PojoPropertyDescriptorImpl getOrCreatePropertyDescriptor(String propertyName) {

    PojoPropertyDescriptorImpl descriptor = this.propertyMap.get(propertyName);
    if (descriptor == null) {
      descriptor = new PojoPropertyDescriptorImpl(propertyName);
      this.propertyMap.put(propertyName, descriptor);
    }
    return descriptor;
  }

}
