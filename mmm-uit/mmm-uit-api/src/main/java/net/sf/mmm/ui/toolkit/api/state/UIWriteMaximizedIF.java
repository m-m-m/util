/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This interface gives {@link UIReadMaximizedIF#isMaximized() read} and
 * {@link #setMaximized(boolean) write} access to the maximized state of an
 * {@link net.sf.mmm.ui.toolkit.api.UIObjectIF object}.
 * 
 * @see net.sf.mmm.ui.toolkit.api.window.UIWindowIF
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWriteMaximizedIF extends UIReadMaximizedIF {

    /**
     * This method (un)maximizes the object. If it is maximized its position
     * will be moved to the top left corner and its size will be set to the
     * maximum size available (display for windows). If it is unmaximized after
     * it was maximized, its size and position will be restored to the values
     * before it was maximized.
     * 
     * @param maximize -
     *        if <code>true</code>, the window will be maximized, else it
     *        will be unmaximized.
     */
    void setMaximized(boolean maximize);

}
