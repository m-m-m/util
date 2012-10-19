/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.util.lang.api.Orientation;

/**
 * This interface gives read and {@link #setOrientation(Orientation) write} access to the
 * {@link #getOrientation() orientation} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteOrientation extends AttributeReadOrientation {

  /**
   * This method sets the orientation to the given value.
   * 
   * @see AttributeReadOrientation#getOrientation()
   * 
   * @param orientation is the new orientation.
   */
  void setOrientation(Orientation orientation);

}
