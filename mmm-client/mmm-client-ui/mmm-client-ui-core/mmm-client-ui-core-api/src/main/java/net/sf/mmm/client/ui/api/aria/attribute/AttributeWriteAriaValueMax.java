/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

/**
 * This interface gives read and write access to the {@link #getValueMax() valueMax} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAriaValueMax extends AttributeReadAriaValueMax {

  /**
   * This method sets the {@link #getValueMax() valueMax} property of this object.
   * 
   * @param max is the new value of {@link #getValueMax()}.
   */
  void setValueMax(double max);

}
