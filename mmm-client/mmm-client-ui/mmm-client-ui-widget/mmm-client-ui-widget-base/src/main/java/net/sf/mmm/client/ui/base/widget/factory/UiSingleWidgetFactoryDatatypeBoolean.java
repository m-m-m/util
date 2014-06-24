/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.factory;

import java.util.Arrays;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetRadioButtonsField;
import net.sf.mmm.util.lang.base.BooleanFormatter;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.api.widget.factory.UiSingleWidgetFactoryDatatype}
 * for the datatype {@link Boolean}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiSingleWidgetFactoryDatatypeBoolean extends AbstractUiSingleWidgetFactoryDatatype<Boolean> {

  /**
   * The constructor.
   */
  public UiSingleWidgetFactoryDatatypeBoolean() {

    super(Boolean.class);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetField<Boolean> create(UiContext context) {

    UiWidgetRadioButtonsField<Boolean> radioButtons = context.getWidgetFactory()
        .create(UiWidgetRadioButtonsField.class);
    radioButtons.setFormatter(BooleanFormatter.getInstance());
    radioButtons.setOptions(Arrays.asList(Boolean.TRUE, Boolean.FALSE));
    return radioButtons;
  }
}
