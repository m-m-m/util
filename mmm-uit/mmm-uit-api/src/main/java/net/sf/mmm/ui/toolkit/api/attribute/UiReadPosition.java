/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the position of an
 * {@link net.sf.mmm.ui.toolkit.api.UiObject object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiReadPosition {

  /**
   * This method gets the position of this object on the x-axis (horizontal).
   * 
   * @return the x position.
   */
  int getX();

  /**
   * This method gets the position of this object on the y-axis (vertical).
   * 
   * @return the y position.
   */
  int getY();

}
