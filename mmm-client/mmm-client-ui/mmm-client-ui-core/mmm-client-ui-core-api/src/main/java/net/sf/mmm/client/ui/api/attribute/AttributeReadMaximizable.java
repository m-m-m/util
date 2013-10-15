/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to the {@link #isMaximizable() maximizable} state of an object (window).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadMaximizable {

  /**
   * This method determines if this object can be {@link AttributeWriteMaximized#setMaximized(boolean)
   * (un)maximized} by the end-user.
   * 
   * @return <code>true</code> if the object is maximizable, <code>false</code> otherwise.
   */
  boolean isMaximizable();

}
