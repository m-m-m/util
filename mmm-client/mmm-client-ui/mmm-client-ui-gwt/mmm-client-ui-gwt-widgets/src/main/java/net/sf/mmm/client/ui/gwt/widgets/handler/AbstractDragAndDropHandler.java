/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.gwt.widgets.handler;

import net.sf.mmm.util.gwt.api.GwtUtil;

import com.google.gwt.event.dom.client.DragEndEvent;
import com.google.gwt.event.dom.client.DragEndHandler;
import com.google.gwt.event.dom.client.DragOverEvent;
import com.google.gwt.event.dom.client.DragOverHandler;
import com.google.gwt.event.dom.client.DragStartEvent;
import com.google.gwt.event.dom.client.DragStartHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the abstract base class for an HTML5 drag and drop handler.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractDragAndDropHandler implements DragStartHandler, DragEndHandler, DropHandler,
    DragOverHandler, AbstractCustomHandler {

  /** @see #getWidget() */
  private Widget widget;

  /**
   * The constructor.
   */
  public AbstractDragAndDropHandler() {

    super();
  }

  /**
   * @return the widget
   */
  protected Widget getWidget() {

    return this.widget;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void register(Widget dragWidget, HandlerRegistrationCollector collector) {

    if (this.widget != null) {
      // already registered...
      throw new IllegalStateException();
    }
    this.widget = dragWidget;
    this.widget.getElement().setAttribute("draggable", "true");
    HandlerRegistration handlerRegistration = this.widget.addDomHandler(this, DragStartEvent.getType());
    if (collector != null) {
      collector.addHandlerRegistration(handlerRegistration);
    }
    handlerRegistration = this.widget.addDomHandler(this, DragEndEvent.getType());
    if (collector != null) {
      collector.addHandlerRegistration(handlerRegistration);
    }
    handlerRegistration = this.widget.addDomHandler(this, DragOverEvent.getType());
    if (collector != null) {
      collector.addHandlerRegistration(handlerRegistration);
    }
    handlerRegistration = this.widget.addDomHandler(this, DropEvent.getType());
    if (collector != null) {
      collector.addHandlerRegistration(handlerRegistration);
    }
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
  public void onDragStart(DragStartEvent event) {

    if (isActive()) {
      event.setData("text/plain", GwtUtil.getInstance().getId(this.widget));
    }
  }

  /**
   * {@inheritDoc}
   * 
   * <br/>
   * <b>ATTENTION:</b><br/>
   * This method is invoked on the listener registered on the widget that is currently under the dragged item
   * and NOT on the widget that is actually dragged around.
   */
  @Override
  public void onDragOver(DragOverEvent event) {

    // prevent default behavior (e.g. link to be clicked while dragging over)
    event.preventDefault();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onDrop(DropEvent event) {

    // prevent default behavior such as navigating to element-id as URL or other nonsense.
    event.preventDefault();
  }

}
