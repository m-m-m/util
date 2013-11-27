/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetIntegerSliderField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterIntegerSliderField;

/**
 * This is the abstract base implementation of {@link UiWidgetIntegerSliderField}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetIntegerSliderField<ADAPTER extends UiWidgetAdapterIntegerSliderField> extends
    AbstractUiWidgetNumberField<ADAPTER, Integer> implements UiWidgetIntegerSliderField {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetIntegerSliderField(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<Integer> getValueClass() {

    return Integer.class;
  }

}
