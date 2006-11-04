/* $Id$ */
package net.sf.mmm.ui.toolkit.api.state;

/**
 * This is the interface for an
 * {@link net.sf.mmm.ui.toolkit.api.UIObject object} of the UI framework that
 * can be (un)minimized.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIWriteMinimized {

  /**
   * This method (un)minimizes the object (window). If it is minimized it will
   * be hidden from the screen (iconified). Depending on the underlying
   * implementation (and maybe the OS) an icon will be placed on the desktop
   * that represents the frame. If it is unminimized, its will be shown again
   * with the same size and position as before it was minimized.
   * 
   * @param minimize -
   *        if <code>true</code>, the frame will be minimized (iconified),
   *        else it will be unminimized.
   */
  void setMinimized(boolean minimize);

}
