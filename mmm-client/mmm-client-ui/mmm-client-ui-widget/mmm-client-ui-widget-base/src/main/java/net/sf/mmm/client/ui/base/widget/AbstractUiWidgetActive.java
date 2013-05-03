/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.common.EventType;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventFocus;
import net.sf.mmm.client.ui.api.widget.UiWidgetActive;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterActive;

/**
 * This is the abstract base implementation of a {@link #setFocused() focusable}
 * {@link net.sf.mmm.client.ui.api.widget.UiWidget}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <VALUE> is the generic type of the {@link #getValue() value}. Use {@link Void} for no value.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetActive<ADAPTER extends UiWidgetAdapterActive, VALUE> extends
    AbstractUiWidgetNative<ADAPTER, VALUE> implements UiWidgetActive {

  /** @see #getAccessKey() */
  private char accessKey;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetActive(UiContext context) {

    super(context);
    this.accessKey = ACCESS_KEY_NONE;
    // ensure EventSender gets created and will be attached to focus events...
    getEventSender();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.accessKey != ACCESS_KEY_NONE) {
      adapter.setAccessKey(this.accessKey);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFocused() {

    if (hasWidgetAdapter()) {
      getWidgetAdapter().setFocused();
    } else {
      fireEvent(EventType.FOCUS_GAIN, true);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFocused() {

    return getEventSender().isFocused();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addFocusHandler(UiHandlerEventFocus handler) {

    addEventHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeFocusHandler(UiHandlerEventFocus handler) {

    return removeEventHandler(handler);
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
