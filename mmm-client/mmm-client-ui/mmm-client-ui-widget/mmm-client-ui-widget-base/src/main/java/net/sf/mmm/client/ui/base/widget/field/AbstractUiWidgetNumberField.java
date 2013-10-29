/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetRangeField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterNumberField;

/**
 * This is the abstract base implementation of {@link UiWidgetRangeField}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetNumberField<ADAPTER extends UiWidgetAdapterNumberField<VALUE>, VALUE extends Number>
    extends AbstractUiWidgetTextualInputField<ADAPTER, VALUE, VALUE> implements UiWidgetRangeField<VALUE> {

  /** @see #getMinimumValue() */
  private VALUE minimumValue;

  /** @see #getMaximumValue() */
  private VALUE maximumValue;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetNumberField(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.minimumValue != null) {
      adapter.setMinimumValue(this.minimumValue);
    }
    if (this.maximumValue != null) {
      adapter.setMaximumValue(this.maximumValue);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getMinimumValue() {

    return this.minimumValue;
  }

  /**
   * {@inheritDoc}
   */
  public void setMinimumValue(VALUE minimum) {

    if (hasWidgetAdapter()) {
      getWidgetAdapter().setMinimumValue(minimum);
    }
    this.minimumValue = minimum;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getMaximumValue() {

    return this.maximumValue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumValue(VALUE maximum) {

    if (hasWidgetAdapter()) {
      getWidgetAdapter().setMaximumValue(maximum);
    }
    this.maximumValue = maximum;
  }

}
