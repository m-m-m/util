/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.menu.adapter;

import net.sf.mmm.client.ui.base.widget.menu.adapter.UiWidgetAdapterMenuItemSeparator;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwt;

import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItemSeparator;

/**
 * This is the implementation of {@link UiWidgetAdapterMenuItemSeparator} using GWT based on
 * {@link MenuItemSeparator}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtMenuItemSeparator extends UiWidgetAdapterGwt<MenuItemSeparator> implements
    UiWidgetAdapterMenuItemSeparator {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtMenuItemSeparator() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeFromParent() {

    MenuItemSeparator separator = getToplevelWidget();
    MenuBar parentMenu = separator.getParentMenu();
    if (parentMenu != null) {
      parentMenu.removeSeparator(separator);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    // nothing to do...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected MenuItemSeparator createToplevelWidget() {

    return new MenuItemSeparator();
  }

}
