/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This is the interface for an object of the UI framework that has a position.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWritePosition extends UIReadPosition {

  /**
   * This method sets the position of the object.
   * 
   * @param x
   *        is the position on the x-axsis (horizontal).
   * @param y
   *        is the position on the y-axsis (vertical).
   */
  void setPosition(int x, int y);

}
