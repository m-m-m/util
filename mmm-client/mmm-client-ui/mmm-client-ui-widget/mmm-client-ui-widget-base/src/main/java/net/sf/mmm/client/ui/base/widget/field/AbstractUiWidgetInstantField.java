/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field;

import java.time.Instant;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetInstantField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterInstantField;

/**
 * This is the abstract base implementation of {@link UiWidgetInstantField}.
 *
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetInstantField<ADAPTER extends UiWidgetAdapterInstantField> extends
    AbstractUiWidgetTextualInputField<ADAPTER, Instant, Instant> implements UiWidgetInstantField {

  /**
   * The constructor.
   *
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetInstantField(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<Instant> getValueClass() {

    return Instant.class;
  }

}
