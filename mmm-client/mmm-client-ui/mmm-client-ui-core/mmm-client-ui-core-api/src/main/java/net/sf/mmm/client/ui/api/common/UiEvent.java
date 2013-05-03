/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

/**
 * This is the interface for an event of the UI. Such event is sent from a UI widget implementing
 * {@link net.sf.mmm.client.ui.api.feature.UiFeatureEvent} to a
 * {@link net.sf.mmm.client.ui.api.handler.event.UiHandlerEvent} via the
 * {@link net.sf.mmm.client.ui.api.handler.event.UiHandlerEvent#onEvent(net.sf.mmm.client.ui.api.feature.UiFeatureEvent, UiEvent, boolean)
 * onEvent} method.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiEvent { // extends Event {

  /**
   * This method gets the {@link EventType type} of this event.
   * 
   * @return the {@link EventType}.
   */
  EventType getType();

}
