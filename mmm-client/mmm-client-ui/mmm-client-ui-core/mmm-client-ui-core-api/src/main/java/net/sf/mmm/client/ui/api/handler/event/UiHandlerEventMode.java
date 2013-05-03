/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.event;

import net.sf.mmm.client.ui.api.attribute.AttributeReadMode;
import net.sf.mmm.client.ui.api.common.EventType;
import net.sf.mmm.client.ui.api.common.UiEvent;
import net.sf.mmm.client.ui.api.common.UiMode;
import net.sf.mmm.client.ui.api.feature.UiFeatureEvent;

/**
 * This is the {@link UiHandlerEvent} for the action {@link #onModeChange(AttributeReadMode, UiMode, boolean)
 * onModeChange}.
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
  public void onEvent(UiFeatureEvent source, UiEvent event, boolean programmatic) {

    if (event.getType() == EventType.MODE) {
      AttributeReadMode modeSource = (AttributeReadMode) source;
      onModeChange(modeSource, modeSource.getMode(), programmatic);
    }
  }

  /**
   * This method is invoked if an {@link AttributeReadMode object} has
   * {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteMode#setMode(UiMode) changed} its {@link UiMode}.
   * 
   * @param source is the object that triggered the change.
   * @param newMode is the new {@link UiMode}.
   * @param programmatic - <code>true</code> if the
   *        {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteMode#setMode(UiMode) change was triggered
   *        by the program}, <code>false</code> if performed by the end-user.
   */
  public abstract void onModeChange(AttributeReadMode source, UiMode newMode, boolean programmatic);

}
