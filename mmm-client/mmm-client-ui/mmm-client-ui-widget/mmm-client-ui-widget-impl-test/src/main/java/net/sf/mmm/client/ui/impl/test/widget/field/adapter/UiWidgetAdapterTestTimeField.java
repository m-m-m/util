/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field.adapter;

import java.time.LocalTime;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterTimeField;

/**
 * This is the implementation of {@link UiWidgetAdapterTimeField} for testing without a native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterTestTimeField extends UiWidgetAdapterTestField<LocalTime, LocalTime> implements
    UiWidgetAdapterTimeField {

  /**
   * The constructor.
   */
  public UiWidgetAdapterTestTimeField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected LocalTime convertValueFromString(String stringValue) {

    return LocalTime.parse(stringValue);
  }

}
