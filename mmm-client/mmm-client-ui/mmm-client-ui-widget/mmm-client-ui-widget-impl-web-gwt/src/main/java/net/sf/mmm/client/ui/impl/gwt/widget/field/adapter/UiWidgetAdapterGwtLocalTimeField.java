/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import java.time.LocalTime;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterLocalTimeField;
import net.sf.mmm.client.ui.gwt.widgets.LocalTimeBox;

/**
 * This is the implementation of {@link UiWidgetAdapterLocalTimeField} using GWT based on {@link LocalTimeBox}
 * .
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtLocalTimeField extends
    UiWidgetAdapterGwtFieldValueBoxBase<LocalTimeBox, LocalTime, LocalTime> implements UiWidgetAdapterLocalTimeField {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtLocalTimeField() {

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
