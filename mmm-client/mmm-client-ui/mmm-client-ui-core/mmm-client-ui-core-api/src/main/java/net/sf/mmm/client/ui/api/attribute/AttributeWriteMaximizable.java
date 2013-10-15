/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read and write access to the {@link #isMaximizable() maximizable} attribute of an
 * object (e.g. window).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteMaximizable extends AttributeReadMaximizable {

  /**
   * This method set the {@link #isMaximizable() maximizable} state of this object.
   * 
   * @see #isMaximizable()
   * 
   * @param maximizable - <code>true</code> if a (icon) button shall be available to (un)maximize the object
   *        (window), <code>false</code> otherwise.
   */
  void setMaximizable(boolean maximizable);

}
