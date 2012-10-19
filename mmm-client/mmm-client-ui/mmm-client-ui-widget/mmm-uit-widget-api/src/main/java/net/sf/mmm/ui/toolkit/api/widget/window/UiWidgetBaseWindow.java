/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.window;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeReadResizable;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWritePosition;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteSizeInPixel;
import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteStringTitle;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetDynamicComposite;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular;

/**
 * This is the abstract base interface for a {@link UiWidgetDynamicComposite dynamic composite widget} that
 * represents a <em>window</em>. A window is a rectangular area on the screen that has a border. The top
 * border shows its {@link #getTitle() title} and potential icon buttons (e.g. to close the window). The right
 * and bottom border may contain scrollbars as needed and potentially allow to {@link #isResizable() resize}
 * the window.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetBaseWindow extends UiWidgetDynamicComposite<UiWidgetRegular>,
    AttributeWriteStringTitle, AttributeWriteSizeInPixel, AttributeReadResizable, AttributeWritePosition {

  /**
   * This method sets the position of the window, so that it is in the center of the screen. You should set
   * the {@link #setSizeInPixel(int, int) size} before invoking this method.
   */
  void centerWindow();

}
