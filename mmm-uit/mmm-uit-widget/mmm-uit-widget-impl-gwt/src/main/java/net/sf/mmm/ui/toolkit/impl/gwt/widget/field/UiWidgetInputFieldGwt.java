/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetInputField;

import com.google.gwt.user.client.ui.ValueBoxBase;

/**
 * This is the implementation of {@link UiWidgetInputField} using GWT based on {@link ValueBoxBase}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class UiWidgetInputFieldGwt<VALUE, WIDGET extends ValueBoxBase<VALUE>> extends
    UiWidgetFieldGwtValueBoxBase<VALUE, WIDGET> implements UiWidgetInputField<VALUE> {

  /** @see #getMaximumTextLength() */
  private int maximumTextLength;

  /**
   * The constructor.
   */
  public UiWidgetInputFieldGwt() {

    super();
    this.maximumTextLength = Integer.MAX_VALUE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final int getMaximumTextLength() {

    return this.maximumTextLength;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setMaximumTextLength(int length) {

    this.maximumTextLength = length;
    doSetMaximumTextLength(length);
  }

  /**
   * Has to be implemented instead of {@link #setMaximumTextLength(int)}.
   * 
   * @param length - see {@link #setMaximumTextLength(int)}.
   */
  protected abstract void doSetMaximumTextLength(int length);

}
