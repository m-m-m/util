/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This is the implementation of {@link net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterBaseWindow}
 * using GWT based on {@link DialogBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class UiWidgetAdapterGwtBaseWindowDialogBox<WIDGET extends DialogBox> extends
    UiWidgetAdapterGwtBaseWindow<WIDGET> {

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
      this.contentPanel.insert(getWidget(child), index);
    } else {
      this.contentPanel.add(getWidget(child));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getWidthInPixel() {

    return getWidget().getOffsetWidth();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHeightInPixel() {

    return getWidget().getOffsetHeight();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(String title) {

    getWidget().setText(title);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPosition(int x, int y) {

    getWidget().setPopupPosition(x, y);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setResizable(boolean resizable) {

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
  public void setSizeInPixel(int width, int height) {

    getWidget().setPixelSize(width, height);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidthInPixel(int width) {

    // unsupported - we need to delegate to setPixelSize
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeightInPixel(int height) {

    // unsupported - we need to delegate to setPixelSize
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getPositionX() {

    return getWidget().getAbsoluteLeft();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getPositionY() {

    return getWidget().getAbsoluteTop();
  }

}
