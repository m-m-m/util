/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetIntegerField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterIntegerField;

/**
 * This is the abstract base implementation of {@link UiWidgetIntegerField}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetIntegerField<ADAPTER extends UiWidgetAdapterIntegerField> extends
    AbstractUiWidgetTextualInputField<ADAPTER, Integer, Integer> implements UiWidgetIntegerField {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetIntegerField(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<Integer> getValueClass() {

    return Integer.class;
  }

}
