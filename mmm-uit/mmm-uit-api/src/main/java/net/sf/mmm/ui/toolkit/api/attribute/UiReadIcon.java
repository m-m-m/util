/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.ui.toolkit.api.UIPicture;

/**
 * This interface gives read access to the
 * {@link net.sf.mmm.ui.toolkit.api.UIPicture icon} of an
 * {@link net.sf.mmm.ui.toolkit.api.UIObjectRenamed object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UiReadIcon {

  /**
   * This method gets the icon of this object.
   * 
   * @return the icon of this object or <code>null</code>, if no icon is set.
   */
  UIPicture getIcon();

}
