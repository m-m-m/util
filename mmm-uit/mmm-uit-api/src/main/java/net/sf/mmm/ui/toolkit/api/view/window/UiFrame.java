/* $Id: UIFrame.java 955 2011-02-16 23:10:16Z hohwille $
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.window;

import net.sf.mmm.ui.toolkit.api.attribute.UiWriteMaximized;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteMinimized;
import net.sf.mmm.ui.toolkit.api.view.menu.UiMenuBar;

/**
 * This is the interface for a frame.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiFrame extends UiWindow, UiWriteMaximized, UiWriteMinimized {

  /** the type of this object */
  String TYPE = "Frame";

  /**
   * This method gets the menu bar of this window.
   * 
   * @return the menu bar. This method will never return <code>null</code> but
   *         create an empty menu-bar on the first call.
   */
  UiMenuBar getMenuBar();

  /**
   * This method creates a new frame as child of this frame.
   * 
   * @param title is the title the new frame will have.
   * @param resizeable - if <code>true</code> the frame can be resized by the
   *        user.
   * @return the created frame.
   */
  UiFrame createFrame(String title, boolean resizeable);

}
