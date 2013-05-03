/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.event;

import net.sf.mmm.client.ui.api.common.EventType;
import net.sf.mmm.client.ui.api.common.UiEvent;
import net.sf.mmm.client.ui.api.feature.UiFeatureClick;
import net.sf.mmm.client.ui.api.feature.UiFeatureEvent;

/**
 * This is the {@link UiHandlerEvent} for the action {@link #onClick(UiFeatureClick, boolean)}.
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
  public void onEvent(UiFeatureEvent source, UiEvent event, boolean programmatic) {

    if (event.getType() == EventType.CLICK) {
      onClick((UiFeatureClick) source, programmatic);
    }
  }

  /**
   * This method is invoked if an UI object (a button, menu-item, etc.) has been clicked.
   * 
   * @param source is the object hat has been clicked.
   * @param programmatic - <code>true</code> if the
   *        {@link net.sf.mmm.client.ui.api.feature.UiFeatureClick#click() click was triggered by the program}
   *        , <code>false</code> if the click was performed by the end-user.
   */
  public abstract void onClick(UiFeatureClick source, boolean programmatic);

}
