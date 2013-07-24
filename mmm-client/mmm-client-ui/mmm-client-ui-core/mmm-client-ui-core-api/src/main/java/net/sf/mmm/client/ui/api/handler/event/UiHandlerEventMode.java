/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.event;

import net.sf.mmm.client.ui.api.common.UiMode;
import net.sf.mmm.client.ui.api.event.EventType;
import net.sf.mmm.client.ui.api.event.UiEvent;
import net.sf.mmm.client.ui.api.event.UiEventMode;

/**
 * This is the {@link UiHandlerEvent} for the action {@link #onModeChange(UiEventMode, UiMode)}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
// TODO hohwille We need Java8 support for GWT!
// public interface UiHandlerEventMode extends UiHandlerEvent {
public abstract class UiHandlerEventMode implements UiHandlerEvent {

  /**
   * {@inheritDoc}
   */
  @Override
  public void onEvent(UiEvent event) {

    if (event.getType() == EventType.MODE) {
      UiEventMode modeEvent = (UiEventMode) event;
      onModeChange(modeEvent, modeEvent.getSource().getMode());
    }
  }

  /**
   * This method is invoked if an {@link net.sf.mmm.client.ui.api.feature.UiFeatureMode object} has
   * {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteMode#setMode(UiMode) changed} its {@link UiMode}.
   * 
   * @param event is the {@link UiEventMode mode change event}.
   * @param newMode is the new {@link UiMode}.
   */
  public abstract void onModeChange(UiEventMode event, UiMode newMode);

}
