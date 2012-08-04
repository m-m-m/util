/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field.adapter;

import java.util.Date;

import net.sf.mmm.ui.toolkit.base.widget.field.adapter.UiWidgetAdapterDateField;

import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ValueBoxBase;
import com.google.gwt.user.datepicker.client.DateBox;

/**
 * This is the implementation of {@link net.sf.mmm.ui.toolkit.base.widget.field.adapter.UiWidgetAdapterField}
 * using GWT based on {@link DateBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the changed value - typically {@link Double}.
 */
public class UiWidgetAdapterGwtDateField<VALUE> extends UiWidgetAdapterGwtField<DateBox, VALUE, Date> implements
    UiWidgetAdapterDateField<DateBox, VALUE> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtDateField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected DateBox createWidget() {

    return new DateBox();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Focusable getWidgetAsFocusable() {

    return getWidget().getTextBox();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasAllFocusHandlers getWidgetAsHasAllFocusHandlers() {

    return getWidget().getTextBox();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasKeyPressHandlers getWidgetAsKeyPressHandlers() {

    return getWidget().getTextBox();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasChangeHandlers getWidgetAsHasChangeHandlers() {

    return getWidget().getTextBox();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasValue<Date> getWidgetAsHasValue() {

    return getWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ValueBoxBase<?> getWidgetAsValueBoxBase() {

    return getWidget().getTextBox();
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
  public void setMaximumTextLength(int length) {

    getWidget().getTextBox().setMaxLength(length);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getMaximumTextLength() {

    return getWidget().getTextBox().getMaxLength();
  }

}
