/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter;

import java.util.Date;

import net.sf.mmm.ui.toolkit.api.feature.UiFeatureValue;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventValueChange;
import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterInputField;

import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.datepicker.client.DateBox;

/**
 * This is the implementation of {@link net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterField} using
 * GWT based on {@link DateBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the changed value - typically {@link Double}.
 */
public class UiWidgetAdapterGwtDateBox<VALUE> extends UiWidgetAdapterGwtWidgetWithFocus<DateBox> implements
    UiWidgetAdapterInputField<DateBox, VALUE, Date> {

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
  public void setValue(Date value) {

    getWidget().setValue(value, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Date getValue() {

    return getWidget().getValue();
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

  /**
   * {@inheritDoc}
   */
  @Override
  public void setChangeEventSender(UiFeatureValue<VALUE> source, UiHandlerEventValueChange<VALUE> sender) {

    // TODO Auto-generated method stub

  }

}
