/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterTextAreaField;

import com.google.gwt.user.client.ui.TextArea;

/**
 * This is the implementation of {@link UiWidgetAdapterTextAreaField} using GWT based on {@link TextArea}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtTextAreaField extends UiWidgetAdapterGwtTextAreaBase<TextArea> implements
    UiWidgetAdapterTextAreaField {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtTextAreaField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TextArea createActiveWidget() {

    return new TextArea();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeightInRows(int rows) {

    getActiveWidget().setVisibleLines(rows);
  }

}
