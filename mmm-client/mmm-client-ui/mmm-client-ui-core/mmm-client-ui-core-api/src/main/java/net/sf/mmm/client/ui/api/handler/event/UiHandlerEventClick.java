/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.event;

import net.sf.mmm.client.ui.api.feature.UiFeatureClick;

/**
 * This is the {@link UiHandlerEvent} for the action {@link #onClick(UiFeatureClick, boolean)}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerEventClick extends UiHandlerEvent {

  /**
   * This method is invoked if an UI object (a button, menu-item, etc.) has been clicked.
   * 
   * @param source is the object hat has been clicked.
   * @param programmatic - <code>true</code> if the
   *        {@link net.sf.mmm.client.ui.api.feature.UiFeatureClick#click() click was triggered by the
   *        program}, <code>false</code> if the click was performed by the end-user.
   */
  void onClick(UiFeatureClick source, boolean programmatic);

}
