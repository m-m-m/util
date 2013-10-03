/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

import net.sf.mmm.util.lang.api.Direction;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This is the {@link net.sf.mmm.util.lang.api.Datatype} for a 2-dimensional rectangle measured in
 * {@link SizeUnit#PIXEL pixels}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class Rectangle extends Point {

  /** UID for serialization. */
  private static final long serialVersionUID = 1790116489654948639L;

  /** @see #getWidth() */
  private int width;

  /** @see #getHeight() */
  private int height;

  /**
   * The constructor for de-serialization.
   */
  protected Rectangle() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param x is the {@link #getX() x-position}.
   * @param y is the {@link #getY() y-position}.
   * @param width is the {@link #getWidth() width}.
   * @param height is the {@link #getHeight() height}.
   */
  public Rectangle(int x, int y, int width, int height) {

    super(x, y);
    if (width < 0) {
      throw new ValueOutOfRangeException(Integer.valueOf(width), Integer.valueOf(0), Integer.valueOf(Integer.MAX_VALUE));
    }
    if (height < 0) {
      throw new ValueOutOfRangeException(Integer.valueOf(height), Integer.valueOf(0),
          Integer.valueOf(Integer.MAX_VALUE));
    }
    this.width = width;
    this.height = height;
  }

  /**
   * Creates a new {@link Rectangle} of this moved to the given {@link Point}.
   * 
   * @param p is the {@link Point} to move to.
   * @return a new {@link Rectangle} with the same size and located at the given {@link Point}.
   */
  public Rectangle moveTo(Point p) {

    return moveTo(p.getX(), p.getY());
  }

  /**
   * Creates a new {@link Rectangle} of this moved to the point given by <code>x</code> and <code>y</code>.
   * 
   * @param newX is the new {@link #getX() x-position}.
   * @param newY is the new {@link #getY() y-position}.
   * @return a new {@link Rectangle} with the same size and located at the given point.
   */
  public Rectangle moveTo(int newX, int newY) {

    return new Rectangle(newX, newY, this.width, this.height);
  }

  /**
   * Creates a new {@link Rectangle} of this moved from its current position relatively by the to the given
   * {@link Point}.
   * 
   * @param p is the {@link Point} with the delta-coordinate to move by.
   * @return a new {@link Rectangle} with the same size and moved by the given {@link Point}.
   */
  public Rectangle moveBy(Point p) {

    return moveBy(p.getX(), p.getY());
  }

  /**
   * Creates a new {@link Rectangle} of this moved from its current position relatively by the to the given
   * <code>dx</code> and <code>dy</code> coordinate offsets.
   * 
   * @param dx is the value to add to the {@link #getX() x-position}. May be negative to move left.
   * @param dy is the value to add to the {@link #getY() y-position}. May be negative to move up.
   * @return a new {@link Rectangle} with the same size and moved by the given offset.
   */
  public Rectangle moveBy(int dx, int dy) {

    return new Rectangle(getX() + dx, getY() + dy, this.width, this.height);
  }

  /**
   * This method calculates a clipped variant from this {@link Rectangle} that fits inside the given
   * <code>clippingArea</code>.
   * 
   * @param clippingArea is the {@link Rectangle} the result has to fit in.
   * @param move - if <code>true</code> that try to avoid resizing the resulting {@link Rectangle} by moving
   *        towards the top left of the <code>clippingArea</code>, <code>false</code> otherwise (if resizing
   *        is appreciated).
   * @return the clipped {@link Rectangle} fitting inside the <code>clippingArea</code>.
   */
  public Rectangle clipTo(Rectangle clippingArea, boolean move) {

    int newX = getX();
    int newY = getY();
    int newWidth = this.width;
    int newHeight = this.height;
    boolean modified = false;
    // clip newX
    int clipX = clippingArea.getX();
    if (newX < clipX) {
      newX = clipX;
      modified = true;
    }
    int clipX2 = clippingArea.getX2();
    if (newX > clipX2) {
      newX = clipX2;
      modified = true;
    }
    // clip newY
    int clipY = clippingArea.getY();
    if (newY < clipY) {
      newY = clipY;
      modified = true;
    }
    int clipY2 = clippingArea.getY2();
    if (newY > clipY2) {
      newY = clipY2;
      modified = true;
    }
    int x2 = newX + newWidth;
    if (x2 > clipX2) {
      int dx = x2 - clipX2;
      modified = true;
      if (move) {
        newX = newX - dx;
        if (newX < clipX) {
          newWidth = newWidth - (newX - clipX);
          newX = clipX;
        }
      } else {
        newWidth = newWidth - dx;
      }
    }
    int y2 = newY + newHeight;
    if (y2 > clipY2) {
      int dy = y2 - clipY2;
      modified = true;
      if (move) {
        newY = newY - dy;
        if (newY < clipY) {
          newHeight = newHeight - (newY - clipY);
          newY = clipY;
        }
      } else {
        newHeight = newHeight - dy;
      }
    }
    if (!modified) {
      return this;
    }
    return new Rectangle(newX, newY, newWidth, newHeight);
  }

  /**
   * Calculates a new {@link Rectangle} from this resized by <code>dx</code> and <code>dy</code> in the given
   * {@link Direction}.
   * 
   * @param dx is the delta offset for the {@link #getX() x-position}.
   * @param dy is the delta offset for the {@link #getY() y-position}.
   * @param direction is the {@link Direction} where to resize.
   * @param minWidth is the minimum {@link #getWidth() width}.
   * @param minHeight is the minimum {@link #getHeight() height}.
   * @return the resized {@link Rectangle}.
   */
  public Rectangle resize(int dx, int dy, Direction direction, int minWidth, int minHeight) {

    int newX = getX();
    int newY = getY();
    int newHeight = this.height;
    int newWidth = this.width;

    if (direction.isToEast()) {
      newWidth = newWidth + dx;
      if (newWidth < minWidth) {
        newWidth = minWidth;
      }
    } else if (direction.isToWest()) {
      if ((newWidth - dx) < minWidth) {
        newX = newX + (newWidth - minWidth);
        newWidth = minWidth;
      } else {
        newX = newX + dx;
        newWidth = newWidth - dx;
      }
    }

    if (direction.isToSouth()) {
      newHeight = newHeight + dy;
      if (newHeight < minHeight) {
        newHeight = minHeight;
      }
    } else if (direction.isToNorth()) {
      if ((newHeight - dy) < minHeight) {
        newY = newY + (newHeight - minHeight);
        newHeight = minHeight;
      } else {
        newY = newY + dy;
        newHeight = newHeight - dy;
      }

    }
    return new Rectangle(newX, newY, newWidth, newHeight);
  }

  /**
   * @return the width of this {@link Rectangle} in {@link SizeUnit#PIXEL pixel}.
   */
  public int getWidth() {

    return this.width;
  }

  /**
   * @return the height of this {@link Rectangle} in {@link SizeUnit#PIXEL pixel}.
   */
  public int getHeight() {

    return this.height;
  }

  /**
   * @return the x-position of the bottom right edge of this {@link Rectangle}.
   */
  public int getX2() {

    return getX() + this.width;
  }

  /**
   * @return the y-position of the bottom right edge of this {@link Rectangle}.
   */
  public int getY2() {

    return getY() + this.height;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + this.height;
    result = prime * result + this.width;
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if (!super.equals(obj)) {
      return false;
    }
    Rectangle other = (Rectangle) obj;
    if (this.height != other.height) {
      return false;
    }
    if (this.width != other.width) {
      return false;
    }
    return true;
  }

}
