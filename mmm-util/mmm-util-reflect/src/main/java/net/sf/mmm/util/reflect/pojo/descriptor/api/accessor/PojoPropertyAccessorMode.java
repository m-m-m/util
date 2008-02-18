/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.descriptor.api.accessor;

/**
 * This is the abstract base class for the mode of a specific
 * {@link PojoPropertyAccessor}.
 * 
 * @see PojoPropertyAccessor#getMode()
 * @see net.sf.mmm.util.reflect.pojo.descriptor.api.PojoPropertyDescriptor#getAccessor(PojoPropertyAccessorMode)
 * 
 * @param <ACCESSOR> is the type of the accessor
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class PojoPropertyAccessorMode<ACCESSOR extends PojoPropertyAccessor> {

  /** @see #getName() */
  private final String name;

  /**
   * The constructor.
   * 
   * @param name is the {@link #getName() name} of this mode.
   */
  PojoPropertyAccessorMode(String name) {

    super();
    this.name = name;
  }

  /**
   * This method gets the name of this mode.
   * 
   * @return the name.
   */
  public String getName() {

    return this.name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.name;
  }

}
