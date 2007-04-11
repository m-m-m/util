/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.base;

import net.sf.mmm.value.api.MutableGenericValue;

/**
 * This is a simple implementation of the
 * {@link net.sf.mmm.value.api.MutableGenericValue} interface that is always
 * {@link #isEmpty() empty}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class EmptyValue extends AbstractObjectValue {

  /** UID for serialization. */
  private static final long serialVersionUID = 4749888507801081650L;

  /** the singleton instance */
  private static final EmptyValue INSTANCE = new EmptyValue("empty");

  /** @see #toString() */
  private final String name;

  /**
   * The constructor.
   * 
   * @param valueName
   *        is the name of the value returned by {@link #toString()}.
   */
  public EmptyValue(String valueName) {

    super();
    assert (valueName != null);
    this.name = valueName;
  }

  /**
   * This method gets the singleton instance of the empty, immutable value.
   * 
   * @return the instance.
   */
  public static MutableGenericValue getInstance() {

    return INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Object getPlainValue() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setPlainValue(Object newValue) {

    throw new IllegalStateException();
  }

  /**
   * {@inheritDoc}
   */
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
  public String toString() {

    return this.name;
  }

}
