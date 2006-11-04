/* $Id$ */
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

  /** UID for serialization */
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
   * @see net.sf.mmm.value.api.MutableGenericValue#isEditable()
   */
  public boolean isEditable() {

    return this.editable;
  }

  /**
   * @see net.sf.mmm.value.base.AbstractTemplatedGenericValue#getPlainValue()
   * 
   */
  @Override
  protected String getPlainValue() {

    return this.value;
  }

  /**
   * @see net.sf.mmm.value.base.AbstractTemplatedGenericValue#setPlainValue(java.lang.Object)
   * 
   */
  @Override
  protected void setPlainValue(String newValue) {

    this.value = newValue;
  }

}
