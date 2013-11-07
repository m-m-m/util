/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import java.util.Date;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterTextualInputField;

import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.datepicker.client.DateBox;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterField}
 * using GWT based on {@link DateBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @deprecated - use HTML5 via {@link UiWidgetAdapterGwtDateField}.
 */
@Deprecated
public class UiWidgetAdapterGwtDateBox extends UiWidgetAdapterGwtField<DateBox, Date, Date> implements
    UiWidgetAdapterTextualInputField<Date, Date> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtDateBox() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected DateBox createActiveWidget() {

    return new DateBox();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Focusable getWidgetAsFocusable() {

    return getActiveWidget().getTextBox();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasAllFocusHandlers getWidgetAsHasAllFocusHandlers() {

    return getActiveWidget().getTextBox();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasKeyPressHandlers getWidgetAsKeyPressHandlers() {

    return getActiveWidget().getTextBox();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasChangeHandlers getWidgetAsHasChangeHandlers() {

    return getActiveWidget().getTextBox();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasValue<Date> getWidgetAsTakesValue() {

    return getActiveWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    getActiveWidget().setEnabled(enabled);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumTextLength(int length) {

    getActiveWidget().getTextBox().setMaxLength(length);
  }

}
