/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.impl;

import net.sf.mmm.value.base.AbstractStringValue;

/**
 * This is a simple implementation of the
 * {@link net.sf.mmm.value.api.MutableGenericValue} interface that uses a
 * string as internal value.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class StringValue extends AbstractStringValue {

  /** UID for serialization. */
  private static final long serialVersionUID = 1838925287686720743L;

  /** the actual native value */
  private String value;

  /** the editable flag */
  private boolean editable;

  /**
   * The constructor.
   */
  public StringValue() {

    this(null);
  }

  /**
   * The constructor.
   * 
   * @param initialValue
   *        is the initial value.
   */
  public StringValue(String initialValue) {

    super();
    this.value = initialValue;
    this.editable = true;
  }

  /**
   * This method sets the {@link #isEditable() "editable flag"}.
   * 
   * @param editableFlag
   *        is the new status of the editable flag.
   */
  public void setEditable(boolean editableFlag) {

    this.editable = editableFlag;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEditable() {

    return this.editable;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getPlainValue() {

    return this.value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setPlainValue(String newValue) {

    this.value = newValue;
  }

}
