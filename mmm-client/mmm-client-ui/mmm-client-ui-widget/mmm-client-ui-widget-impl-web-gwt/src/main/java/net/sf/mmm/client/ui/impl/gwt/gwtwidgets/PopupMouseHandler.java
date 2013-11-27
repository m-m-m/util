/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import net.sf.mmm.client.ui.api.common.Rectangle;
import net.sf.mmm.util.lang.api.Direction;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Window;

/**
 * This class is the handler for mouse events to move an resize a {@link PopupWindow}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
class PopupMouseHandler implements MouseDownHandler, MouseUpHandler, MouseMoveHandler, Event.NativePreviewHandler {

  /** The {@link PopupWindow} to manage. */
  private final PopupWindow popupWindow;

  /** The resize {@link Direction} or <code>null</code> for move. */
  private final Direction resizeDirection;

  /** The initial x position while dragging the mouse. */
  private int mouseX;

  /** The initial y position while dragging the mouse. */
  private int mouseY;

  /** The current {@link Rectangle} of the {@link PopupWindow}. */
  private Rectangle popupRectangle;

  /** The current {@link Rectangle} of the browser. */
  private Rectangle browserRectangle;

  /** The minimum width of the {@link PopupWindow}. */
  private int minWidth;

  /** The minimum height of the {@link PopupWindow}. */
  private int minHeight;

  /**
   * The {@link HandlerRegistration} for the global mouse-listener registration while dragging or
   * <code>null</code>.
   */
  private HandlerRegistration registration;

  /**
   * The constructor.
   * 
   * @param resizeDirection is the resize {@link Direction} or <code>null</code> for move.
   * @param popupWindow is the {@link PopupWindow}.
   */
  public PopupMouseHandler(PopupWindow popupWindow, Direction resizeDirection) {

    super();
    this.popupWindow = popupWindow;
    this.resizeDirection = resizeDirection;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onPreviewNativeEvent(NativePreviewEvent event) {

    int eventType = event.getTypeInt();
    if (eventType == Event.ONMOUSEMOVE) {
      NativeEvent nativeEvent = event.getNativeEvent();
      onMouseMove(nativeEvent.getClientX(), nativeEvent.getClientY());
    } else if (eventType == Event.ONMOUSEUP) {
      onMouseUp(null);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onMouseDown(MouseDownEvent event) {

    if (!this.popupWindow.isResizable() && (this.resizeDirection != null)) {
      return;
    }
    if (!this.popupWindow.isMovable() && (this.resizeDirection == null)) {
      return;
    }
    this.mouseX = event.getClientX();
    this.mouseY = event.getClientY();
    this.popupRectangle = new Rectangle(this.popupWindow.getAbsoluteLeft(), this.popupWindow.getAbsoluteTop(),
        this.popupWindow.getOffsetWidth(), this.popupWindow.getOffsetHeight());

    // TODO: calculate from CSS values
    this.minWidth = 200;
    this.minHeight = 100;

    this.browserRectangle = new Rectangle(0, 0, Window.getClientWidth(), Window.getClientHeight());

    assert (this.registration == null);
    this.registration = Event.addNativePreviewHandler(this);
    // prevent other things such as text selection while dragging...
    event.preventDefault();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onMouseMove(MouseMoveEvent event) {

    onMouseMove(event.getClientX(), event.getClientY());
  }

  /**
   * @see #onMouseMove(MouseMoveEvent)
   * 
   * @param x is the mouse X position.
   * @param y is the mouse Y position.
   */
  private void onMouseMove(int x, int y) {

    int deltaX = x - this.mouseX;
    int deltaY = y - this.mouseY;

    Rectangle newRectangle;

    if (this.resizeDirection == null) {
      // move...
      Rectangle clippingArea = new Rectangle(0, 0, Window.getClientWidth() + this.popupRectangle.getWidth() - 32,
          Window.getClientHeight() + this.popupRectangle.getHeight() - 32);
      newRectangle = this.popupRectangle.moveBy(deltaX, deltaY).clipTo(clippingArea, true);
    } else {
      newRectangle = this.popupRectangle.resize(deltaX, deltaY, this.resizeDirection, this.minWidth, this.minHeight);
    }
    this.popupWindow.setPixelSize(newRectangle.getWidth(), newRectangle.getHeight());
    this.popupWindow.setPopupPosition(newRectangle.getX(), newRectangle.getY());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onMouseUp(MouseUpEvent event) {

    // DOM.releaseCapture(((Widget) event.getSource()).getElement());
    this.registration.removeHandler();
    this.registration = null;
  }
}
