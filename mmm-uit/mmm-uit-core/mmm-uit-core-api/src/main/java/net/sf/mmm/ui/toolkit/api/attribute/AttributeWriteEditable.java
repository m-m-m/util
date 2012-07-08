/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read and write access to the {@link #isEditable() editable} attribute of an object.
 * 
 * @see AtrributeReadEditable
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteEditable extends AtrributeReadEditable {

  /**
   * This method set the editable status.
   * 
   * @param editableFlag if <code>true</code> the object will become editable, if <code>false</code> the
   *        object will become uneditable.
   */
  void setEditable(boolean editableFlag);

}
