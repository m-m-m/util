/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.base.widget.adapter;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeWriteFocused;
import net.sf.mmm.ui.toolkit.api.feature.UiFeatureFocus;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventFocus;

/**
 * This is the interface for a {@link UiWidgetAdapter} adapting
 * {@link net.sf.mmm.ui.toolkit.api.widget.UiWidget} with {@link #setFocused(boolean)}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public interface UiWidgetAdapterWithFocus<WIDGET> extends UiWidgetAdapter<WIDGET>, AttributeWriteFocused {

  /**
   * This method registers the given {@link UiHandlerEventFocus focus handler} in the {@link #getWidget()
   * widget}. This method will be called only once.
   * 
   * @param source is the {@link UiHandlerEventFocus#onFocusChange(UiFeatureFocus, boolean, boolean)
   *        event source}.
   * @param sender is the {@link UiHandlerEventFocus}.
   */
  void setFocusEventSender(UiFeatureFocus source, UiHandlerEventFocus sender);

}
