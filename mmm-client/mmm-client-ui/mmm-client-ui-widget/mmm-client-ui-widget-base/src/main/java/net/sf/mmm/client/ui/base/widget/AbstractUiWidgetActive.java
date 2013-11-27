/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.event.UiEventFocusGain;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventFocus;
import net.sf.mmm.client.ui.api.widget.UiWidgetActive;
import net.sf.mmm.client.ui.base.binding.UiAccessKeyBinding;
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
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetActive(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
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
  public boolean setFocused() {

    setFocused(true);
    return true;
  }

  /**
   * @see #setFocused()
   * 
   * @param programmatic - see {@link net.sf.mmm.client.ui.api.event.UiEvent#isProgrammatic()}.
   */
  protected void setFocused(boolean programmatic) {

    if (hasWidgetAdapter()) {
      getWidgetAdapter().setFocused();
    } else {
      fireEvent(new UiEventFocusGain(this, programmatic));
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

    UiAccessKeyBinding accessKeyBinding = getContext().getAccessKeyBinding();
    if ((accessKeyBinding != null) && (this.accessKey != ACCESS_KEY_NONE)) {
      accessKeyBinding.unbindAccessKey(this.accessKey, this);
    }
    this.accessKey = accessKey;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setAccessKey(accessKey);
    }
    if ((accessKeyBinding != null) && (this.accessKey != ACCESS_KEY_NONE)) {
      accessKeyBinding.bindAccessKey(this.accessKey, this);
    }
  }

  /**
   * 
   * @param programmatic - <code>true</code> if the access key was "pressed" by the program,
   *        <code>false</code> if performed by the end-user.
   */
  public void onAccessKeyPressed(boolean programmatic) {

    setFocused(programmatic);
  }

}
