/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.window;

import net.sf.mmm.client.ui.api.common.CssStyles;
import net.sf.mmm.client.ui.api.widget.UiWidgetNative;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetButtonPanel;

/**
 * This is the interface for a {@link UiWidgetAbstractWindow base window widget} that represents a
 * <em>popup</em> window. A popup is a modal window that blocks the client application until it is
 * {@link #close() closed}. <br>
 * <b>NOTE:</b><br>
 * You typically want to call {@link #createAndAddCloseButton()}. This is not done automatically so you can
 * define the order of the buttons yourself.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetPopup extends UiWidgetAbstractDialogWindow, UiWidgetNative {

  /** The default {@link #addStyle(String) additional style} of this widget. */
  String STYLE_POPUP = CssStyles.POPUP;

  /**
   * @return the {@link UiWidgetButtonPanel} located at the bottom of this popup where
   *         {@link net.sf.mmm.client.ui.api.widget.core.UiWidgetButton buttons} shall be added.
   */
  UiWidgetButtonPanel getButtonPanel();

  /**
   * This method creates a {@link UiWidgetButton button} to close this popup and adds it to the
   * {@link #getButtonPanel() button panel}.
   *
   * @return the close button.
   */
  UiWidgetButton createAndAddCloseButton();

}
