/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.pojo.descriptor.base.AbstractPojoPropertyDescriptor;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyDescriptorImpl extends AbstractPojoPropertyDescriptor implements
    PropertyDescriptor {

  /** @see #getAccessor(PojoPropertyAccessorMode) */
  private final Map<PojoPropertyAccessorMode<?>, PojoPropertyAccessor> accessorMap;

  /** @see #getAccessors() */
  private final Collection<? extends PojoPropertyAccessor> accessors;

  /**
   * The constructor.
   * 
   * @param propertyName is the {@link #getName() name} of the property.
   */
  public PojoPropertyDescriptorImpl(String propertyName) {

    super(propertyName);
    this.accessorMap = new HashMap<PojoPropertyAccessorMode<?>, PojoPropertyAccessor>();
    this.accessors = Collections.unmodifiableCollection(this.accessorMap.values());
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public <ACCESSOR extends PojoPropertyAccessor> ACCESSOR getAccessor(
      PojoPropertyAccessorMode<ACCESSOR> mode) {

    return (ACCESSOR) this.accessorMap.get(mode);
  }

  /**
   * {@inheritDoc}
   */
  public Collection<? extends PojoPropertyAccessor> getAccessors() {

    return this.accessors;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PojoPropertyAccessor putAccessor(PojoPropertyAccessor accessor) {

    PojoPropertyAccessorMode<?> mode = accessor.getMode();
    return this.accessorMap.put(mode, accessor);
  }

  /**
   * {@inheritDoc}
   */
  public boolean hasConstraints() {

    // TODO Auto-generated method stub
    return false;
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getElementClass() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public Set<ConstraintDescriptor<?>> getConstraintDescriptors() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public ConstraintFinder findConstraints() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isCascaded() {

    // TODO Auto-generated method stub
    return false;
  }

  /**
   * {@inheritDoc}
   */
  public String getPropertyName() {

    // TODO Auto-generated method stub
    return null;
  }

}
