/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field.adapter;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterField;
import net.sf.mmm.client.ui.impl.test.widget.adapter.UiWidgetAdapterTest;

/**
 * This is the implementation of {@link UiWidgetAdapterField} for testing without a native toolkit.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <ADAPTER_VALUE> is the generic type of the {@link #getValue() value} of the adapted
 *        {@link #getToplevelWidget() widget}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterTestField<VALUE, ADAPTER_VALUE> extends UiWidgetAdapterTest implements
    UiWidgetAdapterField<VALUE, ADAPTER_VALUE> {

  /** @see #getValue() */
  private ADAPTER_VALUE value;

  /** @see #getValueAsString() */
  private String valueAsString;

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
    if (this.value != null) {
      return this.value;
    }
    if (this.valueAsString != null) {
      return convertValueFromString(this.valueAsString);
    }
    return null;
  }

  /**
   * @param stringValue is the value as {@link String}.
   * @return the value as {@literal <ADAPTER_VALUE>}.
   */
  protected abstract ADAPTER_VALUE convertValueFromString(String stringValue);

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(ADAPTER_VALUE value) {

    verifyNotDisposed();
    this.value = value;
    this.valueAsString = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValueAsString() {

    verifyNotDisposed();
    if (this.valueAsString != null) {
      return this.valueAsString;
    }
    if (this.value == null) {
      return "";
    } else {
      return this.value.toString();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValueAsString(String valueAsString) {

    verifyNotDisposed();
    this.valueAsString = valueAsString;
    this.value = null;
  }

}
