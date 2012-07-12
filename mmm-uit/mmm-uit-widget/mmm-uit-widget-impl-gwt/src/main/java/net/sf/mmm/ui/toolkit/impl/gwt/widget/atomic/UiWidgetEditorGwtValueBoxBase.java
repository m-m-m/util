/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteKeyboardFilter;
import net.sf.mmm.ui.toolkit.impl.gwt.handler.event.FocusEventSenderGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.handler.event.KeyboardFilterAdapter;
import net.sf.mmm.util.filter.api.CharFilter;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.ValueBoxBase;

/**
 * This is the implementation of {@link net.sf.mmm.ui.toolkit.api.widget.atomic.UiWidgetEditor} using GWT
 * based on {@link ValueBoxBase}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 */
public abstract class UiWidgetEditorGwtValueBoxBase<VALUE, WIDGET extends ValueBoxBase<VALUE>> extends
    UiWidgetEditorGwt<VALUE, WIDGET> implements AttributeWriteKeyboardFilter {

  /** @see #getKeyboardFilter() */
  private KeyboardFilterAdapter keyboardFilterAdapter;

  /**
   * The constructor.
   * 
   * @param widget is the active widget.
   */
  public UiWidgetEditorGwtValueBoxBase(WIDGET widget) {

    super(widget);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void registerChangeHandler(ChangeHandler handler) {

    getActiveWidget().addChangeHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void registerFocusHandler(FocusHandler focusHandler, BlurHandler blurHandler) {

    getActiveWidget().addFocusHandler(focusHandler);
    getActiveWidget().addBlurHandler(blurHandler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFocused(boolean focused) {

    FocusEventSenderGwt sender = getFocusEventSender();
    if (sender != null) {
      sender.setProgrammatic();
    }
    getActiveWidget().setFocus(focused);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetValue(VALUE value) {

    getActiveWidget().setValue(value, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getValue() {

    return getActiveWidget().getValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetEnabled(boolean enabled) {

    getActiveWidget().setEnabled(enabled);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CharFilter getKeyboardFilter() {

    if (this.keyboardFilterAdapter != null) {
      return this.keyboardFilterAdapter.getKeyboardFilter();
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setKeyboardFilter(CharFilter keyboardFilter) {

    if (keyboardFilter == null) {
      if (this.keyboardFilterAdapter != null) {
        this.keyboardFilterAdapter.remove();
      }
      return;
    }
    if (this.keyboardFilterAdapter == null) {
      this.keyboardFilterAdapter = new KeyboardFilterAdapter();
      this.keyboardFilterAdapter.setKeyboardFilter(keyboardFilter);
      this.keyboardFilterAdapter.add(getActiveWidget());
    } else {
      this.keyboardFilterAdapter.setKeyboardFilter(keyboardFilter);
    }
  }

}
