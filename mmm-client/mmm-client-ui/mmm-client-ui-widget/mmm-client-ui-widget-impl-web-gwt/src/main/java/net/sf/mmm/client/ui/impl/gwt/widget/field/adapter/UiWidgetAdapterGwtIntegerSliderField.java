/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterIntegerSliderField;
import net.sf.mmm.client.ui.gwt.widgets.IntegerRangeBox;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterField} using GWT
 * based on {@link IntegerRangeBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtIntegerSliderField extends UiWidgetAdapterGwtNumberField<IntegerRangeBox, Integer>
    implements UiWidgetAdapterIntegerSliderField {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtIntegerSliderField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected IntegerRangeBox createActiveWidget() {

    return new IntegerRangeBox();
  }

}
