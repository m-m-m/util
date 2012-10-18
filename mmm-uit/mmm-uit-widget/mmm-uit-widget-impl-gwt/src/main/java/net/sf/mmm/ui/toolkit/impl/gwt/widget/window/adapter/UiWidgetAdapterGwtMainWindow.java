/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.window.adapter;

import net.sf.mmm.ui.toolkit.api.widget.menu.UiWidgetMenuBar;
import net.sf.mmm.ui.toolkit.base.widget.window.adapter.UiWidgetAdapterMainWindow;
import net.sf.mmm.util.gwt.api.JavaScriptUtil;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * This is the implementation of {@link UiWidgetAdapterMainWindow} using GWT based on {@link RootLayoutPanel}
 * and {@link Window}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtMainWindow extends UiWidgetAdapterGwtBaseWindow<RootPanel> implements
    UiWidgetAdapterMainWindow<RootPanel> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtMainWindow() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return Window.getTitle();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(String title) {

    Window.setTitle(title);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPosition(int x, int y) {

    Window.moveTo(x, y);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSizeInPixel(int width, int height) {

    Window.resizeTo(width, height);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidthInPixel(int width) {

    Window.resizeTo(width, Window.getClientHeight());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeightInPixel(int height) {

    Window.resizeTo(Window.getClientWidth(), height);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getPositionX() {

    return JavaScriptUtil.getInstance().getBrowserPositionX();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getPositionY() {

    return JavaScriptUtil.getInstance().getBrowserPositionY();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setResizable(boolean resizable) {

    // not supported - ignore
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
  public int getWidthInPixel() {

    return JavaScriptUtil.getInstance().getBrowserWidth();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHeightInPixel() {

    return JavaScriptUtil.getInstance().getBrowserHeight();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isResizable() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMenuBar(UiWidgetMenuBar menuBar) {

    getContentPanel().insert(getWidget(menuBar), 0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected RootPanel createWidget() {

    RootPanel rootLayoutPanel = RootPanel.get();
    return rootLayoutPanel;
  }

}
