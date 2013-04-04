/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read and write access to the {@link #setPosition(double, double) position} of an object
 * (e.g. a window).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWritePosition extends AttributeReadPosition {

  /**
   * This method sets the position of the object.
   * 
   * @param x is the {@link #getPositionX() position on the x-axsis} (horizontal).
   * @param y is the {@link #getPositionY() position on the y-axsis} (vertical).
   */
  void setPosition(double x, double y);

}
