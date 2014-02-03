/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterLongField;
import net.sf.mmm.client.ui.gwt.widgets.LongSpinBox;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterField} using GWT
 * based on {@link LongSpinBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtLongField extends UiWidgetAdapterGwtNumberField<LongSpinBox, Long> implements
    UiWidgetAdapterLongField {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtLongField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected LongSpinBox createActiveWidget() {

    return new LongSpinBox();
  }

}
