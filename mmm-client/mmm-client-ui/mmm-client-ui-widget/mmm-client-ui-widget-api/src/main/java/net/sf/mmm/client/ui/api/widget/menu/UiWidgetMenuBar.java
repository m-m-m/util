/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.menu;

import net.sf.mmm.client.ui.api.widget.UiWidgetDynamicComposite;
import net.sf.mmm.client.ui.api.widget.UiWidgetNative;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegularComposite;

/**
 * This is the interface for a {@link UiWidgetRegularComposite composite widget} that represents a
 * <em>menu bar</em>. It is {@link #getChild(int) contains} {@link UiWidgetMenu menus} that are displayed by
 * their title in the menu bar. <br>
 * <b>ATTENTION:</b><br>
 * A {@link UiWidgetMenuBar} should typically be created via
 * {@link net.sf.mmm.client.ui.api.widget.window.UiWidgetMainWindow#getMenuBar()}. For portability and
 * usability you should avoid creating it manually via {@link net.sf.mmm.client.ui.api.UiContext}. However, if
 * you are using GWT with GWTP it might make sense to do so.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetMenuBar extends UiWidgetDynamicComposite<UiWidgetMenu>,
    UiWidgetRegularComposite<UiWidgetMenu>, UiWidgetNative {

  /**
   * This method {@link #addChild(UiWidgetMenu) adds} a {@link UiWidgetMenu menu} to this menu-bar.
   *
   * @param label is the {@link UiWidgetMenu#getLabel() label} of the menu.
   * @return the new menu.
   */
  UiWidgetMenu addMenu(String label);

  /**
   * {@inheritDoc}
   */
  @Override
  void addChild(UiWidgetMenu child);

}
