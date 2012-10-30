/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

/**
 * This interface gives read and write access to the {@link #hasPopup() hasPopup} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAriaHasPopup extends AttributeReadAriaHasPopup {

  /**
   * This method sets the {@link #hasPopup() hasPopup} property of this object.
   * 
   * @param hasPopup is the new value of {@link #hasPopup()}.
   */
  void setHasPopup(boolean hasPopup);

}
