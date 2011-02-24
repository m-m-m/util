/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.view.composite;

/**
 * This class is a simple container for an rectangular area in a coordinate
 * system.
 * 
 * @see java.awt.Rectangle
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class Rectangle {

  /**
   * The x-coordinate.
   */
  public int x;

  /**
   * The y-coordinate.
   */
  public int y;

  /**
   * The width.
   */
  public int width;

  /**
   * The height.
   */
  public int height;

  /**
   * The constructor.
   */
  public Rectangle() {

    super();
    this.x = 0;
    this.y = 0;
    this.width = 0;
    this.height = 0;
  }

  /**
   * The constructor.
   * 
   * @param xPos
   * @param yPos
   * @param w
   * @param h
   */
  public Rectangle(int xPos, int yPos, int w, int h) {

    super();
    this.x = xPos;
    this.y = yPos;
    this.width = w;
    this.height = h;
  }

  /**
   * This method swapps {@link #x}/{@link #y} and {@link #width}/{@link #height}.
   */
  public void swap() {

    int swap = this.x;
    this.x = this.y;
    this.y = swap;
    swap = this.height;
    this.height = this.width;
    this.width = swap;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return "[x=" + this.x + ",y=" + this.y + ",w=" + this.width + ",h=" + this.height + "]";
  }

}
