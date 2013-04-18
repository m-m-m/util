/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import java.util.Date;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterDateField;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtDateField.Html5DateField;
import net.sf.mmm.util.gwt.api.JavaScriptUtil;

import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.TextBox;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterField}
 * using GWT based on {@link Html5DateField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtDateField extends UiWidgetAdapterGwtDateBasedStringField<Html5DateField> implements
    UiWidgetAdapterDateField, TakesValue<Date> {

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
  protected boolean hasTime() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Html5DateField createActiveWidget() {

    return new Html5DateField();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Focusable getWidgetAsFocusable() {

    return getActiveWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasAllFocusHandlers getWidgetAsHasAllFocusHandlers() {

    return getActiveWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasKeyPressHandlers getWidgetAsKeyPressHandlers() {

    return getActiveWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasChangeHandlers getWidgetAsHasChangeHandlers() {

    return getActiveWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TakesValue<Date> getWidgetAsTakesValue() {

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Date getValue() {

    String text = getActiveWidget().getValue();
    if ((text == null) || (text.isEmpty())) {
      return null;
    }
    return convertStringToDate(text);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(Date value) {

    String text = "";
    if (value != null) {
      text = convertDateToString(value);
    }
    getActiveWidget().setValue(text, false);
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

    getActiveWidget().setMaxLength(length);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getMaximumTextLength() {

    return getActiveWidget().getMaxLength();
  }

  /**
   * HTML5 based date field based on {@link TextBox}.
   */
  public static class Html5DateField extends TextBox {

    /**
     * The constructor.
     */
    public Html5DateField() {

      super(JavaScriptUtil.getInstance().createInputElement("date"));
    }

  }

}
