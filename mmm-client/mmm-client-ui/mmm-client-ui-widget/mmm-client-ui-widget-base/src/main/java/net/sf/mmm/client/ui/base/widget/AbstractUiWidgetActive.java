/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventFocus;
import net.sf.mmm.client.ui.api.widget.UiWidgetActive;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.handler.event.FocusEventSender;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterActive;

/**
 * This is the abstract base implementation of a {@link #setFocused(boolean) focusable}
 * {@link net.sf.mmm.client.ui.api.widget.UiWidget}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <VALUE> is the generic type of the {@link #getValue() value}. Use {@link Void} for no value.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetActive<ADAPTER extends UiWidgetAdapterActive<?>, VALUE> extends
    AbstractUiWidgetReal<ADAPTER, VALUE> implements UiWidgetActive {

  /** @see #addFocusHandler(UiHandlerEventFocus) */
  private FocusEventSender focusEventSender;

  /** @see #getAccessKey() */
  private char accessKey;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetActive(AbstractUiContext context) {

    super(context);
    this.accessKey = ACCESS_KEY_NONE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.focusEventSender != null) {
      adapter.setFocusEventSender(this, this.focusEventSender);
    }
    if (this.accessKey != ACCESS_KEY_NONE) {
      adapter.setAccessKey(this.accessKey);
    }
  }

  /**
   * @return the {@link FocusEventSender} or <code>null</code> if NOT yet created.
   */
  protected final FocusEventSender getFocusEventSender() {

    return this.focusEventSender;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFocused(boolean focused) {

    if (hasWidgetAdapter()) {
      // if (this.focusEventSender != null) {
      // this.focusEventSender.setProgrammatic();
      // }
      getWidgetAdapter().setFocused(focused);
    } else if (this.focusEventSender != null) {
      this.focusEventSender.onFocusChange(this, true, !focused);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFocused() {

    if (this.focusEventSender != null) {
      return this.focusEventSender.isFocused();
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addFocusHandler(UiHandlerEventFocus handler) {

    if (this.focusEventSender == null) {
      this.focusEventSender = new FocusEventSender(this, getContext());
      if (hasWidgetAdapter()) {
        getWidgetAdapter().setFocusEventSender(this, this.focusEventSender);
      }
    }
    this.focusEventSender.addHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeFocusHandler(UiHandlerEventFocus handler) {

    if (this.focusEventSender != null) {
      return this.focusEventSender.removeHandler(handler);
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public char getAccessKey() {

    return this.accessKey;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAccessKey(char accessKey) {

    // TODO hohwille add AccessKeyManager to check for duplicate bindings and to implement easily for toolkits
    // no supporting this feature natively.
    this.accessKey = accessKey;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setAccessKey(accessKey);
    }
  }

}
