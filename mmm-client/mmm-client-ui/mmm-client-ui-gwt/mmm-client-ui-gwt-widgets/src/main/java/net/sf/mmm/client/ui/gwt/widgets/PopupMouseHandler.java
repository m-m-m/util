/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

import net.sf.mmm.client.ui.api.common.LengthProperty;
import net.sf.mmm.client.ui.api.common.Rectangle;
import net.sf.mmm.client.ui.gwt.widgets.handler.AbstractMouseDragHandler;
import net.sf.mmm.util.lang.api.Direction;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.user.client.Window;

/**
 * This class is the handler for mouse events to move an resize a {@link PopupWindow}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
class PopupMouseHandler extends AbstractMouseDragHandler {

  /** The {@link PopupWindow} to manage. */
  private final PopupWindow popupWindow;

  /** The resize {@link Direction} or <code>null</code> for move. */
  private final Direction resizeDirection;

  /** The current {@link Rectangle} of the {@link PopupWindow}. */
  private Rectangle popupRectangle;

  /** The minimum width of the {@link PopupWindow}. */
  private int minWidth;

  /** The minimum height of the {@link PopupWindow}. */
  private int minHeight;

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
  protected boolean isActive() {

    if (this.resizeDirection == null) {
      if (!this.popupWindow.isMovable() || this.popupWindow.isMaximized()) {
        return false;
      }
    } else {
      if (!this.popupWindow.isResizable() || this.popupWindow.isMaximized()) {
        return false;
      }
    }
    return super.isActive();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeOnMouseDown(MouseDownEvent event) {

    super.initializeOnMouseDown(event);
    this.popupRectangle = new Rectangle(this.popupWindow.getAbsoluteLeft(), this.popupWindow.getAbsoluteTop(),
        this.popupWindow.getOffsetWidth(), this.popupWindow.getOffsetHeight());

    int clientWidth = Window.getClientWidth();
    int clientHeight = Window.getClientHeight();
    this.minWidth = (int) LengthProperty.MIN_WIDTH.getLengthInPixel(this.popupWindow, clientWidth);
    this.minHeight = (int) LengthProperty.MIN_HEIGHT.getLengthInPixel(this.popupWindow, clientHeight);
    if (this.minWidth == 0) {
      this.minWidth = clientWidth / 10;
    }
    if (this.minHeight == 0) {
      this.minHeight = clientHeight / 10;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onMouseMove(int deltaX, int deltaY, NativeEvent nativeEvent) {

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

}
