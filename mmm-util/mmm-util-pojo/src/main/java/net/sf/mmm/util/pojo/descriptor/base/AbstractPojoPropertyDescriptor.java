/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base;

import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor;

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
   * This method puts the given <code>accessor</code> into this
   * property-descriptor.<br>
   * <b>ATTENTION:</b><br>
   * Be careful NOT to accidently replace existing {@link PojoPropertyAccessor
   * accessors}.
   * 
   * @see #getAccessor(net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorMode)
   * 
   * @param accessor is the accessor to add.
   * @return the {@link PojoPropertyAccessor} with the same
   *         {@link PojoPropertyAccessor#getMode() mode} as the given
   *         <code>accessor</code> that has been replaced by
   *         <code>accessor</code> or <code>null</code> if none has been
   *         replaced.
   */
  public abstract PojoPropertyAccessor putAccessor(PojoPropertyAccessor accessor);

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return "Descriptor for property " + this.name;
  }

}
