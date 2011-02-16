/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.window;

import net.sf.mmm.ui.toolkit.api.attribute.UiWriteMaximized;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteMinimized;

/**
 * This is the interface for a frame.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UIFrame extends UIWindow, UiWriteMaximized, UiWriteMinimized {

  /** the type of this object */
  String TYPE = "Frame";

  /**
   * This method creates a new frame as child of this frame.
   * 
   * @param title is the title the new frame will have.
   * @param resizeable - if <code>true</code> the frame can be resized by the
   *        user.
   * @return the created frame.
   */
  UIFrame createFrame(String title, boolean resizeable);

}
