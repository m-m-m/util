/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.adapter;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteKeyboardFilter;
import net.sf.mmm.client.ui.api.feature.UiFeatureFocus;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventFocus;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterActive;
import net.sf.mmm.client.ui.impl.gwt.handler.event.FocusEventAdapterGwt;
import net.sf.mmm.client.ui.impl.gwt.handler.event.KeyboardFilterAdapter;
import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;

import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetAdapterActive} using GWT based on {@link Widget}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class UiWidgetAdapterGwtWidgetActive<WIDGET extends Widget> extends UiWidgetAdapterGwtWidget<WIDGET>
    implements UiWidgetAdapterActive<WIDGET>, AttributeWriteKeyboardFilter {

  /** @see #getKeyboardFilter() */
  private KeyboardFilterAdapter keyboardFilterAdapter;

  /** The {@link FocusEventAdapterGwt} */
  private FocusEventAdapterGwt focusEventAdapter;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtWidgetActive() {

    super();
  }

  /**
   * @return the {@link #getWidget() widget} as {@link Focusable} or <code>null</code> if NOT supported.
   */
  protected abstract Focusable getWidgetAsFocusable();

  /**
   * @return the {@link #getWidget() widget} as {@link HasKeyPressHandlers} or <code>null</code> if NOT
   *         supported.
   */
  protected abstract HasKeyPressHandlers getWidgetAsKeyPressHandlers();

  /**
   * @return the {@link #getWidget() widget} as {@link HasAllFocusHandlers} or <code>null</code> if NOT
   *         supported.
   */
  protected abstract HasAllFocusHandlers getWidgetAsHasAllFocusHandlers();

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setFocused(boolean focused) {

    Focusable focusWidget = getWidgetAsFocusable();
    if (focusWidget != null) {
      if (this.focusEventAdapter != null) {
        this.focusEventAdapter.setProgrammatic();
      }
      focusWidget.setFocus(focused);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFocusEventSender(final UiFeatureFocus source, final UiHandlerEventFocus sender) {

    if (this.focusEventAdapter != null) {
      throw new NlsIllegalStateException();
    }
    HasAllFocusHandlers focusWidget = getWidgetAsHasAllFocusHandlers();
    if (focusWidget != null) {
      this.focusEventAdapter = new FocusEventAdapterGwt(source, sender);
      focusWidget.addFocusHandler(this.focusEventAdapter);
      focusWidget.addBlurHandler(this.focusEventAdapter);
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
