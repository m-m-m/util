/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.adapter;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteAccessKey;
import net.sf.mmm.client.ui.api.attribute.AttributeWriteFocused;
import net.sf.mmm.client.ui.api.feature.UiFeatureClick;
import net.sf.mmm.client.ui.api.feature.UiFeatureFocus;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventClick;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventFocus;

/**
 * This is the interface for a {@link UiWidgetAdapter} adapting
 * {@link net.sf.mmm.client.ui.api.widget.UiWidget} with {@link #setFocused()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetAdapterActive extends UiWidgetAdapter, AttributeWriteFocused, AttributeWriteAccessKey {

  /**
   * This method registers the given {@link UiHandlerEventClick click handler} in the
   * {@link #getToplevelWidget() widget}. This method will be called only once.
   * 
   * @param eventSource is the {@link UiHandlerEventClick#onClick(UiFeatureClick, boolean) event source}.
   * @param eventSender is the {@link UiHandlerEventClick}.
   */
  void setClickEventSender(UiFeatureClick eventSource, UiHandlerEventClick eventSender);

  /**
   * This method registers the given {@link UiHandlerEventFocus focus handler} in the
   * {@link #getToplevelWidget() widget}. This method will be called only once.
   * 
   * @param source is the
   *        {@link UiHandlerEventFocus#onFocusChange(net.sf.mmm.client.ui.api.attribute.AttributeReadFocused, boolean, boolean)
   *        event source}.
   * @param sender is the {@link UiHandlerEventFocus}.
   */
  void setFocusEventSender(UiFeatureFocus source, UiHandlerEventFocus sender);

}
