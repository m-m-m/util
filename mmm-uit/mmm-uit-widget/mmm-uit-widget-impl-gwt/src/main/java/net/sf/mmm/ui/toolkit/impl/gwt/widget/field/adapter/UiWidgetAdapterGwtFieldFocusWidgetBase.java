/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field.adapter;

import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Focusable;

/**
 * This is the implementation of {@link net.sf.mmm.ui.toolkit.base.widget.field.adapter.UiWidgetAdapterField} using
 * GWT based on {@link FocusWidget} and {@link HasChangeHandlers}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 * @param <VALUE> is the generic type of the changed value.
 * @param <ADAPTER_VALUE> is the generic type of the {@link #getValue() value} of the adapted
 *        {@link #getWidget() widget}.
 */
public abstract class UiWidgetAdapterGwtFieldFocusWidgetBase<WIDGET extends FocusWidget & HasChangeHandlers, VALUE, ADAPTER_VALUE>
    extends UiWidgetAdapterGwtField<WIDGET, VALUE, ADAPTER_VALUE> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtFieldFocusWidgetBase() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setEnabled(boolean enabled) {

    getWidget().setEnabled(enabled);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final Focusable getWidgetAsFocusable() {

    return getWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final HasAllFocusHandlers getWidgetAsHasAllFocusHandlers() {

    return getWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final HasKeyPressHandlers getWidgetAsKeyPressHandlers() {

    return getWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final HasChangeHandlers getWidgetAsHasChangeHandlers() {

    return getWidget();
  }

}
