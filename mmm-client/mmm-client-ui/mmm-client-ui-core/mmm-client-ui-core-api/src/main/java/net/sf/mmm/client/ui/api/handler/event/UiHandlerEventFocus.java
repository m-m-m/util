/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.event;

import net.sf.mmm.client.ui.api.event.EventType;
import net.sf.mmm.client.ui.api.event.UiEvent;
import net.sf.mmm.client.ui.api.event.UiEventFocusGain;
import net.sf.mmm.client.ui.api.event.UiEventFocusLoss;

/**
 * This is the {@link UiHandlerEvent} for the actions {@link #onFocusGain(UiEventFocusGain)} and
 * {@link #onFocusGain(UiEventFocusGain)}. There will always be two events:
 * <ul>
 * <li>The object that previously had the focus will receive an event that its focus has been lost.</li>
 * <li>The object that has been focused will receive and event that its has gained the focus.</li>
 * </ul>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
// TODO hohwille We need Java8 support for GWT!
// public interface UiHandlerEventFocus extends UiHandlerEvent {
public abstract class UiHandlerEventFocus implements UiHandlerEvent {

  /**
   * {@inheritDoc}
   */
  @Override
  public void onEvent(UiEvent event) {

    if (event.getType() == EventType.FOCUS_GAIN) {
      onFocusGain((UiEventFocusGain) event);
    } else if (event.getType() == EventType.FOCUS_LOSS) {
      onFocusLoss((UiEventFocusLoss) event);
    }
  }

  /**
   * This method is invoked if an UI object (a text input, etc.) has gained focus.
   * 
   * @param event is the {@link UiEventFocusGain focus gain event}.
   */
  public abstract void onFocusGain(UiEventFocusGain event);

  /**
   * This method is invoked if an UI object (a text input, etc.) has lost focus (blur).
   * 
   * @param event is the {@link UiEventFocusLoss focus gain event}.
   */
  public abstract void onFocusLoss(UiEventFocusLoss event);

}
