/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteKeyboardFilter;
import net.sf.mmm.ui.toolkit.impl.gwt.handler.event.FocusEventSenderGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.handler.event.KeyboardFilterAdapter;
import net.sf.mmm.util.filter.api.CharFilter;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.FocusWidget;

/**
 * This is the implementation of {@link net.sf.mmm.ui.toolkit.api.widget.atomic.UiWidgetField} using GWT based
 * on {@link FocusWidget}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class UiWidgetFieldGwtFocusWidget<VALUE, WIDGET extends FocusWidget> extends
    UiWidgetFieldGwt<VALUE, WIDGET> implements AttributeWriteKeyboardFilter {

  /** @see #getKeyboardFilter() */
  private KeyboardFilterAdapter keyboardFilterAdapter;

  /**
   * The constructor.
   * 
   * @param widget is the {@link #getWidget() widget}.
   */
  public UiWidgetFieldGwtFocusWidget(WIDGET widget) {

    super(widget);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final void registerFocusHandler(FocusHandler focusHandler, BlurHandler blurHandler) {

    getWidget().addFocusHandler(focusHandler);
    getWidget().addBlurHandler(blurHandler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setFocused(boolean focused) {

    FocusEventSenderGwt sender = getFocusEventSender();
    if (sender != null) {
      sender.setProgrammatic();
    }
    getWidget().setFocus(focused);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final void doSetEnabled(boolean enabled) {

    getWidget().setEnabled(enabled);
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
      this.keyboardFilterAdapter.setKeyboardFilter(keyboardFilter);
      this.keyboardFilterAdapter.add(getWidget());
    } else {
      this.keyboardFilterAdapter.setKeyboardFilter(keyboardFilter);
    }
  }

}
