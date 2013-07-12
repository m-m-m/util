/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field.adapter;

import java.util.List;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterComboBoxField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterOptionsField;

/**
 * This is the implementation of {@link UiWidgetAdapterOptionsField} for testing without a native toolkit.
 * 
 * @param <VALUE> is the generic type of the widget value.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterTestOptionsField<VALUE> extends UiWidgetAdapterTestField<VALUE, String> implements
    UiWidgetAdapterComboBoxField<VALUE> {

  /** @see #getOptions() */
  private List<String> options;

  /**
   * The constructor.
   */
  public UiWidgetAdapterTestOptionsField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getOptions() {

    verifyNotDisposed();
    return this.options;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setOptions(List<String> options) {

    verifyNotDisposed();
    this.options = options;
  }

}
