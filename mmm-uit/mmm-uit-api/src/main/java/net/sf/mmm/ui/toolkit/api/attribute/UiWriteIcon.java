/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.ui.toolkit.api.UiImage;

/**
 * This interface gives read and write access to the
 * {@link net.sf.mmm.ui.toolkit.api.UiImage icon} of an
 * {@link net.sf.mmm.ui.toolkit.api.UiObject object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UiWriteIcon extends UiReadIcon {

  /**
   * This method sets the icon of this object.
   * 
   * @param icon is the new icon for this object.
   */
  void setIcon(UiImage icon);

}
