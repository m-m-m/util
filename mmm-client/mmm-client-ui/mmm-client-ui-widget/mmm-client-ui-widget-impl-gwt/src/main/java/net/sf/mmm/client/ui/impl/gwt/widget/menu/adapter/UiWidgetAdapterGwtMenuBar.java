/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.menu.adapter;

import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenu;
import net.sf.mmm.client.ui.base.widget.menu.adapter.UiWidgetAdapterMenuBar;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtWidget;

import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

/**
 * This is the implementation of
 * {@link net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterDynamicComposite} using GWT based on
 * {@link MenuBar}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtMenuBar extends UiWidgetAdapterGwtWidget<MenuBar> implements
    UiWidgetAdapterMenuBar<MenuBar> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtMenuBar() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected MenuBar createWidget() {

    MenuBar menuBar = new MenuBar();
    menuBar.setAutoOpen(true);
    menuBar.setAnimationEnabled(true);
    return menuBar;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(UiWidgetMenu child, int index) {

    MenuItem menu = getWidget(child, MenuItem.class);
    getWidget().addItem(menu);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeChild(UiWidgetMenu child, int index) {

    MenuItem menu = getWidget(child, MenuItem.class);
    getWidget().removeItem(menu);
  }

}
