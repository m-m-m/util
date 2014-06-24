/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.factory;

import java.time.LocalTime;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetLocalTimeField;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetFactoryDatatype}
 * for the datatype {@link LocalTime}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiSingleWidgetFactoryDatatypeLocalTime extends AbstractUiSingleWidgetFactoryDatatype<LocalTime> {

  /**
   * The constructor.
   */
  public UiSingleWidgetFactoryDatatypeLocalTime() {

    super(LocalTime.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetField<LocalTime> create(UiContext context) {

    UiWidgetLocalTimeField widget = context.getWidgetFactory().create(UiWidgetLocalTimeField.class);
    return widget;
  }
}
