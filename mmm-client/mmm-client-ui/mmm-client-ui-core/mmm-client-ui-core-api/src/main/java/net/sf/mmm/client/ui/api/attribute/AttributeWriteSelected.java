/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This is the interface for an object of the UI framework that is selectable.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteSelected extends AttributeReadSelected {

  /**
   * This method sets the selection status of this object. <br>
   * 
   * @param selected - if <code>true</code> the object will be selected, else it will be de-selected.
   */
  void setSelected(boolean selected);

}
