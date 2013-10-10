/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to the {@link #isMovable() movable} attribute of an object.
 * 
 * @see AttributeWriteMovable
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadMovable {

  /**
   * This method determines if this object can be moved.
   * 
   * @return <code>true</code> if the object can be moved by the end-user (by dragging around),
   *         <code>false</code> otherwise.
   */
  boolean isMovable();

}
