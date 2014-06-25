/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field.adapter;

import java.util.Date;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterDateField;
import net.sf.mmm.util.date.base.Iso8601UtilLimitedImpl;

/**
 * This is the implementation of {@link UiWidgetAdapterDateField} for testing without a native toolkit.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterTestDateField extends UiWidgetAdapterTestField<Date, Date> implements
    UiWidgetAdapterDateField {

  /**
   * The constructor.
   */
  public UiWidgetAdapterTestDateField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Date convertValueFromString(String stringValue) {

    // TODO: this is not the same behavior as native widget (e.g. GWT)
    return Iso8601UtilLimitedImpl.getInstance().parseDate(stringValue);
  }

}
