/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.javafx.widget.window.adapter;

import javafx.stage.Window;
import net.sf.mmm.client.ui.api.common.Length;
import net.sf.mmm.client.ui.api.common.SizeUnit;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.base.widget.window.adapter.UiWidgetAdapterAbstractWindow;
import net.sf.mmm.client.ui.impl.javafx.widget.adapter.UiWidgetAdapterJavaFx;

/**
 * This is the implementation of {@link UiWidgetAdapterAbstractWindow} using JavaFx based on {@link Window}.
 * 
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterJavaFxBaseWindow<WIDGET extends Window> extends UiWidgetAdapterJavaFx<WIDGET>
    implements UiWidgetAdapterAbstractWindow {

  /**
   * The constructor.
   */
  public UiWidgetAdapterJavaFxBaseWindow() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setVisible(boolean visible) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Length getWidth() {

    return Length.valueOfPixel(getToplevelWidget().getWidth());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Length getHeight() {

    return Length.valueOfPixel(getToplevelWidget().getHeight());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    // TODO hohwille pass to children...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setId(String id) {

    // ID is not supported and will be ignored...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidth(Length width) {

    assert (width.getUnit() == SizeUnit.PIXEL);
    getToplevelWidget().setWidth(width.getAmount());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeight(Length height) {

    assert (height.getUnit() == SizeUnit.PIXEL);
    getToplevelWidget().setHeight(height.getAmount());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAttribute(String name, String value) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setTitle(String title) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getPositionX() {

    return getToplevelWidget().getX();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getPositionY() {

    return getToplevelWidget().getY();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(UiWidgetRegular child, int index) {

    // getToplevelWidget().get
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeChild(UiWidgetRegular child, int index) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPosition(double x, double y) {

    getToplevelWidget().setX(x);
    getToplevelWidget().setY(y);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getWidthInPixel() {

    return getToplevelWidget().getWidth();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getHeightInPixel() {

    return getToplevelWidget().getHeight();
  }

}
