/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read and write access to the {@link #isMovable() movable} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteMovable extends AttributeReadMovable {

  /**
   * This method sets the {@link #isMovable() movable} attribute.
   * 
   * @param movable is the new value of {@link #isMovable()}.
   */
  void setMovable(boolean movable);

}
