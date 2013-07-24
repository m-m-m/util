/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.event;

import net.sf.mmm.client.ui.api.event.EventType;
import net.sf.mmm.client.ui.api.event.UiEvent;
import net.sf.mmm.client.ui.api.event.UiEventClick;

/**
 * This is the {@link UiHandlerEvent} for the action {@link #onClick(UiEventClick)}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
// TODO hohwille We need Java8 support for GWT!
// public interface UiHandlerEventClick extends UiHandlerEvent {
public abstract class UiHandlerEventClick implements UiHandlerEvent {

  /**
   * {@inheritDoc}
   */
  @Override
  public void onEvent(UiEvent event) {

    if (event.getType() == EventType.CLICK) {
      onClick((UiEventClick) event);
    }
  }

  /**
   * This method is invoked if an UI object (a button, menu-item, etc.) has been clicked.
   * 
   * @param event is the {@link UiEventClick click event}.
   */
  public abstract void onClick(UiEventClick event);

}
