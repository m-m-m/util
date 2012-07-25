/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.adapter;

import net.sf.mmm.ui.toolkit.api.widget.menu.UiWidgetMenuBar;

/**
 * This is the interface for a {@link UiWidgetAdapter} adapting
 * {@link net.sf.mmm.ui.toolkit.api.widget.window.UiWidgetMainWindow}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public interface UiWidgetAdapterMainWindow<WIDGET> extends UiWidgetAdapterBaseWindow<WIDGET> {

  /**
   * This method sets the {@link UiWidgetMenuBar}. It will be called just once.
   * 
   * @param menuBar is the {@link UiWidgetMenuBar} to attach at the top of the window.
   */
  void setMenuBar(UiWidgetMenuBar menuBar);

}
