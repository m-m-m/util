/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.menu.adapter;

import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenuItem;
import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenuItemSeparator;
import net.sf.mmm.client.ui.base.widget.menu.adapter.UiWidgetAdapterMenu;

import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.MenuItemSeparator;

/**
 * This is the implementation of {@link UiWidgetAdapterMenu} using GWT based on {@link MenuItem} and
 * {@link MenuBar}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtMenu extends UiWidgetAdapterGwtMenuItemBase implements UiWidgetAdapterMenu {

  /** The {@link MenuBar}. */
  private MenuBar menuBar;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtMenu() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected MenuItem createToplevelWidget() {

    this.menuBar = new MenuBar(true);
    return new MenuItem("", this.menuBar);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(UiWidgetMenuItem child, int index) {

    if (child instanceof UiWidgetMenuItemSeparator) {
      MenuItemSeparator separator = getToplevelWidget(child, MenuItemSeparator.class);
      this.menuBar.addSeparator(separator);
    } else {
      MenuItem menu = getToplevelWidget(child, MenuItem.class);
      this.menuBar.addItem(menu);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeChild(UiWidgetMenuItem child, int index) {

    MenuItem menuItem = getToplevelWidget(child, MenuItem.class);
    this.menuBar.removeItem(menuItem);
  }

}
