/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field.adapter;

import java.time.LocalTime;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterLocalTimeField;

/**
 * This is the implementation of {@link UiWidgetAdapterLocalTimeField} for testing without a native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterTestLocalTimeField extends UiWidgetAdapterTestField<LocalTime, LocalTime> implements
    UiWidgetAdapterLocalTimeField {

  /**
   * The constructor.
   */
  public UiWidgetAdapterTestLocalTimeField() {

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
