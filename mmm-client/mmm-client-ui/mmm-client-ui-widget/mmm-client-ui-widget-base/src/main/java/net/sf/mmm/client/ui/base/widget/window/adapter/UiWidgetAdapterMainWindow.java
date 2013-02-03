/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.window.adapter;

import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenuBar;

/**
 * This is the interface for a {@link net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter} adapting
 * {@link net.sf.mmm.client.ui.api.widget.window.UiWidgetMainWindow}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetAdapterMainWindow extends UiWidgetAdapterBaseWindow {

  /**
   * This method sets the {@link UiWidgetMenuBar}. It will be called just once.
   * 
   * @param menuBar is the {@link UiWidgetMenuBar} to attach at the top of the window.
   */
  void setMenuBar(UiWidgetMenuBar menuBar);

}
