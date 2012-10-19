/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to the {@link #isModal() modal} attribute of an object (a dialog).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadModal {

  /**
   * This method determines if this object (window) is modal. If a modal window (popup) is opened all previous
   * windows of the application are blocked until the window is closed.
   * 
   * @return <code>true</code> if modal, <code>false</code> otherwise.
   */
  boolean isModal();

}
