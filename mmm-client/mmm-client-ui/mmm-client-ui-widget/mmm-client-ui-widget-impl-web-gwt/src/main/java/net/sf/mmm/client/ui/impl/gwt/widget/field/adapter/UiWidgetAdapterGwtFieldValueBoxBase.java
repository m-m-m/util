/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.ValueBoxBase;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterField}
 * using GWT based on {@link ValueBoxBase}.
 * 
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 * @param <VALUE> is the generic type of the changed value.
 * @param <ADAPTER_VALUE> is the generic type of the {@link #getValue() value} of the adapted
 *        {@link #getToplevelWidget() widget}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterGwtFieldValueBoxBase<WIDGET extends ValueBoxBase<ADAPTER_VALUE>, VALUE, ADAPTER_VALUE>
    extends UiWidgetAdapterGwtFieldFocusWidget<WIDGET, VALUE, ADAPTER_VALUE> {

  /** @see #getWidgetAsTakesValueString() */
  private ValueAdapter valueAdapter;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtFieldValueBoxBase() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TakesValue<String> getWidgetAsTakesValueString() {

    if (this.valueAdapter == null) {
      this.valueAdapter = new ValueAdapter();
    }
    return this.valueAdapter;
  }

  /**
   * Adapter from {@link ValueBoxBase} to {@link TakesValue}{@literal <String>}.
   */
  protected class ValueAdapter implements TakesValue<String> {

    /**
     * {@inheritDoc}
     */
    @Override
    public String getValue() {

      return getActiveWidget().getText();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(String value) {

      getActiveWidget().setText(value);
    }

  }

}
