/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.util.reflect.pojo.api.PojoPropertyDescriptor;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorMode;

/**
 * This is the implementation of the {@link PojoPropertyDescriptor} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyDescriptorImpl implements PojoPropertyDescriptor {

  /** @see #getName() */
  private final String name;

  /** @see #getAccessor(PojoPropertyAccessorMode) */
  private final Map<PojoPropertyAccessorMode<?>, PojoPropertyAccessor> accessorMap;

  /**
   * The constructor.
   * 
   * @param propertyName is the {@link #getName() name} of the property.
   */
  public PojoPropertyDescriptorImpl(String propertyName) {

    super();
    this.name = propertyName;
    this.accessorMap = new HashMap<PojoPropertyAccessorMode<?>, PojoPropertyAccessor>();
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {

    return this.name;
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
   * This method adds the given <code>accessor</code> to this
   * property-descriptor.
   * 
   * @see #getAccessor(PojoPropertyAccessorMode)
   * 
   * @param accessor is the accessor to add.
   * @return <code>false</code> if this descriptor already contains an
   *         accessor for the same {@link PojoPropertyAccessor#getMode() mode}
   *         and <code>true</code> if the given <code>accessor</code> has
   *         been added successfully.
   */
  protected boolean addAccessor(PojoPropertyAccessor accessor) {

    PojoPropertyAccessorMode<?> mode = accessor.getMode();
    if (this.accessorMap.containsKey(mode)) {
      return false;
    }
    this.accessorMap.put(mode, accessor);
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return "Descriptor for property " + this.name;
  }

}
