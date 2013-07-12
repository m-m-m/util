/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterTextualInputField;

import com.google.gwt.user.client.ui.ValueBox;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterField}
 * using GWT based on {@link ValueBox}.
 * 
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 * @param <VALUE> is the generic type of the changed value.
 * @param <ADAPTER_VALUE> is the generic type of the {@link #getValue() value} of the adapted
 *        {@link #getToplevelWidget() widget}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterGwtFieldValueBox<WIDGET extends ValueBox<ADAPTER_VALUE>, VALUE, ADAPTER_VALUE>
    extends UiWidgetAdapterGwtFieldValueBoxBase<WIDGET, VALUE, ADAPTER_VALUE> implements
    UiWidgetAdapterTextualInputField<VALUE, ADAPTER_VALUE> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtFieldValueBox() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumTextLength(int length) {

    getActiveWidget().setMaxLength(length);
  }

}
