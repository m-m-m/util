/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

/**
 * This interface gives read and write access to the {@link #getValueNow() valueNow} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAriaValueNow extends AttributeReadAriaValueNow {

  /**
   * This method sets the {@link #getValueNow() valueNow} property of this object.
   * 
   * @param now is the new value of {@link #getValueNow()}.
   */
  void setValueNow(Double now);

}
