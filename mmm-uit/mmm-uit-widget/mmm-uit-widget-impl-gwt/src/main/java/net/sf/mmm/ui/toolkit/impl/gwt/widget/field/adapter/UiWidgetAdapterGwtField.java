/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field.adapter;

import net.sf.mmm.ui.toolkit.api.feature.UiFeatureValue;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventValueChange;
import net.sf.mmm.ui.toolkit.base.widget.field.adapter.UiWidgetAdapterField;
import net.sf.mmm.ui.toolkit.impl.gwt.handler.event.ChangeEventAdapterGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter.UiWidgetAdapterGwtWidgetWithFocus;

import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetAdapterField} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 * @param <VALUE> is the generic type of the changed value.
 * @param <ADAPTER_VALUE> is the generic type of the {@link #getValue() value} of the adapted
 *        {@link #getWidget() widget}.
 */
public abstract class UiWidgetAdapterGwtField<WIDGET extends Widget, VALUE, ADAPTER_VALUE> extends
    UiWidgetAdapterGwtWidgetWithFocus<WIDGET> implements UiWidgetAdapterField<WIDGET, VALUE, ADAPTER_VALUE> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtField() {

    super();
  }

  /**
   * @return the {@link #getWidget() widget} as {@link HasChangeHandlers} or <code>null</code> if NOT
   *         supported.
   */
  protected abstract HasChangeHandlers getWidgetAsHasChangeHandlers();

  /**
   * @return the {@link #getWidget() widget} as {@link HasChangeHandlers} or <code>null</code> if NOT
   *         supported.
   */
  protected abstract HasValue<ADAPTER_VALUE> getWidgetAsHasValue();

  /**
   * {@inheritDoc}
   */
  @Override
  public void setChangeEventSender(final UiFeatureValue<VALUE> source, final UiHandlerEventValueChange<VALUE> sender) {

    HasChangeHandlers widget = getWidgetAsHasChangeHandlers();
    if (widget != null) {
      ChangeEventAdapterGwt<VALUE> handler = new ChangeEventAdapterGwt<VALUE>(source, sender);
      widget.addChangeHandler(handler);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ADAPTER_VALUE getValue() {

    return getWidgetAsHasValue().getValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(ADAPTER_VALUE value) {

    getWidgetAsHasValue().setValue(value, false);
  }

}
