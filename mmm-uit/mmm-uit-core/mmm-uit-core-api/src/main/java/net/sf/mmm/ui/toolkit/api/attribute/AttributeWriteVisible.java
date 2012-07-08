/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read and write access to the {@link #isVisible() visibility} of an object.
 * 
 * @see #setVisible(boolean)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteVisible extends AttributeReadVisible {

  /**
   * This method shows or hides this object.
   * 
   * @see AttributeReadVisibleState#getVisibleState()
   * 
   * @param visible is the new visibility status of this object. If <code>true</code>, the object will be
   *        shown (and raised), if false the object will be hidden (iconified).
   */
  void setVisible(boolean visible);

}
