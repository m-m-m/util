/* $Id$ */
package net.sf.mmm.ui.toolkit.api.window;

import net.sf.mmm.ui.toolkit.api.state.UIWriteMaximized;
import net.sf.mmm.ui.toolkit.api.state.UIWriteMinimized;

/**
 * This is the interface for a frame.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIFrame extends UIWindow, UIWriteMaximized, UIWriteMinimized {

  /** the type of this object */
  String TYPE = "Frame";

  /**
   * This method creates a new frame as child of this frame.
   * 
   * @param title
   *        is the title the new frame will have.
   * @param resizeable -
   *        if <code>true</code> the frame can be resized by the user.
   * @return the created frame.
   */
  UIFrame createFrame(String title, boolean resizeable);

}
