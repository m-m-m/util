/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.factory;

import java.time.LocalDate;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetLocalDateField;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetFactoryDatatype}
 * for the datatype {@link LocalDate}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiSingleWidgetFactoryDatatypeLocalDate extends AbstractUiSingleWidgetFactoryDatatype<LocalDate> {

  /**
   * The constructor.
   */
  public UiSingleWidgetFactoryDatatypeLocalDate() {

    super(LocalDate.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetField<LocalDate> create(UiContext context) {

    UiWidgetLocalDateField widget = context.getWidgetFactory().create(UiWidgetLocalDateField.class);
    return widget;
  }
}
