/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets;

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
import com.google.gwt.user.client.ui.Widget;

/**
 * This class is the handler for mouse events to move an resize a {@link PopupWindow}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractMouseDragHandler implements MouseDownHandler, MouseUpHandler, MouseMoveHandler,
    Event.NativePreviewHandler {

  /** The initial x position while dragging the mouse. */
  private int mouseX;

  /** The initial y position while dragging the mouse. */
  private int mouseY;

  /**
   * The {@link HandlerRegistration} for the global mouse-listener registration while dragging or
   * <code>null</code>.
   */
  private HandlerRegistration registration;

  /**
   * The constructor.
   */
  public AbstractMouseDragHandler() {

    super();
  }

  /**
   * @return the mouseX
   */
  public int getMouseX() {

    return this.mouseX;
  }

  /**
   * @return the mouseY
   */
  public int getMouseY() {

    return this.mouseY;
  }

  /**
   * This method can be overridden for dynamic activation of this handler.
   * 
   * @return <code>true</code> if this handler is active, <code>false</code> if it is inactive (ignores all
   *         events and does nothing).
   */
  protected boolean isActive() {

    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void onMouseDown(MouseDownEvent event) {

    if (!isActive()) {
      return;
    }
    this.mouseX = event.getClientX();
    this.mouseY = event.getClientY();

    initializeOnMouseDown();

    // assert (this.registration == null);
    this.registration = Event.addNativePreviewHandler(this);
    // prevent other things such as text selection while dragging...
    event.preventDefault();
  }

  /**
   * Override to add initialization logic to {@link #onMouseDown(MouseDownEvent)}.
   */
  protected void initializeOnMouseDown() {

    // Nothing by default...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onPreviewNativeEvent(NativePreviewEvent event) {

    int eventType = event.getTypeInt();
    if (eventType == Event.ONMOUSEMOVE) {
      NativeEvent nativeEvent = event.getNativeEvent();
      int button = nativeEvent.getButton();
      if (button == NativeEvent.BUTTON_LEFT) {
        onMouseMove(nativeEvent.getClientX() - this.mouseX, nativeEvent.getClientY() - this.mouseY, nativeEvent);
      } else {
        onMouseUp(null);
      }
    } else if (eventType == Event.ONMOUSEUP) {
      onMouseUp(null);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onMouseMove(MouseMoveEvent event) {

    onMouseMove(event.getClientX() - this.mouseX, event.getClientY() - this.mouseY, event.getNativeEvent());
  }

  /**
   * @see #onMouseMove(MouseMoveEvent)
   * 
   * @param deltaX is the relative movement of the mouse position on X-axis.
   * @param deltaY is the relative movement of the mouse position on Y-axis.
   * @param nativeEvent is the {@link NativeEvent} that triggered this invocation.
   */
  protected abstract void onMouseMove(int deltaX, int deltaY, NativeEvent nativeEvent);

  /**
   * {@inheritDoc}
   */
  @Override
  public void onMouseUp(MouseUpEvent event) {

    // DOM.releaseCapture(((Widget) event.getSource()).getElement());
    if (this.registration != null) {
      this.registration.removeHandler();
    }
    this.registration = null;
  }

  /**
   * This method registers this mouse drag handler in the given {@link Widget}. It will also ensure that the
   * widget {@link Widget#sinkEvents(int) sends} the required mouse events.
   * 
   * @param widget is the {@link Widget} where to register this handler.
   * @return the {@link HandlerRegistration} that may be used to {@link HandlerRegistration#removeHandler()
   *         remove} the handler.
   */
  public HandlerRegistration registerMouseDragHandler(Widget widget) {

    widget.sinkEvents(Event.ONMOUSEDOWN);
    HandlerRegistration handlerRegistration = widget.addDomHandler(this, MouseDownEvent.getType());
    return handlerRegistration;
  }
}
