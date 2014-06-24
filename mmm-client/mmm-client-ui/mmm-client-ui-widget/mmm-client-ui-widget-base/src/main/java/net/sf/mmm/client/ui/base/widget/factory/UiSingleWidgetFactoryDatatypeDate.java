/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.factory;

import java.util.Date;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetDateField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetFactoryDatatype}
 * for the datatype {@link Date}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiSingleWidgetFactoryDatatypeDate extends AbstractUiSingleWidgetFactoryDatatype<Date> {

  /**
   * The constructor.
   */
  public UiSingleWidgetFactoryDatatypeDate() {

    super(Date.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetField<Date> create(UiContext context) {

    UiWidgetDateField widget = context.getWidgetFactory().create(UiWidgetDateField.class);
    return widget;
  }
}
