/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter;

import net.sf.mmm.ui.toolkit.api.feature.UiFeatureValue;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventValueChange;
import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterField;
import net.sf.mmm.ui.toolkit.impl.gwt.handler.event.ChangeEventAdapterGwt;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.user.client.ui.FocusWidget;

/**
 * This is the implementation of {@link UiWidgetAdapterField} using GWT based on {@link FocusWidget}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 * @param <VALUE> is the generic type of the changed value.
 * @param <ADAPTER_VALUE> is the generic type of the {@link #getValue() value} of the adapted
 *        {@link #getWidget() widget}.
 */
public abstract class UiWidgetAdapterGwtFocusWidgetFieldBase<WIDGET extends FocusWidget, VALUE, ADAPTER_VALUE> extends
    UiWidgetAdapterGwtFocusWidget<WIDGET> implements UiWidgetAdapterField<WIDGET, VALUE, ADAPTER_VALUE> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtFocusWidgetFieldBase() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setChangeEventSender(final UiFeatureValue<VALUE> source,
      final UiHandlerEventValueChange<VALUE> sender) {

    ChangeEventAdapterGwt<VALUE> handler = new ChangeEventAdapterGwt<VALUE>(source, sender);
    getWidget().addDomHandler(handler, ChangeEvent.getType());
  }

}
