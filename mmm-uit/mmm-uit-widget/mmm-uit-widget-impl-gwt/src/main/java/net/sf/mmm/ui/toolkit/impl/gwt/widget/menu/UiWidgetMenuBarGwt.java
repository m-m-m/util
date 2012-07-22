/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.menu;

import net.sf.mmm.ui.toolkit.api.widget.menu.UiWidgetMenu;
import net.sf.mmm.ui.toolkit.api.widget.menu.UiWidgetMenuBar;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.UiWidgetDynamicCompositeGwt;

import com.google.gwt.user.client.ui.MenuBar;

/**
 * This is the implementation of {@link UiWidgetMenuBar} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetMenuBarGwt extends UiWidgetDynamicCompositeGwt<MenuBar, UiWidgetMenu> implements UiWidgetMenuBar {

  /**
   * The constructor.
   */
  public UiWidgetMenuBarGwt() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected MenuBar createWidget() {

    return new MenuBar();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetEnabled(boolean newEnabled) {

    // getWidget().setEnabled
  }

}
