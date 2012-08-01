/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter;

import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterTextAreaBase;
import net.sf.mmm.util.nls.api.NlsUnsupportedOperationException;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasValue;

/**
 * This is the implementation of {@link UiWidgetAdapterTextAreaBase} using GWT based on {@link FocusWidget}
 * and {@link HasValue}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 * @param <VALUE> is the generic type of the changed value - typically {@link String}.
 */
public abstract class UiWidgetAdapterGwtTextAreaBase<WIDGET extends FocusWidget & HasValue<String> & HasChangeHandlers, VALUE>
    extends UiWidgetAdapterGwtFieldFocusWidget<WIDGET, VALUE, String> implements
    UiWidgetAdapterTextAreaBase<WIDGET, VALUE> {

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
  public int getMaximumTextLength() {

    throw new NlsUnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumTextLength(int length) {

    getWidget().getElement().setAttribute("maxlength", Integer.toString(length));
  }

}
