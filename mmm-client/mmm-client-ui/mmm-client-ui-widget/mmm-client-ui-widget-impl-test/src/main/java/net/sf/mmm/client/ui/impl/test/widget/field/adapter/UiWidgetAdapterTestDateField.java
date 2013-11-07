/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field.adapter;

import java.time.LocalDate;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterDateField;

/**
 * This is the implementation of {@link UiWidgetAdapterDateField} for testing without a native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterTestDateField extends UiWidgetAdapterTestField<LocalDate, LocalDate> implements
    UiWidgetAdapterDateField {

  /**
   * The constructor.
   */
  public UiWidgetAdapterTestDateField() {

    super();
  }

}
