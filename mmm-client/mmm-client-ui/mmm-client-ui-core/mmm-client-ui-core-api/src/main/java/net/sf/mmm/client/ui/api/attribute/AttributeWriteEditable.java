/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read and write access to the {@link #isEditable() editable} attribute of an object.
 * 
 * @see AttributeReadEditable
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteEditable extends AttributeReadEditable {

  /**
   * This method set the {@link #isEditable() editable} status.
   * 
   * @param editableFlag if <code>true</code> the object will become editable, if <code>false</code> the
   *        object will become un-editable.
   */
  void setEditable(boolean editableFlag);

}
