/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

import net.sf.mmm.util.lang.api.Orientation;

/**
 * This enum contains the available values for the filling of an object. The filling determines if the
 * {@link net.sf.mmm.client.ui.api.attribute.AttributeReadSize size} of the object will be expanded
 * horizontally and/or vertically if there is more space available.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum Filling {

  /**
   * The object will keep its {@link net.sf.mmm.client.ui.api.attribute.AttributeReadSize size}.
   */
  NONE,

  /**
   * The width of the object will be expanded while the height is kept.
   */
  HORIZONTAL,

  /**
   * The height of the object will be expanded while the width is kept.
   */
  VERTICAL,

  /**
   * Both width and height of the object will be expanded.
   */
  BOTH;

  /**
   * This method returns the mirrored filling. <br>
   * <code>({@link #NONE}.mirror == {@link #NONE}</code>
   * <code>({@link #HORIZONTAL}.mirror == {@link #VERTICAL}</code>
   * <code>({@link #VERTICAL}.mirror == {@link #HORIZONTAL}</code>
   * <code>({@link #BOTH}.mirror == {@link #BOTH}</code>
   * 
   * @return the mirrored filling.
   */
  public Filling getMirrored() {

    if (this == HORIZONTAL) {
      return VERTICAL;
    }
    if (this == VERTICAL) {
      return HORIZONTAL;
    }
    return this;
  }

  /**
   * This method extracts the horizontal or vertical part of this filling.
   * 
   * @param orientation is the orientation of the requested part.
   * @return {@link #NONE} if this filling is {@link #NONE} or orthogonal to the given orientation. The
   *         filling parallel to the orientation, otherwise.
   */
  public Filling getPart(Orientation orientation) {

    if (orientation == Orientation.HORIZONTAL) {
      if ((this == Filling.BOTH) || (this == Filling.HORIZONTAL)) {
        return Filling.HORIZONTAL;
      } else {
        return Filling.NONE;
      }
    } else {
      if ((this == Filling.BOTH) || (this == Filling.VERTICAL)) {
        return Filling.VERTICAL;
      } else {
        return Filling.NONE;
      }
    }
  }

}
