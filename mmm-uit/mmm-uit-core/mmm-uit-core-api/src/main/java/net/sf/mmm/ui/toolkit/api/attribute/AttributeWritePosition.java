/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This is the interface for an object of the UI framework that has a position.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface AttributeWritePosition extends AttributeReadPosition {

  /**
   * This method sets the position of the object.
   * 
   * @param x is the position on the x-axsis (horizontal).
   * @param y is the position on the y-axsis (vertical).
   */
  void setPosition(int x, int y);

}
