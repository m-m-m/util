/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import com.google.gwt.user.client.ui.ValueBox;

/**
 * This is the implementation of {@link net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetInputField} using GWT
 * based on {@link ValueBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class UiWidgetInputFieldGwtValueBox<VALUE, WIDGET extends ValueBox<VALUE>> extends
    UiWidgetInputFieldGwt<VALUE, WIDGET> {

  /**
   * The constructor.
   * 
   * @param widget is the {@link #getWidget() widget}.
   */
  public UiWidgetInputFieldGwtValueBox(WIDGET widget) {

    super(widget);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetMaximumTextLength(int length) {

    getWidget().setMaxLength(length);
  }

}
