/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.event;

import net.sf.mmm.client.ui.api.event.EventType;
import net.sf.mmm.client.ui.api.event.UiEvent;
import net.sf.mmm.client.ui.api.event.UiEventOpen;

/**
 * This is the {@link UiHandlerEvent} for the action {@link #onOpen(UiEventOpen)}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
// TODO hohwille We need Java8 support for GWT!
// public interface UiHandlerEventOpen extends UiHandlerEvent {
public abstract class UiHandlerEventOpen implements UiHandlerEvent {

  /**
   * {@inheritDoc}
   */
  @Override
  public void onEvent(UiEvent event) {

    if (event.getType() == EventType.OPEN) {
      onOpen((UiEventOpen) event);
    }
  }

  /**
   * This method is invoked if an {@link net.sf.mmm.client.ui.api.feature.UiFeatureOpenClose open-/closeable
   * object} is {@link net.sf.mmm.client.ui.api.feature.UiFeatureOpenClose#open() opened}.
   *
   * @param event is the {@link UiEventOpen open event}.
   */
  public abstract void onOpen(UiEventOpen event);

}
