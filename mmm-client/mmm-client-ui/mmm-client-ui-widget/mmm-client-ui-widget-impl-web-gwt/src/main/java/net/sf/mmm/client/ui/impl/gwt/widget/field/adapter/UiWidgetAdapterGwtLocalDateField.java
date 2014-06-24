/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import java.time.LocalDate;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterLocalDateField;
import net.sf.mmm.client.ui.gwt.widgets.LocalDateBox;

/**
 * This is the implementation of {@link UiWidgetAdapterLocalDateField} using GWT based on {@link LocalDateBox}
 * .
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtLocalDateField extends
    UiWidgetAdapterGwtFieldValueBoxBase<LocalDateBox, LocalDate, LocalDate> implements UiWidgetAdapterLocalDateField {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtLocalDateField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected LocalDateBox createActiveWidget() {

    return new LocalDateBox();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumTextLength(int length) {

    getActiveWidget().setMaximumTextLength(length);
  }

}
