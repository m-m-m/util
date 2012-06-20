/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.window;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteMaximized;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteMinimized;
import net.sf.mmm.ui.toolkit.api.view.menu.UiMenuBar;

/**
 * This is the interface for a frame. A frame is a non
 * {@link net.sf.mmm.ui.toolkit.api.attribute.AttributeReadModal#isModal() modal} window. Unlike a
 * {@link UiDialog} it typically exists for a major purpose rather than just a temporary interaction. A frame
 * can exist standalone and will then appear in something like a task-bar. Otherwise it exists as embedded
 * {@link #createFrame(String, boolean) child frame} of the {@link UiWorkbench}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiFrame extends UiWindow, AttributeWriteMaximized, AttributeWriteMinimized {

  /** the type of this object */
  String TYPE = "Frame";

  /**
   * This method gets the menu bar of this window.
   * 
   * @return the menu bar. This method will never return <code>null</code> but create an empty menu-bar on the
   *         first call.
   */
  UiMenuBar getMenuBar();

  /**
   * This method creates a new frame as child of this frame.
   * 
   * @param title is the title the new frame will have.
   * @param resizable - if <code>true</code> the frame can be resized by the user.
   * @return the created frame.
   */
  UiFrame createFrame(String title, boolean resizable);

  /**
   * {@inheritDoc}
   */
  @Override
  UiFrame getParent();

}
