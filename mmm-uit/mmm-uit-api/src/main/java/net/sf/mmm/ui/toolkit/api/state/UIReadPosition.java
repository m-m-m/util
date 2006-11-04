/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives read access to the position of an
 * {@link net.sf.mmm.ui.toolkit.api.UIObject object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIReadPosition {

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
