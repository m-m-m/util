/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.composite;

/**
 * This enum contains the available values for the orientation of a
 * {@link net.sf.mmm.ui.toolkit.api.UiElement component}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum Orientation {

  /**
   * A horizontal orientation means that objects are ordered from the left to
   * the right.
   */
  HORIZONTAL,

  /**
   * A vertical orientation means that objects are ordered from the top to the
   * bottom.
   */
  VERTICAL;

  /**
   * This method gets the inverse orientation.
   * 
   * @return {@link #VERTICAL} if this orientation is {@link #HORIZONTAL} and
   *         vice versa.
   */
  public Orientation getMirrored() {

    if (this == HORIZONTAL) {
      return VERTICAL;
    } else {
      return HORIZONTAL;
    }
  }

}
