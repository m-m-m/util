/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field.adapter;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterField;
import net.sf.mmm.client.ui.impl.test.widget.adapter.UiWidgetAdapterTest;

/**
 * This is the implementation of {@link UiWidgetAdapterField} for testing without a native toolkit.
 * 
 * @param <VALUE>
 * @param <ADAPTER_VALUE>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterTestField<VALUE, ADAPTER_VALUE> extends UiWidgetAdapterTest implements
    UiWidgetAdapterField<VALUE, ADAPTER_VALUE> {

  /** @see #getValue() */
  private ADAPTER_VALUE value;

  /**
   * The constructor.
   */
  public UiWidgetAdapterTestField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ADAPTER_VALUE getValue() {

    verifyNotDisposed();
    return this.value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(ADAPTER_VALUE value) {

    verifyNotDisposed();
    this.value = value;
  }

}
