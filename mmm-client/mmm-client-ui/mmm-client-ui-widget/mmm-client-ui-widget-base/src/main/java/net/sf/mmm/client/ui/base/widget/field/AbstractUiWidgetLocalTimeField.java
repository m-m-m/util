/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field;

import java.time.LocalTime;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetLocalTimeField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterLocalTimeField;

//import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterTimeField;

/**
 * This is the abstract base implementation of {@link UiWidgetLocalTimeField}.
 *
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetLocalTimeField<ADAPTER extends UiWidgetAdapterLocalTimeField> extends
    AbstractUiWidgetTextualInputField<ADAPTER, LocalTime, LocalTime> implements UiWidgetLocalTimeField {

  /**
   * The constructor.
   *
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetLocalTimeField(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<LocalTime> getValueClass() {

    return LocalTime.class;
  }

}
