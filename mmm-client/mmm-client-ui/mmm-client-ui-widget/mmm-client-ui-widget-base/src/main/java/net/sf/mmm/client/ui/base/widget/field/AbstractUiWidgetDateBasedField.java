/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field;

import java.util.Date;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetDateBasedField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterDateBasedField;

/**
 * This is the abstract base implementation of {@link UiWidgetDateBasedField}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <ADAPTER_VALUE> is the generic type of the {@link #getWidgetAdapter() adapter} value.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetDateBasedField<ADAPTER extends UiWidgetAdapterDateBasedField<ADAPTER_VALUE>, ADAPTER_VALUE>
    extends AbstractUiWidgetField<ADAPTER, Date, ADAPTER_VALUE> implements UiWidgetDateBasedField {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetDateBasedField(UiContext context) {

    super(context);
  }

}
