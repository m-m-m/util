/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

/**
 * This interface gives read and write access to the {@link #isMultiLine() multiLine} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAriaMultiLine extends AttributeReadAriaMultiLine {

  /**
   * This method sets the {@link #isMultiLine() multiLine} property of this object.
   * 
   * @param multiLine is the new value of {@link #isMultiLine()}.
   */
  void setMultiLine(boolean multiLine);

}
