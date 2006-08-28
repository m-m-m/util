/* $Id$ */
package net.sf.mmm.ui.toolkit.api.window;

import net.sf.mmm.ui.toolkit.api.state.UIWriteMaximizedIF;
import net.sf.mmm.ui.toolkit.api.state.UIWriteMinimizedIF;

/**
 * This is the interface for a frame.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIFrameIF extends UIWindowIF, UIWriteMaximizedIF, UIWriteMinimizedIF {

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
    UIFrameIF createFrame(String title, boolean resizeable);
    
}
