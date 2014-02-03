/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterDoubleField;
import net.sf.mmm.client.ui.gwt.widgets.DoubleSpinBox;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterField} using GWT
 * based on {@link DoubleSpinBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtDoubleField extends UiWidgetAdapterGwtNumberField<DoubleSpinBox, Double> implements
    UiWidgetAdapterDoubleField {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtDoubleField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected DoubleSpinBox createActiveWidget() {

    return new DoubleSpinBox();
  }

}
