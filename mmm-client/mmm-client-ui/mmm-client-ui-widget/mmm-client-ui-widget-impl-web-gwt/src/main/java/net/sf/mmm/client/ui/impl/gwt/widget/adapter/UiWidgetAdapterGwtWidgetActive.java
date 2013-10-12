/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.adapter;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteKeyboardFilter;
import net.sf.mmm.client.ui.api.event.EventType;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterActive;
import net.sf.mmm.client.ui.impl.gwt.handler.event.EventAdapterGwt;
import net.sf.mmm.client.ui.impl.gwt.handler.event.KeyboardFilterAdapter;
import net.sf.mmm.util.filter.api.CharFilter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.HasFocusHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetAdapterActive} using GWT based on {@link Widget}.
 * 
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterGwtWidgetActive<WIDGET extends Widget> extends UiWidgetAdapterGwtWidget<WIDGET>
    implements UiWidgetAdapterActive, AttributeWriteKeyboardFilter {

  /** @see #getKeyboardFilter() */
  private KeyboardFilterAdapter keyboardFilterAdapter;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtWidgetActive() {

    super();
  }

  /**
   * @return the {@link #getToplevelWidget() widget} as {@link Focusable} or <code>null</code> if NOT
   *         supported.
   */
  protected abstract Focusable getWidgetAsFocusable();

  /**
   * @return the {@link #getToplevelWidget() widget} as {@link HasKeyPressHandlers} or <code>null</code> if
   *         NOT supported.
   */
  protected abstract HasKeyPressHandlers getWidgetAsKeyPressHandlers();

  /**
   * @return the {@link #getToplevelWidget() widget} as {@link HasAllFocusHandlers} or <code>null</code> if
   *         NOT supported.
   */
  protected abstract HasAllFocusHandlers getWidgetAsHasAllFocusHandlers();

  /**
   * @return the {@link #getToplevelWidget() widget} as {@link HasFocusHandlers} or <code>null</code> if NOT
   *         supported.
   */
  protected HasFocusHandlers getWidgetAsHasFocusHandlers() {

    return getWidgetAsHasAllFocusHandlers();
  }

  /**
   * @return the {@link #getToplevelWidget() widget} as {@link HasBlurHandlers} or <code>null</code> if NOT
   *         supported.
   */
  protected HasBlurHandlers getWidgetAsHasBlurHandlers() {

    return getWidgetAsHasAllFocusHandlers();
  }

  /**
   * Registers the given <code>handler</code> for click events.
   * 
   * @param handler is the {@link ClickHandler} to register.
   * @param widget is the {@link Widget} where to register the {@link ClickHandler}.
   * @return the GWT {@link HandlerRegistration}.
   */
  protected HandlerRegistration setClickEventSender(ClickHandler handler, Widget widget) {

    HandlerRegistration registration = widget.addDomHandler(handler, ClickEvent.getType());
    return registration;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean setFocused() {

    Focusable focusWidget = getWidgetAsFocusable();
    if (focusWidget != null) {
      EventAdapterGwt eventAdapter = getEventAdapter();
      if (eventAdapter != null) {
        eventAdapter.setProgrammaticEventType(EventType.FOCUS_GAIN);
      }
      focusWidget.setFocus(true);
      return true;
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void applyEventAdapterForClick(EventAdapterGwt adapter) {

    getToplevelWidget().addDomHandler(adapter, ClickEvent.getType());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void applyEventAdapterForFocus(EventAdapterGwt adapter) {

    HasFocusHandlers focusHandlers = getWidgetAsHasFocusHandlers();
    if (focusHandlers != null) {
      focusHandlers.addFocusHandler(adapter);
    }
    HasBlurHandlers blurHandlers = getWidgetAsHasBlurHandlers();
    if (blurHandlers != null) {
      blurHandlers.addBlurHandler(adapter);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final CharFilter getKeyboardFilter() {

    if (this.keyboardFilterAdapter != null) {
      return this.keyboardFilterAdapter.getKeyboardFilter();
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setKeyboardFilter(CharFilter keyboardFilter) {

    HasKeyPressHandlers widget = getWidgetAsKeyPressHandlers();
    if (widget == null) {
      return;
    }
    if (keyboardFilter == null) {
      if (this.keyboardFilterAdapter != null) {
        this.keyboardFilterAdapter.remove();
      }
      return;
    }
    if (this.keyboardFilterAdapter == null) {
      this.keyboardFilterAdapter = new KeyboardFilterAdapter();
    }
    this.keyboardFilterAdapter.setKeyboardFilter(keyboardFilter);
    if (this.keyboardFilterAdapter.isRemoved()) {
      this.keyboardFilterAdapter.add(widget);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAccessKey(char accessKey) {

    getWidgetAsFocusable().setAccessKey(accessKey);
  }

}
