/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.window;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteMovable;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteResizable;
import net.sf.mmm.client.ui.api.common.CssStyles;

/**
 * This is the abstract base interface for a {@link UiWidgetAbstractWindow window widget} that represents a
 * <em>dialog window</em>. That is any window other than the {@link UiWidgetMainWindow}. Such window is opened
 * dynamically by the application.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetAbstractDialogWindow extends UiWidgetAbstractWindow, AttributeWriteResizable,
    AttributeWriteMovable {

  /** The default {@link #getPrimaryStyle() primary style} of this widget. */
  String STYLE_PRIMARY = CssStyles.WINDOW;

  /**
   * This method sets the position of the window, so that it is in the center of the screen. You should set
   * the {@link #setSize(net.sf.mmm.client.ui.api.common.Length, net.sf.mmm.client.ui.api.common.Length) size}
   * before invoking this method.
   */
  @Override
  void centerWindow();

}
