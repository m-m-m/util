/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.window;

import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenuBar;

/**
 * This is the interface for a {@link UiWidgetAbstractWindow base window widget} that represents a
 * <em>main window</em>. Each client application has an instance of this main window widget. In case of a
 * web-application this represents the browser window (or more precisely the tab-instance).
 * 
 * @see net.sf.mmm.client.ui.api.widget.factory.UiWidgetFactoryNative#getMainWindow()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetMainWindow extends UiWidgetAbstractWindow {

  /**
   * @return the {@link UiWidgetMenuBar menu bar} of this window. Will be created and attached to the window
   *         on the first call of this method.
   */
  UiWidgetMenuBar getMenuBar();

  /**
   * @return <code>true</code> if the {@link #setPosition(double, double) position} of
   *         {@link UiWidgetAbstractWindow windows} is absolute (on {@link net.sf.mmm.client.ui.api.UiDisplay}),
   *         <code>false</code> if relative to this main window (MDI mode).
   */
  boolean isWindowPositionAbsolute();

}
