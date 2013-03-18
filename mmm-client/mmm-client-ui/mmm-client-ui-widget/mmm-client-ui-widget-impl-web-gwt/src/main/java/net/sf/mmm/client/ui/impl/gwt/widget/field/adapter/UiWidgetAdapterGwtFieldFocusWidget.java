/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HasValue;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterField}
 * using GWT based on {@link FocusWidget} and {@link HasValue}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 * @param <VALUE> is the generic type of the changed value.
 * @param <ADAPTER_VALUE> is the generic type of the {@link #getValue() value} of the adapted
 *        {@link #getToplevelWidget() widget}.
 */
public abstract class UiWidgetAdapterGwtFieldFocusWidget<WIDGET extends FocusWidget & HasValue<ADAPTER_VALUE> & HasChangeHandlers, VALUE, ADAPTER_VALUE>
    extends UiWidgetAdapterGwtFieldFocusWidgetBase<WIDGET, VALUE, ADAPTER_VALUE> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtFieldFocusWidget() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final HasValue<ADAPTER_VALUE> getWidgetAsHasValue() {

    return getActiveWidget();
  }

}
