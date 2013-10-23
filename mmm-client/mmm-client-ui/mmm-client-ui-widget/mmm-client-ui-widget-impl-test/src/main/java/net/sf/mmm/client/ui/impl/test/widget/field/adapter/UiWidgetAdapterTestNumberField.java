/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field.adapter;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterNumberField;

/**
 * This is the implementation of {@link UiWidgetAdapterNumberField} for testing without a native toolkit.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterTestNumberField<VALUE extends Number> extends
    UiWidgetAdapterTestField<VALUE, VALUE> implements UiWidgetAdapterNumberField<VALUE> {

  /** @see #getMinimumValue() */
  private VALUE min;

  /** @see #getMaximumValue() */
  private VALUE max;

  /**
   * The constructor.
   */
  public UiWidgetAdapterTestNumberField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getMinimumValue() {

    return this.min;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMinimumValue(VALUE minimum) {

    this.min = minimum;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getMaximumValue() {

    return this.max;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumValue(VALUE maximum) {

    this.max = maximum;
  }

}
