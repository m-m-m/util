/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read and {@link #setMaximized(boolean) write} access to the
 * {@link AttributeReadMaximized#isMaximized() maximized} attribute of an object (e.g. window).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteMaximized extends AttributeReadMaximized {

  /**
   * This method (un){@link #isMaximized() maximizes} the object. If it is un-maximized, its size and position
   * will be restored to the values before it was maximized.
   * 
   * @see #isMaximized()
   * 
   * @param maximize - <code>true</code> to maximize, <code>false</code> to un-maximize.
   */
  void setMaximized(boolean maximize);

}
