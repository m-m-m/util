/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

import net.sf.mmm.ui.toolkit.api.composite.Orientation;

/**
 * This interface gives {@link UIReadOrientation#getOrientation() read} and
 * {@link #setOrientation(Orientation) write} access to the
 * {@link net.sf.mmm.ui.toolkit.api.composite.Orientation} of an
 * {@link net.sf.mmm.ui.toolkit.api.UIObject object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWriteOrientation extends UIReadOrientation {

  /**
   * This method sets the orientation to the given value.
   * 
   * @see UIReadOrientation#getOrientation()
   * 
   * @param orientation
   *        is the new orientation.
   */
  void setOrientation(Orientation orientation);

}
