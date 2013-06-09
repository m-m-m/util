/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetDoubleField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterDoubleField;

/**
 * This is the abstact base implementation of {@link UiWidgetDoubleField}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetDoubleField<ADAPTER extends UiWidgetAdapterDoubleField> extends
    AbstractUiWidgetTextualInputField<ADAPTER, Double, Double> implements UiWidgetDoubleField {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetDoubleField(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<Double> getValueClass() {

    return Double.class;
  }
}
