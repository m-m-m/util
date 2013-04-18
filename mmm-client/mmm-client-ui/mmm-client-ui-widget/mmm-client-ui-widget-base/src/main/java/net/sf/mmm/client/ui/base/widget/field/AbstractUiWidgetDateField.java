/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetDateField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterDateField;

/**
 * This is the abstract base implementation of {@link UiWidgetDateField}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetDateField<ADAPTER extends UiWidgetAdapterDateField> extends
    AbstractUiWidgetDateBasedField<ADAPTER> implements UiWidgetDateField {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetDateField(UiContext context) {

    super(context);
  }

}
