/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterIntegerField;
import net.sf.mmm.client.ui.gwt.widgets.IntegerSpinBox;

/**
 * This is the implementation of {@link UiWidgetAdapterIntegerField} using GWT based on {@link IntegerSpinBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtIntegerField extends UiWidgetAdapterGwtNumberField<IntegerSpinBox, Integer>
    implements UiWidgetAdapterIntegerField {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtIntegerField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected IntegerSpinBox createActiveWidget() {

    return new IntegerSpinBox();
  }

}
