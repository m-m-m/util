/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import com.google.gwt.user.client.ui.TextBox;

/**
 * This is the implementation of {@link net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetInputField} using GWT
 * based on {@link TextBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class UiWidgetInputFieldGwtTextBox<WIDGET extends TextBox> extends
    UiWidgetInputFieldGwt<String, WIDGET> {

  /**
   * The constructor.
   */
  public UiWidgetInputFieldGwtTextBox() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetMaximumTextLength(int length) {

    getWidget().setMaxLength(length);
  }

}
