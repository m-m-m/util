/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

/**
 * This interface gives read and write access to the {@link #getSelected() selected} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAriaSelected extends AttributeReadAriaSelected {

  /**
   * This method sets the {@link #getSelected() selected} state of this object.
   * 
   * @param selected is the new value of {@link #getSelected()}.
   */
  void setSelected(Boolean selected);

}
