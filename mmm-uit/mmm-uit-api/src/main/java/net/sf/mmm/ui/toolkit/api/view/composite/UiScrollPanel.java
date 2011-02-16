/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.composite;

/**
 * This is the interface for a
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiComposite composite} that can
 * hold one child
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiComposite composite}. It has a
 * horizontal and a vertical scrollbar. The scrollbars can be used to scroll the
 * contained child if its width/height is greater than the width/height
 * available for this panel.<br>
 * The implementation should only show the scrollbars as needed.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UiScrollPanel extends UiComposite {

  /** the type of this object */
  String TYPE = "ScrollPanel";

  /**
   * This method sets the child viewed inside this scroll-panel.
   * 
   * @param child is the new contained child composite.
   */
  void setComponent(UiComposite child);

}
