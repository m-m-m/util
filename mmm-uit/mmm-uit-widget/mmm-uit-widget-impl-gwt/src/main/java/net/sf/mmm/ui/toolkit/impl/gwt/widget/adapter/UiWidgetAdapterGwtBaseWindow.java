/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular;
import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterBaseWindow;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetAdapterBaseWindow} using GWT based on {@link DialogBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class UiWidgetAdapterGwtBaseWindow<WIDGET extends DialogBox> extends
    UiWidgetAdapterGwtSingleComposite<WIDGET, UiWidgetRegular> implements UiWidgetAdapterBaseWindow<WIDGET> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtBaseWindow() {

    super();
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
  public void setChild(UiWidgetRegular child) {

    Widget widget = null;
    if (child != null) {
      widget = getWidget(child);
    }
    getWidget().setWidget(widget);
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
