/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

import net.sf.mmm.util.lang.api.AbstractSimpleDatatypeBase;

/**
 * This is the {@link net.sf.mmm.util.lang.api.Datatype} for a simple 2-dimensional point measured in
 * {@link LengthUnit#PIXEL pixels}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class Point extends AbstractSimpleDatatypeBase {

  /** UID for serialization. */
  private static final long serialVersionUID = -6404311020184915937L;

  /** @see #getX() */
  private int x;

  /** @see #getY() */
  private int y;

  /**
   * The constructor for de-serialization.
   */
  protected Point() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param x is the {@link #getX() x-position}.
   * @param y is the {@link #getY() y-position}.
   */
  public Point(int x, int y) {

    super();
    this.x = x;
    this.y = y;
  }

  /**
   * @return the x-position of this {@link Point} in {@link LengthUnit#PIXEL pixel}.
   */
  public int getX() {

    return this.x;
  }

  /**
   * @return the y-position of this {@link Point} in {@link LengthUnit#PIXEL pixel}.
   */
  public int getY() {

    return this.y;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return this.x + "," + this.y;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if ((obj == null) || (obj.getClass() != getClass())) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    Point other = (Point) obj;
    if (this.x != other.x) {
      return false;
    }
    if (this.y != other.y) {
      return false;
    }
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    return (this.x * 31) + this.y;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Point getValue() {

    return this;
  }

}
