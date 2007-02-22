/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
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
