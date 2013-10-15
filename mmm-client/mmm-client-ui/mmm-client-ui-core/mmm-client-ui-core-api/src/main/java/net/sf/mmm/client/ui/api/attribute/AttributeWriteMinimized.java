/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This is the interface for an object (window) that can be (un)minimized.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteMinimized extends AttributeReadMinimized {

  /**
   * This method (un)minimizes the object (window). If it is minimized it will be hidden from the screen
   * (iconified). Depending on the underlying implementation (and maybe the OS) an icon will be placed on the
   * desktop or in the task-bar that represents the window. If it is unminimized, its will be shown again with
   * the same size and position as before it was minimized.
   * 
   * @param minimize - if <code>true</code>, the frame will be minimized (iconified), else it will be
   *        unminimized.
   */
  void setMinimized(boolean minimize);

}
