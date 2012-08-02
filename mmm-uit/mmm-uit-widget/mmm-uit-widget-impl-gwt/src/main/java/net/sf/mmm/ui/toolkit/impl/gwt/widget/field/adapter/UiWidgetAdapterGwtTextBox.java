/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field.adapter;

import net.sf.mmm.ui.toolkit.base.widget.field.adapter.UiWidgetAdapterTextField;

import com.google.gwt.user.client.ui.TextBox;

/**
 * This is the implementation of {@link net.sf.mmm.ui.toolkit.base.widget.field.adapter.UiWidgetAdapterField} using
 * GWT based on {@link TextBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the changed value - typically {@link String}.
 */
public class UiWidgetAdapterGwtTextBox<VALUE> extends UiWidgetAdapterGwtTextBoxBase<TextBox, VALUE> implements
    UiWidgetAdapterTextField<TextBox, VALUE> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtTextBox() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TextBox createWidget() {

    return new TextBox();
  }
}
