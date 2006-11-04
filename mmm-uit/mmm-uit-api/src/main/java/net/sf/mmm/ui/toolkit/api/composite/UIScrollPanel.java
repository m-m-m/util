/* $Id$ */
package net.sf.mmm.ui.toolkit.api.composite;

/**
 * This is the interface for a
 * {@link net.sf.mmm.ui.toolkit.api.composite.UIComposite composite} that can
 * hold one child
 * {@link net.sf.mmm.ui.toolkit.api.composite.UIComposite composite}. It has
 * a horizontal and a vertical scrollbar. The scrollbars can be used to scroll
 * the contained child if its width/height is greater than the width/height
 * available for this panel.<br>
 * The implementation should only show the scrollbars as needed.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UIScrollPanel extends UIComposite {

  /** the type of this object */
  String TYPE = "ScrollPanel";

  /**
   * This method sets the child viewed inside this scroll-panel.
   * 
   * @param child
   *        is the new contained child composite.
   */
  void setComponent(UIComposite child);

}
