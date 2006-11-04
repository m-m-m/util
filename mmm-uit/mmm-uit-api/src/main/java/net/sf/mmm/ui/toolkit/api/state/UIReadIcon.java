/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

import net.sf.mmm.ui.toolkit.api.UIPicture;

/**
 * This interface gives read access to the
 * {@link net.sf.mmm.ui.toolkit.api.UIPicture icon} of an
 * {@link net.sf.mmm.ui.toolkit.api.UIObject object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIReadIcon {

  /**
   * This method gets the icon of this object.
   * 
   * @return the icon of this object or <code>null</code>, if no icon is
   *         set.
   */
  UIPicture getIcon();

}
