/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.impl;

import net.sf.mmm.value.base.AbstractObjectValue;

/**
 * This is a simple implementation of the
 * {@link net.sf.mmm.value.api.MutableGenericValue} interface that uses an
 * object as internal value.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ObjectValue extends AbstractObjectValue {

  /** UID for serialization. */
  private static final long serialVersionUID = -8334640713105624373L;

  /** the actual value */
  private Object value;

  /**
   * The constructor.
   */
  public ObjectValue() {

    this(null);
  }

  /**
   * The constructor.
   * 
   * @param internalValue is the internal value object to represent.
   */
  public ObjectValue(Object internalValue) {

    super();
    this.value = internalValue;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEditable() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Object getPlainValue() {

    return this.value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setPlainValue(Object newValue) {

    this.value = newValue;
  }

}
