/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.javafx.widget.window.adapter;

import javafx.stage.Window;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.base.widget.window.adapter.UiWidgetAdapterBaseWindow;
import net.sf.mmm.client.ui.impl.javafx.widget.adapter.UiWidgetAdapterJavaFx;

/**
 * This is the implementation of {@link UiWidgetAdapterBaseWindow} using JavaFx based on {@link Window}.
 * 
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterJavaFxBaseWindow<WIDGET extends Window> extends UiWidgetAdapterJavaFx<WIDGET>
    implements UiWidgetAdapterBaseWindow {

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
  public String getWidth() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getHeight() {

    // TODO Auto-generated method stub
    return null;
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
  public void setWidth(String width) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeight(String height) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSize(String width, String height) {

    // TODO Auto-generated method stub

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
  public void setSizeInPixel(int width, int height) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidthInPixel(int width) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeightInPixel(int height) {

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
  public int getPositionX() {

    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getPositionY() {

    // TODO Auto-generated method stub
    return 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setResizable(boolean resizable) {

    // TODO Auto-generated method stub

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
  public void setPosition(int x, int y) {

    getToplevelWidget().setX(x);
    getToplevelWidget().setY(y);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getWidthInPixel() {

    return (int) getToplevelWidget().getWidth();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getHeightInPixel() {

    return (int) getToplevelWidget().getHeight();
  }

}
