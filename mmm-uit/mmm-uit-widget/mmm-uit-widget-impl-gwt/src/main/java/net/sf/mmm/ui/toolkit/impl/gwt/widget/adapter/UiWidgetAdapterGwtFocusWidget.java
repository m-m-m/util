/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteKeyboardFilter;
import net.sf.mmm.ui.toolkit.api.feature.UiFeatureFocus;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventFocus;
import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterWithFocus;
import net.sf.mmm.ui.toolkit.impl.gwt.handler.event.FocusEventAdapterGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.handler.event.KeyboardFilterAdapter;
import net.sf.mmm.util.filter.api.CharFilter;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;

import com.google.gwt.user.client.ui.FocusWidget;

/**
 * This is the implementation of {@link UiWidgetAdapterWithFocus} using GWT based on {@link FocusWidget}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class UiWidgetAdapterGwtFocusWidget<WIDGET extends FocusWidget> extends
    UiWidgetAdapterGwtWidget<WIDGET> implements UiWidgetAdapterWithFocus<WIDGET>, AttributeWriteKeyboardFilter {

  /** @see #getKeyboardFilter() */
  private KeyboardFilterAdapter keyboardFilterAdapter;

  /** The {@link FocusEventAdapterGwt} */
  private FocusEventAdapterGwt focusEventAdapter;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtFocusWidget() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeFromParent() {

    getWidget().removeFromParent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    getWidget().setEnabled(enabled);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFocused() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFocused(boolean focused) {

    if (this.focusEventAdapter != null) {
      this.focusEventAdapter.setProgrammatic();
    }
    getWidget().setFocus(focused);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFocusEventSender(final UiFeatureFocus source, final UiHandlerEventFocus sender) {

    if (this.focusEventAdapter != null) {
      throw new NlsIllegalStateException();
    }
    this.focusEventAdapter = new FocusEventAdapterGwt(source, sender);
    getWidget().addFocusHandler(this.focusEventAdapter);
    getWidget().addBlurHandler(this.focusEventAdapter);
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
      this.keyboardFilterAdapter.add(getWidget());
    }
  }
}
