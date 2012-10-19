/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to the {@link #isMaximized() maximized} state of an object (window).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadMaximized {

  /**
   * This method determines if this object is maximized. In this case the size is fully expanded to cover the
   * entire screen (also called <em>full-screen</em>).
   * 
   * @return <code>true</code> if the object is maximized, <code>false</code> otherwise.
   */
  boolean isMaximized();

}
