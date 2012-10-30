/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.field.adapter;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteValidationFailure;
import net.sf.mmm.client.ui.api.feature.UiFeatureValue;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventValueChange;
import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterActive;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteValue;

/**
 * This is the interface for a {@link net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter} adapting
 * {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 * @param <VALUE> is the generic type of the changed value.
 * @param <ADAPTER_VALUE> is the generic type of the {@link #getValue() value} of the adapted
 *        {@link #getWidget() widget}.
 */
public interface UiWidgetAdapterField<WIDGET, VALUE, ADAPTER_VALUE> extends UiWidgetAdapterActive<WIDGET>,
    AttributeWriteValue<ADAPTER_VALUE>, AttributeWriteValidationFailure {

  /**
   * This method registers the given {@link UiHandlerEventValueChange value change handler} in the
   * {@link #getWidget() widget}. This method will be called only once.
   * 
   * @param source is the
   *        {@link UiHandlerEventValueChange#onValueChange(net.sf.mmm.util.lang.api.attribute.AttributeReadValue, boolean)
   *        event source}.
   * @param sender is the {@link UiHandlerEventValueChange}.
   */
  void setChangeEventSender(UiFeatureValue<VALUE> source, UiHandlerEventValueChange<VALUE> sender);

}
