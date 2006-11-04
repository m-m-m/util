/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

import net.sf.mmm.ui.toolkit.api.UIPicture;

/**
 * This interface gives read and write access to the
 * {@link net.sf.mmm.ui.toolkit.api.UIPicture icon} of an
 * {@link net.sf.mmm.ui.toolkit.api.UIObject object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWriteIcon extends UIReadIcon {

  /**
   * This method sets the icon of this object.
   * 
   * @param icon
   *        is the new icon for this object.
   */
  void setIcon(UIPicture icon);

}
