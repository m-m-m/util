/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

/**
 * This interface gives read and write access to the {@link #getActiveDescendant() activeDescendant} attribute
 * of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAriaActiveDescendant extends AttributeReadAriaActiveDescendant {

  /**
   * This method sets the {@link #getActiveDescendant() activeDescendant} attribute of this object.
   * 
   * @param id is the ID of the {@link #getActiveDescendant() activeDescendant} or <code>null</code> for none.
   */
  void setActiveDescendant(String id);

}
