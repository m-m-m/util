/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read and {@link #setMaximized(boolean) write} access to the
 * {@link AttributeReadMaximized#isMaximized() maximized} attribute of an object (window).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteMaximized extends AttributeReadMaximized {

  /**
   * This method (un)maximizes the object. If it is maximized its position will be moved to the top left
   * corner and its size will be set to the maximum size available (display for windows). If it is unmaximized
   * after it was maximized, its size and position will be restored to the values before it was maximized.
   * 
   * @param maximize - if <code>true</code>, the window will be maximized, else it will be unmaximized.
   */
  void setMaximized(boolean maximize);

}
