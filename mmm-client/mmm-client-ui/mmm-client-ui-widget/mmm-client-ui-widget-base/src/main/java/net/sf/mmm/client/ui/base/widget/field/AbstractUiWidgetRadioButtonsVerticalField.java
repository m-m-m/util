/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetRadioButtonsVerticalField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterRadioButtonsField;

/**
 * This is the abstract base implementation of {@link UiWidgetRadioButtonsVerticalField}.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetRadioButtonsVerticalField<ADAPTER extends UiWidgetAdapterRadioButtonsField<VALUE>, VALUE>
    extends AbstractUiWidgetOptionsField<ADAPTER, VALUE> implements UiWidgetRadioButtonsVerticalField<VALUE> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetRadioButtonsVerticalField(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
  }

}
