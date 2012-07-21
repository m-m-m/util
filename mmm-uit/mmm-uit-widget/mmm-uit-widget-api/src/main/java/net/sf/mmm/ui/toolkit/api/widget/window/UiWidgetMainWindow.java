/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.window;

import net.sf.mmm.ui.toolkit.api.widget.menu.UiWidgetMenuBar;

/**
 * This is the interface for a {@link UiWidgetBaseWindow base window widget} that represents a
 * <em>main window</em>. Each client application has a single instance of this main window widget. In case of
 * a web-application this represents the browser window.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetMainWindow extends UiWidgetBaseWindow {

  /**
   * @return the {@link UiWidgetMenuBar menu bar} of this window. Will become visible after the first
   *         {@link net.sf.mmm.ui.toolkit.api.widget.menu.UiWidgetMenu menu} is
   *         {@link UiWidgetMenuBar#addChild(net.sf.mmm.ui.toolkit.api.widget.menu.UiWidgetMenu) added}.
   */
  UiWidgetMenuBar getMenuBar();

}
