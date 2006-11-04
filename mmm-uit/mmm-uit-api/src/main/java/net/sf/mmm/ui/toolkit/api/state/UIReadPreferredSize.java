/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This is the interface for an
 * {@link net.sf.mmm.ui.toolkit.api.UIObject object} of the UI framework that
 * has a preferred size.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIReadPreferredSize {

  /**
   * This method gets the preferred width of this object in pixels.
   * 
   * @return the width.
   */
  int getPreferredWidth();

  /**
   * This method gets the preferred height of this object in pixels.
   * 
   * @return the height.
   */
  int getPreferredHeight();

}
