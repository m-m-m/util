/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field.adapter;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterIntegerField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterIntegerSliderField;

/**
 * This is the implementation of {@link UiWidgetAdapterIntegerField} for testing without a native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterTestIntegerField extends UiWidgetAdapterTestField<Integer, Integer> implements
    UiWidgetAdapterIntegerField, UiWidgetAdapterIntegerSliderField {

  /** @see #getMinimumValue() */
  private Integer min;

  /** @see #getMaximumValue() */
  private Integer max;

  /**
   * The constructor.
   */
  public UiWidgetAdapterTestIntegerField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Integer getMinimumValue() {

    return this.min;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMinimumValue(Integer minimum) {

    this.min = minimum;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Integer getMaximumValue() {

    return this.max;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumValue(Integer maximum) {

    this.max = maximum;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Integer convertValueFromString(String stringValue) {

    return Integer.valueOf(stringValue);
  }

}
