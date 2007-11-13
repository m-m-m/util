/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.base;

import net.sf.mmm.util.pojo.api.PojoPropertyAccessMode;
import net.sf.mmm.util.pojo.api.PojoPropertyDescriptor;

/**
 * This is the implementation of the {@link PojoPropertyDescriptor} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyDescriptorImpl implements PojoPropertyDescriptor {

  /** @see #getName() */
  private final String name;

  /** @see PojoPropertyAccessMode#READ */
  private AbstractPojoPropertyAccessor read;

  /** @see PojoPropertyAccessMode#WRITE */
  private AbstractPojoPropertyAccessor write;

  /** @see PojoPropertyAccessMode#ADD */
  private AbstractPojoPropertyAccessor add;

  /** @see PojoPropertyAccessMode#REMOVE */
  private AbstractPojoPropertyAccessor remove;

  /**
   * The constructor.
   * 
   * @param propertyName is the {@link #getName() name} of the property.
   */
  public PojoPropertyDescriptorImpl(String propertyName) {

    super();
    this.name = propertyName;
    this.read = null;
    this.write = null;
    this.add = null;
    this.remove = null;
  }

  /**
   * This method gets the programmatic (technical) name of this property.
   * 
   * @see java.beans.PropertyDescriptor#getName()
   * 
   * @return the property name.
   */
  public String getName() {

    return this.name;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractPojoPropertyAccessor getAccessor(PojoPropertyAccessMode mode) {

    switch (mode) {
      case READ:
        return this.read;
      case WRITE:
        return this.write;
      case ADD:
        return this.add;
      case REMOVE:
        return this.remove;
      default :
        // actually an illegal argument...
        return null;
    }
  }

  /**
   * This method sets the given <code>accessor</code>.
   * 
   * @see #getAccessor(PojoPropertyAccessMode)
   * 
   * @param accessor is the accessor to set.
   */
  protected void setAccessor(AbstractPojoPropertyAccessor accessor) {

    switch (accessor.getAccessMode()) {
      case READ:
        this.read = accessor;
        break;
      case WRITE:
        this.write = accessor;
        break;
      case ADD:
        this.add = accessor;
        break;
      case REMOVE:
        this.remove = accessor;
        break;
      default :
        // actually an illegal argument...
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
  
    return "Descriptor for property " + this.name;
  }
  
}
