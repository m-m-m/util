/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter;

import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterInputField;

import com.google.gwt.user.client.ui.TextBox;

/**
 * This is the implementation of {@link net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterField} using
 * GWT based on {@link TextBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 * @param <VALUE> is the generic type of the changed value - typically {@link String}.
 */
public abstract class UiWidgetAdapterGwtTextBoxBase<WIDGET extends TextBox, VALUE> extends
    UiWidgetAdapterGwtFocusWidgetField<WIDGET, VALUE, String> implements
    UiWidgetAdapterInputField<WIDGET, VALUE, String> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtTextBoxBase() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumTextLength(int length) {

    getWidget().setMaxLength(length);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getMaximumTextLength() {

    return getWidget().getMaxLength();
  }

}
