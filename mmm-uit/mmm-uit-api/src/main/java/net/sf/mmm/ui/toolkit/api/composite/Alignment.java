/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.composite;

/**
 * This enum contains the available values for alignment of a
 * {@link net.sf.mmm.ui.toolkit.api.UIComponent component}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public enum Alignment {

  /**
   * the component will be horizontally and vertically centered.<br>
   * 000<br>
   * 0x0<br>
   * 000<br>
   */
  CENTER,

  /**
   * the component will be located at the top and horizontally centered.<br>
   * 0x0<br>
   * 000<br>
   * 000<br>
   */
  TOP,

  /**
   * the component will be located at the bottom and horizontally centered.<br>
   * 000<br>
   * 000<br>
   * 0x0<br>
   */
  BOTTOM,

  /**
   * the component will be located at the left and vertically centered.<br>
   * 000<br>
   * x00<br>
   * 000<br>
   */
  LEFT,

  /**
   * the component will be located at the right and vertically centered.<br>
   * 000<br>
   * 00x<br>
   * 000<br>
   */
  RIGHT,

  /**
   * the component will be located at the right and vertically centered.<br>
   * x00<br>
   * 000<br>
   * 000<br>
   */
  TOP_LEFT,

  /**
   * the component will be located at the right and vertically centered.<br>
   * 00x<br>
   * 000<br>
   * 000<br>
   */
  TOP_RIGHT,

  /**
   * the component will be located at the right and vertically centered.<br>
   * 000<br>
   * 000<br>
   * x00<br>
   */
  BOTTOM_LEFT,

  /**
   * the component will be located at the right and vertically centered.<br>
   * 000<br>
   * 000<br>
   * 00x<br>
   */
  BOTTOM_RIGHT;

  /**
   * This method extracts the vertical part of the alignment.
   * 
   * @return the vertical alignment as one of {@link #TOP}, {@link #CENTER}
   *         or {@link #BOTTOM}.
   */
  public Alignment getVerticalPart() {

    if ((this == TOP_LEFT) || (this == TOP) || (this == TOP_RIGHT)) {
      return TOP;
    } else if ((this == LEFT) || (this == CENTER) || (this == RIGHT)) {
      return CENTER;
    } else {
      return BOTTOM;
    }
  }

  /**
   * This method extracts the horizontal part of the alignment.
   * 
   * @return the vertical alignment as one of {@link #LEFT}, {@link #CENTER}
   *         or {@link #RIGHT}.
   */
  public Alignment getHorizontalPart() {

    if ((this == TOP_LEFT) || (this == LEFT) || (this == BOTTOM_LEFT)) {
      return LEFT;
    } else if ((this == TOP) || (this == CENTER) || (this == BOTTOM)) {
      return CENTER;
    } else {
      return RIGHT;
    }
  }

  /**
   * This method extracts the horizontal or vertical part of this alignment.
   * 
   * @param orientation
   *        is the orientation of the requested part.
   * @return {@link #getHorizontalPart()} if orientation is
   *         {@link Orientation#HORIZONTAL}, {@link #getVerticalPart()}
   *         otherwise.
   */
  public Alignment getPart(Orientation orientation) {

    if (orientation == Orientation.HORIZONTAL) {
      return getHorizontalPart();
    } else {
      return getVerticalPart();
    }
  }

  /**
   * This method gets the inverse alignment.
   * 
   * @return the inverse alignment. E.g. {@link #BOTTOM_RIGHT} for
   *         {@link #TOP_LEFT}. Returns itself if {@link #CENTER}.
   */
  public Alignment getMirrored() {

    switch (this) {
      case TOP_LEFT:
        return BOTTOM_RIGHT;
      case TOP:
        return BOTTOM;
      case TOP_RIGHT:
        return BOTTOM_LEFT;
      case LEFT:
        return RIGHT;
      case CENTER:
        return CENTER;
      case RIGHT:
        return LEFT;
      case BOTTOM_LEFT:
        return TOP_RIGHT;
      case BOTTOM:
        return TOP;
      case BOTTOM_RIGHT:
        return TOP_LEFT;
      default :
        throw new IllegalStateException();
    }
  }

}
