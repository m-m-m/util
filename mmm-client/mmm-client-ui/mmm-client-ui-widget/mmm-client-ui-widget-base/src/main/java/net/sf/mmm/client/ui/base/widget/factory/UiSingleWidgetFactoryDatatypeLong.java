/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.factory;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetLongField;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetFactoryDatatype}
 * for the datatype {@link Long}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiSingleWidgetFactoryDatatypeLong extends AbstractUiSingleWidgetFactoryDatatype<Long> {

  /**
   * The constructor.
   */
  public UiSingleWidgetFactoryDatatypeLong() {

    super(Long.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetField<Long> create(UiContext context) {

    return context.getWidgetFactory().create(UiWidgetLongField.class);
  }
}
