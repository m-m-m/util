/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.field;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetInputField;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterInputField;

/**
 * This is the abstract base implementation of {@link UiWidgetInputField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <ADAPTER_VALUE> is the generic type of the {@link #getWidgetAdapter() adapter} value.
 */
public abstract class AbstractUiWidgetInputField<ADAPTER extends UiWidgetAdapterInputField<?, VALUE, ADAPTER_VALUE>, VALUE, ADAPTER_VALUE>
    extends AbstractUiWidgetField<ADAPTER, VALUE, ADAPTER_VALUE> implements UiWidgetInputField<VALUE> {

  /** @see #getMaximumTextLength() */
  private int maximumTextLength;

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public AbstractUiWidgetInputField(AbstractUiWidgetFactory<?> factory) {

    super(factory);
    this.maximumTextLength = Integer.MAX_VALUE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    if (this.maximumTextLength != Integer.MAX_VALUE) {
      adapter.setMaximumTextLength(this.maximumTextLength);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getMaximumTextLength() {

    return this.maximumTextLength;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumTextLength(int length) {

    this.maximumTextLength = length;
    if (hasWidgetAdapter()) {
      getWidgetAdapter().setMaximumTextLength(length);
    }
  }

}
