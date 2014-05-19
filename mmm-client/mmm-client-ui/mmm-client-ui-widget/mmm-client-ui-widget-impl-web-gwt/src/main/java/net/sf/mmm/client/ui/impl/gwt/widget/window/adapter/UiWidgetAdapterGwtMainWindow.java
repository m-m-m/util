/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.window.adapter;

import net.sf.mmm.client.ui.api.common.Length;
import net.sf.mmm.client.ui.api.common.LengthProperty;
import net.sf.mmm.client.ui.api.common.LengthUnit;
import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenuBar;
import net.sf.mmm.client.ui.base.widget.window.adapter.UiWidgetAdapterMainWindow;
import net.sf.mmm.client.ui.gwt.widgets.VerticalFlowPanel;
import net.sf.mmm.util.gwt.api.JavaScriptUtil;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * This is the implementation of {@link UiWidgetAdapterMainWindow} using GWT based on {@link RootPanel} and
 * {@link Window}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtMainWindow extends UiWidgetAdapterGwtAbstractWindow<RootPanel> implements
    UiWidgetAdapterMainWindow {

  /** @see #getContentPanel() */
  private final VerticalFlowPanel contentPanel;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtMainWindow() {

    super();
    this.contentPanel = new VerticalFlowPanel();
    getToplevelWidget().add(this.contentPanel);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected VerticalFlowPanel getContentPanel() {

    return this.contentPanel;
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
  public void setPosition(double x, double y) {

    Window.moveTo((int) x, (int) y);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLength(LengthProperty property, Length value) {

    switch (property) {
      case HEIGHT:
        Window.resizeTo(Window.getClientWidth(), convertLength(value));
        break;
      case WIDTH:
        Window.resizeTo(convertLength(value), Window.getClientHeight());
        break;
      // TODO hohwille min/max width/height
      default :
        break;
    }
  }

  /**
   * Converts the width to pixels. Actual conversion has already been done by the
   * {@link net.sf.mmm.client.ui.base.widget.window.AbstractUiWidgetAbstractWindow}.
   *
   * @param length is the {@link Length}.
   * @return the {@link Length#getAmount() amount} of pixels.
   */
  private int convertLength(Length length) {

    assert (length.getUnit() == LengthUnit.PIXEL);
    return (int) length.getAmount();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getPositionX() {

    return JavaScriptUtil.getInstance().getBrowserPositionX();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getPositionY() {

    return JavaScriptUtil.getInstance().getBrowserPositionY();
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
  public double getWidthInPixel() {

    // return JavaScriptUtil.getInstance().getBrowserWidth();
    return Window.getClientWidth();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getHeightInPixel() {

    // return JavaScriptUtil.getInstance().getBrowserHeight();
    return Window.getClientHeight();
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

    getContentPanel().insert(getToplevelWidget(menuBar), 0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected RootPanel createToplevelWidget() {

    RootPanel rootLayoutPanel = RootPanel.get();
    return rootLayoutPanel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Element getSizeElement() {

    return getContentPanel().getElement();
  }

}
