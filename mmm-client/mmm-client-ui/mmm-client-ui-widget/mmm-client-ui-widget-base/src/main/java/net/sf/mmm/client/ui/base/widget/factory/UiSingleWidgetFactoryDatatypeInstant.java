/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.factory;

import java.time.Instant;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetInstantField;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetFactoryDatatype}
 * for the datatype {@link Instant}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiSingleWidgetFactoryDatatypeInstant extends AbstractUiSingleWidgetFactoryDatatype<Instant> {

  /**
   * The constructor.
   */
  public UiSingleWidgetFactoryDatatypeInstant() {

    super(Instant.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetField<Instant> create(UiContext context) {

    UiWidgetInstantField widget = context.getWidgetFactory().create(UiWidgetInstantField.class);
    return widget;
  }
}
