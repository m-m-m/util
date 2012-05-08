/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.api.accessor;

/**
 * This is the abstract base class for the mode of a specific {@link PojoPropertyAccessor}.
 * 
 * @see PojoPropertyAccessor#getMode()
 * @see net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor#getAccessor(PojoPropertyAccessorMode)
 * 
 * @param <ACCESSOR> is the type of the accessor
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.0
 */
public abstract class PojoPropertyAccessorMode<ACCESSOR extends PojoPropertyAccessor> {

  /** @see #getName() */
  private final String name;

  /** @see #isReading() */
  private final boolean reading;

  /**
   * The constructor.
   * 
   * @param name is the {@link #getName() name} of this mode.
   * @param reading is a flag that determines if this mode is for {@link #isReading() reading}.
   */
  PojoPropertyAccessorMode(String name, boolean reading) {

    super();
    this.name = name;
    this.reading = reading;
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
   * This method determines if this mode is for a reading {@link PojoPropertyAccessor accessor}.
   * 
   * @return <code>true</code> if this mode is for a reading {@link PojoPropertyAccessor accessor} that
   *         {@link PojoPropertyAccessor#getReturnType() returns} a property-value, <code>false</code> if this
   *         mode is for a modifying {@link PojoPropertyAccessor accessor} that expects a
   *         {@link PojoPropertyAccessor#getPropertyType() property}-value as argument.
   */
  public boolean isReading() {

    return this.reading;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.name;
  }

}
