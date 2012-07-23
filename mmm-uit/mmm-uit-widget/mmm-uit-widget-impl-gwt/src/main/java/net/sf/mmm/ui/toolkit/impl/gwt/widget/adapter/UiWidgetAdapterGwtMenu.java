/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter;

import net.sf.mmm.ui.toolkit.api.feature.UiFeatureClick;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.ui.toolkit.api.widget.menu.UiWidgetMenuItem;
import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterMenu;
import net.sf.mmm.util.nls.api.NlsUnsupportedOperationException;

import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;

/**
 * This is the implementation of {@link UiWidgetAdapterMenu} using GWT based on {@link MenuItem} and
 * {@link MenuBar}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtMenu extends UiWidgetAdapterGwtMenuItemBase implements UiWidgetAdapterMenu<MenuItem> {

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
  protected MenuItem createWidget() {

    this.menuBar = new MenuBar(true);
    return new MenuItem("", this.menuBar);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(UiWidgetMenuItem child, int index) {

    MenuItem menuItem = getWidget(child, MenuItem.class);
    this.menuBar.addItem(menuItem);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeChild(UiWidgetMenuItem child, int index) {

    MenuItem menuItem = getWidget(child, MenuItem.class);
    this.menuBar.removeItem(menuItem);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setClickEventSender(UiFeatureClick source, UiHandlerEventClick clickEventSender) {

    throw new NlsUnsupportedOperationException();
  }

}
