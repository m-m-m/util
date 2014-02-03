/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import java.time.LocalTime;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterTimeField;
import net.sf.mmm.client.ui.gwt.widgets.LocalTimeBox;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterField}
 * using GWT based on {@link LocalTimeBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtTimeField extends
    UiWidgetAdapterGwtFieldValueBoxBase<LocalTimeBox, LocalTime, LocalTime> implements UiWidgetAdapterTimeField {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtTimeField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected LocalTimeBox createActiveWidget() {

    return new LocalTimeBox();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumTextLength(int length) {

    getActiveWidget().setMaximumTextLength(length);
  }

}
