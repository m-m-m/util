/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field.adapter;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterIntegerRangeField;

/**
 * This is the implementation of {@link UiWidgetAdapterIntegerRangeField} for testing without a native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterTestIntegerRangeField extends UiWidgetAdapterTestIntegerField implements
    UiWidgetAdapterIntegerRangeField {

  /**
   * The constructor.
   */
  public UiWidgetAdapterTestIntegerRangeField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumValue(Integer maximum) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMinimumValue(Integer minimum) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Integer getMinimumValue() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Integer getMaximumValue() {

    // TODO Auto-generated method stub
    return null;
  }

}
