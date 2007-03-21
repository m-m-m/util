/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.impl;

/**
 * This is a simple,
 * {@link net.sf.mmm.value.base.AbstractGenericValue#isAddDefaults() immutable}
 * implementation of the {@link net.sf.mmm.value.api.GenericValue} interface
 * that uses an object as internal value.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ImmutableObjectValue extends ObjectValue {

  /** UID for serialization */
  private static final long serialVersionUID = 5962310960812151729L;

  /**
   * The constructor.
   * 
   * @param internalValue
   */
  public ImmutableObjectValue(Object internalValue) {

    super(internalValue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEditable() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isAddDefaults() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setPlainValue(Object newValue) {

  // just to get sure...
  }

}
