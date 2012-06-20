/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.ui.toolkit.api.view.UiImage;

/**
 * This interface gives read access to the {@link net.sf.mmm.ui.toolkit.api.view.UiImage icon} of an
 * {@link net.sf.mmm.ui.toolkit.api.UiObject}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiReadImage {

  /**
   * This method gets the image of this object.
   * 
   * @return the icon of this object or <code>null</code>, if no icon is set.
   */
  UiImage getImage();

}
