/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.window.adapter;

import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This is the implementation of
 * {@link net.sf.mmm.client.ui.base.widget.window.adapter.UiWidgetAdapterBaseWindow} using GWT based on
 * {@link DialogBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterGwtBaseWindowDialogBox extends UiWidgetAdapterGwtBaseWindow<DialogBox> {

  /** The container for the children. */
  private final VerticalPanel contentPanel;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtBaseWindowDialogBox() {

    super();
    this.contentPanel = new VerticalPanel();
    this.contentPanel.setWidth("100%");
  }

  /**
   * @return the content panel.
   */
  @Override
  protected VerticalPanel getContentPanel() {

    return this.contentPanel;
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
  public void addChild(UiWidgetRegular child, int index) {

    if (index >= 0) {
      this.contentPanel.insert(getToplevelWidget(child), index);
    } else {
      this.contentPanel.add(getToplevelWidget(child));
    }
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
  public void setTitle(String title) {

    getToplevelWidget().setText(title);
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
