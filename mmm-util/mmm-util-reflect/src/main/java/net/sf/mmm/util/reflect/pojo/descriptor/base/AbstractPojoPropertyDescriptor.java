/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.descriptor.base;

import net.sf.mmm.util.reflect.pojo.descriptor.api.PojoPropertyDescriptor;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessor;

/**
 * This is the abstract base implementation of the
 * {@link PojoPropertyDescriptor} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPojoPropertyDescriptor implements PojoPropertyDescriptor {

  /** @see #getName() */
  private final String name;

  /**
   * The constructor.
   * 
   * @param propertyName is the {@link #getName() name} of the property.
   */
  public AbstractPojoPropertyDescriptor(String propertyName) {

    super();
    this.name = propertyName;
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {

    return this.name;
  }

  /**
   * This method adds the given <code>accessor</code> to this
   * property-descriptor.
   * 
   * @see #getAccessor(net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorMode)
   * 
   * @param accessor is the accessor to add.
   * @return <code>false</code> if this descriptor already contains an
   *         accessor for the same {@link PojoPropertyAccessor#getMode() mode}
   *         and <code>true</code> if the given <code>accessor</code> has
   *         been added successfully.
   */
  public abstract boolean addAccessor(PojoPropertyAccessor accessor);

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return "Descriptor for property " + this.name;
  }

}
