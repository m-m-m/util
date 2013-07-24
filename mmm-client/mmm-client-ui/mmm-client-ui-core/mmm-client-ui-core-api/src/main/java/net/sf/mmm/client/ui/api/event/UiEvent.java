/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.event;

import net.sf.mmm.client.ui.api.feature.UiFeature;

/**
 * This is the interface for an event of the UI. Such event is sent from a UI object (a widget) implementing
 * {@link net.sf.mmm.client.ui.api.feature.UiFeature} to a
 * {@link net.sf.mmm.client.ui.api.handler.event.UiHandlerEvent} via the
 * {@link net.sf.mmm.client.ui.api.handler.event.UiHandlerEvent#onEvent(UiEvent) onEvent} method.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiEvent { // extends Event {

  /**
   * @return the source object (widget) that sent this event.
   */
  UiFeature getSource();

  /**
   * This method gets the {@link EventType type} of this event.
   * 
   * @return the {@link EventType}.
   */
  EventType getType();

  /**
   * @return - <code>true</code> if the {@link UiEvent} was triggered by the program (e.g. via
   *         {@link net.sf.mmm.client.ui.api.feature.UiFeatureClick#click()}), <code>false</code> if performed
   *         by the end-user via keyboard, touch, or mouse interaction.
   */
  boolean isProgrammatic();

}
