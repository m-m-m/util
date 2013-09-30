/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.window.adapter;

import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.PopupWindow;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.VerticalFlowPanel;

/**
 * This is the implementation of
 * {@link net.sf.mmm.client.ui.base.widget.window.adapter.UiWidgetAdapterAbstractWindow} using GWT based on
 * {@link PopupWindow}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterGwtAbstractPopupWindow extends UiWidgetAdapterGwtAbstractWindow<PopupWindow> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtAbstractPopupWindow() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected VerticalFlowPanel getContentPanel() {

    return getToplevelWidget().getContentPanel();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(String title) {

    getToplevelWidget().setTitleText(title);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getWidthInPixel() {

    return getToplevelWidget().getOffsetWidth();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getHeightInPixel() {

    return getToplevelWidget().getOffsetHeight();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPosition(double x, double y) {

    getToplevelWidget().setPopupPosition((int) x, (int) y);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setResizable(boolean resizable) {

    // getToplevelWidget().set
    // getWidget().set
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isResizable() {

    // TODO Auto-generated method stub
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getPositionX() {

    return getToplevelWidget().getAbsoluteLeft();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getPositionY() {

    return getToplevelWidget().getAbsoluteTop();
  }

}
