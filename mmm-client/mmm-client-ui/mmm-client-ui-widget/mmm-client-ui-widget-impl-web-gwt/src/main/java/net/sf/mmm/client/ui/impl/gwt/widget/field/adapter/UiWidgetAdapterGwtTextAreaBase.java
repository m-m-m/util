/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterTextAreaFieldBase;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasValue;

/**
 * This is the implementation of {@link UiWidgetAdapterTextAreaFieldBase} using GWT based on
 * {@link FocusWidget} and {@link HasValue}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 */
public abstract class UiWidgetAdapterGwtTextAreaBase<WIDGET extends FocusWidget & HasValue<String> & HasChangeHandlers>
    extends UiWidgetAdapterGwtFieldFocusWidget<WIDGET, String, String> implements UiWidgetAdapterTextAreaFieldBase {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtTextAreaBase() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumTextLength(int length) {

    getToplevelWidget().getElement().setAttribute("maxlength", Integer.toString(length));
  }

}
