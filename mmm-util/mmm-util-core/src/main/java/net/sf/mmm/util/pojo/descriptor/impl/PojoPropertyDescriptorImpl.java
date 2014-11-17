/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorMode;
import net.sf.mmm.util.pojo.descriptor.base.AbstractPojoPropertyDescriptor;

/**
 * This is the implementation of the {@link net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor}
 * interface.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public class PojoPropertyDescriptorImpl extends AbstractPojoPropertyDescriptor {
	
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
   * The constructor connecting the propertydes
   * @param field
   */
  public PojoPropertyDescriptorImpl(Field field) {
	  
	super(field);
	this.accessorMap = new HashMap<>();
	this.accessors = Collections.unmodifiableCollection(this.accessorMap.values());
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public <ACCESSOR extends PojoPropertyAccessor> ACCESSOR getAccessor(PojoPropertyAccessorMode<ACCESSOR> mode) {

    return (ACCESSOR) this.accessorMap.get(mode);
  }

  /**
   * {@inheritDoc}
   */
  @Override
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

}
