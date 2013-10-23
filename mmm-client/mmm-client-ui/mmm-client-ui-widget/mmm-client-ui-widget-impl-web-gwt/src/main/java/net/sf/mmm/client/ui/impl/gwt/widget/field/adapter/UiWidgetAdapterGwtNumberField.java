/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteMaximumTextLength;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterRangeField;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.NumberBox;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterField} using GWT
 * based on {@link NumberBox}.
 * 
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterGwtNumberField<WIDGET extends NumberBox<VALUE>, VALUE extends Number> extends
    UiWidgetAdapterGwtFieldValueBoxBase<WIDGET, VALUE, VALUE> implements UiWidgetAdapterRangeField<VALUE>,
    AttributeWriteMaximumTextLength {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtNumberField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMinimumValue(VALUE minimum) {

    getActiveWidget().setMinimumValue(minimum);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumValue(VALUE maximum) {

    getActiveWidget().setMaximumValue(maximum);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumTextLength(int length) {

    getActiveWidget().setMaxLength(length);
  }

}
