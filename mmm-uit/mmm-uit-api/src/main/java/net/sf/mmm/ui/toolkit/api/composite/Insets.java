/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.composite;

/**
 * This class is a simple container for spacings of a border.
 * 
 * @see java.awt.Insets
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class Insets {

  /** no spacing at all */
  public static final Insets NO_SPACE = new Insets(0, 0, 0, 0);

  /** 2 pixel spacing in all directions */
  public static final Insets SMALL_SPACE = new Insets(2, 2, 2, 2);

  /** 2 pixel spacing in all directions */
  public static final Insets SMALL_SPACE_HORIZONTAL = new Insets(2, 0, 2, 0);

  /** the spacing on the left in pixels */
  public final int left;

  /** the spacing at the top in pixels */
  public final int top;

  /** the spacing on the right in pixels */
  public final int right;

  /** the spacing at the bottom in pixels */
  public final int bottom;

  /**
   * The constructor.
   * 
   * @param spaceLeft
   *        is the spacing on the left in pixels
   * @param spaceTop
   *        is the spacing at the top in pixels
   * @param spaceRight
   *        is the spacing on the right in pixels
   * @param spaceBottom
   *        is the spacing at the bottom in pixels
   */
  public Insets(int spaceLeft, int spaceTop, int spaceRight, int spaceBottom) {

    super();
    this.left = spaceLeft;
    this.top = spaceTop;
    this.right = spaceRight;
    this.bottom = spaceBottom;
  }

  /**
   * This methods gets the swapped insets. TODO
   * 
   * @return the swapped insets.
   */
  public Insets getSwapped() {

    return new Insets(this.top, this.left, this.bottom, this.right);
  }

  /**
   * This method gets the sum of the {@link #left} and {@link #right} space.
   * 
   * @return the horizontal space.
   */
  public int getHorizontalSpace() {

    return this.left + this.right;
  }

  /**
   * This method gets the sum of the {@link #top} and {@link #bottom} space.
   * 
   * @return the vertical space.
   */
  public int getVerticalSpace() {

    return this.top + this.bottom;
  }

}
