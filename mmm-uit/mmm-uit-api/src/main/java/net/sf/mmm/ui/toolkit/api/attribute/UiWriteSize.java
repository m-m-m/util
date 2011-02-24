/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This is the interface for an object of the UI framework that can be sized.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWriteSize extends UiReadSize {

  /**
   * This method sets the size of this object.
   * 
   * @param width is the new width of the object.
   * @param height is the new height of the object.
   */
  void setSize(int width, int height);

  /**
   * This method determines if this object is resizeable.
   * 
   * @return <code>true</code> if the object can be resized by the user.
   *         <code>false</code> if the {@link #setSize(int, int)} method has no
   *         effect.
   */
  boolean isResizeable();

}
