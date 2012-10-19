/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import com.google.gwt.user.client.ui.ValueBoxBase;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterField}
 * using GWT based on {@link ValueBoxBase}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 * @param <VALUE> is the generic type of the changed value.
 * @param <ADAPTER_VALUE> is the generic type of the {@link #getValue() value} of the adapted
 *        {@link #getWidget() widget}.
 */
public abstract class UiWidgetAdapterGwtFieldValueBoxBase<WIDGET extends ValueBoxBase<ADAPTER_VALUE>, VALUE, ADAPTER_VALUE>
    extends UiWidgetAdapterGwtFieldFocusWidget<WIDGET, VALUE, ADAPTER_VALUE> {

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
  protected ValueBoxBase<?> getWidgetAsValueBoxBase() {

    return getWidget();
  }

}
