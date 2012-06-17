/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.ui.toolkit.api.view.UiImage;

/**
 * This interface gives read and write access to the
 * {@link net.sf.mmm.ui.toolkit.api.view.UiImage icon} of an
 * {@link net.sf.mmm.ui.toolkit.api.UiObject object}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWriteImage extends UiReadImage {

  /**
   * This method sets the image of this object.
   * 
   * @param icon is the new icon for this object.
   */
  void setImage(UiImage icon);

}
