/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.window.adapter;

import net.sf.mmm.client.ui.api.common.Length;
import net.sf.mmm.client.ui.api.common.LengthProperty;
import net.sf.mmm.client.ui.api.event.EventType;
import net.sf.mmm.client.ui.base.widget.window.adapter.UiWidgetAdapterAbstractDialogWindow;
import net.sf.mmm.client.ui.gwt.widgets.PopupWindow;
import net.sf.mmm.client.ui.gwt.widgets.VerticalFlowPanel;
import net.sf.mmm.client.ui.impl.gwt.handler.event.EventAdapterGwt;
import net.sf.mmm.util.gwt.api.JavaScriptUtil;

import com.google.gwt.dom.client.Element;

/**
 * This is the implementation of
 * {@link net.sf.mmm.client.ui.base.widget.window.adapter.UiWidgetAdapterAbstractDialogWindow} using GWT based
 * on {@link PopupWindow}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterGwtAbstractDialogWindow extends UiWidgetAdapterGwtAbstractWindow<PopupWindow>
    implements UiWidgetAdapterAbstractDialogWindow {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtAbstractDialogWindow() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void applyEventAdapter(EventAdapterGwt adapter) {

    super.applyEventAdapter(adapter);
    getToplevelWidget().addCloseHandler(adapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setVisible(boolean visible, boolean programmatic) {

    EventAdapterGwt eventAdapter = getEventAdapter();
    if (visible) {
      if (programmatic && (eventAdapter != null)) {
        eventAdapter.setProgrammaticEventType(EventType.OPEN);
      }
      getToplevelWidget().show();
      // GWT has no OPEN events and this is the only way to open the
      if (eventAdapter != null) {
        eventAdapter.fireEvent(EventType.OPEN);
      }
    } else {
      if (programmatic && (eventAdapter != null)) {
        eventAdapter.setProgrammaticEventType(EventType.CLOSE);
      }
      getToplevelWidget().hide();
    }
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

    return new Length(JavaScriptUtil.getInstance().getComputedStyle(getToplevelWidget().getElement())
        .getPropertyValue(LengthProperty.WIDTH.getStyleName())).getAmount();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double getHeightInPixel() {

    return new Length(JavaScriptUtil.getInstance().getComputedStyle(getToplevelWidget().getElement())
        .getPropertyValue(LengthProperty.HEIGHT.getStyleName())).getAmount();
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
  public boolean isResizable() {

    return getToplevelWidget().isResizable();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setResizable(boolean resizable) {

    getToplevelWidget().setResizable(resizable);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMovable() {

    return getToplevelWidget().isMovable();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMovable(boolean movable) {

    getToplevelWidget().setMovable(movable);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isClosable() {

    return getToplevelWidget().isClosable();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setClosable(boolean closable) {

    getToplevelWidget().setClosable(closable);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMaximizable() {

    return getToplevelWidget().isMaximizable();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximizable(boolean maximizable) {

    getToplevelWidget().setMaximizable(maximizable);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMaximized() {

    return getToplevelWidget().isMaximized();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximized(boolean maximized) {

    getToplevelWidget().setMaximized(maximized);
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

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLength(LengthProperty property, Length length) {

    super.setLength(property, length);
    // getToplevelWidget().getElement().getStyle().setProperty(property.getMemberName(), length.toString());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Element getSizeElement() {

    return getContentPanel().getElement();
  }

}
