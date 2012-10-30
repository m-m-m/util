/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

/**
 * This interface gives read and write access to the {@link #getGrabbed() grabbed} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAriaGrabbed extends AttributeReadAriaGrabbed {

  /**
   * This method sets the {@link #getGrabbed() grabbed} state of this object.
   * 
   * @param grabbed is the new value of {@link #getGrabbed()}. May be <code>null</code>.
   */
  void setGrabbed(Boolean grabbed);

}
