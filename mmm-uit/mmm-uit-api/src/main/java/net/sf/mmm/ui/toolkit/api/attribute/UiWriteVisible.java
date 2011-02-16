/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This is the interface for an object of the UI framework that has can be made
 * visible (shown) or invisible (hidden).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UiWriteVisible {

  /**
   * This method shows or hides this object.
   * 
   * @param visible is the new visiblilty status of this object. If
   *        <code>true</code>, the object will be shown (and raised), if
   *        false the object will be hidden (iconified).
   */
  void setVisible(boolean visible);

  /**
   * This method checks if this object is visible.
   * 
   * @return <code>true</code> iff this object is visible
   */
  boolean isVisible();

}
