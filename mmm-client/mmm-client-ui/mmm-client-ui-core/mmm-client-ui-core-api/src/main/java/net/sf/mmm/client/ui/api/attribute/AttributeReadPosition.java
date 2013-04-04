/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

/**
 * This interface gives read access to the {@link #getPositionX() position} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadPosition {

  /**
   * This method gets the pixel position of the left border object on the x-axis (horizontal).
   * 
   * @see #getPositionY()
   * 
   * @return the x position.
   */
  double getPositionX();

  /**
   * This method gets the pixel position of the top border of this object on the y-axis (vertical).
   * 
   * @see #getPositionX()
   * 
   * @return the y position.
   */
  double getPositionY();

}
